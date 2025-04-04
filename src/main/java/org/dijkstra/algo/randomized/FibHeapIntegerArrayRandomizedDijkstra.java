package org.dijkstra.algo.randomized;

import org.dijkstra.fib.wrapper.FibHeapInteger;
import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;

import java.util.*;

public class FibHeapIntegerArrayRandomizedDijkstra {

	private static Map<Integer, Integer> b;
	private static Map<Integer, Integer> firstInBallMap;
	private static Map<Integer, Map<Integer, Integer>> previousBallMap;
	private static Map<Integer, Set<Integer>> Bundle;
	private static Map<Integer, Set<Integer>> Ball;
	private static Map<Integer, Integer> d;
	private static Map<Integer, Map<Integer, Integer>> dist;

	public static void createPreviousArray(int[][] neighbours,
										   int[][] weights,
										   int source,
										   int[] previous,
										   FibonacciIntegerObject[] fibonacciIntegerObjectArray,
										   FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
										   Random random
	) {
		int n = neighbours.length;
		Map.Entry<Set<Integer>, Set<Integer>> entryR = getSetR(n, source, random);
		Set<Integer> R = entryR.getKey();
		Set<Integer> notR = entryR.getValue();

		b = new HashMap<>();
		firstInBallMap = new HashMap<>();
		previousBallMap = new HashMap<>();
		Bundle = new HashMap<>();
		Ball = new HashMap<>();
		d = new HashMap<>();
		dist = new HashMap<>();

		formBundleAndBall(neighbours,
				weights,
				fibHeapInteger,
				R,
				notR);

		for (int node = 0; node < n; node++) {
			d.put(node, Integer.MAX_VALUE);
		}
		d.put(source, 0);
		previous[source] = -1;

		fibHeapInteger.clear();
		for (Integer node : R) {
			FibonacciIntegerObject object = fibonacciIntegerObjectArray[node];
			object.priority = node == source ? 0 : Integer.MAX_VALUE;
			fibonacciIntegerObjectArray[node] = object;

			// Add all FibonacciIntegerObject in FibonacciHeap
			fibHeapInteger.add(object);
		}

		Set<Integer> extractedNodes = new HashSet<>();
		while (fibHeapInteger.size() != 0) {

			// extract min
			FibonacciIntegerObject min = fibHeapInteger.extractMin();
			Integer u = min.node;
			extractedNodes.add(u);

			for (Integer v : Bundle.get(u)) {
				Map<Integer, Integer> dist_v = dist.get(v);
				relax(firstInBallMap.get(v), v, min.priority + dist_v.get(u), R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
				Set<Integer> ball_v = Ball.getOrDefault(v, new HashSet<>());
				for (Integer y : ball_v) {
					int newPriority = d.get(y) + dist_v.get(y);
					relax(firstInBallMap.get(v), v, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
				}
				for (Integer z2 : ball_v) {
					for (Integer z1 : neighbours[z2]) {
						int w_z1_z2 = weights[z1][z2];
						int newPriority = d.get(z1) + w_z1_z2 + dist_v.get(z2);
						int candidate = firstInBallMap.get(v);
						if (Objects.equals(z2, v)) {
							candidate = z2;
						}
						relax(candidate, v, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					}
				}
			}

			for (Integer x : Bundle.get(u)) {
				for (Integer y : neighbours[x]) {
					int w_x_y = weights[x][y];
					int newPriority = d.get(x) + w_x_y;
					relax(x, y, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					for (Integer z1 : Ball.get(y)) {
						newPriority = d.get(x) + w_x_y + dist.get(y).get(z1);
						relax(previousBallMap.get(y).get(z1), z1, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					}
				}
			}
		}
	}

	private static void relax(Integer u,
							  Integer v,
							  Integer alt,
							  Set<Integer> R,
							  Set<Integer> extractedNodes,
							  FibonacciIntegerObject[] fibonacciIntegerObjectArray,
							  FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
							  int[] previous) {
		if (alt >= 0 && alt < d.get(v)) {
			d.put(v, alt);
			previous[v] = u;
			if (!R.contains(v)) {
				Integer bundle = b.get(v);
				int newPriority = d.get(v) + dist.get(v).get(bundle);
				relax(previousBallMap.get(v).get(bundle), bundle, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);			} else if (!extractedNodes.contains(v)) {
				fibHeapInteger.decreasePriority(fibonacciIntegerObjectArray[v], alt);
			}
		}
	}

	private static double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	private static Map.Entry<Set<Integer>, Set<Integer>> getSetR(int n, Integer source, Random random) {
		Set<Integer> R = new HashSet<>();
		Set<Integer> notR = new HashSet<>();
		double k = Math.sqrt(log2(n) / log2(log2(n))); // Math.sqrt(Math.log(n) / Math.log(Math.log(n)));

		for (int node = 0; node < n; node++) {
			if (Objects.equals(node, source) || random.nextDouble() <= 1.0/k) {
				R.add(node);
			} else {
				notR.add(node);
			}
		}

		return Map.entry(R, notR);
	}

	private static void DijkstraStop(int[][] neighbours,
									 int[][] weights,
									 Integer source,
									 FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
									 Set<Integer> R
	) {
		int n = neighbours.length;
		fibHeapInteger.clear();

		// Insert in fibHeapInteger when relax
		FibonacciIntegerObject[] fibonacciIntegerObjectArray = new FibonacciIntegerObject[n];
		FibonacciIntegerObject sourceObject = new Neo4JFibonacciIntegerObject(source, 0);
		fibonacciIntegerObjectArray[source] = sourceObject;
		fibHeapInteger.add(sourceObject);

		Map<Integer, Integer> shortestDist = new HashMap<>();
		Integer bundle = null;
		boolean foundFirstInBall = false;

		Map<Integer, Integer> previousBall = new HashMap<>();

		while (fibHeapInteger.size() != 0) {

			// extract min
			FibonacciIntegerObject min = fibHeapInteger.extractMin();
			Integer u = min.node;
			shortestDist.put(u, min.priority);

			if (!Objects.equals(u, source) && !foundFirstInBall) {
				foundFirstInBall = true;
				firstInBallMap.put(source, u);
			}

			for (Integer neighbour : neighbours[u]) {
				int[] weightsU = weights[u];
				int alt = fibonacciIntegerObjectArray[u].priority + weightsU[neighbour];
				if (fibonacciIntegerObjectArray[neighbour] == null) {
					FibonacciIntegerObject object = new Neo4JFibonacciIntegerObject(neighbour, alt);
					fibonacciIntegerObjectArray[neighbour] = object;
					fibHeapInteger.add(object);
					previousBall.put(neighbour, u);
				} else if (alt < fibonacciIntegerObjectArray[neighbour].priority) {
					fibHeapInteger.decreasePriority(fibonacciIntegerObjectArray[neighbour], alt);
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
		Set<Integer> bundleU = Bundle.get(bundle);
		bundleU.add(source);
		Bundle.put(bundle, bundleU);

		// for all vertices v meet before u, they are include in Ball(v)
		Set<Integer> ball = new HashSet<>(shortestDist.keySet());
		ball.remove(bundle);
		Ball.put(source, ball);

		// priority from vertex v to each vertex in Ball(v)
		dist.put(source, shortestDist);
	}

	public static void formBundleAndBall(int[][] neighbours,
										 int[][] weights,
										 FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
										 Set<Integer> R,
										 Set<Integer> notR) {
		for (Integer u : R) {
			// for every vertex u in R, b(u) = u
			b.put(u, u);

			// add u in Bundle(u)
			Set<Integer> bundle = new HashSet<>();
			bundle.add(u);
			Bundle.put(u, bundle);

			// no Ball for nodes in R
			Ball.put(u, new HashSet<>());

			// priority for vertices in R need only itself
			Map<Integer, Integer> dist_u = new HashMap<>();
			dist_u.put(u, 0);
			dist.put(u, dist_u);
		}

		for (Integer v : notR) {
			// for every vertex v not in R, run Dijkstra using v as a source
			DijkstraStop(
					neighbours,
					weights,
					v,
					fibHeapInteger,
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

	public static int pathCalculate(int[] path, int[][] weights) {
		int totalWeight = 0;
		for (int i = 1; i < path.length; ++i) {
			totalWeight += weights[path[i-1]][path[i]];
		}
		return totalWeight;
	}
}
