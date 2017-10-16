package game.puzzle.model;

import java.util.Random;

/*
	 * the Board class represents a particular state of the n-Puzzle game
	 * 
	 */
public class Board {
	
	private static int     dimension;
	private static int[][] goalBlocks;
	private        int[][] blocks;
	private 	   int     column;  
	private 	   int     row;
	
	public Board(int[][] blocks) {
		this.blocks = blocks;
		this.setBlankCoordinates();
	}

	public Board shiftBoard(Shift TYPE) {
		int auxColumn = 0;
		int auxRow    = 0;
		switch (TYPE) {
		case UP:
			auxColumn = column; auxRow = row - 1;
			break;
		case RIGHT:
			auxColumn = column + 1; auxRow = row;
			break;
		case DOWN:
			auxColumn = column; auxRow = row + 1;
			break;
		case LEFT:
			auxColumn = column - 1; auxRow = row;
			break;
		default:
			break;
		}
		if((   auxRow > Board.dimension - 1) || (auxRow    < 0)) return null;
		if((auxColumn > Board.dimension - 1) || (auxColumn < 0)) return null;
		int[][] newBlocks = copy(this.blocks);
		newBlocks[row][column]       = this.blocks[auxRow][auxColumn];
		newBlocks[auxRow][auxColumn] = -1;
		return new Board(newBlocks);
	}
	
	private int[][] copy(int source[][]) {
		int[][] result = new int[Board.dimension][Board.dimension];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = source[i][j];
			}
		}
		return result;
	}
	
	public int size() {
		return Board.dimension;
	}
	
	public int hamingFunction() {
		int priority = 0;
		for (int row = 0; row < this.blocks.length; row++) {
			for (int column = 0; column < this.blocks.length; column++) {
				if(this.blocks[row][column] == Board.goalBlocks[row][column])
					priority++;
			}
		}
		return priority;
	}

	public static void setGoalBlocks(int[][] goalBlocks) {
		Board.goalBlocks = goalBlocks;
	}
	
	public static void setDimension(int dimension) {
		Board.dimension = dimension;
	}
	
	private void setBlankCoordinates() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(this.blocks[i][j] == -1) {
					this.row    = i;
					this.column = j;
				}
			}
		}
	}
	
	public boolean isGoal() {
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks.length; j++) {
				if(this.blocks[i][j] != Board.goalBlocks[i][j]) 
					return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) 
			return false;
		
		if(!(obj instanceof Board)) 
			return false;
		
		if(obj == this) 
			return true;
		
		Board other = (Board)obj;
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks.length; j++) {
				if(this.blocks[i][j] != other.blocks[i][j]) 
					return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int prime  = 119;
		int result = 0;
		// Use HashCodeBuilder
		for (int i = 0; i < this.blocks.length; i++) {
			result = result + prime * this.blocks[i][(new Random()).nextInt(dimension-1)];
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks.length; j++) {
				sb.append(this.blocks[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	
}
