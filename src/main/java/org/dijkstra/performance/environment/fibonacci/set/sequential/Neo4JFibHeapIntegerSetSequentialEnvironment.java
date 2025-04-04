package org.dijkstra.performance.environment.fibonacci.set.sequential;

import org.dijkstra.algo.sequential.FibHeapIntegerSetSequentialDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapInteger;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Arrays;
import java.util.Random;

public class Neo4JFibHeapIntegerSetSequentialEnvironment implements IntegerPerformanceEnvironment {

	public NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();

	int[] previous;
	Neo4JFibonacciIntegerObject[] fibObjectArray;
	Neo4JFibHeapInteger fibonacciHeap;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4JFibHeapIntegerSetSequentialEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			FibHeapIntegerSetSequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, fibObjectArray, fibonacciHeap);
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
		FibHeapIntegerSetSequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, fibObjectArray, fibonacciHeap);

		System.out.printf("previous: %s\n", Arrays.toString(previous));
		for (int i = 0; i < previous.length; ++i) {
			System.out.printf("i: %d | neighbor: %s | previous[i]: %d | ", i, generator.neighbours.get(i), previous[i]);
			int total = FibHeapIntegerSetSequentialDijkstra.pathCalculate(previous, i, generator.weights);
			System.out.printf("total: %d\n", total);
		}
		return previous;
	}
}
