package game.chatterbox.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import game.chatterbox.components.MainFrame;
import game.chatterbox.components.UpdateDialog;
import game.chatterbox.error.UnknownReplyException;
import game.chatterbox.model.Category;
import game.chatterbox.model.User;
import game.chatterbox.parser.ChatbotMemory;



public class AplicationManager implements ActionListener {

	MainFrame     mainFrame;
	User          user;
	ChatbotMemory memory;
	UpdateDialog  updateDialog;

	public AplicationManager(MainFrame mainFrame) throws SAXException, IOException, ParserConfigurationException {
		this.mainFrame = mainFrame;
		this.memory = new ChatbotMemory();
		
		this.mainFrame.getReplyArea().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
					processUserReply(mainFrame.getReplyArea().getText());
			}
		});
	}
	
	public void processUserReply(String userReply) {
		String botReply = "";
		try {
			botReply = memory.getReply(userReply);
		} catch (UnknownReplyException e) {
			if(mainFrame.isEditable()) {
				updateDialog = new UpdateDialog(mainFrame,this);
				updateDialog.setVisible(true);
				botReply = "-memory updated-";
			} else {
				botReply = "I don't know this";
			}
		}
		mainFrame.getRepliesArea().insert(
				"you   : " + userReply + "\n" + 
				"mihai : " + botReply  + "\n", 
				mainFrame.getRepliesArea().getText().length()
												);
		mainFrame.getReplyArea().setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		e.getSource();
	}

	public void addCategory(Category category) {
		this.memory.add(category);
		try {
			this.memory.serializeMemory();
		} catch (Exception e) {
			System.out.println("memory serialization failed");
		} 
	}
	
}
