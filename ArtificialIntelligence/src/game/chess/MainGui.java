package game.chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.chess.gui.Game;
import game.chess.model.ChessStateFileSerializer;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;

public class MainGui extends JFrame
{
	private static final long serialVersionUID = 7934048699542573198L;
	private int gameCounter = 1;
	private JTabbedPane tabbedPane;
	private int selectedGameIndex;
	private boolean whiteFirst = false;
	
	public MainGui() throws IOException
	{
		getContentPane().setBackground(Color.WHITE);
		setMinimumSize(new Dimension(600, 655));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 594, 603);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 594, 603);
		tabbedPane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				MainGui.this.selectedGameIndex = MainGui.this.tabbedPane.getSelectedIndex();
			}
		});
		panel.add(tabbedPane);
		
		Game game = new Game(ChessStateFileSerializer.loadDefaultState(), true);
		tabbedPane.addTab("Game " + gameCounter++, null, game, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnAiNextMove = new JButton("AI Next Move");
		btnAiNextMove.setBounds(400, 64, 115, 45);
		panel_1.add(btnAiNextMove);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		JMenu mnNewMenu = new JMenu("Current Game");
		optionsMenu.add(mnNewMenu);
		
		JMenuItem mntmLoadState = new JMenuItem("Load State");
		mntmLoadState.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory( new File(ChessStateFileSerializer.resourcesDirectoryPath));
                int option = chooser.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION)
                	MainGui.this.loadStateOnSelectedGame(chooser.getSelectedFile());
            }
		});
		mnNewMenu.add(mntmLoadState);
		
		JMenuItem mntmCloseCurrentGame = new JMenuItem("Close Game");
		mntmCloseCurrentGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainGui.this.endSelectedGame();
			}
		});
		mnNewMenu.add(mntmCloseCurrentGame);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainGui.this.startNewGame();
			}
		});
		optionsMenu.add(mntmNewGame);
		
		JCheckBoxMenuItem chckbxmntmWhiteFirst = new JCheckBoxMenuItem("White First");
		chckbxmntmWhiteFirst.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MainGui.this.toggleWhiteFirst();
			}
		});
		optionsMenu.add(chckbxmntmWhiteFirst);
	}

	protected void toggleWhiteFirst()
	{
		this.whiteFirst = !whiteFirst;
	}

	private void loadStateOnSelectedGame(File stateFile)
	{
		Game selectedGame = (Game)tabbedPane.getSelectedComponent();
		selectedGame.loadNewGameState(stateFile);
	}
	
	private void endSelectedGame()
	{
		tabbedPane.remove(selectedGameIndex);
	}
	
	private void startNewGame()
	{
		Game game = new Game(ChessStateFileSerializer.loadDefaultState(), whiteFirst);
		tabbedPane.addTab("Game " + gameCounter, null, game, null);
		gameCounter++;
	}
	
	public static void main(String... args) throws Exception
	{
		MainGui mw = new MainGui();
		mw.setVisible(true);

	}
}
