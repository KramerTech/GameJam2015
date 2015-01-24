package editor;

import processing.core.PApplet;

public interface Element {

	public void draw(PApplet g);
	public boolean contains(int x, int y, int mode, PApplet g);
}
