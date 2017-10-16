package model.state;

import java.util.List;

import model.EvaluationFunction;

public interface StateChooser<S extends State>
{
	/**
	 * 
	 * @param nextPossibleStates
	 * @param state               the state that spanned nextPossibleStates
	 * @param evaluationFunction  this function will evaluate every nextPossibleState
	 * @return
	 */
	public abstract <F extends EvaluationFunction<S>> S choose(List<S> nextPossibleStates, S state, F evaluationFunction);
}
