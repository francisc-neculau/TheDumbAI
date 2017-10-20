package game.ion;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph<T> implements Iterable<T> {

    private Map<T, Set<T>> dataModel = new HashMap<T, Set<T>>();

    public boolean addNode(T node) {
    	if(!dataModel.containsKey(node)) {
    		dataModel.put(node, new HashSet<T>());
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void addNodes(Set<T> nodes) {
    	for (T node : nodes) {
			this.addNode(node);
		}
    }
    
    public void addNodeEdges(T node, Set<T> adjacentNodes) {
    	for (T adjacentNode : adjacentNodes) {
    		this.addEdge(node, adjacentNode);
    	}
    }
    
    public boolean addEdge(T firstNode, T secondNode) {
    	if(!dataModel.get(firstNode).contains(secondNode)) {
    		dataModel.get(firstNode).add(secondNode);
        	dataModel.get(secondNode).add(firstNode);
        	return true;
    	} else {
    		return false;
    	}
    }
    
	public boolean hasEdge(T firstNode, T secondNode) {
		//if(firstNode == secondNode) return true;
		return (dataModel.containsKey(firstNode) ? dataModel.get(firstNode).contains(secondNode) : false);
	}
    
    public boolean hasNode(T node) {
    	return dataModel.containsKey(node);
    }
    
    public Set<T> getAdjacentNodes(T node) {
    	return dataModel.get(node);
    	// no element found exception
    }
    
	@Override
	public Iterator<T> iterator() {
		return dataModel.keySet().iterator();
	}

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	Set<T> nodeSet = dataModel.keySet();
    	for (T node : nodeSet) {
			sb.append(node);
			sb.append("\n");
			sb.append(dataModel.get(node));
			sb.append("\n");
			sb.append("\n");
			
		}
    	return sb.toString();
    }

}