package game.puzzle;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import game.puzzle.model.Board;
import game.puzzle.model.SearchNode;

public class Main {
	
	public static void main(String[] args) {
		
		int[][][] testCase = {
				{{-1,1,3},{4,2,5},{7,8,6}}, //
				{{-1,3,1},{4,2,5},{7,8,6}},
				{{8,1,3},{4,-1,2},{7,6,5}}, //
				{{8,1,3},{4,-1,2},{7,5,6}},
				{{8,1,3},{4,2,-1},{7,5,6}},
				{{8,1,3},{4,2,-1},{6,5,7}}, //
				{{3,1,8},{2,4,-1},{7,5,6}},
				//{{-1,2,6},{4,1,7},{5,3,8}},
				//{{-1,5,4},{8,6,7},{2,3,1}},
				{{3,8,1},{4,2,-1},{7,5,6}},
		};
		int[][] goal = {{1,2,3},{4,5,6},{7,8,-1}};
		
		double averageTime = 0.0;
		long   startTime, endTime;
		int    solved   = 0;
		int    solvable = 0;
	
		
		startTime = System.currentTimeMillis();
		for (int i = 0; i < testCase.length; i++) {
			if(checkSolvable(testCase[i]))  {
				solvable++;
				if(hillClimbing(testCase[i],goal).isGoal())
					solved++;
			}
		}
		endTime = System.currentTimeMillis();
		averageTime = ((double)(endTime - startTime))/30;
		System.out.println("test time : " + averageTime );
		System.out.println("solvable  : " + solvable + "/" + testCase.length);
		System.out.println("solved    : " + solved   + "/" + solvable);
		//0 2 5 7 8
//		System.out.println(checkSolvable(testCase[5]));
//		printSolution(bestFirst(testCase[5],goal));
//		printSolution(bestFirst(testCase[5],goal));
	}
	
	public static void printSolution(SearchNode root) {
		if(root == null) {
			System.out.println("no solution");
			return;
		}
		while(root.hasPrevious()) {
			System.out.println(root);
			root = root.getPrevious();
			if(!root.hasPrevious())
				System.out.println(root);
		}
	}
	
	public static SearchNode hillClimbing(int[][] start, int[][] goal) {
		SearchNode 		 currentNode = new SearchNode(new Board(start), null, 0);
		List<SearchNode> neighbours ;
		SearchNode 	  	 nextNode;
		int 			 nextMinimum;
		
		Board.setGoalBlocks(goal);
		Board.setDimension(start.length);
		
		do {
			neighbours  = currentNode.getNeighbours();
			nextMinimum = -1;
			nextNode    = null;
			for (SearchNode neighbour : neighbours)
				if(neighbour.costFunction() > nextMinimum) {
					nextMinimum = neighbour.costFunction();
					nextNode    = neighbour;
				}
			if(nextMinimum <= currentNode.costFunction())
				break;
			currentNode = nextNode;
		} while(true);
		return currentNode;
	}
	
	public static SearchNode bestFirst(int[][] start, int[][] goal) {
		PriorityQueue<SearchNode> queue   = new PriorityQueue<>();
		Set<SearchNode>           visited = new LinkedHashSet<>();
		
		Board.setGoalBlocks(goal);
		Board.setDimension(start.length);
		
		SearchNode initial 	= new SearchNode(new Board(start), null, 0);
		SearchNode current  = null;
		
		List<SearchNode> neighbours;
		
		queue.add(initial);
		
		while(!queue.isEmpty()) {
			current = queue.poll();
			if(visited.contains(current))
				continue;
			else 
				visited.add(current);
			
			if(current.isGoal()) 
				break;
			
			neighbours = current.getNeighbours();
			for (SearchNode neighbour : neighbours)
				queue.add(neighbour);
		}
		
		return current;
	}
	
	public static boolean checkSolvable(int[][] matrix) {
        int inversions = 0;
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if(matrix[i][j] != -1)
					array.add(matrix[i][j]);
			}
		}
        for (int i = 0; i < array.size(); i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if(array.get(i) > array.get(j))
					inversions++;
			}
		}

        if (inversions%2 == 1)
            return false;
        else
            return true;
    }
	
}
