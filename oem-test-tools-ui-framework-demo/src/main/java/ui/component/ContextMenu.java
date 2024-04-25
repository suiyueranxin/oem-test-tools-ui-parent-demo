package ui.component;

import runtime.ByPath;

public class ContextMenu extends Comp implements ICheckChild {
	
	@ByPath(path="//ul[@class='sapUiMnuLst']/li//*[text()='%s']")
	protected Span menuItem$;
	
	@ByPath(path="//ul[contains(@class,'sapUiMnuNoSbMnu')]/li//*[text()='%s']")
	protected Span subMenuItem$;
	
	public ContextMenu(String path) {
		super(path);
	}
	
	public void select(String menu) {
		menuItem$.formatDomPath(menu);
		menuItem$.hover();
	}
	
	public void select(String menu, String subMenu) {
		this.select(menu);
		subMenuItem$.formatDomPath(subMenu);
		subMenuItem$.click();
	}
	
	public void checkHasItem(String menu) {
		menuItem$.formatDomPath(menu);
		menuItem$.checkIsVisible();
	}
}
