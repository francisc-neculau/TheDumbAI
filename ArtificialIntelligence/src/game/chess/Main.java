package game.chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import game.chess.gui.Game;

public class Main extends JFrame
{
	private static final long serialVersionUID = 7934048699542573198L;

	
	public Main() throws IOException
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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 594, 603);
		panel.add(tabbedPane);
		
		Game game = new Game();
		tabbedPane.addTab("Game2", null, game, null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Options = new JMenu("Options");
		menuBar.add(Options);
	}

	public static void main(String... args) throws Exception
	{
		Main mw = new Main();
		mw.setVisible(true);

//		  byte[] whitePawns = {0, -1, 0, 0, 0, 0, 0, 0}; byte[] blackPawns = {0, -1, 0, 0, 0, 0, 0, 0}; 
//		  ChessState state = new ChessState(whitePawns, blackPawns, false);
//		  
////		  int row = 0; int column = 0; boolean b = isFreeAt(row, column,state.getWhitePawns(), state.getBlackPawns()); //
////		  System.out.println(b); // System.out.println(isPawnAt(0, blackPawns[1])); //
//		  System.out.println(state);
//		  
//		  
//		  ChessStateTransitioner stateTransitioner = new ChessStateTransitioner();
//		  
//		  
//		  String filePath; ChessStateFileSerializer serializer = new
//		  ChessStateFileSerializer(); 
//		  filePath = "resources//chess//standard_start.txt";
//		  
//		  System.out.println(serializer.readState(filePath));
//		  
//		  filePath = "resources//chess//b_advantage_case_en_passant.txt";
//		  System.out.println(serializer.readState(filePath));
//		  
//		  filePath = "resources//chess//w_move_case_attack_pawn.txt";
//		  System.out.println(serializer.readState(filePath));
//		  
//		  System.out.println();System.out.println();System.out.println();
//		  
//		  for (ChessState s :
//		  stateTransitioner.generateAllNextLegalStates(serializer.readState(filePath)))
//		  { System.out.println(s); }
		
		/*
		 * byte[] whitePawns = {0, -1, 0, 0, 0, 0, 0, 0}; byte[] blackPawns = {0, -1, 0,
		 * 0, 0, 0, 0, 0}; ChessState state = new ChessState(whitePawns, blackPawns,
		 * false);
		 * 
		 * int row = 0; int column = 0; boolean b = isFreeAt(row,
		 * column,state.getWhitePawns(), state.getBlackPawns()); //
		 * System.out.println(b); // System.out.println(isPawnAt(0, blackPawns[1])); //
		 * System.out.println(state);
		 * 
		 * 
		 * ChessStateTransitioner stateTransitioner = new ChessStateTransitioner();
		 * 
		 * 
		 * String filePath; ChessStateFileSerializer serializer = new
		 * ChessStateFileSerializer(); // // filePath =
		 * "resources//chess//standard_start.txt"; //
		 * System.out.println(serializer.readState(filePath)); // // filePath =
		 * "resources//chess//b_advantage_case_en_passant.txt"; //
		 * System.out.println(serializer.readState(filePath)); // filePath =
		 * "resources//chess//w_move_case_attack_pawn.txt"; //
		 * System.out.println(serializer.readState(filePath));
		 * System.out.println(serializer.readState(filePath));
		 * System.out.println();System.out.println();System.out.println();
		 * 
		 * for (ChessState s :
		 * stateTransitioner.generateAllNextLegalStates(serializer.readState(filePath)))
		 * { System.out.println(s); }
		 */
	}
}