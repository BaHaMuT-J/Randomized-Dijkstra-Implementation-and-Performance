package org.dijkstra.performance.environment;

import org.dijkstra.fib.SetPriorityQueueDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4jFibonacciHeap;
import org.dijkstra.fib.wrapper.heap.Neo4jFibonacciObject;
import org.dijkstra.fib.wrapper.heap.Neo4jPriorityObject;
import org.dijkstra.fib.wrapper.heap.Neo4jPriorityQueue;
import org.dijkstra.graph.ConstantDegreeGraph;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.ConstantDegreePerformanceEnvironment;
import org.dijkstra.performance.PerformanceEnvironment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Neo4jConstantDegreeFibHeapEnvironment implements ConstantDegreePerformanceEnvironment {

	NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();
	ConstantDegreeGraph constantDegreeGraph = new ConstantDegreeGraph();

	Map<CycleNode, CycleNode> previous;
	Map<CycleNode, Neo4jFibonacciObject> fibonacciObjectMap;
	Neo4jFibonacciHeap fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4jConstantDegreeFibHeapEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
//			SetPriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, fibonacciObjectMap, fibonacciHeap);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new HashMap<>();
		generator.generateRandomGraph(size, p, random);
		constantDegreeGraph.transformGraph(generator);
		fibonacciHeap = new Neo4jFibonacciHeap();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new Neo4jFibonacciObject(node, 0));
		}
	}

	@Override
	public Map<CycleNode, CycleNode> testPrevious(int randomSeed) {
		previous = new HashMap<>();
		generator.generateRandomGraph(size, p, random);

		System.out.println(generator.neighbours);
		System.out.println(generator.weights);

		constantDegreeGraph.transformGraph(generator);

		fibonacciHeap = new Neo4jFibonacciHeap();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new Neo4jFibonacciObject(node, 0));
		}

		// Manually random element from nodes
		int randomIndex = random.nextInt(constantDegreeGraph.nodes.size());
		Iterator<CycleNode> iterator = constantDegreeGraph.nodes.iterator();
		CycleNode origin = null;
		for (int i = 0; i <= randomIndex; i++) {
			origin = iterator.next();
		}

		System.out.println("origin: " + origin);
//		SetPriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, fibonacciObjectMap, fibonacciHeap);
		
		return previous;
	}
}
