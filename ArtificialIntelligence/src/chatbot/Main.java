package chatbot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import chatbot.data.UserDb;

public class Main extends JFrame
{
	private static final long serialVersionUID = -3675946471320764957L;
	private static Bot bot;
	
	public Main()
	{
		getContentPane().setBackground(Color.WHITE);
		setMinimumSize(new Dimension(400, 450));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);
		getContentPane().setLayout(null);
		/* * * * * * * * * * * */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(10, 83, 374, 280);
		getContentPane().add(scrollPane);
				
		JTextArea conversationComponent = new JTextArea();
		conversationComponent.setBorder(null);
		scrollPane.setViewportView(conversationComponent);
		conversationComponent.setEditable(false);

		JTextField replyComponent = new JTextField();
		replyComponent.setBorder(new EmptyBorder(0, 10, 0, 0));
		replyComponent.setBackground(SystemColor.control);
		replyComponent.setBounds(10, 374, 374, 36);
		replyComponent.addActionListener(new AbstractAction()
		{
			private static final long serialVersionUID = -448083876195112090L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String conversation = conversationComponent.getText();
				String userReply    = replyComponent.getText();
				replyComponent.setText("");

				conversation += System.lineSeparator();
				conversation += "Me : ";
				conversation += userReply;
				conversationComponent.setText(conversation);
				conversation += System.lineSeparator();
				conversation += bot.getName() + " : ";
				conversation += bot.generateReply(" " + userReply + " ");
				conversationComponent.setText(conversation);
			}
		});
		getContentPane().add(replyComponent);
	}


	public static void main(String[] args)
	{
		bot = new Bot("Guta", "C:\\ComputerScience\\Projects\\ArtificialIntelligence\\resources\\chatbot\\knowledge.xml");
		Main mw = new Main();
		mw.setVisible(true);
		
	}
}
