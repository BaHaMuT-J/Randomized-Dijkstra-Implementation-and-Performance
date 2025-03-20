package gabormakrai.dijkstra.priority.impl;

import gabormakrai.dijkstra.priority.PriorityQueue;
import gabormakrai.org.apache.nutch.util.FibonacciHeap;

import gabormakrai.dijkstra.priority.PriorityObject;

public class NutchFibonacciPriorityQueue implements PriorityQueue<PriorityObject> {
	
	FibonacciHeap heap = new FibonacciHeap();
	
	@Override
	public void add(PriorityObject item) {
		heap.add(item, item.priority);
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(item, priority);
	}

	@Override
	public PriorityObject extractMin() {
		return (PriorityObject) heap.popMin();
	}

	@Override
	public void clear() {
		heap = new FibonacciHeap(); 
	}

	@Override
	public int size() {
		return heap.size();
	}	

}
