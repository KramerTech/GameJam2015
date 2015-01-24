package core;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class PhysicsTest {

	public static void main(String[] args) {
		
		Vec2 gravity = new Vec2(1.0f, -10.0f);
		boolean doSleep = true;
		World world = new World(gravity, doSleep);
		
		//body definition
		BodyDef bd = new BodyDef();
		bd.position.set(50, 50);  
		bd.type = BodyType.DYNAMIC;
		 
		//define shape of the body.
		CircleShape cs = new CircleShape();
		cs.m_radius = 0.5f;  
		 
		//define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.5f;
		fd.friction = 0.3f;        
		fd.restitution = 0.5f;
		 
		//create the body and add fixture to it
		Body body =  world.createBody(bd);
		body.createFixture(fd);
		
		//body definition
		BodyDef bd2 = new BodyDef();
		bd2.position.set(50, 50);  
		bd2.type = BodyType.DYNAMIC;
		 
		//define shape of the body.
		CircleShape cs2 = new CircleShape();
		cs2.m_radius = 0.5f;  
		 
		//define fixture of the body.
		FixtureDef fd2 = new FixtureDef();
		fd2.shape = cs2;
		fd2.density = 0.5f;
		fd2.friction = 0.3f;        
		fd2.restitution = 0.5f;
		 
		//create the body and add fixture to it
		Body body2 =  world.createBody(bd2);
		body.createFixture(fd2);
		
		WeldJointDef wd = new WeldJointDef();
		wd.bodyA = body;
		wd.bodyB = body2;
        wd.localAnchorB.set(new Vec2(4, 0));
		
		world.createJoint(wd);
		
		body2.setLinearVelocity(new Vec2(1, 0));
		
		float timeStep = 1.0f / 60.f;
		int velocityIterations = 6;
		int positionIterations = 2;
		 
		for (int i = 0; i < 60; ++i) {
		world.step(timeStep, velocityIterations, positionIterations);
		      Vec2 position = body.getPosition();
		      float angle = body.getAngle();
		      System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
		      position = body2.getPosition();
		      angle = body.getAngle();
		      System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
		}
	}

}
