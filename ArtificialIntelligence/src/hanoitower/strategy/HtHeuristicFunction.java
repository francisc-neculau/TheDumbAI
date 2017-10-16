package hanoitower.strategy;

import hanoitower.model.HtState;
import model.EvaluationFunction;

public class HtHeuristicFunction implements EvaluationFunction<HtState>
{
	
	@Override
	public double evaluate(HtState state)
	{
		return -(double)(t(state) + d(state));
	}
	
	private int t(HtState state)
	{
		int disk = state.getLastMovementDisk();
		int expectedMovementTimeFrame = (int)Math.pow(2, disk);
		int movementTime = state.getLastMovementTime();
		int movementOffset = (int)Math.pow(2, (double)(disk - 1));
		if((movementTime - movementOffset) % expectedMovementTimeFrame == 0)
			return 1;
		return 3;
	}
	
	/**
	  If the number of disks which are played is an even number,
		then:
		• The disks with even weight (2, 4, 6, etc.) have to be
		moved to the left.
		• The disks with odd weight (1, 3, 5, etc.) have to be
		moved move to the right.
		 Otherwise, if the number of played disks is an odd number
		the directions are exchanged.
		 Thus:
		• Even weight disks to the right.
		• Odd weight disks to the left. 
	 */
	private int d(HtState state)
	{
		int disk = state.getLastMovementDisk();
		if(HtState.NUMBER_OF_DISKS % 2 == 0)
			if((disk % 2 == 0 && state.isLastDiskMovedLeft()) || (disk % 2 == 1 && !state.isLastDiskMovedLeft())) // if k can be moved to its expected direction
			{
				return 1;
			}
			else // if k cannot be moved to its expected direction
			{
				return 3;
			}
		else
			if((disk % 2 == 0 && !state.isLastDiskMovedLeft()) || (disk % 2 == 1 && state.isLastDiskMovedLeft())) // if k can be moved to its expected direction
			{
				return 1;
			}
			else // if k cannot be moved to its expected direction
			{
				return 3;
			}
	}
	
}
