package ui.component;

import runtime.SeleniumWrapper;
import ui.utils.AnnotationHelper;
import ui.utils.Verifier;
import org.openqa.selenium.WebElement;

import javafx.util.Pair;
import log.Log;

public class Comp implements ICheckVisible, ICheckImage {
	private WebElement dom = null;
	
	protected String path = "";
	private String iframe = "";
	
	public Comp(String path) {
		this.init(path);
		AnnotationHelper.generatedByPathAnnotated(this);
	}
	
	private void init(String path) {
		Pair<String, String> pair = SeleniumWrapper.handleIframe(path);
		this.path = pair.getKey();
		this.iframe = pair.getValue();
	}
	
	protected WebElement getDom() {
		if (this.isExisted() && dom == null) {
			dom = SeleniumWrapper.findElement(this.path);
		}
		return dom;
	}
	
	public boolean isExisted() {
		Log.logDebug("if exists: " + path);
		return SeleniumWrapper.hasElement(this.path);
	}
	
	public boolean isNotExisted() {
		Log.logDebug("if not exists: " + path);
		return SeleniumWrapper.notHasElement(this.path);
	}
	
	public boolean isExisted(long timeout) {
		Log.logDebug("if exists: " + path);
		return SeleniumWrapper.hasElement(this.path, timeout);
	}
	
	public boolean isNotExisted(long timeout) {
		Log.logDebug("if not exists: " + path);
		return SeleniumWrapper.notHasElement(this.path, timeout);
	}
	
	public void hover() {
		SeleniumWrapper.hover(this.path);
	}
	
	public void waitUntilShow() {
		SeleniumWrapper.waitUntilShow(this.path);
	}
	
	public void waitUntilShow(long timeout) {
		SeleniumWrapper.waitUntilShow(this.path, timeout);
	}
	
	public void waitUntilHide() {
		SeleniumWrapper.waitUntilHide(this.path);
	}
	
	public void waitUntilHide(long timeout) {
		SeleniumWrapper.waitUntilHide(this.path, timeout);
	}
	
	public void checkIsVisible() {
		Verifier.visibleCompare(this.path, true);
	}
	
	public void checkIsInvisible() {
		Verifier.visibleCompare(this.path, false);
	}
	
	public void checkByImage(String bkName) {
		Verifier.imageCompare(this.path, bkName);
	}
	
	public String subDomPath(String subPath, boolean isRelative) {
		String result = subPath;
		if (isRelative) {
			result = this.path + subPath;
		}
		
		if (!this.iframe.isEmpty()) {
			result = this.iframe + ";" + result;
		}
		
		return result;
	}
	
	public void formatDomPath(Object ...params) {
		if (this.path.indexOf("%") != -1) {
			this.path = String.format(this.path, params);
		}
	}
		
	public void scrollToShow() {
		SeleniumWrapper.scrollToShow(this.path);
	}
}
