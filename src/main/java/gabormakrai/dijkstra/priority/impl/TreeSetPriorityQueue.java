package gabormakrai.dijkstra.priority.impl;

import java.util.TreeSet;

import gabormakrai.dijkstra.priority.PriorityObject;
import gabormakrai.dijkstra.priority.PriorityQueue;

public class TreeSetPriorityQueue implements PriorityQueue<PriorityObject> {
	
	TreeSet<PriorityObject> tree = new TreeSet<PriorityObject>();

	@Override
	public void add(PriorityObject item) {
		tree.add(item);
	}

	@Override
	public void decreasePriority(PriorityObject item, double priority) {
		tree.remove(item);
		item.priority = priority;
		tree.add(item);
	}

	@Override
	public PriorityObject extractMin() {
		return tree.pollFirst();
	}

	@Override
	public void clear() {
		tree.clear();
	}

	@Override
	public int size() {
		return tree.size();
	}	

}
