package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibHeap;
import org.dijkstra.fib.wrapper.FibonacciObject;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.Comparator;

public class Neo4jFibonacciHeap implements FibHeap<FibonacciObject> {

    Comparator<FibonacciObject> fibObjectComparator = FibonacciObject::compareTo;

    FibonacciHeap<FibonacciObject> heap = new FibonacciHeap<>(fibObjectComparator);

    @Override
    public void add(FibonacciObject item) {
        FibonacciHeap<FibonacciObject>.FibonacciHeapNode node = heap.insert(item);
        ((Neo4jFibonacciObject) item).node = node;
    }

    @Override
    public void decreaseDistance(FibonacciObject item, int distance) {
        item.distance = distance;
        heap.decreaseKey(((Neo4jFibonacciObject) item).node, item);
    }

    @Override
    public FibonacciObject extractMin() {
        return heap.extractMin();
    }

    @Override
    public void clear() {
        heap = new FibonacciHeap<>(fibObjectComparator);
    }

    @Override
    public int size() {
        return heap.size();
    }
}
