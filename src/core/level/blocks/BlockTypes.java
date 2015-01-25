package core.level.blocks;


public class BlockTypes {

	public static Block air() {
		return Block.getBlock(new Property[] {
				new Property(Property.COLOR, 0x00000000),
				new Property(Property.COLLIDES, 0),
		});
	}
	
}
