package ui.business.enums;

import java.util.regex.Pattern;

public enum VariableType {
	Member, Date;
	
	public static boolean isDate(String type) {
		// 2020-07-01
        // 2020.07.01
        // 20200701
		String pattern ="\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(pattern, type);
	}
}
