package core.level;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import core.level.blocks.AirBlock;
import core.level.blocks.Block;
import core.level.blocks.GrassBlock;
import processing.core.PGraphics;

public class Level {

	private int width, height;
	
	private Block[][] level;
	
	public Level(int w, int h) {
		this.width = w;
		this.height = h;
		level = new Block[w][h];
		clear();
	}
	
	public Block getBlock(int x, int y) {
		if (x > 0 && y > 0 && x < width && y < height)
			return level[x][y];
		
		return null;
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

	public void setBlock(int i, int j, GrassBlock gb) {
		if (i >= 0 && j >= 0 && i < width && j < height)
			level[i][j] = gb;
	}
	
	public void clear() {
		AirBlock air = new AirBlock();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				level[x][y] = air;
			}
		}
	}
	
	public void initPhysics(World world) {
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (level[i][j].isSolid()) {
					BodyDef bd = new BodyDef();
					bd.position.set(i, j);
					bd.type = BodyType.STATIC;
					
					PolygonShape ps = new PolygonShape();
					ps.setAsBox(.49f, .49f);
					
					FixtureDef fd = new FixtureDef();
					fd.shape = ps;
					fd.density = .5f;
					fd.friction = 0f;
					fd.restitution = 0f;
					
					world.createBody(bd).createFixture(fd);
				}
			}
		}
	}
}
