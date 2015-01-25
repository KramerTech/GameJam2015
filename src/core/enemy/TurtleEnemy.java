package core.enemy;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import core.contactlistener.SensorData;
import core.contactlistener.TouchListener;
import core.projectile.Projectile;
import processing.core.PGraphics;

public class TurtleEnemy extends Enemy implements TouchListener {

	private float health = 3;
	private World world;
	private boolean direction = false;
	private Body body;

	public TurtleEnemy(Vec2 pos, World world) {
		this.world = world;
		BodyDef bd = new BodyDef();
		bd.position.set(pos.x/32.0f, pos.y/32.0f);
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(1.5f/2f, .5f);
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .5f;
		fd.friction = 0f;
		fd.restitution = 0f;
		fd.userData = new SensorData(Enemy.SENSOR_ID, this);
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		ps = new PolygonShape();
		ps.setAsBox(.1f, .1f, new Vec2(-1, 0), 0);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.userData = new SensorData(TouchListener.SENSOR_ID, this, 1);
		
		body.createFixture(fd);
		
		ps = new PolygonShape();
		ps.setAsBox(.1f, .1f, new Vec2(1, 0), 0);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.userData = new SensorData(TouchListener.SENSOR_ID, this, 2);
		
		body.createFixture(fd);
	}
	
	@Override
	public boolean update(float delta) {
		if (health <= 0) {
			world.destroyBody(body);
			return true;
		}
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
			g.fill(255, 0, 255);
			g.rect(body.getPosition().x*32, body.getPosition().y*32, 48, 32);
		g.popMatrix();
	}

	@Override
	public void hit(float val) {
		health -= val;
	}

	@Override
	public void touch(SensorData a, SensorData b) {
		System.out.println(a.extra);
	}
		


}
