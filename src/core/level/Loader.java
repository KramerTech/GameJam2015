package core.level;

import java.io.File;
import java.util.Scanner;

import core.GameWorld;
import core.level.blocks.Property;
import core.player.Player;

public class Loader {

	public static final String SAVES = "res/levels/";
	
	public static GameWorld Load(String name) {
		Scanner in;
		try {
			in = new Scanner(new File(SAVES + name));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Scanner line = new Scanner(in.nextLine());
		Level level = new Level(line.nextInt(), line.nextInt());
		
        while (in.hasNextInt()) {
            line = new Scanner(in.nextLine());
	        Property[] p = new Property[Property.PROPERTY_COUNT];
        	int x = line.nextInt();
        	int y = line.nextInt();
        	while (line.hasNextInt()) {
        		int i = line.nextInt();
        		p[i] = new Property(i, line.nextInt());
        	}
        	
        	//if (p[Property.BLOCK] == 1) {
        	//	level.setBlock(x, y, new Block(p));
        	//}
        	
        	line.close();
		}
        in.close();
        int playerx = 0, playery = 0;
		GameWorld build = new GameWorld(level, new Player(playerx, playery, null, null));
		//build
		
		return build;
	}
}
