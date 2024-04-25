package ui.business.workflow;

import io.qameta.allure.Step;
import ui.business.page.DashboardPage;
import ui.business.page.HomePage;

public class Dashboard {
	
	@Step("Click tile {0}->{1} in home page")
	public DashboardPage open(String tileGroup, String tileName) {
		HomePage home = new HomePage();
		home.openTile(tileGroup, tileName);
		DashboardPage page = new DashboardPage();
		page.waitForReady();
		
		return page;
	}
	
}
