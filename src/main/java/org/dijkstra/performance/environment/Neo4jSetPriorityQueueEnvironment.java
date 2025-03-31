package org.dijkstra.performance.environment;

import org.dijkstra.fib.SetPriorityQueueDijkstra;
import org.dijkstra.fib.wrapper.heap.Neo4jPriorityObject;
import org.dijkstra.fib.wrapper.heap.Neo4jPriorityQueue;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.performance.PerformanceEnvironment;

import java.util.Random;

public class Neo4jSetPriorityQueueEnvironment implements PerformanceEnvironment {

	NeighbourSetGraphGenerator generator = new NeighbourSetGraphGenerator();

	int[] previous;
	Neo4jPriorityObject[] priorityObjectArray;
	Neo4jPriorityQueue priorityQueue;
	Random random;

	int size;
	double p;
	int previousArrayBuilds;

	public Neo4jSetPriorityQueueEnvironment(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			SetPriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new Neo4jPriorityQueue();
		priorityObjectArray = new Neo4jPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4jPriorityObject(i, 0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new Neo4jPriorityQueue();
		priorityObjectArray = new Neo4jPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4jPriorityObject(i, 0);
		}
		
		int origin = random.nextInt(size);
		System.out.println("origin: " + origin);
		SetPriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
