package ui.component;

import runtime.ByPath;

public class Navigator extends Comp{
	
	@ByPath(path="//li//*[text()='%s']")
	protected Span optionOfValue$;
	
	@ByPath(path="//li")
	protected Span optionOfIndex$;
	
	public Navigator(String path) {
		super(path);
	}
	
	public void select(String option) {
		optionOfValue$.formatDomPath(option);
		optionOfValue$.click();
	}
	
	public void select(int index) {
		optionOfIndex$.formatDomPath(index);
		optionOfIndex$.click();
	}
}
