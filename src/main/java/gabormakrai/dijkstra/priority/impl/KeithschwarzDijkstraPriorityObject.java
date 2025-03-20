package gabormakrai.dijkstra.priority.impl;

import gabormakrai.com.keithschwarz.FibonacciHeap.Entry;

import gabormakrai.dijkstra.priority.PriorityObject;

public class KeithschwarzDijkstraPriorityObject extends PriorityObject {
	
	public Entry<KeithschwarzDijkstraPriorityObject> entry;

	public KeithschwarzDijkstraPriorityObject(int node, double distance) {
		super(node, distance);
	}

}
