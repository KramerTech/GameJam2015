package core;

import processing.core.PGraphics;
import core.level.Level;
import core.player.Player;

public class World {

	private Level level;
	private Player player;
	
	public boolean playerLeft, playerRight, playerJump;
	
	public World(Level level, Player player) {
		this.level = level;
		this.player = player;
		resetKeys();
	}
	
	public void draw(PGraphics g) {
		
		level.draw(g, 0, 0, 20, 20);
		drawPlayer(g);
		
	}
	
	public void update(float delta) {
		player.update(delta);
		player.doMovement(playerRight, playerLeft, playerJump);
	}
	
	public void drawPlayer(PGraphics g) {
		g.pushMatrix();
			g.translate(player.getX(), player.getY());
			player.draw(g);
		g.popMatrix();
	}
	
	public void resetKeys() {
		playerLeft = false;
		playerRight = false;
		playerJump = false;
	}
}
