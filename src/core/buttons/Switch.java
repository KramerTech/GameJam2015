package core.buttons;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PGraphics;
import core.Entity.Entity;
import core.Entity.Toggled;
import core.contactlistener.SensorSwitch;
import core.player.Player;

public class Switch extends Entity implements Toggled {

	public int r, g, b;
	public int x, y;
	public boolean dir;
	public Body body;
	public SensorSwitch switchSensor;
	public World world;
	public int state;
	
	public Switch(int x, int y, boolean dir, int r, int g, int b, World world) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		this.dir = dir;
		
		this.world = world;
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.STATIC;
		bd.fixedRotation = true;
		body = world.createBody(bd);
		
		
		switchSensor = new SensorSwitch(new int[] {
				Player.PLAYER_SENSOR_ID,
				});
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(.5f, .5f, new Vec2(0, 0), 0);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = .05f;
		fd.friction = 0f;
		fd.isSensor = true;
		fd.userData = switchSensor.getSensorData();
		
		body.createFixture(fd);
		
	}
	
	@Override
	public boolean update(float delta) {
		switch (state) {
		case 0:
			if (switchSensor.touches > 0)
				state++;
			break;
		case 1:
			if (switchSensor.touches == 0)
				state++;
			break;
		case 2:
			if (switchSensor.touches > 0)
				state++;
			break;
		case 3:
			if (switchSensor.touches == 0)
				state = 0;
			break;
		}
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		g.pushMatrix();
			g.fill(r, this.g, b);
			int offs = dir ? -8 : 8;
			g.translate(x*32 - offs*2, y*32);
			float val = dir ? .5f : -.5f;
			g.rotate(getState() ? val : -val);
			g.translate(offs, 0);
			g.rect(0, 0, 16, 8);
		g.popMatrix();
	}

	@Override
	public boolean getState() {
		return state == 2 || state == 3;
	}

}
