package org.dijkstra.performance.environment.fibonacci.set.randomized;

import org.dijkstra.algo.randomized.FibHeapIntegerSetRandomizedDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapInteger;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Arrays;
import java.util.Random;

public class Neo4JFibHeapIntegerSetRandomizedEnvironment implements IntegerPerformanceEnvironment {

	public NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();

	int[] previous;
	Neo4JFibonacciIntegerObject[] fibObjectArray;
	Neo4JFibHeapInteger fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4JFibHeapIntegerSetRandomizedEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			FibHeapIntegerSetRandomizedDijkstra.createPreviousArray(generator.neighbours.keySet(),
					generator.neighbours,
					generator.weights,
					origin,
					previous,
					fibObjectArray,
					fibonacciHeap,
					random);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		fibonacciHeap = new Neo4JFibHeapInteger();
		fibObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			fibObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);

//		System.out.println(generator.neighbours);
//		System.out.println(generator.weights);

		fibonacciHeap = new Neo4JFibHeapInteger();
		fibObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			fibObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}
		
		int origin = random.nextInt(size);
		System.out.println("origin: " + origin);
		FibHeapIntegerSetRandomizedDijkstra.createPreviousArray(generator.neighbours.keySet(),
				generator.neighbours,
				generator.weights,
				origin,
				previous,
				fibObjectArray,
				fibonacciHeap,
				random);

		System.out.printf("previous: %s\n", Arrays.toString(previous));
		for (int i = 0; i < previous.length; ++i) {
			System.out.printf("i: %d | neighbor: %s | previous[i]: %d | ", i, generator.neighbours.get(i), previous[i]);
			int total = FibHeapIntegerSetRandomizedDijkstra.pathCalculate(previous, i, generator.weights);
			System.out.printf("total: %d\n", total);
		}
		return previous;
	}
}
