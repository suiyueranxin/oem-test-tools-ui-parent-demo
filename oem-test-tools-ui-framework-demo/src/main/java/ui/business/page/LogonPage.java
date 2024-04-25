package ui.business.page;

import config.GlobalProperty;
import ui.component.Button;
import ui.component.Input;
import ui.utils.LocatorResource;

public class LogonPage extends BusinessPage {
	
	public void logon(String user, String pwd) {
		String logonStyle = GlobalProperty.getProperty("logonStyle");
		String userRs = LocatorResource.User;
		String pwdRs = LocatorResource.Pwd;
		String logOnRs = LocatorResource.Logon;
		if (null != logonStyle && logonStyle.equals("2")) {
			userRs = LocatorResource.User2;
			pwdRs = LocatorResource.Pwd2;
			logOnRs = LocatorResource.Logon2;
		}
		
		Input userInput = (Input)this.compFactory.createComponent(userRs);
		Input pwdInput = (Input)this.compFactory.createComponent(pwdRs);
		userInput.input(user);
		pwdInput.input(pwd);
		
		Button logonBtn = (Button)this.compFactory.createComponent(logOnRs);
		logonBtn.click();
		
		HomePage home = new HomePage();
		home.waitForReady();
	}
}
