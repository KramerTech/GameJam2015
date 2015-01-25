package editor;

import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import core.level.blocks.Block;
import core.level.blocks.Property;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Bar implements Element {

	
	private int BLACK = 0xff000000;
	private int WHITE = 0xffffffff;
	private int GRAY = 0xffcccccc;
	
	private int mouseOver = -1;
	
	private ArrayList<Block> blocks;
	
	public Bar() {
		blocks = new ArrayList<Block>();
		try {
			Scanner in = new Scanner(new File("res/pallete"));
			Scanner line;
			while (in.hasNextLine()) {
				String l = in.nextLine().toUpperCase();
				line = new Scanner(l);
				Property[] p = new Property[Property.PROPERTY_COUNT];
				while (line.hasNextInt()) {
					int i = line.nextInt();
					if (i == Property.COLOR)
						p[i] = new Property(i, (int) line.nextLong(16));
					else
						p[i] = new Property(i, line.nextInt());
				}
				blocks.add(Block.getBlock(p));
				line.close();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		block = blocks.get(0);
	}
	
	private Block block;
	private int selector = 0; 
	
	public void draw(PApplet g) {
		int x = g.width / 10;
		
		g.noStroke();
		g.fill(255,255,255);
		g.rect(0, 0, x, g.width);
		
		x = x - 2;
		
		int ydelta = g.height / blocks.size();
		for (int y = 0; y < blocks.size(); y++) {
			g.fill(WHITE);
			if (y == mouseOver) g.fill(GRAY);
			g.rect(0, ydelta * y, x, ydelta);
			g.fill(BLACK);
			g.rect(4, ydelta * y + 4, x - 8, ydelta - 8);
			g.fill(blocks.get(y).get(Property.COLOR));
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
		int i = g.mouseY / (g.height / blocks.size());
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
		if (i >= 0 && i < blocks.size()) {
			selector = i;
			block = blocks.get(i);
		}
	}
	
	
}
