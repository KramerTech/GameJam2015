package sound;

import java.util.HashMap;
import java.util.Map.Entry;

import beads.AudioContext;
import beads.Gain;
import beads.Glide;
import beads.Sample;
import beads.SampleManager;
import beads.SamplePlayer;
import processing.core.PApplet;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundPlayer {
	Minim minim;
	AudioContext ac;
	
	Sample smus;
	Sample fmus;
	
	Sample pickup;
	Sample jump;
	Sample stat;
	
	Gain sgain;
	Gain fgain;
	
	SamplePlayer ssp;
	SamplePlayer fsp;
	
	SamplePlayer psp;
	SamplePlayer jsp;
	SamplePlayer stsp;
	
	HashMap<String,Sample> sounds;
	HashMap<String,SamplePlayer> playing;
	
	public SoundPlayer(PApplet applet) {
		minim = new Minim(applet);		
		
		sounds = new HashMap<String,Sample>();
		playing = new HashMap<String,SamplePlayer>();
		

		
		ac = new AudioContext();
		
		smus = SampleManager.sample("res/music/GGJ_Space.mp3");
		fmus = SampleManager.sample("res/music/GGJ_Field.mp3");
		
		pickup = SampleManager.sample("res/sfx/pickup.wav");
		jump = SampleManager.sample("res/sfx/jump.wav");
		stat = SampleManager.sample("res/sfx/static.wav");
		
		sgain = new Gain(ac, 1, 0.0f);
		fgain = new Gain(ac, 1, 0.0f);
		
		ssp = new SamplePlayer(ac, smus);
		fsp = new SamplePlayer(ac, fmus);
		
		jsp = new SamplePlayer(ac, jump);
		psp = new SamplePlayer(ac, pickup);
		stsp = new SamplePlayer(ac, stat);
		
		sounds.put("pickup", pickup);
		sounds.put("jump", jump);
		sounds.put("static", stat);
		
		fsp.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);
		ssp.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);
		
		//fsp.setLoopType();
		
		sgain.addInput(ssp);
		fgain.addInput(fsp);
		
		ac.out.addInput(sgain);
		ac.out.addInput(fgain);
		
		ac.start();
	}
	
	public void play(String name) {
		try {		
			Sample sample = sounds.get(name);
			SamplePlayer player = new SamplePlayer(ac, sample);
			
			ac.out.addInput(player);
			player.setKillOnEnd(true);
			player.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playSpace() {
		sgain.setGain(1.0f);
		fgain.setGain(0.0f);
	}
	
	public void playField() {
		sgain.setGain(0.0f);
		fgain.setGain(1.0f);
	}
	
	public void playLoop(String name, String id) {
		
		if (playing.containsKey(id)) {
			return;
		}
		
		try {
			//String path = sounds.get(name);
		
			//AudioPlayer player = minim.loadFile(path);
			
			//playing.put(id, player);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopLoop(String id) {
		try {
			//AudioPlayer player = playing.remove(id);
			//player.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
