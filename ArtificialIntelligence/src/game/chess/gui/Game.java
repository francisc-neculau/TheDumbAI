package game.chess.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.chess.model.ChessState;
import model.minmax.MinMaxTree;

public class Game extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -7675448288069940521L;
	
	private Board board;
	private JPanel firstSelection;
	private JPanel secondSelection;
	private MinMaxTree<ChessState> minMaxTree;
	
	public Game() throws IOException
	{
		this.setLayout(null);
		this.board = new Board(this);
		this.add(board);
		this.minMaxTree = new MinMaxTree<>(4);
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
 
	}

	private void updateBoard()
	{
		
	}
	
	private boolean isLegalMove(JPanel from, JPanel to)
	{
		System.out.println(from.getName());
		System.out.println(to.getName());
		return true;
	}
	
	@Override
 	public void mouseClicked(MouseEvent e)
	{
		if(firstSelection == null)
		{
			firstSelection = (JPanel) e.getSource();
			togleBoldText(getLabel(firstSelection));
		}
		else
		{
			secondSelection = (JPanel) e.getSource();
			if(isLegalMove(firstSelection, secondSelection)) // condition to move
			{
				JLabel first, second;
				first  = getLabel(firstSelection);
				second = getLabel(secondSelection);
				second.setText(first.getText());
				first.setText("");
			}
			togleBoldText(getLabel(firstSelection));
			firstSelection  = null;
			secondSelection = null;
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		JPanel cell = (JPanel) e.getSource();
		if(cell == firstSelection)
			return;
		for (Component jc : cell.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            }
        }
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		JPanel cell = (JPanel) e.getSource();
		if(cell == firstSelection)
			return;
		for (Component jc : cell.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
            }
        }
	}
	
	private void togleBoldText(JLabel label)
	{
		Font f = label.getFont();
		label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
	}
	
	private JLabel getLabel(JPanel panel)
	{
		for (Component jc : panel.getComponents())
            if (jc instanceof JLabel)
                return (JLabel) jc;
		return null;
	}
}