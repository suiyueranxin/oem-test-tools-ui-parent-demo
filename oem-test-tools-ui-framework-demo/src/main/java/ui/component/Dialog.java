package ui.component;

import runtime.ByPath;

public class Dialog extends Comp {
	
	@ByPath(path="//button[@data-ff-name='okButton']", isRelative=false)
	protected Button ok$;
	
	@ByPath(path="//button[@data-ff-name='cancelButton']", isRelative=false)
	protected Button cancel$;
	
	public Dialog(String path) {
		super(path);
	}
	
	public void ok() {
		ok$.click();
	}
	
	public void cancel() {
		cancel$.click();
	}
	
}
