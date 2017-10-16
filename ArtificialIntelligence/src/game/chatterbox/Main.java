package game.chatterbox;
import javax.swing.SwingUtilities;

import components.MainFrame;
import manager.AplicationManager;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> createAndShowGUI());
	}

	protected static void createAndShowGUI() {
		try {
			new AplicationManager(new MainFrame());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
