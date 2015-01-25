package core.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;


import org.jbox2d.dynamics.World;

import core.contactlistener.SensorData;
import processing.core.PGraphics;

public class BounceyBall extends Projectile {
	
	private float life = 80;
	private World world;

	public BounceyBall(Vec2 pos, Vec2 vel, World world) {
		this.world = world;
		BodyDef bd = new BodyDef();
		bd.position.set(pos.x/32.0f, pos.y/32.0f);
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;

		CircleShape cs = new CircleShape();
		cs.m_radius = 0.2f;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = .5f;
		fd.friction = 0f;
		fd.restitution = .8f;
		fd.userData = new SensorData(SENSOR_ID, this);
		
		super.body = world.createBody(bd);
		super.body.createFixture(fd);
		super.body.setLinearVelocity(vel);
	}
	
	@Override
	public boolean update(float delta) {
		life -= delta;
		if (life <= 0 || isHit()) {
			world.destroyBody(super.body);
			return true;
		}
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
			g.fill(0, 0, 255);
			g.ellipse(body.getPosition().x*32, body.getPosition().y*32, 20, 20);
		g.popMatrix();
	}

}
