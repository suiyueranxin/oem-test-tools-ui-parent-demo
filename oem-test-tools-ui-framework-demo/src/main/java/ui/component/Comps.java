package ui.component;

import java.util.List;
import javafx.util.Pair;
import runtime.SeleniumWrapper;
import ui.utils.Verifier;

public class Comps {
	
	private String path;
	public Comps(String path) {
		Pair<String, String> pair = SeleniumWrapper.handleIframe(path);
		this.path = pair.getKey();
	}
	
	public void checkCount(int expected) {
		Verifier.countCompare(this.path, expected);
	}
	
	public void checkContent(List<String> expected) {
		Verifier.textListCompare(this.path, expected);
	}
}
