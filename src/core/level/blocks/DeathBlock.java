package core.level.blocks;

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
import core.level.Level;
import core.player.Player;

public class DeathBlock extends Entity {

	public static final int SENSOR_ID = 9;
	
	public int x, y;
	public Body body;
	public SensorSwitch switchSensor;
	public World world;
	
	public DeathBlock(int x, int y, World world) {
		this.x = x;
		this.y = y;
		
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
		
	} //Added comment
	
	@Override
	public boolean update(float delta) {
		if (switchSensor.touches > 0) {
			if (switchSensor.lastData.actor instanceof Player) {
				((Player) switchSensor.lastData.actor).kill();
			}
		}
		return false;
	}

	@Override
	public void draw(PGraphics g) {
	}
}
