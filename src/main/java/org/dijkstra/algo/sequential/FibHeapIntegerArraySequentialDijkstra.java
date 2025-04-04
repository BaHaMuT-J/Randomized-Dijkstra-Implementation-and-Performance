package org.dijkstra.algo.sequential;

import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.FibHeapInteger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FibHeapIntegerArraySequentialDijkstra {

	private static Map<Integer, Integer> d;
	
	public static void createPreviousArray(int[][] neighbours, int[][] weights, int source, int[] previous, FibonacciIntegerObject[] fibonacciIntegerObjectArray, FibHeapInteger<FibonacciIntegerObject> fibHeapInteger) {
		
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
			if (neighbours[u] == null) {
				continue;
			}
						
			for (int i = 0; i < neighbours[u].length; ++i) {
				int alt = fibonacciIntegerObjectArray[u].priority + weights[u][neighbours[u][i]];
				if (alt < fibonacciIntegerObjectArray[neighbours[u][i]].priority) {
					fibHeapInteger.decreasePriority(fibonacciIntegerObjectArray[neighbours[u][i]], alt);
					d.put(neighbours[u][i], alt);
					previous[neighbours[u][i]] = u;
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
}
