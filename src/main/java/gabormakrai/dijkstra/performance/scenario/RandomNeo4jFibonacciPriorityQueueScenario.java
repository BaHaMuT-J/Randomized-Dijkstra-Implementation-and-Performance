package gabormakrai.dijkstra.performance.scenario;

import java.util.Random;

import gabormakrai.dijkstra.graph.NeighbourArrayGraphGenerator;
import gabormakrai.dijkstra.performance.PerformanceScenario;
import gabormakrai.dijkstra.priority.PriorityQueueDijkstra;
import gabormakrai.dijkstra.priority.impl.Neo4jDijkstraPriorityObject;
import gabormakrai.dijkstra.priority.impl.Neo4jFibonacciPrioityQueue;

public class RandomNeo4jFibonacciPriorityQueueScenario implements PerformanceScenario {
	
	NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
	
	int[] previous;
	Neo4jDijkstraPriorityObject[] priorityObjectArray;
	Neo4jFibonacciPrioityQueue priorityQueue;
	Random random;
	
	int size;
	double p;
	int previousArrayBuilds;
	
	public RandomNeo4jFibonacciPriorityQueueScenario(int size, double p, int previousArrayBuilds, Random random) {
		this.size = size;
		this.p = p;
		this.previousArrayBuilds = previousArrayBuilds;
		this.random = random;
	}
	
	@Override
	public void runShortestPath() {
		for (int i = 0; i < previousArrayBuilds; ++i) {
			int origin = random.nextInt(size);
			PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		}
	}
	
	@Override
	public void generateGraph() {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new Neo4jFibonacciPrioityQueue();
		priorityObjectArray = new Neo4jDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4jDijkstraPriorityObject(i, 0.0);
		}
	}

	@Override
	public int[] testPrevious(int randomSeed) {
		previous = new int[size];
		generator.generateRandomGraph(size, p, random);
		priorityQueue = new Neo4jFibonacciPrioityQueue();
		priorityObjectArray = new Neo4jDijkstraPriorityObject[size];
		for (int i = 0; i < size; ++i) {
			priorityObjectArray[i] = new Neo4jDijkstraPriorityObject(i, 0.0);
		}
		
		int origin = random.nextInt(size);
//		System.out.println("origin: " + origin);
		PriorityQueueDijkstra.createPreviousArray(generator.neighbours, generator.weights, origin, previous, priorityObjectArray, priorityQueue);
		
		return previous;
	}
}
