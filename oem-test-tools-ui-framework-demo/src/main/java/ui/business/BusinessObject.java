package ui.business;

import ui.component.CompFactory;
import ui.utils.AnnotationHelper;

public class BusinessObject {
	protected CompFactory compFactory = null;
	
	public BusinessObject() {
		compFactory = new CompFactory(this.getClass().getName());
		AnnotationHelper.generateInIFrameAnnotated(this);
	}
	
	public CompFactory getCompFactory() {
		return this.compFactory;
	}
}
