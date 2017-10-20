package hanoitower.model;

import model.state.FinalStateChecker;

public class HtFinalStateChecker implements FinalStateChecker<HtState>
{

	@Override
	public boolean isFinal(HtState state)
	{
		try 
		{
			return state.getRods().get(HtState.NUMBER_OF_RODS - 1).size() == HtState.NUMBER_OF_DISKS;	
		} 
		catch (Exception e) 
		{
			System.out.println("Hello");
		}
		
		return false;
	}

}
