package org.dijkstra.fib;

import org.dijkstra.fib.wrapper.FibHeap;
import org.dijkstra.fib.wrapper.FibonacciObject;
import org.dijkstra.node.CycleNode;

import java.util.*;

public class RandomizedConstantFibonacciHeapDijkstra {
	
	public static void createPreviousArray(Set<CycleNode> nodes,
										   Map<CycleNode, Set<CycleNode>> neighbours,
										   Map<CycleNode, Map<CycleNode, Integer>> weights,
										   CycleNode source,
										   Map<CycleNode, CycleNode> previous,
										   Map<CycleNode, FibonacciObject> fibonacciObjectMap,
										   FibHeap<FibonacciObject> fibonacciHeap,
										   Random random
	) {
		Map.Entry<Set<CycleNode>, Set<CycleNode>> entryR = getSetR(nodes, source, random);
		Set<CycleNode> R = entryR.getKey();
		Set<CycleNode> notR = entryR.getValue();
	}

	private static double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	private static Map.Entry<Set<CycleNode>, Set<CycleNode>> getSetR(Set<CycleNode> nodes, CycleNode source, Random random) {
		Set<CycleNode> R = new HashSet<>();
		Set<CycleNode> notR = new HashSet<>();
		int n = nodes.size();
		double k = Math.sqrt(log2(n) / log2(log2(n))); // Math.sqrt(Math.log(n) / Math.log(Math.log(n)));

		for (CycleNode node : nodes) {
			if (Objects.equals(node, source) || random.nextDouble() <= 1.0/k) {
				R.add(node);
			} else {
				notR.add(node);
			}
		}

		return Map.entry(R, notR);
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
