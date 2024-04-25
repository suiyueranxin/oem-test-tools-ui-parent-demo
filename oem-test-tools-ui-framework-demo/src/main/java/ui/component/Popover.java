package ui.component;

import runtime.ByPath;

public class Popover extends Comp {
	
	@ByPath(path="//div/*[text()='%s']")
	protected Button clickable$;
	
	public Popover(String path) {
		super(path);
	}
	
	public void clickItem(String text) {
		clickable$.formatDomPath(text);
		clickable$.click();
	}
}
