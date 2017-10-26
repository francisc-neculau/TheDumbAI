package hanoitower.strategy;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hanoitower.model.HtState;
import model.EvaluationFunction;
import model.state.StateChooser;

public class HtRandomStateChooser implements StateChooser<HtState> 
{
	Map<Double, HtState> statesMap 	= new HashMap<>();
	Random randomChooser			= new SecureRandom();

	@Override
	public <F extends EvaluationFunction<HtState>> HtState choose(List<HtState> nextPossibleStates, HtState state,
			F evaluationFunction) 
	{
		Double evaluation;
		Double maxEvaluation = Double.NEGATIVE_INFINITY;
		Double secondMaxEvaluation = Double.NEGATIVE_INFINITY;
		
		for (HtState nextPossibleState : nextPossibleStates)
		{
			evaluation = evaluationFunction.evaluate(nextPossibleState);
//			System.out.print(evaluation + " ");
			if(evaluation > maxEvaluation)
			{
				secondMaxEvaluation = maxEvaluation;
				maxEvaluation = evaluation;
			}
			
			statesMap.put(evaluation, nextPossibleState);
		}
		
//		double randomValue = randomChooser.
		System.out.println();
		System.out.println("Max evaluation score: " + maxEvaluation);
		System.out.println("2nd Max evaluation score: " + secondMaxEvaluation);
		
		HtState chosenState;
		if(randomChooser.nextDouble() > 0.5 && secondMaxEvaluation != Double.NEGATIVE_INFINITY)
			chosenState = statesMap.get(secondMaxEvaluation);
		else
			chosenState = statesMap.get(maxEvaluation);
			
		
		
		statesMap.clear();
		return chosenState;
	}

}
