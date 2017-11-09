package model.minmax;

import java.util.ArrayList;
import java.util.List;

import model.state.State;

public class Node<S extends State>
{
	public S state;
	public Node<S> parent;
	public List<Node<S>> childs;
	
	public int min;
	public int max;
	
	public Node(Node<S> parent, S state)
	{
		this.state  = state;
		this.parent = parent;
		this.childs = new ArrayList<>();
	}

	public void addChildNode(Node<S> childNode)
	{
		this.childs.add(childNode);
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.state);
		return sb.toString();
	}

	public S getState()
	{
		return this.state;
	}

	public boolean isLeaf()
	{
		return childs.isEmpty();
	}

	public List<Node<S>> getChilds()
	{
		return this.childs;
	}

	public void setMaxValue(double value)
	{
		// TODO Auto-generated method stub
		
	}

	public void setMinValue(double value)
	{
		// TODO Auto-generated method stub
		
	}
	
}
