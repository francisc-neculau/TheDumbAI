package hanoitower.strategy;

import hanoitower.model.HtState;
import model.EvaluationFunction;

public class HtAStarFunction implements EvaluationFunction<HtState>
{
	/*
	 * According to A star algorithm, we need to move through the nodes 
	 * based on the minimum value of f (x)=g (x)+h (x). 
	 * Use g(x)=depth of the node, and 
	 * h (x)= (the total number of disks of left and middle poles) + 2*(number of disks that in the right pole and smaller than any disk that in left or middle poles)
	 */

	@Override
	public double evaluate(HtState state) 
	{
		if(state.isAlreadyVisited(state.getPredecesor()))
			return Double.NEGATIVE_INFINITY;
		
		return -1 * (state.getDepth() + h(state));
	}

	private double h(HtState state) 
	{
		double sum = 0;
		
		for (int index = 0; index < state.getRods().size(); index++) 
		{
			if(index == state.getRods().size() - 1)
				sum += 2 * state.getRods().get(index).size();
			else
				sum += state.getRods().get(index).size();
		}
		
		return sum;
	}

}
