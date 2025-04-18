package org.dijkstra.algo.sequential;

import org.dijkstra.fib.wrapper.FibHeapCycleNode;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.dijkstra.node.CycleNode;

import java.util.*;

public class FibHeapCycleNodeSequentialDijkstra {

	private static Map<CycleNode, Integer> d;
	
	public static void createPreviousArray(Set<CycleNode> nodes,
										   Map<CycleNode, Set<CycleNode>> neighbours,
										   Map<CycleNode, Map<CycleNode, Integer>> weights,
										   CycleNode source,
										   Map<CycleNode, CycleNode> previous,
										   Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap,
										   FibHeapCycleNode<FibonacciCycleNodeObject> fibonacciHeap
	) {
		fibonacciHeap.clear();
		for (CycleNode node : nodes) {
			FibonacciCycleNodeObject object = fibonacciObjectMap.get(node);
			object.distance = Objects.equals(node, source) ? 0 : Integer.MAX_VALUE;
			fibonacciObjectMap.put(node, object);

			fibonacciHeap.add(object);
		}
		previous.put(source, new CycleNode(-1, -1));

		d = new HashMap<>();
		d.put(source, 0);
				
		while (fibonacciHeap.size() != 0) {

			FibonacciCycleNodeObject min = fibonacciHeap.extractMin();
			CycleNode u = min.node;
						
			for (CycleNode neighbour : neighbours.get(u)) {
				Map<CycleNode, Integer> weightsU = weights.get(u);
				int alt = fibonacciObjectMap.get(u).distance + weightsU.get(neighbour);
				if (alt >= 0 && alt < fibonacciObjectMap.get(neighbour).distance) {
					fibonacciHeap.decreaseDistance(fibonacciObjectMap.get(neighbour), alt);
					d.put(neighbour, alt);
					previous.put(neighbour, u);
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

	public static int pathCalculate(Map<CycleNode, CycleNode> previous, CycleNode destination, Map<CycleNode, Map<CycleNode, Integer>> weights) {
		int totalWeight = 0;
		CycleNode dest = destination;
		CycleNode prev = previous.get(destination);
		while (!Objects.equals(prev, new CycleNode(-1, -1))) {
			totalWeight += weights.get(prev).get(dest);
			dest = prev;
			prev = previous.get(prev);
		}
		return totalWeight;
	}
}
