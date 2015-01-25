package core.Entity;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.World;

import core.enemy.BunnyEnemy;
import core.level.blocks.DeathBlock;

public class EnitityGenerator {

	public static Entity generate(Vec3 info, World world) {
		switch ((int) info.z) {
		case 0:
			return new BunnyEnemy(new Vec2(info.x, info.y), world, null);
		case 1:
			return new DeathBlock((int) info.x/32, (int) info.y/32, world);
		}
		return null;
	}
	
}