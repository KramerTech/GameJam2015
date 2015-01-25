package core.enemy;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.contactlistener.SensorData;
import core.contactlistener.SensorSwitch;
import core.level.Level;
import core.player.Player;

public class BunnyEnemy extends Enemy  {

	private float health = 5;
	private World world;
	private boolean direction = false;
	private float jumpTimer = 0;
	private Body body;
	private SensorSwitch left, right, down;
	private Body target;

	public BunnyEnemy(Vec2 pos, World world, Body target) {
		this.target = target;
		this.world = world;
		BodyDef bd = new BodyDef();
		bd.position.set(pos.x/32.0f, pos.y/32.0f);
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;

		CircleShape cs = new CircleShape();
		//ps.setAsBox(1.5f/2f, .5f);
		cs.m_radius = .5f;
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = .5f;
		fd.friction = 0f;
		fd.restitution = 0f;
		fd.userData = new SensorData(Enemy.SENSOR_ID, this);
		
		body = world.createBody(bd);
		body.createFixture(fd);
		
		
		left = new SensorSwitch(new int[] {Enemy.SENSOR_ID, Level.LEVEL_SENSOR_ID});
		right = new SensorSwitch(new int[] {Enemy.SENSOR_ID, Level.LEVEL_SENSOR_ID});
		down = new SensorSwitch(new int[] {Enemy.SENSOR_ID, Level.LEVEL_SENSOR_ID, Player.PLAYER_SENSOR_ID});		
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(.4f, .4f, new Vec2(-.5f, 0), 0);

		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.isSensor = true;
		fd.userData = left.getSensorData();
		
		body.createFixture(fd);
		
		ps = new PolygonShape();
		ps.setAsBox(.4f, .4f, new Vec2(.5f, 0), 0);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.isSensor = true;
		fd.userData = right.getSensorData();
		
		body.createFixture(fd);
		
		ps = new PolygonShape();
		ps.setAsBox(.4f, .4f, new Vec2(0, .5f), 0);
		
		fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.isSensor = true;
		fd.userData = down.getSensorData();
		
		body.createFixture(fd);
	}
	
	@Override
	public boolean update(float delta) {
		
		if (right.touches > 0)
			direction = false;
		if (left.touches > 0)
			direction = true;
		if (target != null) {
			if (target.getPosition().x < body.getPosition().x) {
				direction = false;
			} else {
				direction = true;
			}
		}
		jumpTimer -= delta;
		if (down.touches > 0) {
			body.setLinearVelocity(new Vec2(body.getLinearVelocity().x * .5f, body.getLinearVelocity().y));
		} else {
			body.applyLinearImpulse(new Vec2(direction ? .1f : -.1f, 0), new Vec2(0, 0));
		}
		if (jumpTimer < 0 && down.touches > 0) {
			jumpTimer = (float) (20 + Math.random()*50);
			body.applyLinearImpulse(new Vec2(direction ? 5 : -5, -5), new Vec2(0, 0));
		}
		
		if (health <= 0) {
			world.destroyBody(body);
			world = null;
			return true;
		}
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
			g.fill(255, 0, 0);
			g.rect(body.getPosition().x*32, body.getPosition().y*32, 32, 32);
		g.popMatrix();
	}

	@Override
	public void hit(float val) {
		health -= val;
	}
	
	public Body getBody() {
		return body;
	}

}
