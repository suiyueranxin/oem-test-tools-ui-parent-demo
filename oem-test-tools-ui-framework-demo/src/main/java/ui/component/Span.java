package ui.component;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;

import runtime.SeleniumWrapper;
import ui.utils.Verifier;

public class Span extends Comp implements IClickable, ICheckText, IContextClickable {
	
	public Span(String path) {
		super(path);
	}
		
	public void click() {
		SeleniumWrapper.click(this.path);
	}
	
	public void checkText(String text) {
		Verifier.textCompare(this.path, text);
	}
	
	public void contextClick() {
		SeleniumWrapper.contextClick(this.path);
	}
	
	public void sendKeys(CharSequence... keys) {
		SeleniumWrapper.sendKeys(this.path, keys);
	}
	
}
