package org.dijkstra.algo.sequential;

import org.dijkstra.fib.wrapper.PriorityObject;
import org.dijkstra.fib.wrapper.PriorityQueue;

import java.util.LinkedList;

public class PriorityQueueDijkstra {
	
	public static void createPreviousArray(int[][] neighbours, int[][] weights, int source, int[] previous, PriorityObject[] priorityObjectArray, PriorityQueue<PriorityObject> priorityQueue) {
		
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
			if (neighbours[u] == null) {
				continue;
			}
						
			for (int i = 0; i < neighbours[u].length; ++i) {
				int alt = priorityObjectArray[u].priority + weights[u][i];
				if (alt < priorityObjectArray[neighbours[u][i]].priority) {
					priorityQueue.decreasePriority(priorityObjectArray[neighbours[u][i]], alt);
					previous[neighbours[u][i]] = u;
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
