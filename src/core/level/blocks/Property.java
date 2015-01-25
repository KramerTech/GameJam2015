package core.level.blocks;

public class Property {

	public static final int COLOR = 0;
	public static final int COLLIDES = 1;
	public static final int ALIVE = 2;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (type != other.type)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public static final int BLOCK = 3;
	
	public static final int PROPERTY_COUNT = 3;
	
	public int type, value;
	public Property(int type, int value) {
		this.type = type;
		this.value = value;
	}
	public Property(int type, boolean value) {
		this(type, value ? 1 : 0);
	}
	
	public String toString() {
		return type + " " + value;
	}
	
}
