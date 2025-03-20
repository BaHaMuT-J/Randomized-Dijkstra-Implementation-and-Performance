package gabormakrai.dijkstra.priority.impl;

import gabormakrai.org.neo4j.graphalgo.impl.util.FibonacciHeap;

import gabormakrai.dijkstra.priority.PriorityObject;

public class Neo4jDijkstraPriorityObject extends PriorityObject {
	
	public FibonacciHeap<PriorityObject>.FibonacciHeapNode node;

	public Neo4jDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
