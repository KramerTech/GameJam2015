package core;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import core.level.Level;
import core.level.blocks.GrassBlock;
import core.player.Player;
import processing.core.PApplet;

public class Main extends PApplet {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		PApplet.main(new String[] {"core.Main" });
	}

	public GameWorld world1;
	public long lastTime;

	public void setup() {
		float scale = .7f;
		size((int) (displayWidth * scale), (int) (displayHeight * scale), P2D);
		if (frame != null) {
			frame.setIconImage(new ImageIcon("res/img/ggj.png").getImage());
			frame.setTitle("GameJam!");
			frame.setResizable(true);
		}
		colorMode(RGB, 255);
		frameRate(60);
		smooth();
		this.rectMode(CENTER);
		
		Player p = new Player(100, 100);
		Level l = new Level(20, 20);
		GrassBlock gb = new GrassBlock();
		for (int i = 0; i < 20; i++) {
			l.setBlock(i, 16, gb);
			l.setBlock(i, 0, gb);
			l.setBlock(0, i, gb);
			l.setBlock(16, i, gb);
		}
		l.setBlock(5, 4, gb);
		
		for (int i = 0; i < 5; i++) {
			l.setBlock(i+3, 15, gb);
			l.setBlock(i+9, 13, gb);
		}
		world1 = new GameWorld(l, p);
		
		lastTime = this.millis();
	}


	public void draw() {
		float delta = getNewDelta();
		clear();
		background(0);
		world1.update(delta);
		world1.draw(g);
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
		if (key == 'w') {
			world1.playerJump = false;
		}
	}
	
	public void keyPressed() {
		if (key == 'a') {
			world1.playerLeft = true;
		}
		if (key == 'd') {
			world1.playerRight = true;
		}
		if (key == 'w') {
			world1.playerJump = true;
		}
	}
}
