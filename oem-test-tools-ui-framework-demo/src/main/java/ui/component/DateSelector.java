package ui.component;

import runtime.ByPath;

public class DateSelector extends Comp {
	
	@ByPath(path="//span[contains(@id,'DatePicker')]")
	protected Button datePickerBtn$;
	
	@ByPath(path="//button[contains(@id,'-cal--Head-B2')]", isRelative=false)
	protected Button yearBtn$;
	
	@ByPath(path="//button[contains(@id,'-cal--Head-B1')]", isRelative=false)
	protected Button monthBtn$;
	
	@ByPath(path="//button[contains(@id,'-cal--Head-prev')]", isRelative=false)
	protected Button prevBtn$;
	
	@ByPath(path="//button[contains(@id,'-cal--Head-next')]", isRelative=false)
	protected Button nextBtn$;
	
	@ByPath(path="//div[@class='sapUiCalYearPicker']//div[text()='%s']", isRelative=false)
	protected Span yearPicker$;
	
	@ByPath(path="(//div[@class='sapUiCalYearPicker']//div[contains(@class,'sapUiCalItem')])[1]", isRelative=false)
	protected Span beginYear$;
	
	@ByPath(path="(//div[@class='sapUiCalYearPicker']//div[contains(@class,'sapUiCalItem')])[20]", isRelative=false)
	protected Span endYear$;
	
	@ByPath(path="(//div[@class='sapUiCalMonthPicker']//div[contains(@class,'sapUiCalItem')])[%d]", isRelative=false)
	protected Span monthPicker$;
	
	@ByPath(path="//div[@class='sapUiCalContent']//span[text()='%s']", isRelative=false)
	protected Span dayPicker$;
		
	public DateSelector(String path) {
		super(path);
	}
	
	// date format:2020-07-17
	public void select(String date) {
		datePickerBtn$.click();
		
		String[] dateInfo = date.split("-");
		String year = dateInfo[0];
		String month = dateInfo[1];
		if (month.startsWith("0")) {
			month = month.substring(1);
		}
		String day = dateInfo[2];
		if (day.startsWith("0")) {
			day = day.substring(1);
		}
		
		this.selectYear(year);
		this.selectMonth(month);
		this.selectDay(day);
	}
	
	private void selectYear(String year) {
		// open year picker
		yearBtn$.click();
		
		// make year in current view by prev button and next button
		String beginYear = beginYear$.getDom().getText();	
		String endYear = endYear$.getDom().getText();
		while(year.compareTo(beginYear) < 0 || year.compareTo(endYear) > 0) {
			if (year.compareTo(beginYear) < 0) {
				prevBtn$.click();
				beginYear = beginYear$.getDom().getText();
			} else if (year.compareTo(endYear) > 0) {
				nextBtn$.click();
				endYear = endYear$.getDom().getText();
			} 
		}
		
		// pick year in current view
		yearPicker$.formatDomPath(year);
		yearPicker$.click();
	}
	
	private void selectMonth(String month) {
		// open month picker
		monthBtn$.click(); 
		
		// picker 
		int index = Integer.parseInt(month);
		monthPicker$.formatDomPath(index);
		monthPicker$.click();
	}
	
	private void selectDay(String day) {
		dayPicker$.formatDomPath(day);
		dayPicker$.click();
	}
}
