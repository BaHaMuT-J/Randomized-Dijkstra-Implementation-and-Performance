package org.dijkstra.algo.sequential;

import org.dijkstra.fib.wrapper.PriorityObject;
import org.dijkstra.fib.wrapper.PriorityQueue;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class SetPriorityQueueDijkstra {
	
	public static void createPreviousArray(Map<Integer, Set<Integer>> neighbours, Map<Integer, Map<Integer, Integer>> weights, int source, int[] previous, PriorityObject[] priorityObjectArray, PriorityQueue<PriorityObject> priorityQueue) {
		
		for (int i = 0; i < priorityObjectArray.length; ++i) {
			priorityObjectArray[i].priority = Integer.MAX_VALUE;
			previous[i] = -1;
		}
		
		priorityObjectArray[source].priority = 0;
		
		priorityQueue.clear();
        for (PriorityObject priorityObject : priorityObjectArray) {
            priorityQueue.add(priorityObject);
        }
				
		while (priorityQueue.size() != 0) {
			
			// extract min
			PriorityObject min = priorityQueue.extractMin();
			int u = min.node;
			
			// find the neighbours
			Set<Integer> neighboursU = neighbours.get(u);
			if (neighboursU.isEmpty()) {
				continue;
			}
						
			for (Integer neighbour : neighboursU) {
				Map<Integer, Integer> weightsU = weights.get(u);
				int alt = priorityObjectArray[u].priority + weightsU.get(neighbour);
				if (alt < priorityObjectArray[neighbour].priority) {
					priorityQueue.decreasePriority(priorityObjectArray[neighbour], alt);
					previous[neighbour] = u;
				}
			}
		}
	}
	
	public static int[] shortestPath(int[] previous, int destination) {
		if (previous[destination] == -1) {
			return null;
		}
		
		LinkedList<Integer> reversedRoute = new LinkedList<>();
		int u = destination;
		
		while (u != -1) {
			reversedRoute.add(u);
			u = previous[u];
		}
		
		int[] path = new int[reversedRoute.size()];
		for (int i = 0; i < path.length; ++i) {
			path[i] = reversedRoute.get(path.length - 1 - i);
		}
		
		return path;
	}		
}
