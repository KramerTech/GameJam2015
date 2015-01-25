package editor;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Save {

	private static String save = null;

	public static void saveAs(World world) {
		save = JOptionPane.showInputDialog("Please input mark for test 1: ");
		System.out.println(save);
		save(world);
		return;
	}


	public static void save(World world) {
		if (!world.safe()) return;
		if (save == null) {
			saveAs(world);
			return;
		}
		ArrayList<ArrayList<Block>> data = world.data();
		synchronized (data) {
			
		}
	}

}
