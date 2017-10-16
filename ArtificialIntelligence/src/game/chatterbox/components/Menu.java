package components;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {

	private JMenu menu;
	private JMenu subMenu;
	private JCheckBoxMenuItem check;
	
	public Menu() {
		
		menu = new JMenu("Options");
		this.add(menu);
		
		subMenu = new JMenu("Edit");
		menu.add(subMenu);

		check = new JCheckBoxMenuItem("On");
		subMenu.add(check);
		
	}

	public JCheckBoxMenuItem getCheck() {
		return check;
	}

}
