package core.level.blocks;

import processing.core.PGraphics;

public class GrassBlock extends Block {

	@Override
	public void draw(PGraphics g) {
		g.fill(80, 200, 0);
		g.rect(0, 0, 32, 32);
	}

	@Override
	public void setFrame(int frame) {
		return;
	}
	
	public boolean isSolid() {
		return true;
	}

}
