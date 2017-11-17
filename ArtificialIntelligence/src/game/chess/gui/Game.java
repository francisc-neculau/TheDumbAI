package game.chess.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.chess.model.ChessFinalStateChecker;
import game.chess.model.ChessState;
import game.chess.model.ChessStateFileSerializer;
import game.chess.model.ChessStateTransitioner;
import game.chess.strategy.ChessDefensiveHeuristics;
import game.chess.strategy.ChessOffensiveHeuristics;
import model.minmax.MinMaxTree;

public class Game extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -7675448288069940521L;
	
	private Board board;
	private JTextField firstSelection;
	private ChessState currentState;
	private boolean isFinished;
	private boolean whiteMove;
	private ChessFinalStateChecker checker;
	private int drawCounter = 0;
	private MinMaxTree<ChessState> minMaxTree;
	
	public Game(ChessState state, boolean whiteMove)
	{
		this.setLayout(null);
		this.board = new Board(state, this);
		this.add(board);
		this.currentState = state;
		this.isFinished = false;
		this.whiteMove = whiteMove;
		this.checker = new ChessFinalStateChecker();
		this.minMaxTree = new MinMaxTree<>(4);
		this.minMaxTree.setTransitioner(new ChessStateTransitioner());
		this.minMaxTree.setEvaluationFunction(new ChessDefensiveHeuristics());
		
		JButton btnAiNextMove = new JButton("AI Next Move");
		btnAiNextMove.setBounds(400, 64, 115, 45);
		btnAiNextMove.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent e)
	        {
	            Game.this.generateAiNextMove();
	        }
	    });
		this.add(btnAiNextMove);
	}
	
	private void generateAiNextMove()
	{
		ChessState unblockingAiNextState; 
		ChessState aiNextState = minMaxTree.nextState(currentState, MinMaxTree.MAX_PLAYER);
		if(aiNextState == null)
		{
			currentState.setWhiteToMove(!currentState.isWhiteToMove());
			currentState.setEnPassantColumn((byte)-1);
			currentState.setEnPassantVulnerable(false);
			unblockingAiNextState = minMaxTree.nextState(currentState, MinMaxTree.MAX_PLAYER);
			if(unblockingAiNextState != null)
				aiNextState = unblockingAiNextState;
		}

		if(aiNextState != null)
		{
			if(this.checker.isFinal(aiNextState) && !isFinished)
			{
				String winner = "";
				if(aiNextState.isWhiteToMove())
					winner = "white";
				else
					winner = "black";
				isFinished = true;
				currentState = aiNextState;
				whiteMove = !whiteMove;
				reDrawBoard();
				JOptionPane.showMessageDialog(this, "Game Over"/*, winner is + winner*/, "Dialog",JOptionPane.ERROR_MESSAGE);
			}
		}
		if(aiNextState == null)
		{
			// remiza
			this.drawCounter++;
			this.isFinished = true;
			if(drawCounter == 2)
				if(ChessStateTransitioner.computeNumberOfPawns(currentState.getBlackPawns()) == 
						ChessStateTransitioner.computeNumberOfPawns(currentState.getWhitePawns()))
					JOptionPane.showMessageDialog(this, "Game Over, Draw :(", "Dialog",JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Game Over", "Dialog",JOptionPane.ERROR_MESSAGE);
			return;
		}
		drawCounter = 0;
//		if(isFinished)
//			return;
		currentState = aiNextState;
		whiteMove = !whiteMove;
		reDrawBoard();
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
		int toColumnIndex   = Integer.parseInt(to.getName().substring(1));
		
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
		if(isFinished)
			return;
		
		JTextField selectedCell = (JTextField) e.getSource();
//		if(firstSelection == null && whiteMove && selectedCell.getText().equals("B"))
//			return;
//		if(firstSelection == null && !whiteMove && selectedCell.getText().equals("W"))
//			return;

		if(firstSelection == null)
		{
			firstSelection = selectedCell;
			firstSelection.setFont(Board.bigBoldFont);
		}
		else
		{
			JTextField secondSelection = (JTextField) e.getSource();
			if(isLegalMove(firstSelection, secondSelection)) // condition to move
			{	
				whiteMove = !whiteMove;
				reDrawBoard();
			}
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
			whiteMove = currentState.isWhiteToMove();
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
