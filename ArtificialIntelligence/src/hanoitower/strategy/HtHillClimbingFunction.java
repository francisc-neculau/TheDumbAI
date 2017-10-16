package hanoitower.strategy;

import java.util.Deque;

import hanoitower.model.HtState;
import model.EvaluationFunction;

public class HtHillClimbingFunction implements EvaluationFunction<HtState>
{
	private double diskValue;

	public HtHillClimbingFunction()
	{
		diskValue = HtState.NUMBER_OF_DISKS/100;
	}

	@Override
	public double evaluate(HtState state)
	{
		double value = 0;
		for ( Deque<Integer> rod : state.getRods())
		{
			value = rod.size() * diskValue;
		}
		return value;
	}

}
