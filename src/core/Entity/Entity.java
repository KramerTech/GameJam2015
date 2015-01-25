package core.Entity;

import processing.core.PGraphics;

public abstract class Entity {
	public abstract boolean update(float delta);
	public abstract void draw(PGraphics g);
}
