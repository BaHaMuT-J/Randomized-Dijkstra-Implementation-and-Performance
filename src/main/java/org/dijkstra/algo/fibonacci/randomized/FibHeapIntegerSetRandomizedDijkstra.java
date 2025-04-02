package org.dijkstra.algo.fibonacci.randomized;

import org.dijkstra.fib.wrapper.FibHeapInteger;
import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;

import java.util.*;

public class FibHeapIntegerSetRandomizedDijkstra {

	private static Map<Integer, Integer> b;
	private static Map<Integer, Integer> firstInBallMap;
	private static Map<Integer, Map<Integer, Integer>> previousBallMap;
	private static Map<Integer, Set<Integer>> Bundle;
	private static Map<Integer, Set<Integer>> Ball;
	private static Map<Integer, Integer> d;
	private static Map<Integer, Map<Integer, Integer>> dist;
	
	public static void createPreviousArray(Set<Integer> nodes,
										   Map<Integer, Set<Integer>> neighbours,
										   Map<Integer, Map<Integer, Integer>> weights,
										   int source,
										   int[] previous,
										   FibonacciIntegerObject[] fibonacciIntegerObjectArray,
										   FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
										   Random random
	) {
		Map.Entry<Set<Integer>, Set<Integer>> entryR = getSetR(nodes, source, random);
		Set<Integer> R = entryR.getKey();
		Set<Integer> notR = entryR.getValue();

		b = new HashMap<>();
		firstInBallMap = new HashMap<>();
		previousBallMap = new HashMap<>();
		Bundle = new HashMap<>();
		Ball = new HashMap<>();
		d = new HashMap<>();
		dist = new HashMap<>();

		formBundleAndBall(nodes,
				neighbours,
				weights,
				fibHeapInteger,
				R,
				notR);

		for (Integer node : nodes) {
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

//		System.out.printf("R: %s\n", R);
//		System.out.printf("notR: %s\n", notR);
//		System.out.printf("Bundle: %s\n", Bundle);
//		System.out.printf("Ball: %s\n", Ball);
//		System.out.printf("dist: %s\n", dist);
//		System.out.printf("firstInBallMap: %s\n", firstInBallMap);
//		System.out.printf("previousBallMap: %s\n", previousBallMap);
//		int test = 29;
//		if (nodes.size() >= 30) System.out.printf("neighbor %d: %s\nweight %d: %s\n", test, neighbours.get(test), test, weights.get(24));

		Set<Integer> extractedNodes = new HashSet<>();
		while (fibHeapInteger.size() != 0) {

			// extract min
			FibonacciIntegerObject min = fibHeapInteger.extractMin();
			Integer u = min.node;
			extractedNodes.add(u);

			for (Integer v : Bundle.get(u)) {
				Map<Integer, Integer> dist_v = dist.get(v);
//				if (Objects.equals(v ,test)) System.out.printf("v: %d | firstInBallMap[v]: %d | u: %d\n", v, firstInBallMap.get(v), u);
				relax(firstInBallMap.get(v), v, min.priority + dist_v.get(u), R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
				Set<Integer> ball_v = Ball.getOrDefault(v, new HashSet<>());
				for (Integer y : ball_v) {
					int newPriority = d.get(y) + dist_v.get(y);
//					if (Objects.equals(v ,test)) System.out.printf("v: %d | firstInBallMap[v]: %d | y: %d\n", v, firstInBallMap.get(v), y);
					relax(firstInBallMap.get(v), v, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
				}
				for (Integer z2 : ball_v) {
					for (Integer z1 : neighbours.get(z2)) {
						int w_z1_z2 = weights.get(z1).get(z2);
						int newPriority = d.get(z1) + w_z1_z2 + dist_v.get(z2);
						int candidate = firstInBallMap.get(v);
						if (Objects.equals(z2, v)) {
							candidate = z2;
						}
//						 if (Objects.equals(v ,test)) System.out.printf("v: %d | candidate: %d | z1: %d | z2: %d\n", v, candidate, z1, z2);
						relax(candidate, v, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					}
				}
			}

			for (Integer x : Bundle.get(u)) {
				for (Integer y : neighbours.get(x)) {
					int w_x_y = weights.get(x).get(y);
					int newPriority = d.get(x) + w_x_y;
//					if (Objects.equals(y ,test)) System.out.printf("y: %d | x: %d\n", y, x);
					relax(x, y, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					for (Integer z1 : Ball.get(y)) {
						newPriority = d.get(x) + w_x_y + dist.get(y).get(z1);
//						if (Objects.equals(z1 ,test)) System.out.printf("z1: %d | prevBall_y_z1: %d | x: %d | y: %d\n", z1, previousBallMap.get(y).get(z1), x, y);
						relax(previousBallMap.get(y).get(z1), z1, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
					}
				}
			}

//			System.out.printf("d: %s\n", d);
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
//		int test = 29;
//		if (Objects.equals(v ,test)) {
//			System.out.printf("relax v: %d | u: %d | previous[v]: %d | d[v]: %d | alt: %d\n", v, u, previous[v], d.get(v), alt);
//		}
		if (alt >= 0 && alt < d.get(v)) {
			d.put(v, alt);
			previous[v] = u;
			if (!R.contains(v)) {
				Integer bundle = b.get(v);
				int newPriority = d.get(v) + dist.get(v).get(bundle);
//				if (Objects.equals(bundle ,test)) System.out.printf("bundle: %d | v: %d | previousBallMap[v][bundle]: %d\n", bundle, v, previousBallMap.get(v).get(bundle));
				relax(previousBallMap.get(v).get(bundle), bundle, newPriority, R, extractedNodes, fibonacciIntegerObjectArray, fibHeapInteger, previous);
			} else if (!extractedNodes.contains(v)) {
				fibHeapInteger.decreasePriority(fibonacciIntegerObjectArray[v], alt);
			}
		}
	}

	private static double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	private static Map.Entry<Set<Integer>, Set<Integer>> getSetR(Set<Integer> nodes, Integer source, Random random) {
		Set<Integer> R = new HashSet<>();
		Set<Integer> notR = new HashSet<>();
		int n = nodes.size();
		double k = Math.sqrt(log2(n) / log2(log2(n))); // Math.sqrt(Math.log(n) / Math.log(Math.log(n)));

		for (Integer node : nodes) {
			if (Objects.equals(node, source) || random.nextDouble() <= 1.0/k) {
				R.add(node);
			} else {
				notR.add(node);
			}
		}

		return Map.entry(R, notR);
	}

	private static void DijkstraStop(Set<Integer> nodes,
									 Map<Integer, Set<Integer>> neighbours,
									 Map<Integer, Map<Integer, Integer>> weights,
									 Integer source,
									 FibHeapInteger<FibonacciIntegerObject> fibHeapInteger,
									 Set<Integer> R
	) {
		fibHeapInteger.clear();

		// Insert in fibHeapInteger when relax
		FibonacciIntegerObject[] fibonacciIntegerObjectArray = new FibonacciIntegerObject[nodes.size()];
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

			for (Integer neighbour : neighbours.get(u)) {
				Map<Integer, Integer> weightsU = weights.get(u);
				int alt = fibonacciIntegerObjectArray[u].priority + weightsU.get(neighbour);
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

	public static void formBundleAndBall(Set<Integer> nodes,
										 Map<Integer, Set<Integer>> neighbours,
										 Map<Integer, Map<Integer, Integer>> weights,
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
			DijkstraStop(nodes,
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
