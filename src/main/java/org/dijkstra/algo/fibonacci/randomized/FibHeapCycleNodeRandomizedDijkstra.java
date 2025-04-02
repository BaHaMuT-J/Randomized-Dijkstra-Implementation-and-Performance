package org.dijkstra.algo.fibonacci.randomized;

import org.dijkstra.fib.wrapper.FibHeapCycleNode;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciCycleNodeObject;
import org.dijkstra.node.CycleNode;

import java.util.*;

public class FibHeapCycleNodeRandomizedDijkstra {

	private static Map<CycleNode, CycleNode> b;
	private static Map<CycleNode, CycleNode> firstInBallMap;
	private static Map<CycleNode, Map<CycleNode, CycleNode>> previousBallMap;
	private static Map<CycleNode, Set<CycleNode>> Bundle;
	private static Map<CycleNode, Set<CycleNode>> Ball;
	private static Map<CycleNode, Integer> d;
	private static Map<CycleNode, Map<CycleNode, Integer>> dist;

	public static void createPreviousArray(Set<CycleNode> nodes,
										   Map<CycleNode, Set<CycleNode>> neighbours,
										   Map<CycleNode, Map<CycleNode, Integer>> weights,
										   CycleNode source,
										   Map<CycleNode, CycleNode> previous,
										   Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap,
										   FibHeapCycleNode<FibonacciCycleNodeObject> fibonacciHeap,
										   Random random
	) {
		Map.Entry<Set<CycleNode>, Set<CycleNode>> entryR = getSetR(nodes, source, random);
		Set<CycleNode> R = entryR.getKey();
		Set<CycleNode> notR = entryR.getValue();

		b = new HashMap<>();
		firstInBallMap = new HashMap<>();
		previousBallMap = new HashMap<>();
		Bundle = new HashMap<>();
		Ball = new HashMap<>();
		d = new HashMap<>();
		dist = new HashMap<>();

		formBundleAndBall(
				neighbours,
				weights,
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
			FibonacciCycleNodeObject object = fibonacciObjectMap.get(node);
			object.distance = Objects.equals(node, source) ? 0 : Integer.MAX_VALUE;
			fibonacciObjectMap.put(node, object);

			// Add all FibonacciCycleNodeObject in FibonacciHeap
			fibonacciHeap.add(object);
		}

		Set<CycleNode> extractedNodes = new HashSet<>();
		while (fibonacciHeap.size() != 0) {

			// extract min
			FibonacciCycleNodeObject min = fibonacciHeap.extractMin();
			CycleNode u = min.node;
			extractedNodes.add(u);

			for (CycleNode v : Bundle.get(u)) {
				Map<CycleNode, Integer> dist_v = dist.get(v);
				relax(firstInBallMap.get(v), v, min.distance + dist_v.get(u), R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
				Set<CycleNode> ball_v = Ball.getOrDefault(v, new HashSet<>());
				for (CycleNode y : ball_v) {
					int newDistance = d.get(y) + dist_v.get(y);
					relax(firstInBallMap.get(v), v, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
				}
				for (CycleNode z2 : ball_v) {
					for (CycleNode z1 : neighbours.get(z2)) {
						int w_z1_z2 = weights.get(z1).get(z2);
						int newDistance = d.get(z1) + w_z1_z2 + dist_v.get(z2);
						CycleNode candidate = firstInBallMap.get(v);
						if (Objects.equals(z2, v)) {
							candidate = z2;
						}
						relax(candidate, v, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
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
						relax(previousBallMap.get(y).get(z1), z1, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
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
							  Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap,
							  FibHeapCycleNode<FibonacciCycleNodeObject> fibonacciHeap,
							  Map<CycleNode, CycleNode> previous) {
		if (alt >= 0 && alt < d.get(v)) {
			d.put(v, alt);
			previous.put(v, u);
			if (!R.contains(v)) {
				CycleNode bundle = b.get(v);
				int newDistance = d.get(v) + dist.get(v).get(bundle);
				relax(previousBallMap.get(v).get(bundle), bundle, newDistance, R, extractedNodes, fibonacciObjectMap, fibonacciHeap, previous);
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

	private static void DijkstraStop(Map<CycleNode, Set<CycleNode>> neighbours,
									 Map<CycleNode, Map<CycleNode, Integer>> weights,
									 CycleNode source,
									 FibHeapCycleNode<FibonacciCycleNodeObject> fibonacciHeap,
									 Set<CycleNode> R
	) {
		fibonacciHeap.clear();

		// Insert in fibonacciHeap when relax
		Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap = new HashMap<>();
		FibonacciCycleNodeObject sourceObject = new Neo4JFibonacciCycleNodeObject(source, 0);
		fibonacciObjectMap.put(source, sourceObject);
		fibonacciHeap.add(sourceObject);

		Map<CycleNode, Integer> shortestDist = new HashMap<>();
		CycleNode bundle = null;
		boolean foundFirstInBall = false;

		Map<CycleNode, CycleNode> previousBall = new HashMap<>();

		while (fibonacciHeap.size() != 0) {

			// extract min
			FibonacciCycleNodeObject min = fibonacciHeap.extractMin();
			CycleNode u = min.node;
			shortestDist.put(u, min.distance);

			if (!Objects.equals(u, source) && !foundFirstInBall) {
				foundFirstInBall = true;
				firstInBallMap.put(source, u);
			}

			for (CycleNode neighbour : neighbours.get(u)) {
				Map<CycleNode, Integer> weightsU = weights.get(u);
				int alt = fibonacciObjectMap.get(u).distance + weightsU.get(neighbour);
				if (!fibonacciObjectMap.containsKey(neighbour)) {
					FibonacciCycleNodeObject object = new Neo4JFibonacciCycleNodeObject(neighbour, alt);
					fibonacciObjectMap.put(neighbour, object);
					fibonacciHeap.add(object);
					previousBall.put(neighbour, u);
				} else if (alt < fibonacciObjectMap.get(neighbour).distance) {
					fibonacciHeap.decreaseDistance(fibonacciObjectMap.get(neighbour), alt);
					previousBall.put(neighbour, u);
				}
			}

			// If extracted node is in R, stop
			if (R.contains(u)) {
				bundle = u;
				previousBallMap.put(source, previousBall);
				break;
			}
		}

		// vertex b_v = u is closet node in R from v
		b.put(source, bundle);

		// v is bundled to u
		Set<CycleNode> bundleU = Bundle.get(bundle);
		bundleU.add(source);
		Bundle.put(bundle, bundleU);

		// for all vertices v meet before u, they are include in Ball(v)
		Set<CycleNode> ball = new HashSet<>(shortestDist.keySet());
		ball.remove(bundle);
		Ball.put(source, ball);

		// priority from vertex v to each vertex in Ball(v)
		dist.put(source, shortestDist);
	}

	public static void formBundleAndBall(Map<CycleNode, Set<CycleNode>> neighbours,
										 Map<CycleNode, Map<CycleNode, Integer>> weights,
										 FibHeapCycleNode<FibonacciCycleNodeObject> fibonacciHeap,
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
			DijkstraStop(
					neighbours,
					weights,
					v,
					fibonacciHeap,
					R);
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
