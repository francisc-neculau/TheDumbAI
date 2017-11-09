package model.minmax;

import java.util.ArrayList;
import java.util.List;

import model.state.State;

public class Node<S extends State>
{
	private S state;
	private Node<S> parent;
	private List<Node<S>> childs;
	
	private double minValue;
	private double maxValue;
	
	public Node(Node<S> parent, S state)
	{
		this.state  = state;
		this.parent = parent;
		this.childs = new ArrayList<>();
	}
	public boolean isLeaf()
	{
		return childs.isEmpty();
	}

	public void addChildNode(Node<S> childNode)
	{
		this.childs.add(childNode);
	}

	public S getState()
	{
		return state;
	}
	public void setState(S state)
	{
		this.state = state;
	}
	public Node<S> getParent()
	{
		return parent;
	}
	public void setParent(Node<S> parent)
	{
		this.parent = parent;
	}
	public List<Node<S>> getChilds()
	{
		return childs;
	}
	public void setChilds(List<Node<S>> childs)
	{
		this.childs = childs;
	}
	public double getMinValue()
	{
		return minValue;
	}
	public void setMinValue(double minValue)
	{
		this.minValue = minValue;
	}
	public double getMaxValue()
	{
		return maxValue;
	}
	public void setMaxValue(double maxValue)
	{
		this.maxValue = maxValue;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.state);
		return sb.toString();
	}
	
}
