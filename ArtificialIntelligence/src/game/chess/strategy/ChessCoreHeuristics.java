package game.chess.strategy;

import java.security.SecureRandom;

import game.chess.model.ChessState;
import game.chess.model.ChessStateTransitionDetails;
import game.chess.model.ChessStateTransitioner;
import model.EvaluationFunction;

public class ChessCoreHeuristics implements EvaluationFunction<ChessState>
{
	protected int [] pawnValuetable = new int[] {
			 	100, 100, 100, 100, 100, 100, 100, 100,
			    75, 75, 75, 75, 75, 75, 75, 75,
			    25, 25, 29, 29, 29, 29, 25, 25,
			     4,  8, 12, 21, 21, 12,  8,  4,
			     0,  4,  8, 17, 17,  8,  4,  0,
			     4, -4, -8,  4,  4, -8, -4,  4,
			     4,  8,  8,-17,-17,  8,  8,  4,
			     0,  0,  0,  0,  0,  0,  0,  0
		};

	@Override
	public double evaluate(ChessState state)
	{
		double score = computePawnDifferenceBonus(state);
		ChessStateTransitionDetails moveDetails = state.getTransitionDetails();
		if(moveDetails.wasForwardMove())
			score += moveDetails.getNumberOfSpaces();
		
		score += pawnValuetable[8 * moveDetails.getRowIndex() + moveDetails.getColumnIndex()];
		return score;
		
//		return new SecureRandom().nextInt(100);
	}

	private double computePawnDifferenceBonus(ChessState state)
	{
		if(state.isWhiteToMove())
			return ChessStateTransitioner.computeNumberOfPawns(state.getBlackPawns()) - ChessStateTransitioner.computeNumberOfPawns(state.getWhitePawns());
		else
			return ChessStateTransitioner.computeNumberOfPawns(state.getWhitePawns()) - ChessStateTransitioner.computeNumberOfPawns(state.getBlackPawns());
					
//		return 0;
	}
}
