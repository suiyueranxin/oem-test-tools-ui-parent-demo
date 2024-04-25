package ui.component;

import runtime.ByPath;

public class SearchInput extends Comp {
	
	@ByPath(path="//input")
	protected Input input$;
	
	@ByPath(path="//div[@title='Search']")
	protected Button searchBtn$;
	
	@ByPath(path="//div[@title='Reset']")
	protected Button clearBtn$;
	
	public SearchInput(String path) {
		super(path);
	}
	
	public void search(String text) {
		input$.input(text);
		searchBtn$.click();
	}
	
	public void clearAndSearch(String text) {
		if (clearBtn$.isExisted(5L)) {
			clearBtn$.click();
		}
		
		this.search(text);
	}
	
	public void searchAndEnter(String text) {
		input$.inputAndEnter(text);
	}
}
