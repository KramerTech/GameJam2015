package core.projectile;

import org.jbox2d.dynamics.Body;

import core.Entity.Entity;
import processing.core.PGraphics;

public abstract class Projectile extends Entity {
	
	public static final int SENSOR_ID = 5;
	
	protected Body body;
	protected boolean hit;

	public abstract boolean update(float delta);
	public abstract void draw(PGraphics g);
	
	public void setHit() {
		hit = true;
	}
	
	public boolean isHit() {
		return hit;
	}
}
