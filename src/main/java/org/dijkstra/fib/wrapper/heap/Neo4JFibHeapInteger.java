package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.dijkstra.fib.wrapper.FibHeapInteger;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.Comparator;

public class Neo4JFibHeapInteger implements FibHeapInteger<FibonacciIntegerObject> {

    Comparator<FibonacciIntegerObject> priorityObjectComparator = FibonacciIntegerObject::compareTo;

    FibonacciHeap<FibonacciIntegerObject> heap = new FibonacciHeap<FibonacciIntegerObject>(priorityObjectComparator);

    @Override
    public void add(FibonacciIntegerObject item) {
        FibonacciHeap<FibonacciIntegerObject>.FibonacciHeapNode node = heap.insert(item);
        ((Neo4JFibonacciIntegerObject)item).node = node;
    }

    @Override
    public void decreasePriority(FibonacciIntegerObject item, int priority) {
        item.priority = priority;
        heap.decreaseKey(((Neo4JFibonacciIntegerObject)item).node, item);
    }

    @Override
    public FibonacciIntegerObject extractMin() {
        return heap.extractMin();
    }

    @Override
    public void clear() {
        heap = new FibonacciHeap<FibonacciIntegerObject>(priorityObjectComparator);
    }

    @Override
    public int size() {
        return heap.size();
    }
}
