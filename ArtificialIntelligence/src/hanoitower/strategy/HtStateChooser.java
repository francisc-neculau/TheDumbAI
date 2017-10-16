package hanoitower.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanoitower.model.HtState;
import model.EvaluationFunction;
import model.state.StateChooser;

public class HtStateChooser implements StateChooser<HtState>
{
	Map<Double, HtState> statesMap = new HashMap<>();

	@Override
	public <F extends EvaluationFunction<HtState>> HtState choose(List<HtState> nextPossibleStates, HtState state,
			F evaluationFunction)
	{
		Double evaluation;
		Double maxEvaluation = -100.0;
		for (HtState nextPossibleState : nextPossibleStates)
		{
			evaluation = evaluationFunction.evaluate(nextPossibleState);
			if(evaluation > maxEvaluation)
				maxEvaluation = evaluation;
			statesMap.put(evaluation, nextPossibleState);
		}
		
		HtState chosenState = statesMap.get(maxEvaluation);
		
		statesMap.clear();
		return chosenState;
	}
	
}
