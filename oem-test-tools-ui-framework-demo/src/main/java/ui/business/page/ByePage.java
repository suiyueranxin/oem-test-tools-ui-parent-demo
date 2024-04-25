package ui.business.page;

import ui.component.Span;
import ui.utils.LocatorResource;

public class ByePage extends BusinessPage {
	public void waitForReady() {
		Span byeSpan = (Span)this.compFactory.createComponent(LocatorResource.ByeText);
		byeSpan.waitUntilShow();
	}
	
	public void backToLogOn() {
		this.back();
	}
}
