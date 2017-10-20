package game.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private static final int DIMENSION = 3;
	private static final int FREE_CEL  = 0;
	
	int[][] matrix;
	
	public Board(int[][] matrix) {
		this.matrix = matrix;
	}

	public boolean isFinal() {
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if(this.matrix[i][j] == FREE_CEL)
					return false;
			}
		}
		return true;
	}
	
	public boolean isWon() {
		int     rowValue = 0, columnValue = 0, centralValue;
		boolean rowWin = false, columnWin = false, diagonalWin = false;

		for (int i = 0; i < DIMENSION; i++) {
			rowValue = this.matrix[i][0];
			rowWin   = true;
			for (int j = 0; j < DIMENSION; j++) {
				if(this.matrix[i][j] != rowValue)    // loop rows
					rowWin = false;
			}
			if(rowWin && rowValue != 0)
				return true;
		}
		for (int i = 0; i < DIMENSION; i++) {
			columnValue = this.matrix[0][i];
			columnWin   = true;
			for (int j = 0; j < DIMENSION; j++) {
				if(this.matrix[j][i] != columnValue) // loop columns
					columnWin = false;
			}
			if(columnWin && columnValue != 0)
				return true;
		}
		rowWin = false;
		columnWin = false;
		// check primary and secondary diagonal
		centralValue = matrix[1][1];
		if(centralValue != 0 && matrix[0][0] == matrix[2][2] && matrix[0][0] == centralValue)
			diagonalWin = true;
		if(centralValue != 0 && matrix[2][0] == matrix[0][2] && matrix[2][0] == centralValue)
			diagonalWin = true;
		if(diagonalWin || rowWin || columnWin)
			return true;
		return false;
			
	}
	
	// the move and the player that makes the move
	public Board result(Move move, Player player) {
		int row    = move.getRow();
		int column = move.getColumn();
		int celValue = 1;
		if(player == Player.MIN)
			celValue = -1;
		int[][] newMatrix = new int[DIMENSION][DIMENSION];
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				newMatrix[i][j] = this.matrix[i][j];
			}
		}
		newMatrix[row][column] = celValue;
		return new Board(newMatrix);
	}

	public List<Move> nextPossibleMoves() {
		List<Move> moves = new ArrayList<>();
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if(this.matrix[i][j] == FREE_CEL)
					moves.add(new Move(i, j));
			}
		}
		return moves;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				sb.append(this.matrix[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
