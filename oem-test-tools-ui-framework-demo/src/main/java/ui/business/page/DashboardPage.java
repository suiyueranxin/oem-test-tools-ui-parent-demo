package ui.business.page;

import java.util.List;

import io.qameta.allure.Step;
import ui.component.Button;
import ui.component.Comps;
import ui.component.ContextMenu;
import ui.component.Popover;
import ui.component.Span;
import ui.utils.LocatorResource;

public class DashboardPage extends SACAnalyticPage {
	
	private static final String JumpTo = "Jump to...";
	
	public DashboardPage() {
		super();
	}
	
	@Step("Switch to page {0}")
	public void switchPage(String title) {
		Button titleBtn = (Button)this.compFactory.createComponent(LocatorResource.PageTitle, title);
		titleBtn.click();
	}
	
	@Step("Check the dashboard have {0} pages: {1}")
	public void checkHavePages(int count, List<String>titles) {
		Comps titleBtns = this.compFactory.createComponents(LocatorResource.PageTitles);
		titleBtns.checkCount(count);
		titleBtns.checkContent(titles);
	}
	
	@Step("IBN jump to {1}")
	public void jumpTo(Span srcComp, String target) {
		srcComp.contextClick();
		ContextMenu contextMenu = (ContextMenu)this.compFactory.createComponent(LocatorResource.DbContextMenu);
		contextMenu.select(JumpTo, target);
	}
	
	@Step("Check dashboard dom {0} data is: {1}")
	public void checkDomData(Span label, String data) {
		label.checkText(data);
	}
}
