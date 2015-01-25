package editor;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class LevelEditor extends PApplet {

	private static final long serialVersionUID = 1L;
	Bar bar;
	World world;
	
	private List<Element> drawers;
	
	public LevelEditor() {
		drawers = new ArrayList<Element>();
		
		world = new World();
		drawers.add(world);
		bar = new Bar();
		drawers.add(bar);
	}
	
	
	public void setTitle(String title) {
		if (title == null) return;
		frame.setTitle(title);
	}
	
	public void setup() {
		size((int) (displayWidth * .45), (int) (displayHeight * .45));
		frameRate(30);
		colorMode(RGB, 255);
		frame.setResizable(true);
		frame.setTitle("untitled.lvl");
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		for (Element element : drawers)
			element.mouseWheel(e, this);
	}
	public void mouseEvent(MouseEvent e) {
		for (Element element : drawers)
			element.mouse(e, this);
	}
	
	
	public Block getBlock() {
		return bar.getBlock();
	}
	
	
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		if (key >= '1' && key <= '9')
			bar.setBlock(key - '1');
		switch (key) {
		case ' ': world.undo(); break;
		case 'r': world.noScroll(); break;
		case 's': setTitle(Save.save(world)); break;
		case 'a': setTitle(Save.saveAs(world)); break;
		default: super.keyPressed(e);
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {mouseEvent(e);}
	public void mousePressed(MouseEvent e) {mouseEvent(e);}
	public void mouseReleased(MouseEvent e) {mouseEvent(e);}
	public void mouseMoved(MouseEvent e) {mouseEvent(e);}
	
	
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
