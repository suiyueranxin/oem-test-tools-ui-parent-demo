package ui.component;

import org.openqa.selenium.Keys;

import runtime.ByPath;

public class VariantView extends Comp {
	
	@ByPath(path="//button[.='Delete View']")
	protected Button deleteVariant$;
	
	@ByPath(path="//div[@role='radio']")
	protected RadioButton setVariantAsDefault$;
	
	@ByPath(path="//input[@class='sapMInputBaseInner']")
	protected Input variantViewName$;
	
	@ByPath(path="//span[contains(@title, 'Favorite')]")
	protected FavoriteIcon addVariantAsFavorite$;
	
	public VariantView(String path) {
		super(path);
	}
	
	public void delete() {
		deleteVariant$.sendKeys(Keys.ENTER);
	}

	public void updateName(String name) {
		variantViewName$.clearAndInput(name);
	}
	
	public void setAsDefault(boolean setAsDefault) {
		setVariantAsDefault$.setChecked(setAsDefault);
	}
	
	public void addAsFavorite(boolean addAsFavorite) {
		addVariantAsFavorite$.setChecked(addAsFavorite);
	}
}
