package core.enemy;

import org.jbox2d.dynamics.Body;

import processing.core.PGraphics;

public abstract class Enemy {
	
	public static int SENSOR_ID = 6;
	
	public abstract boolean update(float delta);
	public abstract void draw(PGraphics g);
	
	public abstract void hit(float val);
	
	public abstract Body getBody();
}
