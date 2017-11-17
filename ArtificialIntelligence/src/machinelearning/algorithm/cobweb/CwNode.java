package machinelearning.algorithm.cobweb;

import java.util.List;
import java.util.Map;

import machinelearning.Instance;

public class CwNode
{
	private int numberOfInstances;
	private Map<String, Map<String, Integer>> attributeToValueToOccurence;
	private List<Instance> instances;
	private List<CwNode> children;
	private CwNode parent;
	
	// memorize uncommitted instances
	public CwNode(Instance instance)
	{
		// FIXME : Update Statistics
		// TODO Auto-generated constructor stub
	}

	public CwNode()
	{
	}


	public boolean isMyChild(CwNode child)
	{
		return false;
	}

	public void addChild(CwNode child)
	{
		// if is just first child, copy the child's statistics do not make any new computations
		updateStatistics();
	}

	public void removeChild(CwNode child)
	{
		
	}
	
	public boolean hasChildren()
	{
		return this.children.isEmpty();
	}

	public List<CwNode> getChildren()
	{
		return this.children;
	}

	public void addChildren(List<CwNode> children)
	{
		this.children.addAll(children);
	}

	public CwNode copySelf()
	{
		return null;
	}


	private void updateStatistics()
	{
	}


	public CwNode selfSpan()
	{
		return null;
	}


	public void addInstance(Instance instance)
	{
		updateStatistics();
		this.instances.add(instance);
	}

}
