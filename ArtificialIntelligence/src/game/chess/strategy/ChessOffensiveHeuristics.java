package game.chess.strategy;

import java.security.SecureRandom;

import game.chess.model.ChessState;
import game.chess.model.ChessStateTransitionDetails;
import game.chess.model.ChessStateTransitioner;

public class ChessOffensiveHeuristics extends ChessCoreHeuristics 
{
	@Override
	public double evaluate(ChessState state) 
	{
		double score = super.evaluate(state);
		if(state.isWhiteToMove())
			score += computeScore(state.getTransitionDetails(), state.getBlackPawns());
		else
			score += computeScore(state.getTransitionDetails(), state.getWhitePawns());
		
		return score;
		
//		return new SecureRandom().nextInt(100);
	}
	
	
	private double computeScore(ChessStateTransitionDetails moveDetails, byte[] stationaryPawns)
	{
		double score = 0.0;
		int pawnRowIndex = moveDetails.getRowIndex();
		int pawnColumnIndex = moveDetails.getColumnIndex();
		
		for(int counter = pawnRowIndex; counter <= 7 - pawnRowIndex; counter++)
		{
			if(ChessStateTransitioner.isPositionBlockedAt(counter, pawnColumnIndex, stationaryPawns))
			{
				score -= 1;
				if(ChessStateTransitioner.isPositionAtackedAt(counter, pawnColumnIndex, stationaryPawns))
					score -= 2;
				
				break;
			}
			
			if(ChessStateTransitioner.isPositionAtackedAt(counter, pawnColumnIndex, stationaryPawns))
				score -= 2;
		}
		
		return score;
	}

}
