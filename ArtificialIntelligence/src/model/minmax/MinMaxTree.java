package model.minmax;

import java.util.List;

import model.EvaluationFunction;
import model.state.State;
import model.state.StateTransitioner;

public class MinMaxTree<S extends State>
{
	public static final int MIN_PLAYER = 1;
	public static final int MAX_PLAYER = 0;
	
	private int globalDepth;
	private Node<S> root;
	
	private StateTransitioner<S> transitioner;
	private EvaluationFunction<S> f;
	
	public MinMaxTree(int globalDepth)
	{
		this.globalDepth = globalDepth;
	}
	
	public S nextState(S state, int player)
	{
		return nextState(state, player, this.globalDepth);
	}
	
	public S nextState(S state, int player, int depth)
	{
		root = new Node<>(null, state);
		// Build the tree and make mark the value of the nodes
		double value = minmax(root, 4, player);
		
		// We know now the best value so we go in the root and pick the child with that value
		// 
		Node bestNode = null; // FIXME : implementation here
		
		return (S) bestNode.getState();
	}
	
	private void buildTreeBranch(Node<S> root)
	{
		List<S> states = transitioner.generateAllNextLegalStates(root.getState());
		for (S state : states)
			root.addChildNode(new Node<>(root, state));
	}
	
	private double minmax(Node<S> node,int depth, int player)
	{
		double value, bestValue;
		if(depth == 0 || node.isLeaf())
			return f.evaluate(node.getState());
		if(player == MAX_PLAYER)
		{
			/* Build the tree here */
			buildTreeBranch(node);
			bestValue = Integer.MIN_VALUE;
			for (Node<S> child : node.getChilds())
			{
				value = minmax(child, depth - 1, MIN_PLAYER);
				child.setMaxValue(value);
				bestValue = value > bestValue ? value : bestValue;
			}
			return bestValue;
		}
		else /* minimizing player */
		{
			/* Build the tree here */
			buildTreeBranch(node);
			bestValue = Integer.MAX_VALUE;
			for (Node<S> child : node.getChilds())
			{
				value = minmax(child, depth - 1, MAX_PLAYER);
				child.setMinValue(value);
				bestValue = value < bestValue ? value : bestValue;
			}
			return bestValue;
		}
	}
	
	private double alphabeta(Node<S> node,int depth, double alpha, double betha, int player)
	{
		double value;
		if(depth == 0 || node.isLeaf())
			return f.evaluate(node.getState());
		if(player == MAX_PLAYER)
		{
			value = Double.NEGATIVE_INFINITY;
			for (Node<S> child : node.getChilds())
			{
				value = alphabeta(child, depth - 1, alpha, betha, MIN_PLAYER);
				alpha = alpha > value ? alpha : value;
				if(betha <= alpha)
					break; /* betha cut-off */
			}
			return value;
		}
		else /* minimizing player */
		{
			value = Double.POSITIVE_INFINITY;
			for (Node<S> child : node.getChilds())
			{
				value = alphabeta(child, depth - 1, alpha, betha, MIN_PLAYER);
				betha = betha < value ? betha : value;
				if(betha <= alpha)
					break; /* alpha cut-off */
			}
			return value;
		}
	}
	
}
