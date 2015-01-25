package core.contactlistener;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import core.enemy.BunnyEnemy;
import core.enemy.Enemy;
import core.enemy.TurtleEnemy;
import core.level.Level;
import core.level.blocks.EndFlagBlock;
import core.player.Player;
import core.projectile.Projectile;

public class WorldContactListener implements ContactListener {
		
	@Override
	public void beginContact(Contact contact) {
		if (hasID(contact, Player.FEET_SENSOR_ID, Level.LEVEL_SENSOR_ID))
				footContacts++;
		
		if (hasID(contact, Player.PLAYER_SENSOR_ID, Projectile.SENSOR_ID)) {
			SensorData p = getDataFromID(contact, Projectile.SENSOR_ID);
			((Projectile) p.actor).setHit();
		}
		
		if (hasID(contact, Enemy.SENSOR_ID, Projectile.SENSOR_ID)) {
			SensorData p = getDataFromID(contact, Projectile.SENSOR_ID);
			((Projectile) p.actor).setHit();
			p = getDataFromID(contact, Enemy.SENSOR_ID);
			((Enemy) p.actor).hit(1);
		}
		
		SensorData data;
		if ((data = getDataFromID(contact, TouchListener.SENSOR_ID)) != null) {
			((TouchListener) data.actor).startTouch(data, (SensorData) ((data == contact.getFixtureA().getUserData()) ? contact.getFixtureB().getUserData() : contact.getFixtureA().getUserData()));
		}
		
		if (hasID(contact, Player.PLAYER_SENSOR_ID, BunnyEnemy.SENSOR_ID)) {
			((Player) getDataFromID(contact, Player.PLAYER_SENSOR_ID).actor).kill();
		}
		
		if (hasID(contact, Player.PLAYER_SENSOR_ID, TurtleEnemy.SENSOR_ID)) {
			((Player) getDataFromID(contact, Player.PLAYER_SENSOR_ID).actor).kill();
		}
		
		if (hasID(contact, Player.PLAYER_SENSOR_ID, EndFlagBlock.SENSOR_ID)) {
			((Player) getDataFromID(contact, Player.PLAYER_SENSOR_ID).actor).win();
		}
	}

	@Override
	public void endContact(Contact contact) {
		if (hasID(contact, Player.FEET_SENSOR_ID, Level.LEVEL_SENSOR_ID))
			footContacts--;
		
		SensorData data;
		if ((data = getDataFromID(contact, TouchListener.SENSOR_ID)) != null) {
			((TouchListener) data.actor).endTouch(data, (SensorData) ((data == contact.getFixtureA().getUserData()) ? contact.getFixtureB().getUserData() : contact.getFixtureA().getUserData()));
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public int footContacts = 0;

	
	private boolean hasID(Contact contact, int id1, int id2) {
		if (contact.getFixtureA().getUserData() == null || contact.getFixtureB().getUserData() == null)
			return false;
		
		if (((SensorData) contact.getFixtureA().getUserData()).value == id1 && ((SensorData) contact.getFixtureB().getUserData()).value == id2)
			return true;
		if (((SensorData) contact.getFixtureA().getUserData()).value == id2 && ((SensorData) contact.getFixtureB().getUserData()).value == id1)
			return true;
		
		return false;
	}
	
	private SensorData getDataFromID(Contact contact, int id) {
		if (((SensorData) contact.getFixtureA().getUserData()).value == id)
			return (SensorData) contact.getFixtureA().getUserData();
		if (((SensorData) contact.getFixtureB().getUserData()).value == id)
			return (SensorData) contact.getFixtureB().getUserData();
		return null;
		
	}
	
}
