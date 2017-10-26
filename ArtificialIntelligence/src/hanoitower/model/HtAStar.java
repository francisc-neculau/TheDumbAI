package hanoitower.model;

import java.util.ArrayList;
import java.util.List;

import model.bestfirst.AStar;

public class HtAStar extends AStar<HtState> {

	@Override
	protected List<HtState> filterVisitedStates(List<HtState> stateList) 
	{
		List<HtState> resultList = new ArrayList<>();
		for (HtState htState : stateList) 
		{
			if(!htState.isAlreadyVisited(htState.getPredecesor()) && !isStateAlreadyAdded(htState))
				resultList.add(htState);
		}
		
		
		return resultList;
	}

	@Override
	protected void printFinalState(HtState finalState) 
	{
		if(finalState.getPredecesor() != null)
			printFinalState(finalState.getPredecesor());

		System.out.println(finalState);
	}

	public boolean isStateAlreadyAdded(HtState htState)
	{
		for (HtState state : iterationDeque) 
		{
			if(state.equals(htState))
				return true;
		}
		
		return false;
	}
}
