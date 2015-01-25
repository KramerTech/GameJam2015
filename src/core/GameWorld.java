package core;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.Entity.Entity;
import core.contactlistener.WorldContactListener;
import core.enemy.Enemy;
import core.level.Level;
import core.player.Player;
import core.projectile.BounceyBall;
import core.projectile.Projectile;

public class GameWorld {

	private Level level;
	public Player player;
	
	public World world;
	
	private WorldContactListener worldCL;
	
	private float camX, camY;
	
	public boolean playerLeft, playerRight, playerJump, playerShoot;

	public ArrayList<Entity> entities;
	
	public GameWorld(Level level, Player player) {
		this.level = level;
		this.player = player;
		
		player.setWorld(this);
		
		resetKeys();
		
		Vec2 gravity = new Vec2(0.0f, 30.0f);
		boolean doSleep = true;
		world = new World(gravity, doSleep);
		
		worldCL = new WorldContactListener();
		world.setContactListener(worldCL);
		
		player.initPhysics(world);
		level.initPhysics(world);
		
		entities = new ArrayList<Entity>();
	}
	
	public void draw(PGraphics g) {
		g.pushMatrix();
			float offsetX = -player.getX();
			float offsetY = -player.getY();
			
			float bound = 100;
			
			if (offsetX > camX + bound)
				camX = offsetX - bound;
			
			if (offsetX < camX - bound)
				camX = offsetX + bound;
			
			if (offsetY > camY + bound)
				camY = offsetY - bound;
			
			if (offsetY < camY - bound)
				camY = offsetY + bound;
			
			g.translate(camX+g.width/2, camY+g.height/2);
			
			int renderHeight = 20;
			int renderWidth = 40;
		
			level.draw(g, (int)(-camX/32-renderWidth/2), (int) (-camY/32-renderHeight/2), renderWidth, renderHeight);
			drawPlayer(g);
			
			for (Entity e : entities) {
				e.draw(g);
			}
		g.popMatrix();
	}
	
	public void update(float delta) {
		if (delta > 2)
			delta = 2;
		
		world.step(delta/45.0f, 12, 4);
		
		player.doMovement(playerRight, playerLeft, playerJump, worldCL.footContacts, playerShoot);
		playerShoot = false;
		player.update(delta);	
		
		Iterator<Entity> i3 = entities.iterator();
		while (i3.hasNext()) {
			Entity e = i3.next();
			if (e.update(delta))
				i3.remove();
		}
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
	
	public void shootProjectile(Projectile p) {
		entities.add(p);
	}
}
