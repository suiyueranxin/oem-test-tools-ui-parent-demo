package ui.business.component;

import ui.business.BusinessObject;
import ui.component.Button;
import ui.utils.LocatorResource;

public class PopupDlg extends BusinessObject {
	
	public void ok() {
		Button okBtn = (Button)this.compFactory.createComponent(LocatorResource.PopupOk);
		okBtn.click();
	}
	
	public void cancel() {
		Button cancelBtn = (Button)this.compFactory.createComponent(LocatorResource.PopupCancel);
		cancelBtn.click();
	}
}
