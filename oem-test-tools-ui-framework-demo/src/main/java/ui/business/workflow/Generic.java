package ui.business.workflow;

import io.qameta.allure.Step;
import ui.business.page.BusinessPage;
import ui.business.page.LogonPage;

public class Generic {
	
	public void logon(String user, String pwd) {
		LogonPage logonPage = new LogonPage();
		logonPage.logon(user, pwd);
	}
	
	@Step("Logon with user {0}")
	public void logon(String user) {
		this.logon(user, "Welcome1!");
	}
	
	@Step("Logout")
	public void logoff() {
		BusinessPage page = new BusinessPage();
		page.logoff();
		page.back();
	}
}
