package components;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private Menu  apMenu  = new Menu() ;
	private Panel apPanel = new Panel();
	
	public MainFrame() {
		this.initialize();
		
		/*
		 * Setting the MainFrame's Menu and Panel
		 */
        this.setJMenuBar(apMenu);
        this.getContentPane().add(apPanel);
	}
	
	private void initialize() {
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(450, 335));

		/*
		 * Seting the display point in the middle of the screen
		 */
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
					(dimension.width - this.getSize().width )/2, 
					(dimension.height- this.getSize().height)/2
				   );
	}
	
	public JTextArea getRepliesArea(){
		return apPanel.getRepliesArea();
	}
	public JTextField getReplyArea() {
		return apPanel.getReplyArea();
	}
	public boolean isEditable() {
		return apMenu.getCheck().isSelected();
	}
}
