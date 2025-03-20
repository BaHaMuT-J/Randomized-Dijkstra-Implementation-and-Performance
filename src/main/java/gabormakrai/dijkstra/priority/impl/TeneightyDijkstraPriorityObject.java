package gabormakrai.dijkstra.priority.impl;

import gabormakrai.org.teneighty.heap.Heap.Entry;

import gabormakrai.dijkstra.priority.PriorityObject;

public class TeneightyDijkstraPriorityObject extends PriorityObject {
	
	public Entry<Double, TeneightyDijkstraPriorityObject> entry;

	public TeneightyDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
