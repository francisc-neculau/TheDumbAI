package game.chatterbox.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import game.chatterbox.manager.AplicationManager;
import game.chatterbox.model.Category;

public class UpdateDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 7420540324379424598L;
	
	private JButton save;
	private JButton cancel;
	private JTextField pattern  = new JTextField();
	private JTextField template = new JTextField();
	private AplicationManager manager;
	
	public UpdateDialog(JFrame parentFrame, AplicationManager manager) {
		super(parentFrame, "Update AIML", true);
		
		this.manager = manager;
		setSize(300, 200);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
					(dimension.width - this.getSize().width )/2, 
					(dimension.height- this.getSize().height)/2
				   		);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("pattern  : ");
		label.setBounds(12, 28, 72, 14);
		getContentPane().add(label);pattern.setBounds(94, 25, 180, 20);
		getContentPane().add(pattern);
		JLabel label_1 = new JLabel("template : ");
		label_1.setBounds(12, 56, 72, 14);
		getContentPane().add(label_1);template.setBounds(94, 53, 180, 20);
		getContentPane().add(template);
		
		getContentPane().add(save   = new JButton("Save"));
		save.setBounds(189, 128, 85, 23);
		getContentPane().add(cancel = new JButton("Cancel"));
		cancel.setBounds(12, 128, 85, 23);
		save.addActionListener(this);
		cancel.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent ae) {
		JButton source = (JButton)(ae.getSource());
		if(source.getText().equals("Cancel")) {
			dispose();
		} 
		if(source.getText().equals("Save")) {
			//WHAT'S UP
			Category c = new Category();
			c.setPattern(pattern.getText());
			c.setRandom(false);
			c.addTemplate(template.getText());
			manager.addCategory(c);
			dispose();
		}
	}
	
}
