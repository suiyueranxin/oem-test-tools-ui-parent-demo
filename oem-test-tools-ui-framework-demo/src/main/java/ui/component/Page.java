package ui.component;

import runtime.SeleniumWrapper;

public class Page extends Comp {
	
	public Page(String path) {
		super(path);
	}
	
	public void back() {
		SeleniumWrapper.back();
	}
	
	public void refresh() {
		SeleniumWrapper.refresh();
	}
	
	public void switchTo(String urlPath) {
		SeleniumWrapper.switchTo(urlPath);
	}
}
