package model.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hanoitower.model.HtState;

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

//	for (HtState state : solution.getStates())
//	{
//		System.out.println(state);
//	}
//	HtState p = solution.getStates().get(solution.getStates().size() - 1).getPredecesor();
//	int counter = 0;
//	while( p.getPredecesor() != null) 
//	{
		//System.out.println(p);
//		p = p.getPredecesor();
//		counter++;
//	}
//	System.out.println(counter);
//	System.out.println("Depth:" + solution.getStates().get(solution.getStates().size() - 1).getDepth());
	
	public S getLastState()
	{
		return states.get(states.size() - 1);
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

	public Set<HtState> getUniquesStates()
	{
		return new HashSet<HtState>((Collection<? extends HtState>) states);
	}
}
