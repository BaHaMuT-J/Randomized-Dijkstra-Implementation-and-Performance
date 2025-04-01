package org.dijkstra.performance.environment.set.sequential;

import org.dijkstra.algo.fibonacci.sequential.FibHeapIntegerSetSequentialDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4JFibonacciIntegerObject;
import org.dijkstra.fib.wrapper.heap.Neo4JFibHeapInteger;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.performance.IntegerPerformanceEnvironment;

import java.util.Random;

public class Neo4JFibHeapIntegerSetSequentialEnvironment implements IntegerPerformanceEnvironment {

	public NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();

	int[] previous;
	Neo4JFibonacciIntegerObject[] priorityObjectArray;
	Neo4JFibHeapInteger priorityQueue;
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
			FibHeapIntegerSetSequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
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

		System.out.println(generator.neighbours);
		System.out.println(generator.weights);

		priorityQueue = new Neo4JFibHeapInteger();
		priorityObjectArray = new Neo4JFibonacciIntegerObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4JFibonacciIntegerObject(i, 0);
		}
		
		int origin = random.nextInt(size);
		System.out.println("origin: " + origin);
		FibHeapIntegerSetSequentialDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
