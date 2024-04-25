package ui.component;

import runtime.ByPath;

public class SearchGroup extends Comp {
	
	@ByPath(path="//div[contains(@id,'Dropdown')][contains(@data-ff-name, 'DropdownSearch')]")
	protected ComboBox searchType$;
	
	@ByPath(path="//div[contains(@id, 'SearchField')]")
	protected SearchInput searchInput$;
	
	public SearchGroup(String path) {
		super(path);
	}
	
	public void search(String type, String text) {
		if (!type.isEmpty()) {
			searchType$.select(type);
		}
		this.search(text);
	}
	
	public void search(String text) {
		searchInput$.clearAndSearch(text);
	}
	
	public String getSearchType() {
		return searchType$.getValue();
	}
}
