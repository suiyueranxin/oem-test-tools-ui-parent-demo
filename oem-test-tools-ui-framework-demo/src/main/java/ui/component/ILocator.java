package ui.component;

import org.json.simple.JSONObject;

import ui.utils.DomLocator;

public interface ILocator {
	public JSONObject parseLocator(String pageName);
	public DomLocator getLocator(String locatorName);
	public DomLocator getLocator(String locatorName, String args);
}
