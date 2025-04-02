package org.dijkstra.performance.environment.binary.cycle.randomized;

import org.dijkstra.algo.fibonacci.randomized.FibHeapCycleNodeRandomizedDijkstra;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.dijkstra.fib.wrapper.heap.BinaryHeapCycleNode;
import org.dijkstra.graph.ConstantDegreeGraph;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.CycleNodePerformanceEnvironment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class BinHeapCycleNodeRandomizedEnvironment implements CycleNodePerformanceEnvironment {

	NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();
	ConstantDegreeGraph constantDegreeGraph = new ConstantDegreeGraph();

	Map<CycleNode, CycleNode> previous;
	Map<CycleNode, FibonacciCycleNodeObject> fibonacciObjectMap;
	BinaryHeapCycleNode fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	CycleNode[] originsList;

	public BinHeapCycleNodeRandomizedEnvironment(int size, double p, int previousArrayBuilds, Random random) {
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
			FibHeapCycleNodeRandomizedDijkstra.createPreviousArray(constantDegreeGraph.nodes,
					constantDegreeGraph.neighbours,
					constantDegreeGraph.weights,
					origin,
					previous,
					fibonacciObjectMap,
					fibonacciHeap,
					random);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new HashMap<>();
		generator.generateRandomGraph(size, p, random);
		constantDegreeGraph.transformGraph(generator);
		fibonacciHeap = new BinaryHeapCycleNode();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new FibonacciCycleNodeObject(node, 0));
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

		System.out.println(generator.neighbours);
		System.out.println(generator.weights);

		constantDegreeGraph.transformGraph(generator);

		fibonacciHeap = new BinaryHeapCycleNode();
		fibonacciObjectMap = new HashMap<>();
		for (CycleNode node : constantDegreeGraph.nodes) {
			fibonacciObjectMap.put(node, new FibonacciCycleNodeObject(node, 0));
		}

		// Manually random element from nodes
		int randomIndex = random.nextInt(constantDegreeGraph.nodes.size());
		Iterator<CycleNode> iterator = constantDegreeGraph.nodes.iterator();
		CycleNode origin = null;
//		for (int i = 0; i <= randomIndex; i++) {
//			origin = iterator.next();
//		}

		origin = new CycleNode(7, 4);
		System.out.println("origin: " + origin);
		FibHeapCycleNodeRandomizedDijkstra.createPreviousArray(constantDegreeGraph.nodes,
				constantDegreeGraph.neighbours,
				constantDegreeGraph.weights,
				origin,
				previous,
				fibonacciObjectMap,
				fibonacciHeap,
				random);

		return previous;
	}
}
