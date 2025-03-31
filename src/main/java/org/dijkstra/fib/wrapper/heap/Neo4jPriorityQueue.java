package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.PriorityObject;
import org.dijkstra.fib.wrapper.PriorityQueue;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.Comparator;

public class Neo4jPriorityQueue implements PriorityQueue<PriorityObject> {

    Comparator<PriorityObject> priorityObjectComparator = PriorityObject::compareTo;

    FibonacciHeap<PriorityObject> heap = new FibonacciHeap<PriorityObject>(priorityObjectComparator);

    @Override
    public void add(PriorityObject item) {
        FibonacciHeap<PriorityObject>.FibonacciHeapNode node = heap.insert(item);
        ((Neo4jPriorityObject)item).node = node;
    }

    @Override
    public void decreasePriority(PriorityObject item, int priority) {
        item.priority = priority;
        heap.decreaseKey(((Neo4jPriorityObject)item).node, item);
    }

    @Override
    public PriorityObject extractMin() {
        return heap.extractMin();
    }

    @Override
    public void clear() {
        heap = new FibonacciHeap<PriorityObject>(priorityObjectComparator);
    }

    @Override
    public int size() {
        return heap.size();
    }
}
