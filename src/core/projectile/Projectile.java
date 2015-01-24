package core.projectile;

import org.jbox2d.dynamics.Body;

import processing.core.PGraphics;

public abstract class Projectile {
	
	public static final int SENSOR_ID = 5;
	
	protected Body body;

	public abstract boolean update(float delta);
	public abstract void draw(PGraphics g);
}
