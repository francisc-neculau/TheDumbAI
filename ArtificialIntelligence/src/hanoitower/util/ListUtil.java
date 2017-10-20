package hanoitower.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ListUtil 
{
	public static boolean deepEquals(List<Deque<Integer>> list1, List<Deque<Integer>> list2)
	{
		if(list1.size() != list2.size())
			return false;
		
		for(int index = 0; index < list1.size(); index++)
		{
			if(list1.get(index).size() != list2.get(index).size())
				return false;
			
			List<Integer> intList1 = new ArrayList<>(list1.get(index));
			List<Integer> intList2 = new ArrayList<>(list2.get(index));
			
			if(!intList1.equals(intList2))
				return false;
//			if(!list1.get(index).equals(list2.get(index)))
//				return false;
		}
		return true;
	}

}
