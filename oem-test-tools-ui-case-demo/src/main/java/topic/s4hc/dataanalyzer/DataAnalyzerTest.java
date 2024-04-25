package topic.s4hc.dataanalyzer;


import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import runtime.BaseTest;
import runtime.dataprovider.DataFile;
import runtime.dataprovider.DataRecord;
import ui.business.page.DataAnalyzerPage;
import ui.business.page.HomePage;
import ui.business.workflow.Generic;
import ui.component.Tile;

public class DataAnalyzerTest extends BaseTest{
	
	@Feature("Open data analyzer")
	@Story("Open built-in dashboards")
	@Description("Open data analyzer by user:CONSLDTN_SPECIALIST")
	@Test(enabled=true, dataProvider="json")
	@DataFile(path="dataanalyzer/group_data_analysis.json")
	public void openGroupDataAnalysis(DataRecord record) {
		// test data
		String user = record.getString("user");
		String tileName = record.getString("tileName");
		String tileGroup = record.getString("tileGroup");
		
		// log on
		Generic generic = new Generic();
		generic.logon(user);
		
		// open a tile from homepage
		HomePage home = new HomePage(); 
		Tile tile = (Tile)this.compFactory.createComponent("tile", tileName);
		home.openTile(tileGroup, tile);
		DataAnalyzerPage page = new DataAnalyzerPage();
		page.waitForReady();
		
		// set variables in variable prompt
		JSONObject variables = (JSONObject)record.get("variables");
		page.setGlobalVariables("", variables, true);
	}
}
