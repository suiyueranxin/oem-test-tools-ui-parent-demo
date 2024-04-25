package ui.business.page;

import ui.business.page.BusinessPage;
import ui.component.Dialog;
import ui.component.Navigator;
import ui.component.Tile;
import ui.utils.LocatorResource;

public class HomePage extends BusinessPage {
		
	public void openTile(String title) {	
		Tile tile = (Tile)this.compFactory.createComponent(LocatorResource.Tile, title);
		tile.open();
	}
	
	public void openTile(String group, String title) {
		Navigator nav = (Navigator)this.compFactory.createComponent(LocatorResource.Navigator);
		nav.select(group);
		this.openTile(title);
	}
	
	public void openTile(String group, Tile tile) {
		Navigator nav = (Navigator)this.compFactory.createComponent(LocatorResource.Navigator);
		nav.select(group);
		tile.open();
	}
	
	public void searchAndOpenTile(String title) {
		this.search(title);
		this.openTile(title);
	}
	
	public void waitForReady() {
		super.waitForReady();
		Dialog samlDlg = (Dialog)this.compFactory.createComponent(LocatorResource.SamlDlg);
		if (samlDlg.isExisted()) {
			samlDlg.waitUntilHide();
		}
//		Button closeBtn = (Button)this.compFactory.createComponent(LocatorResource.InfoBarCloseButton);
//		closeBtn.click();
	}	
	
}
