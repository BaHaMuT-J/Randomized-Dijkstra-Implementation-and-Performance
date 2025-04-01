package org.dijkstra.algo.randomized;

import org.dijkstra.fib.wrapper.FibHeap;
import org.dijkstra.fib.wrapper.FibonacciObject;
import org.dijkstra.fib.wrapper.heap.Neo4jFibonacciObject;
import org.dijkstra.node.CycleNode;

import java.util.*;

public class RandomizedConstantFibonacciHeapDijkstra {

	private static Map<CycleNode, CycleNode> b;
	private static Map<CycleNode, Set<CycleNode>> Bundle;
	private static Map<CycleNode, Set<CycleNode>> Ball;
	private static Map<CycleNode, Integer> d;
	private static Map<CycleNode, Map<CycleNode, Integer>> dist;
	
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

		b = new HashMap<>();
		Bundle = new HashMap<>();
		Ball = new HashMap<>();
		d = new HashMap<>();
		dist = new HashMap<>();

		formBundleAndBall(nodes,
				neighbours,
				weights,
				fibonacciObjectMap,
				fibonacciHeap,
				R,
				notR);

		for (CycleNode node : nodes) {
			d.put(node, Integer.MAX_VALUE);
		}
		d.put(source, 0);
		previous.put(source, new CycleNode(-1, -1));

		fibonacciHeap.clear();
		for (CycleNode node : R) {
			FibonacciObject object = fibonacciObjectMap.get(node);
			object.distance = node == source ? 0 : Integer.MAX_VALUE;
			fibonacciObjectMap.put(node, object);

			// Add all FibonacciObject in FibonacciHeap
			fibonacciHeap.add(object);
		}

		Set<CycleNode> extractedNodes = new HashSet<>();
		while (fibonacciHeap.size() != 0) {

			// extract min
			FibonacciObject min = fibonacciHeap.extractMin();
			CycleNode u = min.node;
			extractedNodes.add(u);

			for (CycleNode v : Bundle.get(u)) {
				Map<CycleNode, Integer> dist_v = dist.get(v);
				relax(u, v, min.distance + dist_v.get(u), R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
				Set<CycleNode> ball_v = Ball.getOrDefault(v, new HashSet<>());
				for (CycleNode y : ball_v) {
					if (y == v) continue;
					int newDistance = d.get(y) + dist_v.get(y);
					relax(y, v, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
				}
				for (CycleNode z2 : ball_v) {
					for (CycleNode z1 : neighbours.get(z2)) {
						int w_z1_z2 = weights.get(z1).get(z2);
						int newDistance = d.get(z1) + w_z1_z2 + dist_v.get(z2);
						relax(z2, v, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
					}
				}
			}

			for (CycleNode x : Bundle.get(u)) {
				for (CycleNode y : neighbours.get(x)) {
					int w_x_y = weights.get(x).get(y);
					int newDistance = d.get(x) + w_x_y;
					relax(x, y, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
					for (CycleNode z1 : Ball.get(y)) {
						newDistance = d.get(x) + w_x_y + dist.get(y).get(z1);
						relax(y, z1, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
					}
				}
			}
		}
	}

	private static void relax(CycleNode u,
							  CycleNode v,
							  Integer alt,
							  Set<CycleNode> R,
							  Set<CycleNode> extractedNodes,
							  Map<CycleNode, FibonacciObject> fibonacciObjectMap,
							  FibHeap<FibonacciObject> fibonacciHeap,
							  Map<CycleNode, CycleNode> previous) {
		if (alt >= 0 && alt < d.get(v)) {
			d.put(v, alt);
			previous.put(v, u);
			if (!R.contains(v)) {
				CycleNode bundle = b.get(v);
				int newDistance = d.get(v) + dist.get(v).get(bundle);
				relax(v, bundle, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
			} else if (!extractedNodes.contains(v)) {
				fibonacciHeap.decreaseDistance(fibonacciObjectMap.get(v), alt);
			}
		}
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

	private static Map.Entry<Map.Entry<Set<CycleNode>, CycleNode>, Map<CycleNode, Integer>> DijkstraStop(Set<CycleNode> nodes,
																										 Map<CycleNode, Set<CycleNode>> neighbours,
																										 Map<CycleNode, Map<CycleNode, Integer>> weights,
																										 CycleNode source,
																										 FibHeap<FibonacciObject> fibonacciHeap,
																										 Set<CycleNode> R
	) {
		fibonacciHeap.clear();

		// Insert in fibonacciHeap when relax
		Map<CycleNode, FibonacciObject> fibonacciObjectMap = new HashMap<>();
		FibonacciObject sourceObject = new Neo4jFibonacciObject(source, 0);
		fibonacciObjectMap.put(source, sourceObject);
		fibonacciHeap.add(sourceObject);

		Map<CycleNode, Integer> shortestDist = new HashMap<>();
		CycleNode bundle = null;

		while (fibonacciHeap.size() != 0) {

			// extract min
			FibonacciObject min = fibonacciHeap.extractMin();
			CycleNode u = min.node;
			shortestDist.put(u, min.distance);

			for (CycleNode neighbour : neighbours.get(u)) {
				Map<CycleNode, Integer> weightsU = weights.get(u);
				int alt = fibonacciObjectMap.get(u).distance + weightsU.get(neighbour);
				if (!fibonacciObjectMap.containsKey(neighbour)) {
					FibonacciObject object = new Neo4jFibonacciObject(neighbour, alt);
					fibonacciObjectMap.put(neighbour, object);
					fibonacciHeap.add(object);
				} else if (alt < fibonacciObjectMap.get(neighbour).distance) {
					fibonacciHeap.decreaseDistance(fibonacciObjectMap.get(neighbour), alt);
				}
			}

			// If extracted node is in R, stop
			if (R.contains(u)) {
				bundle = u;
				break;
			}
		}

		return Map.entry(Map.entry(shortestDist.keySet(), bundle), shortestDist);
	}

	public static void formBundleAndBall(Set<CycleNode> nodes,
										 Map<CycleNode, Set<CycleNode>> neighbours,
										 Map<CycleNode, Map<CycleNode, Integer>> weights,
										 Map<CycleNode, FibonacciObject> fibonacciObjectMap,
										 FibHeap<FibonacciObject> fibonacciHeap,
										 Set<CycleNode> R,
										 Set<CycleNode> notR) {
		for (CycleNode u : R) {
			// for every vertex u in R, b(u) = u
			b.put(u, u);

			// add u in Bundle(u)
			Set<CycleNode> bundle = new HashSet<>();
			bundle.add(u);
			Bundle.put(u, bundle);

			// no Ball for nodes in R
			Ball.put(u, new HashSet<>());

			// distance for vertices in R need only itself
			Map<CycleNode, Integer> dist_u = new HashMap<>();
			dist_u.put(u, 0);
			dist.put(u, dist_u);
		}

		for (CycleNode v : notR) {
			// for every vertex v not in R, run Dijkstra using v as a source
			Map.Entry<Map.Entry<Set<CycleNode>, CycleNode>, Map<CycleNode, Integer>> bigEntry = DijkstraStop(nodes,
					neighbours,
					weights,
					v,
					fibonacciHeap,
					R);
			Map.Entry<Set<CycleNode>, CycleNode> entry = bigEntry.getKey();

			// vertex u is closet node in R from v
			CycleNode u = entry.getValue();
			b.put(v, u);

			// v is bundled to u
			Set<CycleNode> bundle = Bundle.get(u);
			bundle.add(v);
			Bundle.put(u, bundle);

			// for all vertices v meet before u, they are include in Ball(v)
			Set<CycleNode> verticesReachedBeforeU = entry.getKey();
			Ball.put(v, verticesReachedBeforeU);

			// distance from vertex v to each vertex in Ball(v)
			dist.put(v, bigEntry.getValue());
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
