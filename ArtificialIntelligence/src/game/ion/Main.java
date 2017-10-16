import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * 
 * Build a Graph
 * a node represent two states : the one on the left bank and the one of the right bank
 * we need an enum list
 * 
 * 
 * 
 */
public class Main {
	
	public static void main(String[] args) {
		
	    TreeSet<RiverRole> westShore = new TreeSet<>();
	    TreeSet<RiverRole> eastShore = new TreeSet<>();
	    eastShore.add(RiverRole.FARMER);
	    eastShore.add(RiverRole.WOLF);
	    eastShore.add(RiverRole.GOAT);
	    eastShore.add(RiverRole.CABBAGE);
	    
		RiverState initialState 	  = new RiverState(eastShore, westShore, Location.EAST_SHORE);
		RiverState finalState   = new RiverState(westShore, eastShore, Location.WEST_SHORE);
		

		/*
		 *  **************
		 *  BUILDING GRAPH : BFS
		 *  **************
		 */		
		Graph<RiverState> graph 	   = new Graph<>();
		Set<RiverState>   visitedNodes = new HashSet<>();
		Deque<RiverState> queue 	   = new ArrayDeque<>();
		RiverState        currentNode;
		graph.addNode(initialState);
		queue.addLast(initialState);
		
		while (!queue.isEmpty()) {
			currentNode = queue.removeFirst();
			if(!visitedNodes.contains(currentNode)) {
				visitedNodes.add(currentNode);
			
				Set<RiverState> adjacentNodes = generateAdjacentNodes(currentNode, visitedNodes);
				
				graph.addNodes(adjacentNodes);
				
				graph.addNodeEdges(currentNode, adjacentNodes);
				
				for (RiverState adjacentNode : adjacentNodes) {
					queue.addLast(adjacentNode);
				}
			}
		}
		
		/*
		 *  ****************
		 *  TRAVERSING GRAPH : DFS
		 *  ****************
		 */
		Deque<RiverState>        stack      = new ArrayDeque<>();
		Deque<RiverState>        solution   = new ArrayDeque<>();
		Map<RiverState, Boolean> discovered = new HashMap<>();
		
		stack.push(initialState);
		while(!stack.isEmpty()) {
			currentNode = stack.pop();
			if(!discovered.containsKey(currentNode)) {
				discovered.put(currentNode, true);
				
				if(isValidState(currentNode)) {
					
					for (RiverState node : graph.getAdjacentNodes(currentNode)) {
						stack.push(node);
						node.equals(initialState);
					}
					
					if(currentNode.equals(finalState) && graph.hasEdge(currentNode, solution.peekLast())) { solution.addLast(currentNode); break; }
					solution.addLast(currentNode);
					
				}
				
			}
		}
		while (!solution.isEmpty()) {
			System.out.println(solution.pollFirst());
		}
		
	}

	private static Set<RiverState> generateAdjacentNodes(RiverState currentNode, Set<RiverState> visitedNodes) {
		
		Set<RiverState> adjacentNodes = new HashSet<>();

	    SortedSet<RiverRole> westShore = currentNode.getWestShore();
	    SortedSet<RiverRole> eastShore = currentNode.getEastShore();
	    
	    SortedSet<RiverRole> newEastShore;
	    SortedSet<RiverRole> newWestShore;
	    
	    RiverState tranzitNode;
	    
		if(currentNode.getBoatLocation() == Location.EAST_SHORE) {
			
			if(eastShore.contains(RiverRole.FARMER)) {
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.remove(RiverRole.FARMER);
				
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.add(RiverRole.FARMER);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.WEST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(eastShore.contains(RiverRole.WOLF)) {
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.remove(RiverRole.FARMER);
				newEastShore.remove(RiverRole.WOLF);
				
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.add(RiverRole.FARMER);
				newWestShore.add(RiverRole.WOLF);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.WEST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(eastShore.contains(RiverRole.GOAT)) {
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.remove(RiverRole.FARMER);
				newEastShore.remove(RiverRole.GOAT);
				
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.add(RiverRole.FARMER);
				newWestShore.add(RiverRole.GOAT);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.WEST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(eastShore.contains(RiverRole.CABBAGE)) {
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.remove(RiverRole.FARMER);
				newEastShore.remove(RiverRole.CABBAGE);
				
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.add(RiverRole.FARMER);
				newWestShore.add(RiverRole.CABBAGE);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.WEST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
		
		} else {

			if(westShore.contains(RiverRole.FARMER)) {
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.remove(RiverRole.FARMER);
				
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.add(RiverRole.FARMER);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.EAST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(westShore.contains(RiverRole.WOLF)) {
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.remove(RiverRole.FARMER);
				newWestShore.remove(RiverRole.WOLF);
				
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.add(RiverRole.FARMER);
				newEastShore.add(RiverRole.WOLF);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.EAST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(westShore.contains(RiverRole.GOAT)) {
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.remove(RiverRole.FARMER);
				newWestShore.remove(RiverRole.GOAT);
				
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.add(RiverRole.FARMER);
				newEastShore.add(RiverRole.GOAT);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.EAST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			if(westShore.contains(RiverRole.CABBAGE)) {
				newWestShore = new TreeSet<>();
				newWestShore.addAll(westShore);
				newWestShore.remove(RiverRole.FARMER);
				newWestShore.remove(RiverRole.CABBAGE);
				
				newEastShore = new TreeSet<>();
				newEastShore.addAll(eastShore);
				newEastShore.add(RiverRole.FARMER);
				newEastShore.add(RiverRole.CABBAGE);
				
				tranzitNode = new RiverState(newEastShore, newWestShore, Location.EAST_SHORE);
				if(!nodeExists(tranzitNode, visitedNodes)) adjacentNodes.add(tranzitNode);
			}
			
		}
		
		return adjacentNodes;
	}

	private static boolean nodeExists(RiverState node, Set<RiverState> Nodes) {
		for (RiverState n : Nodes) {
			if(n.equals(node)) return true;
		}
		return false;
	}
	
	private static boolean isValidState(RiverState state) {
		
		if(state.getEastShore().contains(RiverRole.WOLF) && 
		   state.getEastShore().contains(RiverRole.GOAT) && !state.getEastShore().contains(RiverRole.FARMER)) {
			return false;
		}
		if(state.getEastShore().contains(RiverRole.CABBAGE) && 
		   state.getEastShore().contains(RiverRole.GOAT)    && !state.getEastShore().contains(RiverRole.FARMER)) {
			return false;
		}
		if(state.getWestShore().contains(RiverRole.WOLF) && 
		   state.getWestShore().contains(RiverRole.GOAT) && !state.getWestShore().contains(RiverRole.FARMER)) {
			return false;
		}
		if(state.getWestShore().contains(RiverRole.CABBAGE) && 
		   state.getWestShore().contains(RiverRole.GOAT)    && !state.getWestShore().contains(RiverRole.FARMER)) {
			return false;
		}
		return true;
	}
}


















