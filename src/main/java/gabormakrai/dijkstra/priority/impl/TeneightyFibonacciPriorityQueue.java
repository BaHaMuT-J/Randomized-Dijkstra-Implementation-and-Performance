package gabormakrai.dijkstra.priority.impl;

import gabormakrai.dijkstra.priority.PriorityQueue;
import gabormakrai.org.teneighty.heap.FibonacciHeap;
import gabormakrai.org.teneighty.heap.Heap.Entry;

import gabormakrai.dijkstra.priority.PriorityObject;

public class TeneightyFibonacciPriorityQueue implements PriorityQueue<PriorityObject> {

	FibonacciHeap<Double, TeneightyDijkstraPriorityObject> heap = new FibonacciHeap<>();

	@Override
	public void add(PriorityObject item) {
		Entry<Double, TeneightyDijkstraPriorityObject> entry = heap.insert(item.priority, (TeneightyDijkstraPriorityObject)item);
		((TeneightyDijkstraPriorityObject)item).entry = entry;
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		item.priority = priority;
		heap.decreaseKey(((TeneightyDijkstraPriorityObject)item).entry, priority);
	}

	@Override
	public PriorityObject extractMin() {
		return heap.extractMinimum().getValue();
	}

	@Override
	public void clear() {
		heap.clear();
	}

	@Override
	public int size() {
		return heap.getSize();
	}	

}
