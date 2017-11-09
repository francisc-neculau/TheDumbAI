package game.chess.strategy;

import java.security.SecureRandom;

import game.chess.model.ChessState;
import game.chess.model.ChessStateTransitionDetails;
import game.chess.model.ChessStateTransitioner;

public class ChessDefensiveHeuristics extends ChessCoreHeuristics 
{
	@Override
	public double evaluate(ChessState state) 
	{
		double score = super.evaluate(state);
		if(state.isWhiteToMove())
			score += computeScore(state.getTransitionDetails(), state.getWhitePawns(), state.getBlackPawns());
		else
			score += computeScore(state.getTransitionDetails(), state.getBlackPawns(), state.getWhitePawns());
		
		return score;
		
//		return new SecureRandom().nextInt(100);
	}

	private double computeScore(ChessStateTransitionDetails moveDetails, byte[] personalPawns, byte[] adversaryPawns) 
	{
		double score = 0.0;
		int rowIndex = moveDetails.getRowIndex();
		int columnIndex = moveDetails.getColumnIndex();
		
		if(columnIndex != 0 && ChessStateTransitioner.isBackedLeft(rowIndex, columnIndex, personalPawns))
			score += 5;
		if(columnIndex != 7 && ChessStateTransitioner.isBackedRight(rowIndex, columnIndex, personalPawns))
			score += 5;
		
		// this method is used for checking if the moved pawn can backup another pawn of the same colour
		if(ChessStateTransitioner.isPositionBackingAt(rowIndex, columnIndex, personalPawns))
			score += 5;
		
		if(ChessStateTransitioner.isPositionBlockingAt(rowIndex, columnIndex, adversaryPawns))
			score += 7;
			
		return score;
	}

}
