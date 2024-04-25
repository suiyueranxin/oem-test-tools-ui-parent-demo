package ui.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;

import log.Log;
import runtime.SeleniumWrapper;
import ui.utils.DomLocator;

public class CompFactory implements ILocator {
	private JSONObject locators = null;
	private String boName = "";
	private String iframe = "";
	
	public CompFactory(String className) {
		this.boName = className;
		this.locators = this.parseLocator(className);
	}
		
	@SuppressWarnings("unchecked")
	public <T extends Comp> T createComponent(DomLocator locator) {
		String compName = "ui.component." + locator.getType();			
		try {
			Class<?> compClass = Class.forName(compName);
			Constructor<?> compGen = compClass.getConstructor(String.class);
			return (T) compGen.newInstance(locator.getPath());
		} catch (ClassNotFoundException e) {
			Log.logException("Component is not defined: " + compName);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Object createComponent(String locatorName) {
		DomLocator locator = this.getLocator(locatorName);
		return this.createComponent(locator);
	}
	
	public Object createComponent(String locatorName, String arg) {
		DomLocator locator = this.getLocator(locatorName, arg);
		return this.createComponent(locator);
	}
	
	private List<String> getSuperBoNames(String boName) {
		try {
			Class<?> boClass = Class.forName(boName);
			List<String> boNames = new ArrayList<String>();
			while(null != boClass.getSuperclass() && !boClass.getSuperclass().getSimpleName().equals("Object")) {
				boNames.add(boClass.getSuperclass().getName());
				boClass = boClass.getSuperclass();
			}
			return boNames;
		} catch (ClassNotFoundException e) {
			Log.logException("Class is not defined: " + boName);
			return null;
		}		
	}
	
	private InputStream getLocatorResourceFilePath(String className) {
		String[] names = className.split("\\.");
		String fileName = names[names.length-1].toLowerCase() + ".json";
		return this.getClass().getResourceAsStream("/locator/" + fileName);
	}
	
	public JSONObject parseLocator(String className) {
		InputStream is = this.getLocatorResourceFilePath(className);
		if (null != is) {
			try {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new InputStreamReader(is));
				return (JSONObject) obj;
			}catch (FileNotFoundException e) {
				Log.logException("Cannot find the locator json file " + is);
			} catch (IOException e) {
				Log.logException("Cannot read the locator json file " + is);
			} catch (ParseException e) {
				Log.logException("Cannot parse locator json in " + is);
			}
		}
		return null;
	}
	
	public DomLocator getLocatorBySuper(String name) {
		List<String> superBoNames = this.getSuperBoNames(boName);
		for (String className : superBoNames) {
			JSONObject locators = this.parseLocator(className);
			DomLocator locator = this.getLocator(locators, name);
			if (null != locator) {
				return locator;
			}
		}	
//		throw new Exception(String.format("Cannot find locator %s in %s", name, this.boName));
		Log.logError(String.format("Cannot find locator %s in %s", name, this.boName));
		return null;
	}
	
	public DomLocator getLocator(JSONObject locators, String name) {
		if (null != locators && locators.containsKey(name)) {
			JSONObject jsonLocator = (JSONObject)locators.get(name);
			String type = (String) jsonLocator.get("type"); 
			String path = (String) jsonLocator.get("path");
			DomLocator locator = new DomLocator(name, type, path);
			if (!iframe.isEmpty()) {
				locator.formatIframePath(iframe);
			}
			return locator;
		} else {		
			return null;
		}
	}
	
	public DomLocator getLocator(String name) {		
		DomLocator locator = this.getLocator(this.locators, name);
		if (null != locator) {
			return locator;
		} else {
			return this.getLocatorBySuper(name);
		}
	}
	
	public DomLocator getLocator(String name, String args) {
		DomLocator locator = this.getLocator(name);
		locator.formatPath(args);
		return locator;
	}
	
	// TODO can be deleted when to use this list?
	public Object createComponentList(DomLocator locator) {
		List<Object> list = new ArrayList<Object>();
		List<WebElement> es = SeleniumWrapper.findElements(locator.getPath());
		for (int index=0; index<es.size(); index++) {
			DomLocator loc = new DomLocator(locator);
			locator.setPath(String.format("(%s)[%s]", loc.getPath(), index+1));
			Object comp = this.createComponent(locator);
			list.add(comp);
		}
		return list;
	}
	
	// TODO can be deleted when to use this list? 
	public Object createComponentList(String locatorName) {
		DomLocator locator = this.getLocator(locatorName);
		return this.createComponentList(locator);
	}
	
	public Comps createComponents(DomLocator locator) {
		Comps comps = new Comps(locator.getPath());
		return comps;
	}
	
	public Comps createComponents(String locatorName) {
		DomLocator locator = this.getLocator(locatorName);
		return this.createComponents(locator);
	}
	
	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	
	public String getIframe() {
		return this.iframe;
	}
}
