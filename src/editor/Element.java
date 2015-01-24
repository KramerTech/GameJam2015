package editor;

import java.awt.event.MouseWheelEvent;

import processing.core.PApplet;
import processing.event.MouseEvent;

public interface Element {

	public void draw(PApplet g);
	public void mouse(MouseEvent e, PApplet g);
	public void mouseWheel(MouseWheelEvent e, PApplet g);
}
