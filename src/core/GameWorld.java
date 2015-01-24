package core;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.level.Level;
import core.player.Player;

public class GameWorld {

	private Level level;
	private Player player;
	
	private World world;
	
	private WorldContactListener worldCL;
	
	public boolean playerLeft, playerRight, playerJump;
	
	public GameWorld(Level level, Player player) {
		this.level = level;
		this.player = player;
		resetKeys();
		
		Vec2 gravity = new Vec2(0.0f, 30.0f);
		boolean doSleep = true;
		world = new World(gravity, doSleep);
		
		worldCL = new WorldContactListener();
		world.setContactListener(worldCL);
		
		player.initPhysics(world);
		level.initPhysics(world);
		

	}
	
	public void draw(PGraphics g) {
		
		level.draw(g, 0, 0, 20, 20);
		drawPlayer(g);
		
	}
	
	public void update(float delta) {
		if (delta > 2)
			delta = 2;
		world.step(delta/35.0f, 12, 4);
		player.update(delta);
		player.doMovement(playerRight, playerLeft, playerJump, worldCL.footContacts);
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
