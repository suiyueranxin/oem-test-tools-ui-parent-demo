package ui.component;

import org.openqa.selenium.Keys;

import runtime.SeleniumWrapper;

public class FavoriteIcon extends StatusButton{
	
	public FavoriteIcon(String path) {
		super(path);
	}
	
	public void setChecked(boolean checked) {
		String title = SeleniumWrapper.getAttribute(this.path, "title");

		if((checked && title.equals("Add as Favorite")) || (!checked && title.equals("Remove from Favorites"))) {
			SeleniumWrapper.sendKeys(this.path, Keys.ENTER);
		}
	}
	
}
