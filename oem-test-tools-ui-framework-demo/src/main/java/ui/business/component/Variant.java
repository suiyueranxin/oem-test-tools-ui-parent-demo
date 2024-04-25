package ui.business.component;

import org.openqa.selenium.Keys;

import ui.business.BusinessObject;
import ui.component.Button;
import ui.component.CheckBox;
import ui.component.Input;
import ui.component.PopoverList;
import ui.component.SearchInput;
import ui.component.VariantView;
import ui.utils.LocatorResource;

public class Variant extends BusinessObject {
	
	public Variant() {
		super();
	}
	
	public void openVariantDlg() {
		Button variantBtn = (Button)this.compFactory.createComponent(LocatorResource.selectViewBtn);
		variantBtn.click();
	}
	
	public void saveAndCloseVariantDlg() {
		Button saveBtn = (Button)this.compFactory.createComponent(LocatorResource.saveVariant);
		saveBtn.click();
	}
	
	public void saveVariant() {	
		
		openVariantDlg();
		
		saveAndCloseVariantDlg();
	}
	
	public void saveAsVariant(String name, boolean setAsDefault, boolean setAsPublic)  {
		
		openVariantDlg();
		
		setAsDefault = setAsDefault | false;
		setAsPublic = setAsPublic | false;
		
		Button saveAsBtn = (Button)this.compFactory.createComponent(LocatorResource.saveAsVariant);
		saveAsBtn.click();	
		
		Input ViewName = (Input)this.compFactory.createComponent(LocatorResource.variantName);
		ViewName.clear();
		ViewName.input(name);
		
		CheckBox setAsDefaultCheckBox = (CheckBox)this.compFactory.createComponent(LocatorResource.variantOption, "Set as Default");
		setAsDefaultCheckBox.setChecked(setAsDefault);
		
		CheckBox setAsPublickCheckBox = (CheckBox)this.compFactory.createComponent(LocatorResource.variantOption, "Public");
		setAsPublickCheckBox.setChecked(setAsPublic);
		
		saveAndCloseVariantDlg();
	}
	
	public void applyVariant(String name) {
		
		openVariantDlg();
		
		PopoverList variants = (PopoverList)this.compFactory.createComponent(LocatorResource.applyVariant);
		variants.select(name);
	}
	
	private void openManageVariantDlg() {
		
		openVariantDlg();
		
		Button saveAsBtn = (Button)this.compFactory.createComponent(LocatorResource.manageVariant);
		saveAsBtn.click();
	}
	
	private void saveAndCloseManageVariantDlg() {
		
		Button OKBtn = (Button)this.compFactory.createComponent(LocatorResource.OKBtn);
		OKBtn.click();
	}
	
	public void updateVariant(String variantName, Boolean tobeRemoved, Boolean setAsDefault, Boolean addAsFavorite, Boolean updateName, String newName) {
		
		openManageVariantDlg();
		
		//filter result
		SearchInput searchInput = (SearchInput)this.compFactory.createComponent(LocatorResource.searchVariant);
		searchInput.searchAndEnter(variantName);
		
		VariantView variantView = (VariantView)this.compFactory.createComponent(LocatorResource.variantView, variantName);
		
		if(!variantView.isExisted(0)) {
			Button cancelBtn = (Button)this.compFactory.createComponent(LocatorResource.cancelBtn);
			cancelBtn.click();
			return;
		}
		
		if(tobeRemoved != null && tobeRemoved) {
			variantView.delete();
			saveAndCloseManageVariantDlg();
			return;
		}
		
		if(updateName != null && updateName) {
			variantView.updateName(newName);
		}
		
		if(setAsDefault != null) {
			variantView.setAsDefault(setAsDefault);
		}
		
		if(addAsFavorite != null) {
			variantView.addAsFavorite(addAsFavorite);
		}
		
		saveAndCloseManageVariantDlg();
			
	}
	
	public void deleteVariant(String name) {
		updateVariant(name,true,null,null,null,null);
	}
	
	public void updateVariantName(String oldName, String newName) {
		updateVariant(oldName, null, null, null, true, newName);
	}
	
	public void addAsFavorite(String name, boolean addAsFavorite) {
		updateVariant(name, null, null, addAsFavorite, null, null);
	}
	
	public void setAsDefault(String name, boolean setAsDefault) {
		updateVariant(name,null,setAsDefault,null,null,null);
	}
	
}
