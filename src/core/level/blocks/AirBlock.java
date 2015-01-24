package core.level.blocks;

import processing.core.PGraphics;

public class AirBlock extends Block {

	@Override
	public void draw(PGraphics g) {
		return;
	}

	@Override
	public void setFrame(int frame) {
		return;
	}
	
	public boolean isSolid() {
		return false;
	}

}
