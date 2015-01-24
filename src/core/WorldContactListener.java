package core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import core.level.Level;
import core.player.Player;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		
		if (hasID(contact, Player.FEET_SENSOR_ID, Level.LEVEL_SENSOR_ID))
				footContacts++;
	}

	@Override
	public void endContact(Contact contact) {
		if (hasID(contact, Player.FEET_SENSOR_ID, Level.LEVEL_SENSOR_ID))
			footContacts--;
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

	private Fixture getFeetFixture(Fixture f1, Fixture f2) {
        if (f1.getUserData() != null && (Integer) f1.getUserData() == Player.FEET_SENSOR_ID) {
            return f1;
        } else if (f2.getUserData() != null && (Integer) f2.getUserData() == Player.FEET_SENSOR_ID) {
            return f2;
        }
        return null;
    }
	
	private boolean hasID(Contact contact, int id1, int id2) {
		if (contact.getFixtureA().getUserData() == null || contact.getFixtureB().getUserData() == null)
			return false;
		
		if ((Integer) contact.getFixtureA().getUserData() == id1 && (Integer) contact.getFixtureB().getUserData() == id2)
			return true;
		if ((Integer) contact.getFixtureA().getUserData() == id2 && (Integer) contact.getFixtureB().getUserData() == id1)
			return true;
		
		return false;
	}
	
}
