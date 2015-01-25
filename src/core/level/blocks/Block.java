package core.level.blocks;

import java.util.Arrays;
import java.util.HashMap;

import processing.core.PGraphics;


public class Block {
	
	private static HashMap<Property[], Block> blocks = new HashMap<Property[], Block>();
	
	private Property[] properties = new Property[Property.PROPERTY_COUNT];
	
	public void draw(PGraphics g) {
		g.fill(properties[Property.COLOR].value);
		g.noStroke();
		g.rect(0, 0, 32, 32);
	}
	
	
	public static Block getBlock(Property[] props) {
		if (blocks.containsKey(props))
			return blocks.get(props);
		Block b = new Block(props);
		blocks.put(props, b);
		return b;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(properties);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (!Arrays.equals(properties, other.properties))
			return false;
		return true;
	}


	private Block(Property[] props) {
		properties = props;
	}

	public Property getProperty(int type) {
		if (properties.length < type)
			return null;
		return properties[type];
	}
	
	public int get(int type) {
		return properties[type] == null ? 0 : properties[type].value;
	}
	
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for (Property p : properties) {
			if (p != null)
				build.append(" " + p.toString());
		}
		return build.toString();
	}
}
