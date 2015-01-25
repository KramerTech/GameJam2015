package core.level;

import java.io.File;
import java.util.Scanner;

import sound.SoundPlayer;
import core.GameWorld;
import core.level.blocks.Block;
import core.level.blocks.Property;
import core.player.Player;

public class Loader {

	public static final String SAVES = "res/levels/";
	
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
		int mapy = line.nextInt();
		
        int playerx = 0, playery = 0;
        
		Level level = new Level(mapx, mapy);
		
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
        	} else if (p[Property.BLOCK] != null) {
        		level.setBlock(x, y, Block.getBlock(p));
        	}
        	
        	line.close();
		}
        in.close();

		GameWorld build = new GameWorld(level, new Player(playerx, playery, soundPlayer, null));
		//build
		
		return build;
	}
}
