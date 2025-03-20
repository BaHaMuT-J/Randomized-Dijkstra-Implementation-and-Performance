package gabormakrai.dijkstra.priority.impl;

import gabormakrai.com.growingwiththeweb.dataStructures.FibonacciHeap;

import gabormakrai.dijkstra.priority.PriorityObject;

public class GrowingWithTheWebDijkstraPriorityObject extends PriorityObject {
	
	public FibonacciHeap.Node<PriorityObject> node;

	public GrowingWithTheWebDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
