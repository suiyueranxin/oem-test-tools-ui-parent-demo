package ui.component;

import java.util.List;

import runtime.ByPath;

public class Table extends Comp {
	@ByPath(path="//tr/td//*[text()='%s']")
	protected Span rowOfText$;
	
	@ByPath(path="//tr/td[contains(@id,'-rows-row%d')]")
	protected Span rowOfIndex$;
	
	private static final String AttrRowSelected = "aria-selected";
	
	public Table(String path) {
		super(path);
	}
	
	public void selectRow(int index) {
		if (!this.rowIsSelected(index)) {
			rowOfIndex$.click();
		}
	}
	
	public void selectRow(String text) {
		if (!this.rowIsSelected(text)) {
			rowOfText$.click();
		}
	}
	
	public void selectRows(List<String> texts) {
		for (String option: texts) {
			this.selectRow(option);
		}
	}
	
	public void unselectRow(String text) {
		if (this.rowIsSelected(text)) {
			rowOfText$.click();
		}
	}
	
	public void unselectRows(List<String> texts) {
		for (String option: texts) {
			this.unselectRow(option);
		}
	}
	
	public boolean rowIsSelected(String text) {
		rowOfText$.formatDomPath(text);
		return "true".equals(rowOfText$.getDom().getAttribute(AttrRowSelected));
	}
	
	public boolean rowIsSelected(int rowIndex) {
		rowOfIndex$.formatDomPath(rowIndex);
		return "true".equals(rowOfIndex$.getDom().getAttribute(AttrRowSelected));
	}
}
