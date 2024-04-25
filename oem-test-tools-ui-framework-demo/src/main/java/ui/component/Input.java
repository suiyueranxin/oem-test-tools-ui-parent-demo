package ui.component;

import runtime.SeleniumWrapper;

public class Input extends Comp implements IEditable {
	
	public Input(String path) {
		super(path);
	}

	public void input(String text) {
		SeleniumWrapper.input(this.path, text);
	}
	
	public void clearAndInput(String text) {
		SeleniumWrapper.clearAndInput(this.path, text);
	}
	
	public void inputAndEnter(String text) {
		SeleniumWrapper.inputAndEnter(this.path, text);
	}
	
	public void clear() {
		SeleniumWrapper.clear(this.path);
	}
}
