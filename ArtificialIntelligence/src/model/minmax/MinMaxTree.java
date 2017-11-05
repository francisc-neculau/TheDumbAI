package model.minmax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.EvaluationFunction;
import model.state.FinalStateChecker;
import model.state.State;
import model.state.StateChooser;
import model.state.StateTransitioner;

public class MinMaxTree<S extends State>
{
	public static final int MIN_PLAYER = 1;
	public static final int MAX_PLAYER = 0;
	
	private int globalDepth;
	private Node<S> root;
	
	private StateTransitioner<S> transitioner;
	private StateChooser<S> chooser;
	private FinalStateChecker<S> checker;
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
		// Build the tree
		Node<S> currentNode = root;
		int currentDepth = 0;
		Map<Integer, List<S>> depthToNodes = new HashMap<>();
		while(depth > currentDepth)
		{
			List<S> childs = transitioner.generateAllNextLegalStates(currentNode.state);
			buildTree(currentNode, childs);
			currentDepth++;
		}
		// MinMax the tree
		
		return null;
	}
	
	private double minmax(Node<S> node,int depth, int player)
	{
		double value, bestValue;
		if(depth == 0 || node.isLeaf())
			return f.evaluate(node.getState());
		if(player == MAX_PLAYER)
		{
			bestValue = Integer.MIN_VALUE;
			for (Node<S> child : node.getChilds())
			{
				value = minmax(child, depth - 1, MIN_PLAYER);
				bestValue = value > bestValue ? value : bestValue;
				// FIXME : Save the value in the nodes !
			}
			return bestValue;
		}
		else /* minimizing player */
		{
			bestValue = Integer.MAX_VALUE;
			for (Node<S> child : node.getChilds())
			{
				value = minmax(child, depth - 1, MAX_PLAYER);
				bestValue = value < bestValue ? value : bestValue;
				// FIXME : Save the value in the nodes !
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
	
	private List<Node<S>> buildTree(Node<S> root, List<S> states)
	{
		List<Node<S>> nodes = new ArrayList<>();
		Node<S> childNode;
		for (S state : states)
		{
			childNode = new Node<>(root, state);
			nodes.add(childNode);
			root.addChildNode(childNode);
		}
		return nodes;
	}
	
}
