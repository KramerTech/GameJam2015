package editor;

import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class Bar implements Element {

	private ArrayList<Integer> colors;
	
	private int BLACK = 0xff000000;
	private int WHITE = 0xffffffff;
	private int GRAY = 0xffcccccc;
	
	private int mouseOver = -1;
	
	private int[] COLORS = {
			0xff00ff00,
			0xffeac300,
			0xff6b2212,
			0xffa0a0a0,
			0xff000000,
			0xffffffff,
	};
	
	
	public Bar() {
		colors = new ArrayList<Integer>();
		for (int i : COLORS)
			colors.add(i);
	}
	
	private Block block = new Block(COLORS[0]);
	private int selector = 0; 
	
	public void draw(PApplet g) {
		int x = g.width / 10;
		
		g.noStroke();
		g.fill(255,255,255);
		g.rect(0, 0, x, g.width);
		
		x = x - 2;
		
		int ydelta = g.height / colors.size();
		for (int y = 0; y < colors.size(); y++) {
			g.fill(WHITE);
			if (y == mouseOver) g.fill(GRAY);
			g.rect(0, ydelta * y, x, ydelta);
			g.fill(BLACK);
			g.rect(4, ydelta * y + 4, x - 8, ydelta - 8);
			g.fill(colors.get(y));
			g.rect(5, ydelta * y + 5, x - 10, ydelta - 10);
			if (y == selector)
				g.rect(0, ydelta * y, x, ydelta);
				
		}
		/*
		g.fill(0, 0, 0, 0);
		g.strokeWeight(10);
		g.stroke(0xffffffff);
		g.rect(0, g.height / colors.size() * selector, x, g.height / colors.size());
		g.strokeWeight(1);		
		*/
		g.noStroke();
		g.fill(0,0,0);
		g.rect(x, 0, 2, g.height);
	}

	@Override
	public void mouse(MouseEvent e, PApplet g) {
		if (g.mouseX >= g.width / 10) {
			mouseOver = -1;
			return;
		}
		int i = g.mouseY / (g.height / colors.size());
		if (i != selector)
			mouseOver = i;
		else
			mouseOver = -1;
		if (e.getButton() == 37 && e.getAction() == MouseEvent.PRESS)
			setBlock(i);
	}

	@Override
	public void mouseWheel(MouseWheelEvent e, PApplet g) {
		// TODO Auto-generated method stub
		
	}

	
	public Block getBlock() {
		return block;
	}

	public void setBlock(int i) {
		if (i >= 0 && i < colors.size()) {
			selector = i;
			block = new Block(colors.get(i));
		}
	}
	
	
}
