package game.tictactoe.model;

import java.util.List;

public class State {

	State  previous;
	Player player;
	Board  board;
	
	
	public State(Player player, Board board) {
		this.previous = null;
		this.player   = player;
		this.board    = board;
	}
	
	
	public Player player() {
		return this.player;
	}
	
	public List<Move> nextPossibleMoves() {
		return board.nextPossibleMoves();
	}
	
	public State result(Move move) {
		if(this.player == Player.MIN)
			return new State(Player.MAX, board.result(move, Player.MIN));
		else
			return new State(Player.MIN, board.result(move, Player.MAX));
	}
	
	public boolean isTerminal() {
		return this.board.isFinal();
	}

	public boolean isGoal() {
		return this.board.isWon();
	}
	
	public int utility(int depth) {
		// Game is over
		if (isTerminal() && isGoal() && player() == Player.MIN) // Min's turn
			return depth - 1;
		else if (isTerminal() && isGoal() && player() == Player.MAX) // Max's turn
			return 1 - depth;
		else 
			return 0;
	}
	
	@Override
	public String toString() {
		return this.board.toString();
	}
	
}