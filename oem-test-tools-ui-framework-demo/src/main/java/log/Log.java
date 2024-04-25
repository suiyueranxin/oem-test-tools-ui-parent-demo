package log;

public class Log {
	public static void logError(String msg) {
		System.out.println("[Error] " + msg);
	}
	
	public static void logException(String msg) {
		System.out.println("[Exception]" + msg);
	}
	
	public static void logDebug(String msg) {
		System.out.println("[Debug]" + msg);
	}
	
	public static void logWarning(String msg) {
		System.out.println("[Warning]" + msg);
	}
}
