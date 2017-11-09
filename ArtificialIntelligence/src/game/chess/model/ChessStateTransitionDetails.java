package game.chess.model;

import model.state.TransitionDetails;

public class ChessStateTransitionDetails implements TransitionDetails
{
	private boolean movedForward;
	private int numberOfSpaces;
	
	private boolean attackingMove;
	private boolean attackingEnPasantMove;
	
	// the moved pawn position
	private int toRowIndex;
	private int toColumnIndex;
	
	// FIXME : Builder Pattern
	public ChessStateTransitionDetails(boolean movedForward, boolean attackingMove, 
			boolean attackingEnPasantMove, int numberOfSpaces, int rowIndex, int columnIndex)
	{
		this.movedForward = movedForward;
		this.attackingMove = attackingMove;
		this.attackingEnPasantMove = attackingEnPasantMove;
		this.numberOfSpaces = numberOfSpaces;
		this.toRowIndex = rowIndex;
		this.toColumnIndex = columnIndex;
	}
	
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
		return toRowIndex;
	}

	public int getColumnIndex() {
		return toColumnIndex;
	}
}
