package runtime;

import java.io.File;
import java.util.List;
import javafx.util.Pair;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TakesScreenshot;

import config.GlobalProperty;
import io.qameta.allure.Attachment;
import log.Log;
import log.Message;

public class SeleniumWrapper {
	public static long Timeout = Long.parseLong(GlobalProperty.get().getProperty("wait")); //timeout of finding dom
	public static long Loading = 1000L; //timeout of loading new dom
	public static boolean InIframe = false;
	
	private static By getBy(String byPath) {
		if (byPath.contains("//")) {
			return By.xpath(byPath);
		} else {
			return By.cssSelector(byPath);
		}
	}
	
	// basic find function
	public static List<WebElement> findElements(String path) {
		By by = SeleniumWrapper.getBy(path);
		try {
			WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), Timeout);			
			List<WebElement> eles = (List<WebElement>) wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			return eles;
		} catch(TimeoutException e) {
			Log.logException(Message.TIMEOUT_WHEN_FIND_ELEMENT);
			return null;
		}
	}
	
	public static WebElement findElement(String path) {
		By by = SeleniumWrapper.getBy(path);
		try {
			WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), Timeout);			
			WebElement ele = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return ele;
		} catch(TimeoutException e) {
			Log.logException(String.format(Message.TIMEOUT_WHEN_FIND_ELEMENT, path));
			return null;
		}
	}
		
	// check an element exist and show
	public static boolean hasElement(String path) {
		return SeleniumWrapper.hasElement(path, Timeout);
	}
	
	// check an existed element show
	public static boolean hasElement(WebElement ele) {
		return ele.isDisplayed();
	}
	
	// check an element not existed or hide
	public static boolean notHasElement(String path) {
		return SeleniumWrapper.notHasElement(path, Timeout);
	}
	
	// check an element exist and show
	public static boolean hasElement(String path, long timeout) {
		By by = SeleniumWrapper.getBy(path);
		try {
			WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), timeout);			
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch(TimeoutException e) {
			return false;
		}
	}
	
	// check an element not existed or hide
	public static boolean notHasElement(String path, long timeout) {
		By by = SeleniumWrapper.getBy(path);
		try {
			WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), timeout);		
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			return true;
		} catch(TimeoutException e) {
			return false;
		}
	}
	
	// basic action for browser and dom element
	public static void openBrowser() {
		String url = GlobalProperty.get().getProperty("url");
		SingleWebDriver.get().get(url);
	}
	
	public static void openBrowser(String browser, String url) {
		SingleWebDriver.get(browser).get(url);
	}
	
	public static void closeBrowser() {
		SingleWebDriver.get().close();
		SingleWebDriver.get().quit();
		SingleWebDriver.reset();
	}
	
	public static void refresh() {
		SingleWebDriver.get().navigate().refresh();
	}
	
	private static boolean isClickable(String path) {
		try
	    {
	        WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), Timeout);
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
	        Log.logDebug("Element is clickable: "+path);
	        return true;
	    }
	    catch (Exception e)
	    {
	    	Log.logDebug("Element wasn't clickable: "+path);
	        return false;
	    }
	}
	
	public static void click(String path) {
		WebElement ele = SeleniumWrapper.findElement(path);
		if(ele.isDisplayed() && ele.isEnabled() && isClickable(path)) {
			Log.logDebug("click: "+path);
			ele.click();
		}else {
			Log.logDebug("sendKey: "+path);
			ele.sendKeys(Keys.ENTER);
		}		
	}
	
	public static void sendKeys(String path, CharSequence... keys) {
		Log.logDebug("sendKeys: "+keys.toString() + " to "+path);
		SeleniumWrapper.findElement(path).sendKeys(keys);
	}
	
	public static void input(String path, String text) {
		Log.logDebug("input: "+path);
		SeleniumWrapper.findElement(path).sendKeys(text);
	}
	
	public static void clearAndInput(String path, String text) {
		Log.logDebug("input: "+path);
		SeleniumWrapper.findElement(path).sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
	}
	
	public static void inputAndEnter(String path, String text) {
		SeleniumWrapper.findElement(path).sendKeys(text, Keys.ENTER);
	}
	
	//clear text
	public static void clear(String path) {
		SeleniumWrapper.findElement(path).clear();
	}
	
	public static void hover(String path) {
		Log.logDebug("hover: "+path);
		WebElement ele = SeleniumWrapper.findElement(path);
		Actions action = new Actions(SingleWebDriver.get());
		action.moveToElement(ele).perform();
	}
	
	public static void dragAndDrop(String pathFrom, String pathTo) {
		WebElement eleFrom = SeleniumWrapper.findElement(pathFrom);
		WebElement eleTo = SeleniumWrapper.findElement(pathTo);
		Actions action = new Actions(SingleWebDriver.get());
		action.dragAndDrop(eleFrom, eleTo).perform();
	}
	
	public static File captureElement(String path) {
		Log.logDebug("screenshot: "+path);
		WebElement ele = SeleniumWrapper.findElement(path);
		File screen = ele.getScreenshotAs(OutputType.FILE);
		return screen;
	}
	
	public static void waitUntilShow(String path) {
		SeleniumWrapper.waitUntilShow(path, Timeout);
	}
	
	public static void waitUntilHide(String path) {
		SeleniumWrapper.waitUntilHide(path, Timeout);
	}
	
	public static void waitUntilShow(String path, long timeout) {
		Log.logDebug("until show: " + path);
		By by = SeleniumWrapper.getBy(path);
		WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), timeout);			
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public static void waitUntilHide(String path, long timeout) {
		Log.logDebug("until hide: " + path);
		By by = SeleniumWrapper.getBy(path);
		WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), timeout);			
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public static void enterIFrame(String path) {
		Log.logDebug("enter iframe: " + path);
		WebElement iframe = SeleniumWrapper.findElement(path);
		SingleWebDriver.get().switchTo().frame(iframe); 
		InIframe = true;
	}
	
	public static void exitIFrame() {
		Log.logDebug("exit iframe");
		SingleWebDriver.get().switchTo().defaultContent();
		InIframe = false;
	}
	
	@Attachment(type="image/png")
	public static byte[] capturePage() {
		byte[] screenshot = ((TakesScreenshot) SingleWebDriver.get()).getScreenshotAs(OutputType.BYTES);	
		return screenshot;
	}
	
	public static void back() {
		SingleWebDriver.get().navigate().back();
	}
	
	public static void forward() {
		SingleWebDriver.get().navigate().forward();
	}
	
	public static void contextClick(String path) {
		Log.logDebug("context click: " + path);
		WebElement ele = SeleniumWrapper.findElement(path);
		Actions action = new Actions(SingleWebDriver.get());
		action.contextClick(ele).perform();
		SeleniumWrapper.waitForLoading();
	}
	
	public static void waitForLoading() {
		synchronized (SingleWebDriver.get()) {
			try {
				SingleWebDriver.get().wait(Loading);
			} catch (InterruptedException e) {
				Log.logException(Message.INTERUPT_WHEN_LOADING);
			}
		}
	}
	
	public static void switchTo(String urlPath) {
		String curUrl = SingleWebDriver.get().getCurrentUrl();
		if (urlPath.startsWith("http")) {
			curUrl = urlPath;
		} else if (urlPath.startsWith("#")){ //
			if (-1 != curUrl.indexOf("#")) {
				curUrl = curUrl.replace("#(\\w)*", urlPath);
			}
		} else {
			//TODO handler other type of path
		}
		SingleWebDriver.get().navigate().to(curUrl);
	}
	
	public static boolean isInIframe() {
		return InIframe;
	}
	
	public static Pair<String, String> handleIframe(String path) {
		String domPath = path;
		String iframePath = "";
		if (path.contains(";")) {
			String[] paths = path.split(";");
			iframePath = paths[0];
			domPath = paths[1];
			if (!SeleniumWrapper.isInIframe()) {
				SeleniumWrapper.enterIFrame(iframePath);
			}
		} else {
			if (SeleniumWrapper.isInIframe()) {
				SeleniumWrapper.exitIFrame();
			}
		}
		Pair<String, String> result = new Pair<>(domPath, iframePath);
		return result;
	}
	
	public static String getAttribute(String path, String attr) {
		return SeleniumWrapper.findElement(path).getAttribute(attr);
	}
	
	public static void scrollToShow(String path) {
		By by = SeleniumWrapper.getBy(path);
		WebDriverWait wait = new WebDriverWait(SingleWebDriver.get(), Timeout);		
		try {
			WebElement ele = (WebElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
			((JavascriptExecutor) SingleWebDriver.get()).executeScript("arguments[0].scrollIntoView(true);", ele);
		} catch(TimeoutException e) {
			Log.logException(String.format(Message.TIMEOUT_WHEN_FIND_ELEMENT, path));
		}
	}
}
