package editor;

import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class World implements Element {

	int maxx = 50;
	int maxy = 30;
	
	int zoom = 32;
	
	Block block;
	
	ArrayList<ArrayList<Block>> world;
	
	Random r = new Random();
	
	private int scrollx = 0;
	private int scrolly = 0;
	
	private Stack<Move> undo = new Stack<Move>();
	private Move current = null;
	
	class Move {
		Stack<XYC> moves = new Stack<XYC>();
		public void undo() {
			while (!moves.isEmpty()) {
				XYC move = moves.pop();
				add(move.x, move.y, move.b);
			}
		}
		public void move(int x, int y, Block b) {
			moves.push(new XYC(x, y, b));
		}
	}
	
	public void undo() {
		if (!undo.isEmpty()) {
			System.out.println(undo.peek().moves.size());
			undo.pop().undo();
		}
	}
	
	
	class XYC {
		public XYC(int x, int y, Block b) {
			this.x = x;
			this.b = b;
			this.y = y;
		}
		public int x, y;
		public Block b;
		public boolean equals(XYC other) {
			return (x == other.x && y == other.y && b == other.b);
		}
		public boolean equals(int x, int y, Block b) {
			return (this.x == x && this.y == y && this.b == b);
		}
	}
	
	public World() {
		world = new ArrayList<ArrayList<Block>>();
	}
	
	
	public int getX(PApplet g) {
		return (scrollx + g.mouseX - g.width / 10) / zoom;
	}
	
	
	public int getY(PApplet g) {
		return (int) (scrolly + g.height - g.mouseY) / zoom;
	}
	
	
	public int getX(int x, PApplet g) {
		return (g.width / 10) + (x * zoom - scrollx);
	}
	
	
	public int getY(int y, PApplet g) {
		return g.height - ((y + 1) * zoom - scrolly);
	}
	
	
	private boolean select = false;
	private boolean grab = false;
	private boolean rect = false;
	private int ox, oy;
	public synchronized void mouse(MouseEvent e, PApplet g) {
		if (e.getAction() == MouseEvent.RELEASE) {
			if (e.getButton() == 3) {
				grab = false;
			} else {
				if (rect) {
					int x = getX(g);
					int y = getY(g);
					int minx = Math.min(x, ox);
					int miny = Math.min(y, oy);
					x = Math.max(x, ox);
					y = Math.max(y, oy);
					for (int i = miny; i <= y; i++) {
						for (int j = minx; j <= x; j++)
							add(j, i, block);
					}
				}
				if (rect || select) {
					if (!current.moves.isEmpty()) {
						undo.add(current);
						current = null;
					}
					select = false;
					rect = false;
				}
			}
		}
		
		if (g.mouseX < g.width / 10) return;
		if (e.getAction() == MouseEvent.PRESS) {
			if (e.getButton() == 3) {
				grab = true;
			} else {
				block = e.getButton() == 37 ? ((LevelEditor) g).getBlock() : null;
				current = new Move();
				if (e.isShiftDown()) {
					select = false;
					rect = true;
					ox = getX(g);
					oy = getY(g);
				} else {
					select = true;
					add(getX(g), getY(g), block);
				}
			}
		}
		if ( e.getAction() == MouseEvent.DRAG) {
			if (grab) shift(g.pmouseX - g.mouseX, g.pmouseY - g.mouseY);
			if (select) add(getX(g), getY(g), block);
		}
	}
	
	
	@Override
	public synchronized void mouseWheel(MouseWheelEvent e, PApplet g) {
		if (g.mouseX < g.width / 10) return;
		int oz = zoom;
		
		int sx = -getX(g);
		int sy = getY(g);

		int y = e.getWheelRotation();
		y *= (1 + zoom / 8);
		zoom -= y;
		zoom = Math.max(zoom, 2);
		zoom = Math.min(zoom, 64);
		y = oz - zoom;
		
		if (oz != zoom)
			shift(sx * y, sy * y);
	}
	
	public void shift(int x, int y) {
		scrollx += x;
		scrolly -= y;
		scrollx = Math.max(-100, scrollx);
		scrolly = Math.max(-100, scrolly);
	}
	

	public void add(int x, int y, Block block) {
		System.out.println(x + " " + y);
		synchronized (world) {
			if (x < 0 || y < 0) return;
			ArrayList<Block> row;
			if (y < world.size()) {
				row = world.get(y);
				if (row == null) {
					row = new ArrayList<Block>();
					world.set(y, row);
				}
			} else {
				for (int i = y - world.size(); i > 0; i--)
					world.add(null);
				row = new ArrayList<Block>();
				world.add(row);
			}
			
			if (x < row.size() && canOverwrite(row.get(x))) {
				if (current != null)
					current.move(x, y, row.get(x));
				row.set(x, block);
			} else {
				for (int i = x - row.size(); i > 0; i--)
					row.add(null);
				row.add(block);
				if (current != null)
					current.move(x, y, null);
			}
		}
	}
	
	
	private boolean canOverwrite(Block o) {
		if (block == o) return false;
		if (o == null) return true;
		return true;
	}
	
	
	public void bite(PApplet g, Block block, int x, int y) {
		if (block == null) return;
		g.fill(block.color);
		g.rect(x - scrollx % zoom, y + scrolly % zoom, zoom, zoom);
	}

	
	
	public synchronized void draw(PApplet g) {
		int y = 0;
		for (int i = scrolly / zoom; ; i++) {
			y += zoom;
			if (i >= world.size()) break;
			if (i < 0) continue;
			ArrayList<Block> row = world.get(i);
			if (row == null) continue;
			int x = 0;
			for (int j = scrollx / zoom; ; j++) {
				if (j >= row.size()) break;
				if (j < 0) {x+=zoom; continue;}
				Block block = row.get(j);
				if (x > g.width + zoom) break;
				bite(g, block, x + g.width / 10, g.height - y);
				x += zoom;
			}
			if (y > g.height + zoom) break;
		}
		
		if (rect) {
			int x = Math.max(0, getX(g));
			y = Math.max(0, getY(g));
			int minx = Math.min(x, ox);
			int miny = Math.max(y, oy);
			x = (Math.max(x, ox) - minx + 1) * zoom;
			y = (miny + 1 - Math.min(y, oy)) * zoom;
			g.fill(block.color);
			g.rect(getX(minx, g), getY(miny, g), x, y);
		}
		
		
		int offx = g.width / 10 - (scrollx < 0 ? scrollx : scrollx % zoom);
		int offy = (scrolly < 0 ? scrolly : scrolly % zoom);
		
		g.stroke(255, 255, 255, 80);
		for (int x = offx; x < g.width; x += zoom)
			g.line(x, 0, x, g.height + offy);
		for (y = g.height + offy; y >= 0; y -= zoom)
			g.line(offx, y, g.width, y);
		g.noStroke();
		
	}

	public void noScroll() {
		scrolly = 0;
		scrollx = 0;
	}
	
	
	public boolean safe() {
		return !(rect || select);
	}
	
	
	public ArrayList<ArrayList<Block>> data() {
		return world;
	}
	
}
