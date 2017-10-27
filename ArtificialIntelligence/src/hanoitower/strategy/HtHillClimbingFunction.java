package hanoitower.strategy;

import hanoitower.model.HtState;
import model.EvaluationFunction;

public class HtHillClimbingFunction implements EvaluationFunction<HtState>
{
	@Override
	public double evaluate(HtState state)
	{
		if (state.isAlreadyVisited(state.getPredecesor()))
			return Double.NEGATIVE_INFINITY;

		return state.getDepth() + h(state);
	}

	private double h(HtState state)
	{
		double sum = 0;

		for (int index = 0; index < state.getRods().size(); index++)
		{
			if (index == state.getRods().size() - 1)
				sum += 2 * state.getRods().get(index).size();
			else
				sum += state.getRods().get(index).size();
		}

		return sum;
	}

}
