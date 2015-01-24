package sound;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import ddf.minim.AudioOutput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;

public class SoundPlayer {
	Minim minim;
	
	HashMap<String,String> sounds;
	HashMap<String,AudioPlayer> playing;
	
	public SoundPlayer(PApplet applet) {
		minim = new Minim(applet);
		
		sounds = new HashMap<String,String>();
		playing = new HashMap<String,AudioPlayer>();
		
		sounds.put("pickup", "res/sfx/pickup.wav");
		sounds.put("jump", "res/sfx/jump.wav");
	}
	
	public void play(String name) {
		try {
			String path = sounds.get(name);
		
			AudioPlayer player = minim.loadFile(path);
			player.play();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playLoop(String name, String id) {
		
		if (playing.containsKey(id)) {
			return;
		}
		
		try {
			String path = sounds.get(name);
		
			AudioPlayer player = minim.loadFile(path);
			player.loop();
			
			playing.put(id, player);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopLoop(String id) {
		try {
			AudioPlayer player = playing.remove(id);
			player.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
