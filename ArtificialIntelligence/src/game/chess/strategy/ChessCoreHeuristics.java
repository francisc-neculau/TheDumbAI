package game.chess.strategy;

import java.security.SecureRandom;

import game.chess.model.ChessState;
import game.chess.model.ChessStateTransitionDetails;
import model.EvaluationFunction;

public class ChessCoreHeuristics implements EvaluationFunction<ChessState>
{

	@Override
	public double evaluate(ChessState state)
	{
		ChessStateTransitionDetails moveDetails = state.getTransitionDetails();
		if(moveDetails.wasForwardMove())
			return moveDetails.getNumberOfSpaces();
		if(moveDetails.wasAttackingMove())
			return 3;
		if(moveDetails.wasAttackingEnPasantMove())
			return 4;
		
		return 0;
		
//		return new SecureRandom().nextInt(100);
	}

}
