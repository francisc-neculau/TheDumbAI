package hanoitower.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import hanoitower.util.ListUtil;
import model.state.State;

public final class HtState implements State
{
	public static int NUMBER_OF_RODS  = 3;
	public static int NUMBER_OF_DISKS = 3;
	
	private HtState predecesor;
	private int lastDiskMoved;
	private boolean lastDiskMovedLeft;
	private int depth;
	/**
	 * This value stores all the information needed
	 * to encode a particular state of the game. First
	 * vector represents the rod index and the vector at
	 * that index represents the disks on it
	 * 
	 * Example :
	 * [][diskIndex]
	 * 
	 * 2   
	 * 1         -
	 * 0 -----  ---
	 *     0     0     0   [rodIndex][]
	 */
	int[][] disksOnRodsDistribution;
	
	
	/**
	 * New Model
	 * @param disksOnRodsDistribution
	 */
	
	List<Deque<Integer>> rodsWithDisks;
	
	public HtState(List<Deque<Integer>> rodsWithDisks)
	{
		this.rodsWithDisks = rodsWithDisks;
	}
	
	public HtState(List<Deque<Integer>> rodsWithDisks, HtState predecesor, int lastDiskMoved, boolean lastDiskMovedLeft, int movementTime)
	{
		this.rodsWithDisks = rodsWithDisks;
		this.predecesor = predecesor;
		this.lastDiskMoved = lastDiskMoved;
		this.lastDiskMovedLeft = lastDiskMovedLeft;
		this.depth = movementTime;
	}
	
	public HtState(int[][] disksOnRodsDistribution)
	{
		this.disksOnRodsDistribution = disksOnRodsDistribution;
		this.rodsWithDisks = new ArrayList<>();
		for (int[] rod : disksOnRodsDistribution)
		{
			Deque<Integer> r = new ArrayDeque<>();
			for(int index = 0; index < NUMBER_OF_DISKS && rod[index] != 0; index++)
				r.add(rod[index]);
			this.rodsWithDisks.add(r);
		}
	}
	
	/**
	 * @return the predecesor
	 */
	public HtState getPredecesor()
	{
		return predecesor;
	}

	/**
	 * @param predecesor the predecesor to set
	 */
	public void setPredecesor(HtState predecesor)
	{
		this.predecesor = predecesor;
	}

	/**
	 * @return the lastDiskMoved
	 */
	public int getLastMovementDisk()
	{
		return lastDiskMoved;
	}

	/**
	 * @param lastDiskMoved the lastDiskMoved to set
	 */
	public void setLastDiskMoved(int lastDiskMoved)
	{
		this.lastDiskMoved = lastDiskMoved;
	}

	/**
	 * @return the lastDiskMovedLeft
	 */
	public boolean isLastDiskMovedLeft()
	{
		return lastDiskMovedLeft;
	}

	/**
	 * @param lastDiskMovedLeft the lastDiskMovedLeft to set
	 */
	public void setLastDiskMovedLeft(boolean lastDiskMovedLeft)
	{
		this.lastDiskMovedLeft = lastDiskMovedLeft;
	}
	
	/**
	 * @return the depth
	 */
	public int getDepth()
	{
		return depth;
	}

//	/**
//	 * @param depth the movementTime to set
//	 */
//	public void setMovementTime(int depth)
//	{
//		this.depth = depth;
//	}

	public List<Deque<Integer>> getRods()
	{
		return rodsWithDisks;
	}
	
	public int[][] getDisksOnRodsDistribution()
	{
		return disksOnRodsDistribution;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(disksOnRodsDistribution);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HtState other = (HtState) obj;
		
		return ListUtil.deepEquals(rodsWithDisks, other.getRods());
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Deque<Integer> rod : rodsWithDisks)
		{
			sb.append("{");
			for (Integer integer : rod)
			{
				sb.append(integer).append(" ");
			}
			sb.append("}");
		}
		return sb.toString();
	}

	public boolean isAlreadyVisited(HtState statesStacktrace) 
	{
		HtState previousState = statesStacktrace.getPredecesor();
		if(previousState == null)
			return false;
		
		if(previousState.equals(this))
			return true;
		else
			return isAlreadyVisited(previousState);
		
	}

}
