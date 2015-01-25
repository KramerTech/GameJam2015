package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import core.level.blocks.Block;
import core.level.blocks.Property;

public class Save {

	public static final String SAVE_LOC = "res/levels/";
	private static String save = null;

	public static String saveAs(World world) {
		if (world.unsafe()) return null;
		String tmp = JOptionPane.showInputDialog("Filename:", save);
		while (tmp != null && tmp.trim().split("\\.")[0].length() == 0)
			tmp = JOptionPane.showInputDialog("Filename:", save);
		
		if (tmp == null) {
			System.out.println("Save canceled.");
			return tmp;
		}
		
		tmp = tmp.trim().split("\\.")[0] + ".lvl";
		if (!tmp.equals(save) && new File(SAVE_LOC + tmp).exists()) {
			if (JOptionPane.showConfirmDialog(null, "File exists. Overwrite?",
					"Warning", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
				System.out.println("Save canceled.");
				return null;
			}
			System.out.println("Overwrite confirmed.");
		}
		save = tmp;
		return save(world);
	}

	
	public static String Load(World world) {
		if (world.unsafe()) return null;
		synchronized (world.data()) {
			if (!world.saved) {
				if (JOptionPane.showConfirmDialog(null, "Wolrd not saved. Lose current changes?",
						"Warning", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
					System.out.println("Load canceled.");
					return null;
				}
			}
			String[] list = new File(SAVE_LOC).list();
            if (list.length == 0) {
				JOptionPane.showConfirmDialog(null, "No levels to load", "Error", JOptionPane.CLOSED_OPTION);
				return null;
			}
            JComboBox<String> optionList = new JComboBox<String>(list);            
            optionList.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, optionList, "Select a level to load.", 0);
            save = list[optionList.getSelectedIndex()];
            try {
            	Scanner in = new Scanner(new File(SAVE_LOC + save));
            	Scanner line;
            	world.data().clear();
            	System.out.println(in.nextLine());
            	Scanner bgScanner = new Scanner(in.nextLine());
            	
            	int bgType = bgScanner.nextInt();
            	
            	while (in.hasNextInt()) {
            		line = new Scanner(in.nextLine());
            		
	            	Property[] p = new Property[Property.PROPERTY_COUNT];
	            	int x = line.nextInt();
	            	int y = line.nextInt();
	            	while (line.hasNextInt()) {
	            		int i = line.nextInt();
	            		p[i] = new Property(i, line.nextInt());
	            	}
	            	line.close();
	            	world.add(x, y, Block.getBlock(p));
            	}
            	in.close();
            } catch (Exception e) {
            	e.printStackTrace();
            	return null;
            }
            world.saved = true;
		}
		return save;
	}
	

	public static String save(World world) {
		if (world.unsafe()) return null;
		if (save == null)
			return saveAs(world);
		ArrayList<ArrayList<Block>> data = world.data();
		synchronized (data) {
			try {
				int maxx = -1; int maxy = -1;
				for (int y = 0; y < data.size(); y++) {
					ArrayList<Block> row = data.get(y);
					if (row == null) continue;
					for (int x = 0; x < row.size(); x++) {
						Block block = row.get(x);
						if (block == null) continue;
						maxx = Math.max(maxx, x);
						maxy = y;
					}
				}
				
				if (maxx < 0) {
					JOptionPane.showConfirmDialog(null, "No data to save!", "Error", JOptionPane.CLOSED_OPTION);
					return null;
				}
				
				world.saved = true;
				maxy++;
				maxx++;
				BufferedWriter writer = new BufferedWriter(new PrintWriter(SAVE_LOC + save));
				writer.write(maxx + " " + maxy);
				writer.write("0");
				for (int y = 0; y < maxy; y++) {
					ArrayList<Block> row = data.get(y);
					if (row == null) continue;
					int top = Math.min(row.size(), maxx);
					for (int x = 0; x < top; x++) {
						if (row.get(x) != null)
							writeBlock(writer, data.get(y).get(x), x, y);
					}
				}
				System.out.println("Saving " + save);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return save;
	}
	
	
	private static void writeBlock(BufferedWriter writer, Block block, int x, int y) throws IOException {
		writer.write("\n" + x + " " + y + block.toString());
	}


	public static String getSave() {
		return save;
	}

}
