package runtime;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;

import log.Log;
import runtime.dataprovider.DataFile;
import runtime.dataprovider.DataRecord;
import ui.business.workflow.Generic;
import ui.component.TestCompFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import config.GlobalProperty;

public class BaseTest {
	protected TestCompFactory compFactory = null;
	
	@Parameters({"env", "browser", "url", "wait", "logonStyle"})
	@BeforeSuite
	public void initSettings(String env,  @Optional("") String browser,  @Optional("") String url,  @Optional("100") String wait,  @Optional("2") String logonStyle) {
		if (!env.equalsIgnoreCase("jenkins")) {
			Log.logDebug("initialize");
			GlobalProperty.load("/env/" + env+".properties");
		} else {
			Log.logDebug("initialize in jenkins");
			GlobalProperty.setProperty("browser", browser);
			GlobalProperty.setProperty("url", url);
			GlobalProperty.setProperty("wait", wait);
			GlobalProperty.setProperty("logonStyle", logonStyle);
		}	
	}
	
	@BeforeClass
	public void initCompFactory() {
		// read the custom locator during test
		// It's better not use custom locator in case layer
		compFactory = new TestCompFactory(this.getClass().getName());
	}
	
	@BeforeMethod
	public void openTestPage() {
		Log.logDebug("before method");
		SeleniumWrapper.openBrowser();
	}
	
	@AfterMethod
	public void closeBrowser() {
		Log.logDebug("after method");
		Generic generic = new Generic();
		generic.logoff();
		SeleniumWrapper.closeBrowser();
	}
	
	@AfterSuite(alwaysRun=true)
	public void clearBrowser() {
		Log.logDebug("after suite");
		try {
			Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
			Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f");
			Runtime.getRuntime().exec("taskkill /im msedgedriver.exe /f");
		} catch (IOException e) {
			Log.logException("Cannot kill browser driver");
		}
	}
	
	@SuppressWarnings("unchecked")
	@DataProvider(name="excel")
	public Iterator<DataRecord> excelData(Method m) {
		DataFile df = m.getAnnotation(DataFile.class);
		List<DataRecord> result = new ArrayList<>();
		String filePath = "/data/" + df.path();
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(filePath);
			Workbook wb = WorkbookFactory.create(inputStream);		
			Sheet sheet = wb.getSheetAt(0);
			Row header = sheet.getRow(0);
			for(int row=1; row<sheet.getPhysicalNumberOfRows(); row++) {
				Row record = sheet.getRow(row);
				JSONObject jsonObj = new JSONObject();
				for(int col=0; col<record.getPhysicalNumberOfCells(); col++) {
					String key = header.getCell(col).getStringCellValue();					
					Object value = this.getCellValue(record.getCell(col));
					jsonObj.put(key, value);
				}
				DataRecord dr = new DataRecord(jsonObj);
				result.add(dr);
			}
		}catch (FileNotFoundException e) {
			Log.logException("Cannot find the excel file " + filePath);
		} catch (IOException e) {
			Log.logException("Cannot read the excel file " + filePath);
		} 
		return result.iterator();
		
	}
	
	private Object getCellValue(Cell cell) {
		Object obj = null;
		HSSFDataFormatter hSSFDataFormatter = new HSSFDataFormatter();
		String value = hSSFDataFormatter.formatCellValue(cell).trim();
		JSONParser parser = new JSONParser();
		
		try {	
			if (value.startsWith("{")) { 
				obj = (JSONObject) parser.parse(value);
			} else if (value.startsWith("[")) {
				obj = (JSONArray) parser.parse(value);
			} else {
				obj = value;
			}
		} catch (ParseException e) {
			Log.logException("Cannot parse the cell value to json " + value);
		}
		return obj;
	}
	
	@DataProvider(name="json")
	public Iterator<DataRecord> jsonData(Method m) {
		DataFile df = m.getAnnotation(DataFile.class);
		List<DataRecord> result = new ArrayList<>();
		String filePath = "/data/" + df.path();
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(filePath);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new InputStreamReader(inputStream));
			DataRecord dr = new DataRecord((JSONObject)obj);
			result.add(dr);
		}catch (FileNotFoundException e) {
			Log.logException("Cannot find the json file " + filePath);
		} catch (IOException e) {
			Log.logException("Cannot read the json file " + filePath);
		} catch (ParseException e) {
			Log.logException("Cannot parse json in " + filePath);
		}
		return result.iterator();
	}
	
}
