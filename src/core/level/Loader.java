package core.level;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.jbox2d.common.Vec3;

import sound.SoundPlayer;
import core.GameWorld;
import core.Entity.EnitityGenerator;
import core.level.blocks.Block;
import core.level.blocks.Property;
import core.player.Player;

public class Loader {

	public static final String SAVES = "res/levels/";
	
	public static void addBlock(Level level, ArrayList<Vec3> entities, int x, int y, Property[] p) {
		if (p[Property.BLOCK] != null) {
			level.setBlock(x, y, Block.getBlock(p));
		} else if (p[Property.ID] != null) {
			entities.add(new Vec3(x * 32, y * 32, p[Property.ID].value));
		}
	}
	
	public static GameWorld load(String name, SoundPlayer soundPlayer) {
		if (!name.contains(".")) name += ".lvl";
		Scanner in;
		try {
			in = new Scanner(new File(SAVES + name));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Scanner line = new Scanner(in.nextLine());
		int mapx = line.nextInt();
		System.out.println(mapx);
		int mapy = line.nextInt();
		line.close();
		
		line = new Scanner(in.nextLine());
		
		int bgType = line.nextInt();
		
        int playerx = 0, playery = 0;
        
		Level level = new Level(mapx, mapy, bgType);
		ArrayList<Vec3> entities = new ArrayList<Vec3>();
        while (in.hasNextInt()) {
            line = new Scanner(in.nextLine());
	        Property[] p = new Property[Property.PROPERTY_COUNT];
        	int x = line.nextInt();
        	int y = mapy - line.nextInt() - 1;
        	while (line.hasNextInt()) {
        		int i = line.nextInt();
        		p[i] = new Property(i, line.nextInt());
        	}
        	
        	if (p[Property.PLAYER] != null) {
        		playerx = x * 32;
        		playery = y * 32;
        		continue;
        	} else {
        		addBlock(level, entities, x, y, p);
        	}
        	
        	line.close();
		}
        in.close();

		GameWorld build = new GameWorld(level, new Player(playerx, playery, soundPlayer, null));
		for (Vec3 e : entities) {
			build.entities.add(EnitityGenerator.generate(e, build.world));
		}
		
		return build;
	}
}
