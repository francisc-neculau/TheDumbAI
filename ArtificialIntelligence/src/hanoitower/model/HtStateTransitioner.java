package hanoitower.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import model.state.StateTransitioner;

public class HtStateTransitioner implements StateTransitioner<HtState>
{

	@Override
	public List<HtState> generateAllNextLegalStates(HtState current)
	{
		List<HtState> newStates = new ArrayList<>();
		
		List<Deque<Integer>> baseNewRods;
		List<Deque<Integer>> newRods;
		Integer disk;
		
		List<Deque<Integer>> rods = current.getRods();
		for(int i = 0; i < rods.size(); i++)
		{
			Deque<Integer> rod = rods.get(i);
			if(rod.isEmpty())
				continue;
			
			// Create the first newRod from rod 
			// without it's top disk then add
			// it to newRods
			baseNewRods = cloneRods(rods);
			disk = baseNewRods.get(i).pop();
			
			
			newRods = cloneRods(baseNewRods);
			for (int j = 0; j < rods.size(); j++)
			{
				if(j == i)
					continue;
				if(rods.get(j).isEmpty() || (rods.get(j).peekFirst() > disk))
				{
					// Found a new possible state.
					newRods.get(j).push(disk);
					HtState nextState = new HtState(newRods, current, disk, j < i , current.getDepth() + 1);
					newStates.add(nextState);
					newRods = cloneRods(baseNewRods);
				}
			}
		}	
		return newStates;
	}

	private List<Deque<Integer>> cloneRods(List<Deque<Integer>> rods)
	{
		List<Deque<Integer>> clonedRods = new ArrayList<>();
		for (Deque<Integer> rod : rods)
		{
			clonedRods.add(new ArrayDeque<>(rod));
		}
		return clonedRods;
	}

}
