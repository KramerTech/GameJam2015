package editor;

import processing.core.PApplet;

public class Bar implements Element {

	public Bar() {
		
	}
	
	public boolean contains(int x, int y, int mode, PApplet g) {
		if (x > (int) (g.width * .1))
			return false;
		return true;
	}
	
	
	public void draw(PApplet g) {
		int x = (int) (g.width * .1);
		g.noStroke();
		g.fill(255,255,255);
		g.rect(0, 0, x, g.width);
		g.fill(0,0,0);
		g.rect(x - 2, 0, 2, g.height);
	}
	
	
}
