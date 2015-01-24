package editor;

import java.util.ArrayList;

import processing.core.PApplet;

public class World implements Element {

	int maxx = 50;
	int maxy = 30;
	
	int zoom = 32;
	
	ArrayList<ArrayList<Integer>> world;
	
	
	private int scrollx = 0;
	private int scrolly = 0;
	
	public World() {
		world = new ArrayList<ArrayList<Integer>>();
		add(0, 0, 10);
	}
	
	
	public boolean contains(int x, int y, int mode, PApplet g) {
		if (x <= (int) (g.displayWidth * .1))
			return false;
		return true;
	}
	
	
	public void shift(int x, int y) {
		scrollx += x;
		scrolly += y;
		scrollx = Math.max(0, scrollx);
		scrolly = Math.max(0, scrolly);
	}
	
	private boolean grab = false;
	
	public void add(int x, int y, int block) {
		ArrayList<Integer> row;
		if (y < world.size()) {
			row = world.get(y);
		} else {
			row = new ArrayList<Integer>();
			world.add(y, row);
		}
		
		if (x < row.size()) {
			row.set(x, block);
		} else {
			row.add(x, block);
		}
	}
	
	
	public void bite(PApplet g, int block, int x, int y) {
		g.fill(30, 30, 30);
		g.rect(x - 30, y - 30, zoom * 10, zoom * 10);
	}
	
	
	public void draw(PApplet g) {
		int y = 0;
		for (ArrayList<Integer> row : world) {
			y += zoom;
			int x = 0;
			for (int block : row) {
				if (x > g.width) break;
				bite(g, block, x + (int) (g.width * .1), g.height - y);
				x += zoom;
			}
			if (y > g.height) break;
		}
	}
	
}
