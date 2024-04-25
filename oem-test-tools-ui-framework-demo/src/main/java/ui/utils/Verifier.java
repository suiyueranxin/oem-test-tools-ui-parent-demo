package ui.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.google.common.base.Verify;

import log.Log;
import runtime.SeleniumWrapper;
public class Verifier {
	private static void verify(boolean pass, String msg) {
		//TODO Assertion and add it in allure report
		Log.logDebug("verify: " + String.valueOf(pass) + " details: " + msg);
		Assert.assertEquals(pass, true);
	} 
	
	private static void verifyException(String check) {
		Verifier.verify(false, String.format(VerifyMsgResource.FailByException, check));
	}
	
	private static void verifyPass(String check, String xpathOrBkName) {
		Verifier.verify(true, String.format(VerifyMsgResource.Pass, check, xpathOrBkName));
	}
	
	private static void verifyFail(String check, String xpath, String expected, String actual) {
		Verifier.verify(false, String.format(VerifyMsgResource.Fail, check, xpath, expected, actual));
	}
	
	private static void verifyFail(String check) {
		Verifier.verify(false, String.format(VerifyMsgResource.Fail2, check));
	}
	
	public static void imageCompare(String xpath, String bkName) {
		File actual = SeleniumWrapper.captureElement(xpath);
		boolean match = false;	
		try {
			InputStream expected = Verify.class.getClass().getResourceAsStream("/bk/" + bkName);
			BufferedImage img1;
			img1 = ImageIO.read(actual);
		
			DataBuffer data1 = img1.getData().getDataBuffer();
			BufferedImage img2 = ImageIO.read(expected);
			DataBuffer data2 = img2.getData().getDataBuffer();
			
			match = true;
			if (data1.getSize() == data1.getSize()) {
				int size = data2.getSize();
				for (int i=0; i<size; i++) {
					if (data1.getElem(i) != data2.getElem(i)) {
						match = false;
						break;
					}
				}
			} else {
				match = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Verifier.verifyException(VerifyMsgResource.ImageCheck);
		}
		
		if (match) {
			Verifier.verifyPass(VerifyMsgResource.ImageCheck, bkName);
		} else {
			Verifier.verifyFail(VerifyMsgResource.ImageCheck);
		}
	}
	
	public static void visibleCompare(String xpath, boolean expected) {
		boolean match = false;
		if (expected) {
			match = SeleniumWrapper.hasElement(xpath); 
		} else {
			match = SeleniumWrapper.notHasElement(xpath);
		}
		
		if (match) {
			Verifier.verifyPass(VerifyMsgResource.VisibleCheck, xpath);
		} else {
			Verifier.verifyFail(VerifyMsgResource.VisibleCheck, xpath, String.valueOf(expected), String.valueOf(!expected));
		}
	}
	
	public static void textCompare(String xpath, String expected) {
		boolean match = false;
		String actual = SeleniumWrapper.findElement(xpath).getText();
		match = expected.equals(actual);
		
		if (match) {
			Verifier.verifyPass(VerifyMsgResource.TextCheck, xpath);
		} else {
			Verifier.verifyFail(VerifyMsgResource.TextCheck, xpath, expected, actual);
		}
		
	}
	
	public static void countCompare(String xpath, int expected) {
		boolean match = false;
		int actual = SeleniumWrapper.findElements(xpath).size();
		match = expected == actual;
		
		if (match) {
			Verifier.verifyPass(VerifyMsgResource.CountCheck, xpath);
		} else {
			Verifier.verifyFail(VerifyMsgResource.CountCheck, xpath, String.valueOf(expected), String.valueOf(actual));
		}
		
	}
	
	public static void textListCompare(String xpath, List<String> expected) {
		boolean match = false;

		List<WebElement> actualEles = SeleniumWrapper.findElements(xpath);
		List<String> actual = new ArrayList<>();
		for (int index=0; index<actualEles.size(); index++) {
			actual.add(actualEles.get(index).getText());
		}
					
		if (expected.size() == actual.size()) {
			match = true;
			for (int index=0; index<actual.size(); index++) {		
				if (!expected.get(index).equals(actual.get(index))) {
					match = false;
					break;
				}
			}		
		}
				
		if (match) {
			Verifier.verifyPass(VerifyMsgResource.TextsCheck, xpath);
		} else {
			Verifier.verifyFail(VerifyMsgResource.TextsCheck, xpath, expected.toString(), actual.toString());
		}
	}
}
