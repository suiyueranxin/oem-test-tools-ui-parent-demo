package topic.s4hc.dashboard;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import runtime.BaseTest;
import runtime.dataprovider.DataFile;
import runtime.dataprovider.DataRecord;
import ui.business.page.DashboardPage;
import ui.business.workflow.Dashboard;
import ui.business.workflow.Generic;
import ui.component.Span;

public class IBNTest extends BaseTest {
	
	private static final String PathDisputeAmount = "disputeamount";
	
	@Feature("Open dashboards")
	@Story("IBN in one dashboard")
	@Description("Open a dashboard and jump to related BO List")
	@Test(enabled=true, dataProvider="json")
	@DataFile(path="dashboard/ibn.json")
	public void JumpToInDispute(DataRecord record) {
		
		// test data
		String user = record.getString("user");
		String tileName = record.getString("tileName");
		String tileGroup = record.getString("tileGroup");
		String jumpTo = record.getString("jumpTo");
		
		// log on
		Generic generic = new Generic();
		generic.logon(user);
		
		// open a tile from homepage
		Dashboard dashboard = new Dashboard();
		DashboardPage page = dashboard.open(tileGroup,tileName);
		
		// right click dispute amount and jump to Customer
		Span disputeAmountSpan = (Span)this.compFactory.createComponent(page, PathDisputeAmount);
		page.jumpTo(disputeAmountSpan, jumpTo);
		
		// TODO: check
		
	}
}
