package ui.business.page;

import io.qameta.allure.Step;
import ui.business.BusinessObject;
import ui.business.component.HeaderBar;
import ui.component.Comp;
import ui.component.Page;
import ui.utils.DomLocator;
import ui.utils.LocatorResource;

public class BusinessPage extends BusinessObject {
	private HeaderBar headerBar;
	private Page page;
	
	public BusinessPage() {
		super();
		this.page = (Page)this.compFactory.createComponent(LocatorResource.Page);
		this.headerBar = new HeaderBar();
	}	
	
	public void search(String text) {
		this.headerBar.search(text);
		this.waitForSearchReady();
	}
	
	public void checkPageDisplay(String bkName) {
		page.checkByImage(bkName);
	}
	
	public void logoff() {
		if (!(this instanceof HomePage)) {
			this.headerBar.switchToHome();
		}
		this.headerBar.logoff();
	}
	
	public void waitForReady() {
		page.waitUntilShow();
	}
	
	public void waitForSearchReady() {
		Page search = (Page)this.compFactory.createComponent(LocatorResource.SearchPage);
		search.waitUntilShow();
	}
	
	@Step("Wait until the current page load completely")
	public void waitForReady(DomLocator waitIndicator) {
		Comp indicator = (Comp)this.compFactory.createComponent(waitIndicator);
		indicator.waitUntilShow();
	}
	
	public void back() {
		this.page.back();
	}
	
	public void refresh() {
		this.page.refresh();
	}
	
	public void switchTo(String urlPath) {
		this.page.switchTo(urlPath);
	}
}
