package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import core.level.Level;
import core.level.blocks.GrassBlock;
import core.player.Player;
import processing.core.PApplet;
import sound.SoundPlayer;

public class Main extends PApplet {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		PApplet.main(new String[] {"core.Main" });
	}
	
	ArrayList<GameWorld> worlds;
	
	GameWorld currWorld;
	SoundPlayer soundPlayer;
	
	int worldChangeDelay = 0;
	
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
		
		soundPlayer = new SoundPlayer(this);
		
		worlds = new ArrayList<GameWorld>();
		
		Level l = new Level(200, 200);
		GrassBlock gb = new GrassBlock();
		for (int i = 0; i < 200; i++) {
			l.setBlock(i, 16, gb);
			l.setBlock(i, 0, gb);
			l.setBlock(0, i, gb);
			//l.setBlock(16, i, gb);
		}
		l.setBlock(5, 4, gb);
		
		for (int i = 0; i < 5; i++) {
			l.setBlock(i+3, 15, gb);
			l.setBlock(i+9, 13, gb);
		}
		
		worlds.add(new GameWorld(l, new Player(100,100, soundPlayer)));
		worlds.add(new GameWorld(l, new Player(100,150, soundPlayer)));
		
		currWorld = worlds.get(0);
		
		lastTime = this.millis();
	}


	public void draw() {
		float delta = getNewDelta();
		clear();
		background(0);
		
		if (worldChangeDelay == 0) {
			currWorld.update(delta);
		} else {
			worldChangeDelay--;
		}
		
		currWorld.draw(g);
		frame.setTitle(delta + "");
	}
	
	public float getNewDelta() {
		long newTime = System.nanoTime();
		long diff = newTime - lastTime;
		lastTime = newTime;
		return (float) (diff/16000000.0);
	}
	
	public void changeWorld(int worldId) {
		currWorld.playerLeft = false;
		currWorld.playerRight = false;
		currWorld.playerJump = false;
		
		soundPlayer.play("static");
		currWorld = worlds.get(worldId);
		worldChangeDelay = 100;
	}
	
	public void keyReleased() {
		if (key == 'a') {
			currWorld.playerLeft = false;
		}
		if (key == 'd') {
			currWorld.playerRight = false;
		}
		if (key == 'w') {
			currWorld.playerJump = false;
		}
		
		if (key == 'l') {
			changeWorld(0);
		}
		if (key == ';') {
			changeWorld(1);
		}
	}
	
	public void keyPressed() {
		if (key == 'a') {
			currWorld.playerLeft = true;
		}
		if (key == 'd') {
			currWorld.playerRight = true;
		}
		if (key == 'w') {
			currWorld.playerJump = true;
		}
	}
}
