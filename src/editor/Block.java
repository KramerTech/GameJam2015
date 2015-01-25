package editor;

public class Block {

	public boolean collides = true;
	public int color;
	
	public Block(int color) {
		this.color = color;
	}
	
	public Block(int color, boolean collide) {
		this.color = color;
		this.collides = collide;
	}
	
	
}
