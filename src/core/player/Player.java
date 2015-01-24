package core.player;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.JointType;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.jbox2d.*;

import core.WorldContactListener;
import core.level.Level;
import core.level.blocks.Block;
import core.level.blocks.GrassBlock;
import processing.core.PApplet;
import processing.core.PGraphics;
import sound.SoundPlayer;

public class Player {
	
	public static final int WIDTH = 32, HEIGHT = 64;
	public static final float MAX_X_SPEED = 5;
	public static final float X_ACC = 1;
	public static final int FEET_SENSOR_ID = 2;
	public static final int PLAYER_SENSOR_ID = 3;

	private float x, y;
	private float vx, vy;
	
	private Body playerBody;
	private Body playerFeet;
	
	public boolean canJump = true;
	
	private SoundPlayer sp;
	
	public Player(float x, float y, SoundPlayer sp) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		
		this.sp = sp;
	}
	
	public void draw(PGraphics g) {
		g.fill(255, 0, 0);
		g.rect(0, 0, WIDTH, HEIGHT);
	}
	
	public void update(float delta) {
		playerBody.setLinearVelocity(new Vec2(playerBody.getLinearVelocity().x*.6f, playerBody.getLinearVelocity().y));
	}
	
	public void doMovement(boolean right, boolean left, boolean jump, int footContacts) {
		if (right) {
			playerBody.applyLinearImpulse(new Vec2(3, 0), new Vec2(0, 0));
		} else if (left) {
			playerBody.applyLinearImpulse(new Vec2(-3, 0), new Vec2(0, 0));
		}
		
		if (footContacts > 0)
			canJump = true;
		
		float totalYImpulse = 0;
		if (footContacts > 0 && Math.abs(playerBody.getLinearVelocity().y) < .01) {
			totalYImpulse = -10;
		}
		if (jump && canJump) {
			playerBody.setLinearVelocity(new Vec2(0, 0));
			totalYImpulse = -20;
			canJump = false;
			sp.play("jump");
		}
		playerBody.applyLinearImpulse(new Vec2(0, totalYImpulse), new Vec2(0, 0));
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
		fd.userData = PLAYER_SENSOR_ID;
		
		playerBody = world.createBody(bd);
		playerBody.createFixture(fd);
		playerBody.setLinearDamping(1f);

		
		//body definition
		BodyDef bd2 = new BodyDef();
		bd.position.set(x/32.0f, y/32.0f);
		bd2.type = BodyType.DYNAMIC;
		 
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
		fd2.userData = FEET_SENSOR_ID;

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