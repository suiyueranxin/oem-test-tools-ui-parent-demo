package topic.s4hc.dashboard;

import java.util.List;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import runtime.BaseTest;
import runtime.dataprovider.DataFile;
import runtime.dataprovider.DataRecord;
import ui.business.page.ByePage;
import ui.business.page.DashboardPage;
import ui.business.workflow.Dashboard;
import ui.business.workflow.Generic;
import ui.component.Span;

public class DashboardTest extends BaseTest {
	private static final String PathDisputeAmount = "disputeamount";
	
	// sample test of reading json data
	@Feature("Open dashboards")
	@Story("Open built-in dashboards")
	@Description("Open dashboard:Disputes by user:AR_MANAGER")
	@Test(enabled=false, dataProvider="json")
	@DataFile(path="dashboard/dispute.json")
	public void openDispute(DataRecord record) {
		// test data
		String user = record.getString("user");
		String tileName = record.getString("tileName");
		String tileGroup = record.getString("tileGroup");
		int count = record.getInt("pageCount");
		List<String> titles = record.getStrings("titles");
		List<String> bks = record.getStrings("bks");
//		String disputedAmount = record.getString("disputedAmount");
		
		// log on
		Generic generic = new Generic();
		generic.logon(user);
		
		// open a tile from homepage
		Dashboard dashboard = new Dashboard();
		DashboardPage page = dashboard.open(tileGroup,tileName);
		
		// check titles
		page.checkHavePages(count, titles);
		
		// check disputed amount
//		Span disputedAmountSpan = (Span)this.compFactory.createComponent(page, PathDisputeAmount);
//		page.checkDomData(disputedAmountSpan, disputedAmount);
		 
		// check each page show correctly
//		for (int index=0; index<titles.size(); index++) {
//			page.checkPageDisplay(bks.get(index));
//			if (index != titles.size() - 1) {
//				page.switchPage(bks.get(index+1));
//			}
//		}
	}
	
	// sample test of reading excel data
	@Feature("Open dashboards")
	@Story("Open built-in dashboards")
	@Description("Open dashboards")
	@Test(enabled=false, dataProvider="excel")
	@DataFile(path="dashboard/dashboard.xlsx")
	public void openDashboards(DataRecord record) {
		String user = record.getString("user");
		String tileName = record.getString("tileName");
		String tileGroup = record.getString("tileGroup");
		int count = record.getInt("pageCount");
		List<String> titles = record.getStrings("titles");
		List<String> bks = record.getStrings("bks");
		
		// log on
		Generic generic = new Generic();
		generic.logon(user);
		
		// open a tile from homepage
		Dashboard dashboard = new Dashboard();
		DashboardPage page = dashboard.open(tileGroup, tileName);
		
		// check titles
		page.checkHavePages(count, titles);
	}
	
	// sample test of reading json data
	@Feature("Open dashboards")
	@Story("Open built-in dashboards")
	@Description("Open dashboard:Disputes by user:AR_MANAGER")
	@Test(enabled=true, dataProvider="json")
	@DataFile(path="dashboard/dispute.json")
	public void setVariableInDispute(DataRecord record) {
		// test data
		String user = record.getString("user");
		String tileName = record.getString("tileName");
		String tileGroup = record.getString("tileGroup");
		List<String> variables = record.getStrings("variables");
		String currency = record.getString("cpCurrency");
		
		// log on
		Generic generic = new Generic();
		generic.logon(user);
		
		// open a tile from homepage
		Dashboard dashboard = new Dashboard();
		DashboardPage page = dashboard.open(tileGroup,tileName);
		
//		page.setGlobalVariable("C_OPENDISPUTECASE", "Display Currency", "European Euro");
		page.setGlobalVariable(variables.get(0), variables.get(1), variables.get(2), true);
		Span span = (Span)this.compFactory.createComponent(page, "currency");
		
		// TODO: get dom value before refresh
		page.waitForReady();
		page.checkDomData(span, currency);
	}
}
