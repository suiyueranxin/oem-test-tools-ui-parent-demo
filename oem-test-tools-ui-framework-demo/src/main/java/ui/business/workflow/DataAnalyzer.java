package ui.business.workflow;

import ui.business.page.HomePage;

public class DataAnalyzer {
	
	public void open(String name) {
		HomePage home = new HomePage();
		home.openTile(name);
	}
}
