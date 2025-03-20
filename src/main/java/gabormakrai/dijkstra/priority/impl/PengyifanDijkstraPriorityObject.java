package gabormakrai.dijkstra.priority.impl;

import gabormakrai.com.pengyifan.commons.collections.heap.FibonacciHeap;

import gabormakrai.dijkstra.priority.PriorityObject;

public class PengyifanDijkstraPriorityObject extends PriorityObject {
	
	public FibonacciHeap.Entry entry;

	public PengyifanDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}
	
}
