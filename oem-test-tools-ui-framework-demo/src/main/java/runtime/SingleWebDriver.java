package runtime;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import config.GlobalProperty;
import log.Log;


public class SingleWebDriver {
	private static WebDriver instance = null;
	
	private static void initWebDriver(String browser) {
		if ("chrome".equalsIgnoreCase(browser)) {
			WebDriverManager.chromedriver().setup();
			instance = new ChromeDriver();				
		} else if ("firefox".equalsIgnoreCase(browser)) {
			WebDriverManager.firefoxdriver().setup();
			instance =  new FirefoxDriver();
		} else if ("edge".equalsIgnoreCase(browser)) {
			WebDriverManager.edgedriver().setup();
			instance = new EdgeDriver();
		} else {
			
		}
		instance.manage().window().maximize();
	}
	
	// read browser from global properties
	public static synchronized WebDriver get() {
		if (null == instance) {
			Log.logDebug("Get web driver acccording to global properties");
			String browser = GlobalProperty.get().getProperty("browser");
			SingleWebDriver.initWebDriver(browser);
		}
		return instance;
	}
	
	// read from testng configuration
	public static synchronized WebDriver get(String browser) {		
		if (null == instance) {
			Log.logDebug("Get web driver according to testng settings");
			SingleWebDriver.initWebDriver(browser);
		}
		return instance;
	}
	
	public static void reset() {
		instance = null;
	}
}
