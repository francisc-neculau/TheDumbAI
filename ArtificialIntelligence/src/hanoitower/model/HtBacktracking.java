package hanoitower.model;

import java.util.ArrayList;
import java.util.List;

import model.backtracking.Backtracking;

public class HtBacktracking extends Backtracking<HtState> 
{

	@Override
	protected List<HtState> filterVisitedStates(List<HtState> stateList) 
	{
		List<HtState> resultList = new ArrayList<>();
		for (HtState htState : stateList) 
		{
			if(!htState.isAlreadyVisited(htState.getPredecesor()))
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

}
