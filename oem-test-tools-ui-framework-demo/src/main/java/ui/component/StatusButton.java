package ui.component;

import org.openqa.selenium.Keys;

import runtime.SeleniumWrapper;

public class StatusButton extends Span implements ICheckStatus {
	private static final String attr_status = "aria-checked";
	
	public StatusButton(String path) {
		super(path);
	}
	
	public void setChecked(boolean checked) {
		
		String status = SeleniumWrapper.getAttribute(this.path, attr_status);

		if(Boolean.valueOf(status) != checked) {
			SeleniumWrapper.sendKeys(this.path, Keys.ENTER);
		}
	}
}
