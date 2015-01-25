package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Save {

	private static String save = null;

	public static String saveAs(World world) {
		if (world.unsafe()) return null;
		String tmp = JOptionPane.showInputDialog("Filename:", save);
		if (tmp == null) {
			System.out.println("Save canceled.");
			return tmp;
		}
		tmp = tmp.trim().split("\\.")[0] + ".lvl";
		if (!tmp.equals(save) && new File("res/levels/" + tmp).exists()) {
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


	public static String save(World world) {
		if (world.unsafe()) return null;
		if (save == null)
			return saveAs(world);
		ArrayList<ArrayList<Block>> data = world.data();
		synchronized (data) {
			try {
				System.out.println("Saving " + save + ".");
				BufferedWriter writer = new BufferedWriter(new PrintWriter("res/levels/" + save));
				writer.write("hello");
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		return save;
	}

}
