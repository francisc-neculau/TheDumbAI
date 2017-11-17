package machinelearning.algorithm.cobweb;

import java.util.List;

import machinelearning.Instance;

/*
 * model : classification tree
 * Clustering Algorithm
 * 
 */
public class Cobweb
{
	static final double norm = 1/Math.sqrt(2*Math.PI);
	
	private CwNode root;
	
	public Cobweb()
	{
		
	}
	
	public void buildTree(List<Instance> instances)
	{
		int counter = 1;
		while(instances.size() != counter)
		{
			addInstance(instances.get(counter));
			counter++;
		}
	}
	
	public CwNode addInstance(Instance instance)
	{
		return cobweb(root, instance);
	}
	
	private CwNode cobweb(CwNode node, Instance instance)
	{
		if(!node.hasChildren())
		{
			node.selfSpan();        
			create(node, instance); // adds child with instance’s feature values
			insert(node, instance); // update root’s statistics
		}
		else
		{
			/*
			 * p is the node with the highest CU ( a )
			 * obtained by placing the instance in it.
			 */
			CwNode p = null;
			double a = 0;
			
			/*
			 * q is the node with the second highest CU( b )
			 * obtained by placing the instance in it.
			 */
			CwNode q = null;
			double b = 0;
			
			/*
			 * r is a new node in witch we place the instance.
			 * c is the CU for r
			 */
			CwNode r = null;
			double c = 0;
			
			/*
			 * d is the CU for merging p and q into one node
			 */
			double d = 0;
			
			/*
			 * e is the score for splitting P into its children
			 */
			double e = 0;

			double cu = 0.0;

			insert(node, instance);
			for (CwNode child : node.getChildren()) // compute the score for placing instance in child
			{
				cu = insertionCategoryUtility(child, instance);
				if(cu >= a)
				{
					b = a;
					a = cu;
					q = p;
					p = child;
				}
				else if(cu >= b)
				{
					b = cu;
					q = child;
				}
			}
			
			
			if(a == cu)
			{
				cobweb(p, instance);
			}
			else if (c == cu)           // add the instance in a new node by itself
			{                           // and attach that node to the root
				create(node, instance);
			}
			else if(d == cu)
			{
				CwNode o = merge(node, p, r);
				cobweb(o, instance);
			}
			else if(e == cu)
			{
				split(node, p);
				cobweb(node, instance);
			}
		}
		return null;
	}

	
	private void insert(CwNode node, Instance instance)
	{
		node.addInstance(instance);
	}

	private void create(CwNode node, Instance instance)
	{
		CwNode child = new CwNode(instance);
		node.addChild(child);
	}
	
	/**
	 * Transforms firstChild and secondChild from children of
	 * parent into nephews of parent.
	 *  
	 * @param parent
	 * @param firstChild
	 * @param secondChild
	 */
	private CwNode merge(CwNode parent, CwNode firstChild, CwNode secondChild)
	{
		// FIXME : There is place for improvements
		CwNode node = new CwNode();
		node.addChild(firstChild);
		node.addChild(secondChild);
		parent.removeChild(firstChild);
		parent.removeChild(secondChild);
		parent.addChild(node);
		return node;
	}
	
	/**
	 * This method moves all children of child to it's
	 * parent and removes itself from the parent
	 * 
	 * @param parent
	 * @param child
	 */
	private void split(CwNode parent, CwNode child)
	{
		if(!parent.isMyChild(child))
			return; // An exception or something
		parent.removeChild(child);
		parent.addChildren(child.getChildren());;
	}
	
	private double categoryUtility(CwNode node)
	{
		return 0.0;
	}
	
	private double insertionCategoryUtility(CwNode node, Instance instance)
	{
		return 0.0;
	}

	/*
	 COBWEB(root, record):
	  Input: A COBWEB node root, an instance to insert record
	  if root has no children then
	    children := {copy(root)}
	    newcategory(record) \\ adds child with record’s feature values.
	    insert(record, root) \\ update root’s statistics
	  else
	    insert(record, root)
	    for child in root’s children do
	      calculate Category Utility for insert(record, child),
	      set best1, best2 children w. best CU.
	    end for
	    if newcategory(record) yields best CU then
	      newcategory(record)
	    else if merge(best1, best2) yields best CU then
	      merge(best1, best2)
	      COBWEB(root, record)
	    else if split(best1) yields best CU then
	      split(best1)
	      COBWEB(root, record)
	    else
	      COBWEB(best1, record)
	    end if
	  end
	 */

}
