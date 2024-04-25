package ui.component;

import runtime.ByPath;

public class ComboBox extends Comp {
		
	@ByPath(path="//span[@class='sapMSltArrow']")
	protected Button arrowBtn$;
	
	@ByPath(path="(//div[@class='sapMPopoverScroll']//ul/li[text()='%s'])[last()]", isRelative=false)
	protected Span optionSpan$;
	
	@ByPath(path="//input")
	protected Input input$;
	
	public ComboBox(String path) {
		super(path);
	}
	
	public void select(String option) {
		if (this.getValue().equals(option)) {
			return;
		}
		arrowBtn$.click();
		
		optionSpan$.formatDomPath(option);
		optionSpan$.click();
	}
	
	public String getValue() {
		return input$.getDom().getText();
	}
	
}
