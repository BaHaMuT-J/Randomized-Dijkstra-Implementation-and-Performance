package org.dijkstra.performance.environment.cycle.sequential;

import org.dijkstra.algo.fibonacci.sequential.FibHeapCycleNodeSequentialDijkstra;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciCycleNodeObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapCycleNode;
import org.dijkstra.graph.ConstantDegreeGraph;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.CycleNodePerformanceEnvironment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Neo4jFibHeapCycleNodeSequentialEnvironment implements CycleNodePerformanceEnvironment {

	NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();
	ConstantDegreeGraph constantDegreeGraph = new ConstantDegreeGraph();

	Map<CycleNode, CycleNode> previous;
	Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap;
	Neo4JFibHeapCycleNode fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	CycleNode[] originsList;

	public Neo4jFibHeapCycleNodeSequentialEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
		this.originsList = new CycleNode[previousArrayBuilds];
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			CycleNode origin = originsList[i];
			FibHeapCycleNodeSequentialDijkstra.createPreviousArray(constantDegreeGraph.nodes,
					constantDegreeGraph.neighbours,
					constantDegreeGraph.weights,
					origin,
					previous,
					fibonacciObjectMap,
					fibonacciHeap);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new HashMap<>();
		generator.generateRandomGraph(size, p, random);
		constantDegreeGraph.transformGraph(generator);
		fibonacciHeap = new Neo4JFibHeapCycleNode();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new Neo4JFibonacciCycleNodeObject(node, 0));
		}

		// Prepare origins for each test iteration
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int randomIndex = random.nextInt(constantDegreeGraph.nodes.size());
			Iterator<CycleNode> iterator = constantDegreeGraph.nodes.iterator();
			for (int j = 0; j <= randomIndex; ++j) {
				originsList[i] = iterator.next();
			}
		}
	}

	@Override
	public Map<CycleNode, CycleNode> testPrevious(int randomSeed) {
		previous = new HashMap<>();
		generator.generateRandomGraph(size, p, random);

//		System.out.println(generator.neighbours);
//		System.out.println(generator.weights);

		constantDegreeGraph.transformGraph(generator);

		fibonacciHeap = new Neo4JFibHeapCycleNode();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new Neo4JFibonacciCycleNodeObject(node, 0));
		}

		// Manually random element from nodes
		int randomIndex = random.nextInt(constantDegreeGraph.nodes.size());
		Iterator<CycleNode> iterator = constantDegreeGraph.nodes.iterator();
		CycleNode origin = null;
		for (int i = 0; i <= randomIndex; i++) {
			origin = iterator.next();
		}

		System.out.println("origin: " + origin);
		FibHeapCycleNodeSequentialDijkstra.createPreviousArray(constantDegreeGraph.nodes,
				constantDegreeGraph.neighbours,
				constantDegreeGraph.weights,
				origin,
				previous,
				fibonacciObjectMap,
				fibonacciHeap);

		return previous;
	}
}
