package org.dijkstra.performance.environment.binary.array.randomized;

import org.dijkstra.algo.fibonacci.randomized.FibHeapIntegerArrayRandomizedDijkstra;
import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.BinaryHeapInteger;
import org.dijkstra.graph.NeighbourArrayGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Random;

public class BinHeapIntegerArrayRandomizedEnvironment implements IntegerPerformanceEnvironment {

	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();

	int[] previous;
	FibonacciIntegerObject[] fibObjectArray;
	BinaryHeapInteger fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public BinHeapIntegerArrayRandomizedEnvironment(int size, double p, int previousArrayBuilds, Random random) {
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
		fibonacciHeap = new BinaryHeapInteger();
		fibObjectArray = new FibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			fibObjectArray[i] = new FibonacciIntegerObject(i, 0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);

//		System.out.println(Arrays.deepToString(generator.neighbours));
//		System.out.println(Arrays.deepToString(generator.weights));

		fibonacciHeap = new BinaryHeapInteger();
		fibObjectArray = new FibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			fibObjectArray[i] = new FibonacciIntegerObject(i, 0);
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
