import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import graph.LabyrinthMatrixGraph;
import model.LabyrinthMatrixModel;
import model.LabyrinthMatrixModelConfiguration;
import model.MatrixCell;

public class Main {
	
	private static int[][] maze;
	private static boolean[][] wasHere;
	private static boolean[][] correctPath;
	private static int startX, startY;
	private static int endX, endY;
	private static int width;
	private static int height;
	
	public static void main(String args[]) {
		
		LabyrinthMatrixModelConfiguration configuration = new LabyrinthMatrixModelConfiguration();
		
		configuration.setFinish(2);
		configuration.setFree(0);
		configuration.setStart(-1);
		configuration.setWall(1);
		configuration.readMatrix("FILES/maze.txt");
		
		LabyrinthMatrixGraph graph = new LabyrinthMatrixGraph(new LabyrinthMatrixModel(configuration));
		
		 /*
		  * ***********************************
		  *	Breath First Search - Shortest Paht
		  * ***********************************
		  */
		Deque<MatrixCell> queue = new LinkedList<>();
		MatrixCell current = null;
		
		queue.add(graph.getStartNode());
		while(!queue.isEmpty()) {
			current = queue.poll();
			for (MatrixCell matrixCell : graph.getAdjacentNodes(current)) {
				if(current.hasParent() && current.getParent().equals(matrixCell))
					continue;
				if(!matrixCell.hasParent())
					queue.add(matrixCell);
				matrixCell.setParent(current);
				if(matrixCell.equals(graph.getFinishNode())){
					current = matrixCell;
					queue = new LinkedList<>();
					break;
				}
			}
		}
		Deque<MatrixCell> stack = new LinkedList<>();
		while(current != null) {
			stack.push(current);
			current = current.getParent();
		}

		while(!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
		//System.out.println(graph);
		
		System.out.println();
		/*
		 *  *************
		 *	Recurse Solve
		 *  *************
		 */
		width  = configuration.getRowsNumber();
		height = configuration.getColumnsNumber();
		
		startX = graph.getStartNode().getRowNumber();
		startY = graph.getStartNode().getColumnNumber();;
		
		endX   = graph.getFinishNode().getRowNumber();
		endY   = graph.getFinishNode().getColumnNumber();
		
		
		maze        = new int[width][height]; // The maze
		wasHere     = new boolean[width][height];
		correctPath = new boolean[width][height]; // The solution to the maze
		
		maze = configuration.getMatrix(); // Create Maze (1 = path, 2 = wall)
	    for (int row = 0; row < maze.length; row++)  
	        // Sets boolean Arrays to default values
	        for (int col = 0; col < maze[row].length; col++){
	            wasHere[row][col] = false;
	            correctPath[row][col] = false;
	        }
	    boolean b = recursiveSolve(startX, startY);
		
	    for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				System.out.print(correctPath[i][j] ? "x" : " ");
			}
			System.out.println();
		}
	}
	
	public static boolean recursiveSolve(int x, int y) {
	    if (x == endX && y == endY) 
	    	return true; // If you reached the end
	    if (maze[x][y] == 1 || wasHere[x][y]) 
	    	return false;  
	    // If you are on a wall or already were here
	    wasHere[x][y] = true;
	    if (x != 0) 					  // Checks if not on left edge
	        if (recursiveSolve(x-1, y)) { // Recalls method one to the left
	            correctPath[x][y] = true; // Sets that path value to true;
	            return true;
	        }
	    if (x != width - 1) 			  // Checks if not on right edge
	        if (recursiveSolve(x+1, y)) { // Recalls method one to the right
	            correctPath[x][y] = true;
	            return true;
	        }
	    if (y != 0)  					  // Checks if not on top edge
	        if (recursiveSolve(x, y-1)) { // Recalls method one up
	            correctPath[x][y] = true;
	            return true;
	        }
	    if (y != height- 1) 			  // Checks if not on bottom edge
	        if (recursiveSolve(x, y+1)) { // Recalls method one down
	            correctPath[x][y] = true;
	            return true;
	        }
	    return false;
	}
	
}
