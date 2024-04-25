package ui.business.component;

import ui.business.BusinessObject;
import ui.business.page.ByePage;
import ui.business.page.HomePage;
import ui.component.Button;
import ui.component.Input;
import ui.component.PopoverList;
import ui.utils.LocatorResource;

public class HeaderBar extends BusinessObject {
	private static final String LogOff = "Sign Out";
	private static final String Home = "Home";
	
	public void search(String text) {	
		Input searchInput = (Input)this.compFactory.createComponent(LocatorResource.SearchBox);
		if (!searchInput.isExisted()) {
			Button searchBtn = (Button)this.compFactory.createComponent(LocatorResource.Search);
			searchBtn.click();
		}
		searchInput.inputAndEnter(text);
	}
	
	public void search(String appType, String text) {
		
	}
	
	public void logoff() {
		Button meBtn = (Button)this.compFactory.createComponent(LocatorResource.Me);
		meBtn.click();
		PopoverList mePopover = (PopoverList)this.compFactory.createComponent(LocatorResource.MePopover);
		mePopover.select(LogOff);
		PopupDlg popup = new PopupDlg();
		popup.ok();
		ByePage bye = new ByePage();
		bye.waitForReady();
	}
	
	public void switchToHome() {
		Button appBtn = (Button)this.compFactory.createComponent(LocatorResource.App);
		appBtn.click();
		PopoverList appPopover = (PopoverList)this.compFactory.createComponent(LocatorResource.AppPopover);
		appPopover.select(Home);
	}
}
