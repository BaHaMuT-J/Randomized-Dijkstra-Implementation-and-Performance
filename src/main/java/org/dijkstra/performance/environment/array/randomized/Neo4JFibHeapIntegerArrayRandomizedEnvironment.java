package org.dijkstra.performance.environment.array.randomized;

import org.dijkstra.algo.fibonacci.randomized.FibHeapIntegerArrayRandomizedDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapInteger;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;
import org.dijkstra.graph.NeighbourArrayGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Arrays;
import java.util.Random;

public class Neo4JFibHeapIntegerArrayRandomizedEnvironment implements IntegerPerformanceEnvironment {

	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();

	int[] previous;
	Neo4JFibonacciIntegerObject[] fibObjectArray;
	Neo4JFibHeapInteger fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4JFibHeapIntegerArrayRandomizedEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}

	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			FibHeapIntegerArrayRandomizedDijkstra.createPreviousArray(generator.neighbours,
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

//		System.out.println(Arrays.deepToString(generator.neighbours));
//		System.out.println(Arrays.deepToString(generator.weights));

		fibonacciHeap = new Neo4JFibHeapInteger();
		fibObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			fibObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}

		int origin = random.nextInt(size);
		System.out.println("origin: " + origin);
		FibHeapIntegerArrayRandomizedDijkstra.createPreviousArray(generator.neighbours,
				generator.weights,
				origin,
				previous,
				fibObjectArray,
				fibonacciHeap,
				random);

		return previous;
	}
}
