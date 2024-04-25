package ui.component;

import org.openqa.selenium.Keys;

import runtime.ByPath;

public class PopoverList extends Comp{
	
	@ByPath(path="//li[.='%s']")
	protected Span optionOfValue$;
	
	@ByPath(path="//ul//li")
	protected Span optionOfIndex$;
	
	public PopoverList(String path) {
		super(path);
	}
	
	public void select(String option) {
		optionOfValue$.formatDomPath(option);
		optionOfValue$.click();
	}
	
	public void selectByKey(String option) {
		optionOfValue$.formatDomPath(option);
		optionOfValue$.sendKeys(Keys.ENTER);;
	}
}
