package core.buttons;

import org.jbox2d.dynamics.Body;

import processing.core.PGraphics;
import core.Entity.Entity;
import core.Entity.Toggled;
import core.contactlistener.SensorSwitch;
import editor.World;

public class Button extends Entity implements Toggled {

	public int r, g, b;
	public int x, y;
	public Body body;
	public SensorSwitch buttonSensor;
	public World world;
	
	public Button(int x, int y, int r, int g, int b, World world) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		
		
	}
	
	@Override
	public boolean update(float delta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(PGraphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getState() {
		return false;
	}

}
