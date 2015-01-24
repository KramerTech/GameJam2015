package core.level;

import core.level.blocks.AirBlock;
import core.level.blocks.Block;
import processing.core.PGraphics;

public class Level {

	private int width, height;
	
	private Block[][] level;
	
	public Level(int w, int h) {
		this.width = w;
		this.height = h;
		level = new Block[w][h];
		AirBlock air = new AirBlock();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				level[x][y] = air;
			}
		}
	}
	
	public Block getBlock(int x, int y) {
		return level[x][y];
	}
	
	public void draw(PGraphics g, int x, int y, int w, int h) {
		Block curBlock;
		for (int xx = x; xx < x+w; xx++) {
			for (int yy = y; yy < y+h; yy++) {
				if (xx >= 0 && xx < w && yy >= 0 && yy < h) {
					curBlock = level[xx][yy];
					if (curBlock != null) {
						g.pushMatrix();
							g.translate(xx*32, yy*32);
							curBlock.draw(g);
						g.popMatrix();
					}
				}
			}
		}
	}
}
