package model;

public class Move {

	private int column;
	private int row;
	
	public Move(int row, int column) {
		this.row    = row;
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}

}
