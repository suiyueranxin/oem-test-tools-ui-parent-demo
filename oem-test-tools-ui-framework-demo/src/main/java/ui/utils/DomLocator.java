package ui.utils;

public class DomLocator {
	private String name; // locator name
	private String path; // path content type of css or xpath 
	private String type; //component type
	
	public DomLocator(String name, String type, String path) {
		this.name = name;
		this.type = type;
		this.path = path;
	}
	
	// path with parameter and need to be parsed by args (separator:,)
	public DomLocator(String name, String type, String path, String args) {
		this(name, type, path);
		this.formatPath(args);
	}
	
	public DomLocator(DomLocator original) {
		this.name = original.name;
		this.type = original.type;
		this.path = original.path;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void formatPath(String args) {
		this.setPath(String.format(path, args));
	}
	
	public void formatIframePath(String iframePath) {
		this.setPath(iframePath + ";" + path);
	}
}
