package ui.business.page;


import java.util.HashMap;

import io.qameta.allure.Step;
import runtime.InIFrame;
import ui.business.component.Variables;
import ui.business.component.Variant;
import ui.business.component.Filter;
import ui.component.Button;
import ui.component.Dialog;
import ui.component.Page;
import ui.utils.LocatorResource;

@InIFrame(path="//body/iframe")
public class SACAnalyticPage extends BusinessPage {	
	
	@InIFrame
	protected Variables variables;
	
	@InIFrame
	protected Filter filter;
	
	protected Variant variant;
	
	
	public SACAnalyticPage() {
		super();
		this.variant = new Variant();
	}
	
	@Step("Wait until loading bar disappear")
	public void waitForReady() {
		Button busyIndicator = (Button)this.compFactory.createComponent(LocatorResource.BusyIndicator);
		if (busyIndicator.isExisted()) {
			busyIndicator.waitUntilHide();
		}
	}
	
	@Step("Wait until variable prompt show then loading bar disappear")
	public void waitForReadyWithVariablePrompt() {
		Button busyIndicator = (Button)this.compFactory.createComponent(LocatorResource.BusyIndicator);
		if (busyIndicator.isExisted()) {
			Dialog prompt = (Dialog)this.compFactory.createComponent(LocatorResource.Prompt);
			if (!prompt.isExisted()) {
				busyIndicator.waitUntilHide();
			}	
		}
	}
	
	@Step("Check current page {0} by image comparing with {1}")
	public void checkPageDisplay(String pageName, String bkName) {
		super.checkPageDisplay(bkName);
	}
	
	public void setGlobalVariable(String query, String varName, String varValue, boolean close) {
		// sometimes no need to open prompt by query when open page with default query
		if (!query.isEmpty()) {
			variables.openVariablesPrompt(query);
		}
		variables.setVariable(varName, varValue);
		if (close) {
			variables.closeVariablesPrompt();
		}	
	}
	
	public void setGlobalVariables(String query, HashMap<String, String> variablePairs, boolean close) {
		// sometimes no need to open prompt by query when open page with default query
		if (!query.isEmpty()) {
			variables.openVariablesPrompt(query);
		}
		variables.setVariables(variablePairs);
		if (close) {
			variables.closeVariablesPrompt();
		}
	}
	
	public void setGlobalFilter() {
		
	}
	
	public Variant getVariant() {
		return this.variant;
	}
}
