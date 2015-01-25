package core.level;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.contactlistener.SensorData;
import core.level.blocks.Block;
import core.level.blocks.Property;

public class Level {
	
	public static final int LEVEL_SENSOR_ID = 4;

	private int width, height;
	private int bgType;
	
	private Block[][] level;
	
	public Level(int w, int h, int bgType) {
		this.width = w;
		this.height = h;
		
		this.bgType = bgType;
		
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
		drawBackground(g);
		for (int xx = x; xx < x+w; xx++) {
			for (int yy = y; yy < y+h; yy++) {
				if (xx >= 0 && yy >= 0 && xx < width && yy < height) {
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

	public void setBlock(int i, int j, Block gb) {
		if (i >= 0 && j >= 0 && i < width && j < height)
			level[i][j] = gb;
	}
	
	public void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				level[x][y] = null;
			}
		}
	}
	
	public void drawBackground(PGraphics g) {
		switch (this.bgType) {
			case 0: g.background(0,128,255); break;
			case 1: g.background(30,30,30); break;
		}
	}
	
	public void initPhysics(World world) {
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (level[i][j] != null && level[i][j].get(Property.COLLIDES) == 1) {
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
					fd.userData = new SensorData(LEVEL_SENSOR_ID);
					
					world.createBody(bd).createFixture(fd);
				}
			}
		}
	}

	public int getBgType() {
		return this.bgType;
	}
}
