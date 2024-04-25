package ui.business.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ui.business.BusinessObject;
import ui.business.enums.VariableDisplayType;
import ui.business.enums.VariableType;
import ui.component.Button;
import ui.component.DateSelector;
import ui.component.MemberSelector;
import ui.component.PopoverList;
import ui.component.Span;
import ui.utils.LocatorResource;

public class Variables extends BusinessObject {
	
	private boolean isOpen = false;
	
	public Variables() {
		super();
	}
	
	public void openVariablesPrompt(String query) {
		Button dataBtn = (Button)this.compFactory.createComponent(LocatorResource.EditPrompt);
		dataBtn.click();
		
		PopoverList queryPopover = (PopoverList)this.compFactory.createComponent(LocatorResource.QueryPopover);
		queryPopover.select(query);
		
		isOpen = true;
	}
	
	public void setVariable(String varName, String varValue) {
		if (!VariableType.isDate(varValue)) {
			if(varValue.indexOf(";") != -1) {
				List<String> varValues = Arrays.asList(varValue.split(";"));
				this.setMemberVariable(varName, varValues);
			} else {
				this.setMemberVariable(varName, varValue);
			}
		} else {
			this.setDateVariable(varName, varValue);
		}
	}
	
	public void setVariables(HashMap<String, String> variables) {
		Iterator<String> iter = variables.keySet().iterator();
		while (iter.hasNext()) {
			String varName = iter.next();
			String varValue = variables.get(varName);
			this.setVariable(varName, varValue);
		}
	}
	
	public void setMemberVariable(String varName, String varValue) {
		this.scrollToShow(varName);
		Button deleteBtn = (Button)this.compFactory.createComponent(LocatorResource.Delete, varName);
		if (deleteBtn.isExisted(5L)) {
			deleteBtn.click();
		}
		MemberSelector member = (MemberSelector)this.compFactory.createComponent(LocatorResource.MemberSelector, varName);
		member.select(varValue, VariableDisplayType.ID_DESCRIPTION.toString());
	}
	
	public void setMemberVariableByID(String varName, String id) {
		this.scrollToShow(varName);
		MemberSelector member = (MemberSelector)this.compFactory.createComponent(LocatorResource.MemberSelector, varName);
		member.enterMemberID(id);
	}
	
	public void setMemberVariable(String varName, List<String> varValues) {
		this.scrollToShow(varName);
		Button clearBtn = (Button)this.compFactory.createComponent(LocatorResource.Clear, varName);
		if (clearBtn.isExisted(5L)) {
			clearBtn.click();
		}
		MemberSelector member = (MemberSelector)this.compFactory.createComponent(LocatorResource.MemberSelector, varName);
		member.select(varValues, VariableDisplayType.ID_DESCRIPTION.toString());
	}
	
	// date format: YY-MM-DD
	public void setDateVariable(String varName, String date) {
		this.scrollToShow(varName);
		DateSelector member = (DateSelector)this.compFactory.createComponent(LocatorResource.DateSelector, varName);
		member.select(date);
	}
	
	public void deleteMemberLine(String varName, String varValue) {
		// How to pass two parameters
	}
	public void deleteMemberLine(String varName) {
		Button deleteBtn = (Button)this.compFactory.createComponent(LocatorResource.Delete, varName);
		deleteBtn.click();
	}
	
	public void clearMemberVariable(String varName) {
		Button clearBtn = (Button)this.compFactory.createComponent(LocatorResource.Clear, varName);
		clearBtn.click();
	}
	
	public void closeVariablesPrompt() {
		if (!isOpen) {
			return;
		}
		Button confirmBtn = (Button)this.compFactory.createComponent(LocatorResource.Submit);
		confirmBtn.click();
	}
	
	public void scrollToShow(String varName) {
		Span varSpan = (Span)this.compFactory.createComponent(LocatorResource.VariableLabel, varName);
		varSpan.scrollToShow();
	}
 }
