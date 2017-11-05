package model;

import model.state.State;

/**
 * The problem of improving performance P at task T can be reduced
 * to learning some particular target function.
 * 
 * TargetFunction : {States} -> {StateTransition}
 * 	Given a 'State', this function produces as output
 * the 'best' StateTransition of it.
 * 
 * Because the aforementioned is in many situations a nonoperational
 * definition we will define an approximation function of it
 * 
 * EvaluationFunction : {States} -> R
 * 	Given the a 'State', this function produces as output
 * the a score of how 'good' the 'State' is
 * 
 * @author franc
 *
 */
public interface EvaluationFunction<S extends State>
{
	/**
	 * FIXME: Add more details
	 * @param state
	 * @return
	 */
	public abstract double evaluate(S state);
}
