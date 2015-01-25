package core.player;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import processing.core.PImage;
import sound.SoundPlayer;
import core.GameWorld;
import core.Main;
import core.contactlistener.SensorData;
import core.projectile.BounceyBall;

public class Player {
	
	public static final int WIDTH = 32, HEIGHT = 96;
	public static final float MAX_X_SPEED = 5;
	public static final float X_ACC = 1;
	public static final int FEET_SENSOR_ID = 2;
	public static final int PLAYER_SENSOR_ID = 3;

	private float x, y;
	
	private boolean direction;
	
	public Body playerBody;
	
	private GameWorld world;
	
	public boolean canJump = true;
	
	private SoundPlayer sp;
	
	public Player(float x, float y, SoundPlayer sp, GameWorld world) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.sp = sp;
	}
	
	public void setWorld(GameWorld world) {
		this.world = world;
	}
	
	public void draw(PGraphics g) {
		g.noStroke();
		PImage sprite = direction ? Main.spriteR : Main.spriteL;
		g.beginShape();
		g.texture(sprite);
		g.vertex(-WIDTH / 2, -HEIGHT / 2, 0, 0);
		g.vertex(-WIDTH / 2, HEIGHT / 2, 0, sprite.height);
		g.vertex(WIDTH / 2, HEIGHT / 2, sprite.width, sprite.height);
		g.vertex(WIDTH / 2, -HEIGHT / 2, sprite.width, 0);
		g.endShape();
	}
	
	public void update(float delta) {
		playerBody.setLinearVelocity(new Vec2(playerBody.getLinearVelocity().x*.6f, playerBody.getLinearVelocity().y));
	}
	
	public void doMovement(boolean right, boolean left, boolean jump, int footContacts, boolean shoot) {
		if (right) {
			direction = true;
			playerBody.applyLinearImpulse(new Vec2(3, 0), new Vec2(0, 0));
		} else if (left) {
			direction = false;
			playerBody.applyLinearImpulse(new Vec2(-3, 0), new Vec2(0, 0));
		}
		
		if (footContacts > 0)
			canJump = true;
		
		float totalYImpulse = 0;
		if (footContacts > 0 && Math.abs(playerBody.getLinearVelocity().y) < .01) {
			totalYImpulse = -10;
		}
		if (jump && canJump) {
			playerBody.setLinearVelocity(new Vec2(playerBody.getLinearVelocity().x, 0));
			totalYImpulse = -20;
			canJump = false;
			sp.play("jump");
		}
		playerBody.applyLinearImpulse(new Vec2(0, totalYImpulse), new Vec2(0, 0));
		
		if (shoot) {
			world.shootProjectile(new BounceyBall(new Vec2(playerBody.getPosition().x*32 + (direction ? 40 : -40), playerBody.getPosition().y*32), new Vec2((direction ? 10 : -10), 0), playerBody.getWorld()));
		}
	}
	
	public float getX() {
		return playerBody.getPosition().x*32;
	}
	
	public float getY() {
		return playerBody.getPosition().y*32;
	}
	
	public void initPhysics(World world) {
		
		BodyDef bd = new BodyDef();
		bd.position.set(x/32.0f, y/32.0f);
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;

		PolygonShape ps = new PolygonShape();
		
		float corner = .2f;
		
		ps.set(new Vec2[] {new Vec2(.49f-corner, -.99f),
				new Vec2(.49f, -.99f+corner),
				new Vec2(.49f, .99f-corner),
				new Vec2(.49f-corner, .99f),
				new Vec2(-.49f+corner, .99f),
				new Vec2(-.49f, .99f-corner),
				new Vec2(-.49f, -.99f+corner),
				new Vec2(-.49f+corner, -.99f)}, 8 );
		
//		CircleShape cs = new CircleShape();
	//	cs.m_radius = 0.5f;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .5f;
		fd.friction = 0f;
		fd.restitution = 0f;
		fd.userData = new SensorData(PLAYER_SENSOR_ID);
		
		playerBody = world.createBody(bd);
		playerBody.createFixture(fd);
		playerBody.setLinearDamping(1f);

		
//		//body definition
//		BodyDef bd2 = new BodyDef();
//		bd.position.set(x/32.0f, y/32.0f);
//		bd2.type = BodyType.DYNAMIC;
		 
		//define shape of the body.
		PolygonShape cs2 = new PolygonShape();
		cs2.setAsBox(.45f, .05f, new Vec2(0, .95f), 0);  
		 
		//define fixture of the body.
		FixtureDef fd2 = new FixtureDef();
		fd2.shape = cs2;
		fd2.density = 0.1f;
		fd2.friction = 0.3f;        
		fd2.restitution = 0.5f;
		fd2.isSensor = true;
		fd2.userData = new SensorData(FEET_SENSOR_ID);

		playerBody.createFixture(fd2);
		
//		playerFeet = world.createBody(bd2);
//		playerFeet.createFixture(fd2);
//		playerFeet.setUserData(FEET_SENSOR_ID);
		
//		WeldJointDef wd = new WeldJointDef();
//		wd.bodyA = playerBody;
//		wd.bodyB = playerFeet;
//        wd.localAnchorB.set(new Vec2(0, 0));
//        wd.collideConnected = false;
//        
//		world.createJoint(wd);
		
	}

}