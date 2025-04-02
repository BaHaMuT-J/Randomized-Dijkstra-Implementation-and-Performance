package org.dijkstra.algo.fibonacci.sequential;

import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.FibHeapInteger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class FibHeapIntegerSetSequentialDijkstra {

	private static Map<Integer, Integer> d;
	
	public static void createPreviousArray(Map<Integer, Set<Integer>> neighbours, Map<Integer, Map<Integer, Integer>> weights, int source, int[] previous, FibonacciIntegerObject[] fibonacciIntegerObjectArray, FibHeapInteger<FibonacciIntegerObject> fibHeapInteger) {
		
		for (int i = 0; i < fibonacciIntegerObjectArray.length; ++i) {
			fibonacciIntegerObjectArray[i].priority = Integer.MAX_VALUE;
			previous[i] = -1;
		}
		
		fibonacciIntegerObjectArray[source].priority = 0;

		d = new HashMap<>();
		d.put(source, 0);
		
		fibHeapInteger.clear();
        for (FibonacciIntegerObject fibonacciIntegerObject : fibonacciIntegerObjectArray) {
            fibHeapInteger.add(fibonacciIntegerObject);
        }
				
		while (fibHeapInteger.size() != 0) {
			
			// extract min
			FibonacciIntegerObject min = fibHeapInteger.extractMin();
			int u = min.node;
			
			// find the neighbours
			Set<Integer> neighboursU = neighbours.get(u);
			if (neighboursU.isEmpty()) {
				continue;
			}
						
			for (Integer neighbour : neighboursU) {
				Map<Integer, Integer> weightsU = weights.get(u);
				int alt = fibonacciIntegerObjectArray[u].priority + weightsU.get(neighbour);
				if (alt < fibonacciIntegerObjectArray[neighbour].priority) {
					fibHeapInteger.decreasePriority(fibonacciIntegerObjectArray[neighbour], alt);
					d.put(neighbour, alt);
					previous[neighbour] = u;
				}
			}
		}
	}
	
	public static int[] shortestPath(int[] previous, int destination) {
		if (previous[destination] == -1) {
			return new int[]{-1};
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

	public static int pathCalculate(int[] previous, int destination, Map<Integer, Map<Integer, Integer>> weights) {
		int totalWeight = 0;
		int dest = destination;
		int prev = previous[destination];
		while (prev != -1) {
			totalWeight += weights.get(prev).get(dest);
			dest = prev;
			prev = previous[prev];
		}
		return totalWeight;
	}
}
