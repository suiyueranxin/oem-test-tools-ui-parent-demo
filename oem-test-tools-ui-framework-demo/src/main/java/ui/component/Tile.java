package ui.component;

public class Tile extends Span{
	
	public Tile(String path) {
		super(path);
	}
	
	public void open() {
		this.click();
	}
}
