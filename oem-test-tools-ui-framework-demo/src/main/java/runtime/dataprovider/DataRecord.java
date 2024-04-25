package runtime.dataprovider;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataRecord {
	private Object jsonObj = null; // JSONObject/JSONArray
	
	public DataRecord(Object jsonObj) {
		this.jsonObj = jsonObj;
	}
	
	public String getString(String chain) {
		return (String)this.get(chain);
	}
	
	public int getInt(String chain) {
	
		if (this.get(chain) instanceof String) {
			return Integer.parseInt((String)this.get(chain));
		} else { 
			return ((Long)this.get(chain)).intValue();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getStrings(String chain) {
		return (List<String>)this.get(chain);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getInts(String chain) {
		return (List<Integer>)this.get(chain);
	}
	
	public DataRecord getDataRecord(String chain) {
		Object obj = this.get(chain);
		return new DataRecord(obj);
	}
	
	// array pattern: [number]
	// obj pattern: ["prop"]
	public Object get(String chain) {
		String pattern = this.format(chain);
		pattern = pattern.trim().substring(1, pattern.length()-1);
		String[] props = pattern.split("\\]\\[");
		Object node = this.jsonObj;
		for (int i=0; i<props.length; i++) {
			String prop = props[i];		
			node = this.getByProperty(prop, node);
		}
		return node;
	}
	
	private Object getByProperty(String prop, Object node) {
		Object result = null;
		if (prop.startsWith("'")) {
			JSONObject obj = (JSONObject)node;
			prop = prop.substring(1, prop.length()-1);
			result = obj.get(prop);
		} else {
			int index = Integer.parseInt(prop);
			JSONArray ary = (JSONArray)node;
			result = ary.get(index);
		}
		return result;
	}
	
	// convert "prop1[0].prop2['prop3']" to "['prop1'][0]['prop2']['prop3']
	private String format(String chain) {
		String result = "";
		boolean charInArray = false;
		boolean charInProperty = false;
		if (!chain.startsWith("[") && !chain.startsWith(".")) {
			chain = "." + chain;
		}
		for (int index=0; index<chain.length(); index++) {
			char ch = chain.charAt(index);
			if (ch == '[') {
				charInArray = true;
				if (charInProperty) {
					result += "']";
					charInProperty = false;
				}
			} else if (ch == ']') {
				charInArray = false;
			}
			
			if (!charInArray) {
				if (ch == '.') {
					if (!charInProperty) {
						result += "['";
						charInProperty = true;
					} else {
						result += "']['";
					}
				} else {
					result += ch;
				}
			} else {
				result += ch;
			}
		}
		if (charInProperty) {
			result += "']";
			charInProperty = false;
		}
		return result;
	}
}
