package model.state;

import java.util.ArrayList;
import java.util.List;

public class SolutionStateTrace<S extends State>
{
	private S initialState;
	private S finalState;
	private List<S> states;

	public SolutionStateTrace(S initialState)
	{
		this.initialState = initialState;
		this.states       = new ArrayList<>();
		this.states.add(initialState);
	}
	
	public boolean add(S state)
	{
		return states.add(state);
	}

	/**
	 * @return the initialState
	 */
	public S getInitialState()
	{
		return initialState;
	}

	/**
	 * @return the finalState
	 */
	public S getFinalState()
	{
		return finalState;
	}

	/**
	 * @return the states
	 */
	public List<S> getStates()
	{
		return states;
	}
}
