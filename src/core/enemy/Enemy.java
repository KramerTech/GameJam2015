package core.enemy;

import processing.core.PGraphics;

public abstract class Enemy {
	
	public abstract void update(float delta);
	public abstract void draw(PGraphics g);
	
	public abstract void hit(float val);
}
