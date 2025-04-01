package org.dijkstra.performance.environment.array;

import org.dijkstra.algo.fibonacci.sequential.FibHeapIntegerArraySequentialDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapInteger;
import org.dijkstra.graph.NeighbourArrayGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Arrays;
import java.util.Random;

public class Neo4JFibHeapIntegerArraySequentialEnvironmentInteger implements IntegerPerformanceEnvironment {

	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();

	int[] previous;
	Neo4JFibonacciIntegerObject[] priorityObjectArray;
	Neo4JFibHeapInteger priorityQueue;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4JFibHeapIntegerArraySequentialEnvironmentInteger(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			FibHeapIntegerArraySequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new Neo4JFibHeapInteger();
		priorityObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);

		System.out.println(Arrays.deepToString(generator.neighbours));
		System.out.println(Arrays.deepToString(generator.weights));

		priorityQueue = new Neo4JFibHeapInteger();
		priorityObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}
		
		int origin = random.nextInt(size);
		System.out.println("origin: " + origin);
		FibHeapIntegerArraySequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
