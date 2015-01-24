package core;

import javax.swing.ImageIcon;

import core.level.Level;
import core.player.Player;
import processing.core.PApplet;

public class Main extends PApplet {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		PApplet.main(new String[] {"core.Main" });
	}

	public World world1;
	public long lastTime;

	public void setup() {
		size((int) (displayWidth * 0.45), (int) (displayHeight * 0.45));
		if (frame != null) {
			frame.setIconImage(new ImageIcon("res/img/ggj.png").getImage());
			frame.setTitle("GameJam!");
			frame.setResizable(true);
		}
		colorMode(RGB, 255);
		frameRate(60);
		smooth();
		
		Player p = new Player(10, 10);
		Level l = new Level(20, 20);
		world1 = new World(l, p);
		
		lastTime = this.millis();
	}


	public void draw() {
		float delta = getNewDelta();
		clear();
		background(0);
		world1.draw(g);
		world1.update(delta);
		frame.setTitle(delta + "");
	}
	
	public float getNewDelta() {
		long newTime = System.nanoTime();
		long diff = newTime - lastTime;
		lastTime = newTime;
		return (float) (diff/16000000.0);
	}
	
	public void keyReleased() {
		if (key == 'a') {
			world1.playerLeft = false;
		}
		if (key == 'd') {
			world1.playerRight = false;
		}
	}
	
	public void keyPressed() {
		if (key == 'a') {
			world1.playerLeft = true;
		}
		if (key == 'd') {
			world1.playerRight = true;
		}
	}
}
