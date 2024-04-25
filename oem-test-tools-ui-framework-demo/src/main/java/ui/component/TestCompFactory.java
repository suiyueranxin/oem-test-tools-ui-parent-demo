package ui.component;

import ui.business.BusinessObject;
import ui.utils.DomLocator;

public class TestCompFactory extends CompFactory{
	
	public TestCompFactory(String className) {
		super(className);
	}
	
	public <T extends Comp> T createComponent(BusinessObject bo, String locatorName) {
		DomLocator locator = this.getLocator(locatorName);
		updateIframe(bo, locator);
		return bo.getCompFactory().createComponent(locator);
	}
	
	public <T extends Comp> T createComponent(BusinessObject bo, String locatorName, String arg) {
		DomLocator locator = this.getLocator(locatorName, arg);
		updateIframe(bo, locator);
		return bo.getCompFactory().createComponent(locator);
	}
	
	public void updateIframe(BusinessObject bo, DomLocator locator) {
		String iframe = bo.getCompFactory().getIframe();
		if (null != iframe) {
			locator.formatIframePath(iframe);
		}
	}
	
}
