package topic.s4hc.dashboard;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import runtime.BaseTest;
import runtime.dataprovider.DataFile;
import runtime.dataprovider.DataRecord;
import ui.business.component.Variant;
import ui.business.page.DashboardPage;
import ui.business.workflow.Dashboard;
import ui.business.workflow.Generic;

public class VariantTest extends BaseTest { 
		
		@Feature("Variant")
		@Story("")
		@Description("Test the changes in prompt can be properly saved in variant")
		@Test(enabled=true, dataProvider="json")
		@DataFile(path="dashboard/variant_prompt.json")
		public void saveVariantOfPrompt(DataRecord record) {
			// test data
			String user = record.getString("user");
			String tileName = record.getString("tileName");
			String tileGroup = record.getString("tileGroup");
			//List<String> bks = record.getStrings("bks");

			// log on
			Generic generic = new Generic();
			generic.logon(user);
			
			// open a tile from homepage
			Dashboard dashboard = new Dashboard();
			DashboardPage page = dashboard.open(tileGroup,tileName);
			Variant variant = page.getVariant();
			
			variant.saveAsVariant("gztest1", false, false);
			variant.applyVariant("Standard");
			variant.updateVariantName("gztest1", "gztest");
			variant.addAsFavorite("gztest", true);
			variant.setAsDefault("gztest", true);
			variant.applyVariant("gztest");
			variant.deleteVariant("gztest");
		}
		
		@Feature("Variant")
		//@Story("")
		@Description("Test the changes in filters can be properly saved in variant")
		@Test(enabled=false, dataProvider="json")
		@DataFile(path="dashboard/variant_filter.json")
		public void saveVariantOfFilter(DataRecord record) {
			
		}
}
