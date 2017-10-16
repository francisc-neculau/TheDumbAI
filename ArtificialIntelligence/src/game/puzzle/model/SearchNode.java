package game.puzzle.model;

import java.util.LinkedList;
import java.util.List;

public class SearchNode implements Comparable<SearchNode>{

	private Board      board;
	private SearchNode parent;
	private int 	   moves;
	
	public SearchNode(Board current, SearchNode parent, int moves) {
		super();
		this.board  = current;
		this.parent = parent;
		this.moves  = moves;
	}
	
	public int costFunction() {
		return ( board.hamingFunction() + moves);
	}
	
	public Board getCurrent() {
		return board;
	}

	public SearchNode getPrevious() {
		return parent;
	}

	public boolean hasPrevious() {
		return this.parent != null;
	}
	
	public int getNumberOfMoves() {
		return moves;
	}
	
	public List<SearchNode> getNeighbours() {
		List<SearchNode> result = new LinkedList<>();
		Board b = null;
		Board parentBoard = ( this.parent == null ) ? null : this.parent.board;
		b = this.board.shiftBoard(Shift.UP);
		if((b != null) && (!b.equals(parentBoard))) 
			result.add(new SearchNode(b, this, this.moves + 1));
		
		b = this.board.shiftBoard(Shift.RIGHT);
		if((b != null) && (!b.equals(parentBoard))) 
			result.add(new SearchNode(b, this, this.moves + 1));
			
		b = this.board.shiftBoard(Shift.DOWN);
		if((b != null) && (!b.equals(parentBoard)))  
			result.add(new SearchNode(b, this, this.moves + 1));
		
		b = this.board.shiftBoard(Shift.LEFT);
		if((b != null) && (!b.equals(parentBoard)))  
			result.add(new SearchNode(b, this, this.moves + 1));
		
		return result;
	}
	
	public boolean isGoal() {
		return this.board.isGoal();
	}
	
	@Override
	public String toString() {
		return this.board.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + moves;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SearchNode)) 
			return false;
		
		if (this == obj) 
			return true;
		
		SearchNode other = (SearchNode) obj;
		if(this.board.equals(other.board) && this.board.equals(other.board) && this.moves == other.moves)
			return true;
		
		return false;
	}

	@Override
	public int compareTo(SearchNode o) {
		if ( this.costFunction() > o.costFunction() ) 
			return 1;
		else if ( this.costFunction() < o.costFunction() ) 
			return -1;
		else 
			return 0;
	}
	
}
