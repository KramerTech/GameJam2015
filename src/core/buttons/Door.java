package core.buttons;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.Entity.Entity;
import core.Entity.Toggled;
import core.contactlistener.SensorData;
import core.contactlistener.SensorSwitch;
import core.enemy.Enemy;
import core.level.Level;
import core.player.Player;

public class Door extends Entity {

	public static final int SENSOR_ID = 7;
	
	public int r, g, b;
	public int x, y;
	public int height;
	public Body body;
	public Fixture fx;
	public World world;
	public Toggled toggle;
	public float size;
	
	public Door(int x, int y,int h, int r, int g, int b, Toggled toggle, World world) {
		this.x = x;
		this.y = y;
		this.height = h;
		this.r = r;
		this.g = g;
		this.b = b;
		this.toggle = toggle;
		
		size = height;
		
		this.world = world;
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.STATIC;
		bd.fixedRotation = true;
		
		body = world.createBody(bd);
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(.5f, .5f*height, new Vec2(0, 0), 0);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 1f;
		fd.friction = 0f;
		fd.userData = new SensorData(SENSOR_ID, this);
		fd.isSensor = false;
		
		body.createFixture(fd);
		
	}
	
	@Override
	public boolean update(float delta) {
		if (toggle.getState()) {
			size = Math.max(size - .05f, 0);
		} else {
			size = Math.min(size + .05f, height);
		}
		world.destroyBody(body);
		
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.STATIC;
		bd.fixedRotation = true;
		
		body = world.createBody(bd);
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(.5f, .5f*size, new Vec2(0, -size/2+.5f), 0);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 1f;
		fd.friction = 0f;
		fd.userData = new SensorData(Level.LEVEL_SENSOR_ID, this);
		fd.restitution = 1f;
		fd.isSensor = false;
		
		body.createFixture(fd);
		
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
			g.fill(r, this.g, b);
			g.rect(x*32, y*32-size*16+16, 29, size*32);
		g.popMatrix();
	}

}
