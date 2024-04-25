package ui.component;

import java.util.List;

import runtime.ByPath;

public class MemberPickerDlg extends Dialog{
	
	@ByPath(path="//div[@class='sapUiTableCCnt']//table", isRelative=false)
	protected Table memberTable$;
	
	@ByPath(path="//div[contains(@data-ff-name,'SearchContainer')]", isRelative=false)
	protected SearchGroup searchGroup$;
	
	@ByPath(path="//div[contains(@data-ff-name,'Presentation')]//div[contains(@id, 'Dropdown')]", isRelative=false)
	protected ComboBox showSettings$;
		 
	public MemberPickerDlg(String path) {
		super(path);
	}
	
	public void select(String option, String type) {	
		searchGroup$.search(type, option);
		memberTable$.selectRow(option);
		this.ok();
	}
	
	public void select(List<String> options, String type) {
		this.show(type);
		for (String option: options) {
			searchGroup$.search(type, option);
			memberTable$.selectRow(option);
		}
		this.ok();
	}
	
	public void show(String showType) {
		showSettings$.select(showType);
	}
	
	public void selectAll() {
	}
	
	public void unselect(String option) {
	}
	
	public void unselect(List<String> options) {
	}
	
	public void unselectAll() {
	}
}
