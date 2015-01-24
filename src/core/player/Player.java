package core.player;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Player {
	
	public static final int WIDTH = 32, HEIGHT = 64;
	public static final float MAX_X_SPEED = 5;
	public static final float X_ACC = 1;

	private float x, y;
	private float vx, vy;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
	}
	
	public void draw(PGraphics g) {
		g.fill(255, 0, 0);
		g.rect(0, 0, WIDTH, HEIGHT);
	}
	
	public void update(float delta) {
		controlSpeed();
		x += vx * delta;
		y += vy * delta;
//		vy += .5f;
//		if (y > 100) {
//			vy = -vy*.9f;
//			y = 100;
//		}
		
	}
	
	public void controlSpeed() {
		if (vx > MAX_X_SPEED)
			vx = MAX_X_SPEED;
		if (vx < -MAX_X_SPEED)
			vx = -MAX_X_SPEED;
	}
	
	public void doMovement(boolean right, boolean left, boolean jump) {
		if (right) {
			vx += X_ACC;
		} else if (left) {
			vx -= X_ACC;
		} else {
			vx = vx * .9f;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
