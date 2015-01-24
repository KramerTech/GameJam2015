package core.level.blocks;

import processing.core.PGraphics;

public abstract class Block {

	public abstract void draw(PGraphics g);
	public abstract void setFrame(int frame);
	public abstract boolean isSolid();
}
