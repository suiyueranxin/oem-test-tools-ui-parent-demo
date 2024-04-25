package ui.component;

import java.util.List;

import runtime.ByPath;

public class MemberSelector extends Comp {
	
	@ByPath(path="//span[@title='Value Help']")
	protected Button valueHelpBtn$;
	
	@ByPath(path="//div[contains(@class, 'sapMDialog')]")
	protected MemberPickerDlg memberPicker$;
	
	@ByPath(path="//Input")
	protected Input input$;
		 
	public MemberSelector(String path) {
		super(path);
	}
	
	private void openMemberPicker() {
		valueHelpBtn$.click();
	}
	
	public void select(String option, String type) {	
		this.openMemberPicker();
		memberPicker$.select(option, type);
	}
	
	public void select(List<String> options, String type) {
		this.openMemberPicker();
		memberPicker$.select(options, type);
	}
	
	public void show(String type) {
		memberPicker$.show(type);
	}
	
	public void enterMemberID(String id) {
		input$.inputAndEnter(id);
	}
} 
