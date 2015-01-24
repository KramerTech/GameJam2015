package editor;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class LevelEditor extends PApplet {

	private static final long serialVersionUID = 1L;
	Bar bar;
	
	private List<Element> drawers;
	
	public LevelEditor() {
		drawers = new ArrayList<Element>();
		drawers.add(new Bar());
		drawers.add(new World());
	}
	
	
	public void setup() {
		size((int) (displayWidth * .45), (int) (displayHeight * .45));
		frameRate(30);
		colorMode(RGB, 255);
		
	}
	
	
	private void mouseManager(int x, int y, int mode) {
		for (Element e : drawers) {
			if (e.contains(x, y, mode, this))
				return;
		}
	}
	
	
	public void mouseMoved() {
		mouseManager(mouseX, mouseY, 4);
	}
	
	public void mousePressed() {
		mouseManager(mouseX, mouseY, 0);
	}
	
	public void mouseReleased() {
		mouseManager(mouseX, mouseY, 1);
	}
	
	
	public void draw() {
		clear();
		background(0, 216, 216);
		for (Element d : drawers)
			d.draw(this);
	}
	
	
	public static void main(String[] args) {
		PApplet.main("editor.LevelEditor");
	}
	
}
