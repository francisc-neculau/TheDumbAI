package game.tictactoe.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {
	
	private State state;
	
	public Game(State state) {
		this.state = state;
	}
	
	
	public boolean isOver() {
		return this.state.isTerminal() || this.state.isGoal();
	}
	
	public Move getBestMove() {
		
		Node root = new Node(this.state, 0);
		
		int bestValue = minmax(root);
		
		return root.getBestMove(bestValue);
		
	}
	
	public int minmax(Node node) {
        Integer bestValue = node.getUtility();
        Integer value;
        
		if(node.isTerminal())
        	return bestValue;
		
        if(node.getPlayer() == Player.MIN) {
        	bestValue = Integer.MAX_VALUE;
        	for (Node child : node) {
				value = minmax(child);
				bestValue = Math.min(value, bestValue);
			}
        }
        if(node.getPlayer() == Player.MAX) {
        	bestValue = Integer.MIN_VALUE;
        	for (Node child : node) {
				value = minmax(child);
        		bestValue = Math.max(value, bestValue);
			}
        }
        node.setUtility(bestValue);
        return bestValue;
    }



	
	public State getState() {
		return state;
	}



	public void setState(State state) {
		this.state = state;
	}
	
}




class Node implements Iterable<Node>{

	private Map<Move,Node> moveNodeMap = new HashMap<>();
	private int   depth;
	private State state;
	private int utility;
	
	public Node(State state, int depth) {
		this.state   = state;
		this.depth   = depth;
		this.utility = state.utility(this.depth);
		this.generetaChildNodes();
	}
	


	private void generetaChildNodes() {
		if(state.isGoal())
			return;
		List<Move> moves = this.state.nextPossibleMoves();
		Node child;
		for (Move move : moves) {
			child = new Node(this.state.result(move), this.getDepth()+1);
			this.moveNodeMap.put(move, child);
		}
	}



	public Move getBestMove(int bestValue) {
		Set<Move> keySet = this.moveNodeMap.keySet();
		Move result = null;
		for (Move move : keySet) {
			if(this.moveNodeMap.get(move).getUtility() == bestValue)
				return move;
		}
		return result;
	}

	
	public Player getPlayer() {
		return this.state.player();
	}

	public boolean isTerminal() {
		return this.state.isTerminal();
	}
	
	
	public int getUtility() {
		return this.utility;
	}

	public void setUtility(Integer utility) {
		this.utility = utility;
	}

	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	
	
	@Override
	public Iterator<Node> iterator() {
		return moveNodeMap.values().iterator();
	}
	
}
