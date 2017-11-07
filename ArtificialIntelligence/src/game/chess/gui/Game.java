package game.chess.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;

import game.chess.model.ChessState;
import game.chess.model.ChessStateFileSerializer;
import game.chess.model.ChessStateTransitioner;

public class Game extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -7675448288069940521L;
	
	private Board board;
	private JTextField firstSelection;
	private ChessState currentState;
//	private MinMaxTree<ChessState> minMaxTree;
	private boolean humanPlayerTurn;
	
	public Game(ChessState state)
	{
		this.setLayout(null);
		this.board = new Board(state, this);
		this.add(board);
		this.currentState = state;
//		this.minMaxTree = new MinMaxTree<>(4);
		this.humanPlayerTurn = true;
	}
	
	private boolean isLegalMove(JTextField from, JTextField to)
	{
		/* Can't move the opponents piece ! */
		if((currentState.isWhiteToMove() && from.getText().equals("B")) ||
				(!currentState.isWhiteToMove() && from.getText().equals("W")))
			return false;
		
		byte[] toBeMovedPawns  = currentState.getBlackPawns();
		byte[] stationaryPawns = currentState.getWhitePawns();
		int fromRowIndex    = Integer.parseInt(from.getName().substring(0, 1));
		int fromColumnIndex = Integer.parseInt(from.getName().substring(1));
		int toRowIndex      = Integer.parseInt(to.getName().substring(0, 1));
		int toColumnIndex   = Integer.parseInt(from.getName().substring(1));
		
		if(currentState.isWhiteToMove())
		{
			toBeMovedPawns  = currentState.getWhitePawns();
			stationaryPawns = currentState.getBlackPawns();
			fromRowIndex    = 7 - fromRowIndex;
			fromColumnIndex = 7 - fromColumnIndex;
			toRowIndex      = 7 - toRowIndex;
			toColumnIndex   = 7 - toColumnIndex;
		}
		
		boolean moveLegal = ChessStateTransitioner.isLegalMove(fromRowIndex, toRowIndex, 
				fromColumnIndex, toColumnIndex, 
				toBeMovedPawns, stationaryPawns, 
				currentState.isEnPassantVulnerable(), currentState.getEnPassantColumn());
		if(moveLegal)
		{
			// FIXME : this should not be here
			currentState = ChessStateTransitioner.move(fromRowIndex, toRowIndex, 
					fromColumnIndex, toColumnIndex,
					currentState);
			return true;
		}
		return false;
	}
	
	@Override
 	public void mouseClicked(MouseEvent e)
	{
		if(!humanPlayerTurn)
			return;
		if(firstSelection == null)
		{
			firstSelection = (JTextField) e.getSource();
			firstSelection.setFont(Board.bigBoldFont);
		}
		else
		{
			JTextField secondSelection = (JTextField) e.getSource();
			if(isLegalMove(firstSelection, secondSelection)) // condition to move
				reDrawBoard();
			firstSelection.setFont(Board.normalBoldFont);
			firstSelection  = null;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		JTextField cell = (JTextField) e.getSource();
		if(cell == firstSelection)
			return;
		cell.setFont(Board.bigBoldFont);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		JTextField cell = (JTextField) e.getSource();
		if(cell == firstSelection)
			return;
		cell.setFont(Board.normalBoldFont);
	}
	
	public void loadNewGameState(File stateFile)
	{
		try
		{
			ChessStateFileSerializer serializer = new ChessStateFileSerializer(); 
			currentState = serializer.readState(stateFile.getAbsolutePath());
			this.reDrawBoard();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void reDrawBoard()
	{
		this.board.reDraw(currentState);
	}
}
