package game.chess.model;

import model.state.TransitionDetails;

public class ChessStateTransitionDetails implements TransitionDetails
{
	private boolean movedForward;
	private int numberOfSpaces;
	
	private boolean attackingMove;
	private boolean attackingEnPasantMove;
	
	// the moved pawn position
	private int rowIndex;
	private int columnIndex;
	
	/*
	 * GETTERS & SETTERS
	 */
	public boolean wasForwardMove() 
	{
		return movedForward;
	}
	
	public int getNumberOfSpaces() 
	{
		return numberOfSpaces;
	}
	
	public boolean wasAttackingMove() 
	{
		return attackingMove;
	}

	public boolean wasAttackingEnPasantMove() 
	{
		return attackingEnPasantMove;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}
}
