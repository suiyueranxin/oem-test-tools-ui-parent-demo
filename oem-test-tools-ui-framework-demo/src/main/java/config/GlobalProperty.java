package config;

import java.io.IOException;
import java.util.Properties;
import log.Log;

public class GlobalProperty {
	private static Properties props = null;
	
	public static void load(String envFilePath) {
		props = new Properties();
		try {
			props.load(GlobalProperty.class.getClass().getResourceAsStream(envFilePath));
		} catch (IOException e) {
			Log.logException("Not found properties file: " + envFilePath);
		}
	}
	
	public static synchronized Properties get() {
		if (null == props) {
			Log.logWarning("Please calling GlobalProperty.load with *.properties at global entry point");
			props = new Properties();
		}
		return props;
	}
	
	public static void setProperty(String key, String value) {
		GlobalProperty.get().setProperty(key, value);
	}
	
	public static String getProperty(String key) {
		return GlobalProperty.get().getProperty(key);
	}
}
