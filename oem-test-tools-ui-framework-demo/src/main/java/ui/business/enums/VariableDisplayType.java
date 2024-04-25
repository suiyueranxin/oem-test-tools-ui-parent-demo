package ui.business.enums;

public enum VariableDisplayType {
	DESCRIPTION("Description"), ID("ID"), ID_DESCRIPTION("ID and Description");
	
	private String content;
	
	private VariableDisplayType(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return this.content;
	}
}
