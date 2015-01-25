package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import core.enemy.BunnyEnemy;
import core.enemy.TurtleEnemy;
import core.level.Level;
import core.level.blocks.GrassBlock;
import core.player.Player;
import processing.core.PApplet;
import processing.core.PImage;
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
	
	public static PImage spriteL, spriteR;

	public void setup() {
		spriteR = loadImage("res/img/upr.png");
		spriteL = loadImage("res/img/upl.png");
		
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
		
		Level l = new Level(200, 30);
		GrassBlock gb = new GrassBlock();
		for (int i = 0; i < 100; i++) {
			l.setBlock(i, 16, gb);
			l.setBlock(i, 0, gb);
			l.setBlock(0, i, gb);
			l.setBlock(100, i, gb);
		}
		l.setBlock(5, 4, gb);
		
		for (int i = 0; i < 5; i++) {
			l.setBlock(i+3, 15, gb);
			l.setBlock(i+9, 13, gb);
		}
		
		for (int x = 0; x < 6; x++) {
			l.setBlock(x+20, 16-x, gb);
			l.setBlock(30-x, 16-x, gb);
		}
		
		worlds.add(new GameWorld(l, new Player(100,100, soundPlayer, null)));
		worlds.add(new GameWorld(l, new Player(100,150, soundPlayer, null)));
		
		currWorld = worlds.get(0);
		
		for (int i = 0; i < 5; i++)
			worlds.get(0).entities.add(new TurtleEnemy(new Vec2(500 + i*100, 100), worlds.get(0).world));
		for (int i = 5; i < 20; i++)
			worlds.get(0).entities.add(new BunnyEnemy(new Vec2(500 + i*100, 100), worlds.get(0).world, (Math.random() < .5) ? worlds.get(0).player.playerBody : null));
		
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
		if (key == 'j') {
			currWorld.playerShoot = false;
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
		if (key == 'j') {
			currWorld.playerShoot = true;
		}
	}
}
