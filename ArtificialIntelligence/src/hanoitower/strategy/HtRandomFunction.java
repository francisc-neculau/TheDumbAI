package hanoitower.strategy;

import java.security.SecureRandom;

import hanoitower.model.HtState;
import model.EvaluationFunction;

public class HtRandomFunction implements EvaluationFunction<HtState>
{
	private SecureRandom random;
	
	public HtRandomFunction()
	{
		this.random = new SecureRandom();
	}
	
	@Override
	public double evaluate(HtState state)
	{
		return this.random.nextDouble() * 100;
	}

}
