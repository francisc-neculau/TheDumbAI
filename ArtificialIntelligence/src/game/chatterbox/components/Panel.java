package game.chatterbox.components;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	
	private JTextArea repliesArea = new JTextArea();
	private JTextField replyArea   = new JTextField();
	
	public Panel() {
		setBounds(0, 0, 445, 286);
		setLayout(null);
		
		replyArea.setBounds(10, 228, 424, 47);
		add(replyArea);
		
		JScrollPane scrollPane = new JScrollPane(repliesArea);
		add(scrollPane);
		scrollPane.setBounds(10, 11, 424, 206);
		
		repliesArea.setEditable(false);
	}

	public JTextArea getRepliesArea() {
		return repliesArea;
	}

	public JTextField getReplyArea() {
		return replyArea;
	}
}
