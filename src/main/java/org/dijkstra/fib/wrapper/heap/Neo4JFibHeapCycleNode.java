package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibHeapCycleNode;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.Comparator;

public class Neo4JFibHeapCycleNode implements FibHeapCycleNode<FibonacciCycleNodeObject> {

    Comparator<FibonacciCycleNodeObject> fibObjectComparator = FibonacciCycleNodeObject::compareTo;

    FibonacciHeap<FibonacciCycleNodeObject> heap = new FibonacciHeap<>(fibObjectComparator);

    @Override
    public void add(FibonacciCycleNodeObject item) {
        FibonacciHeap<FibonacciCycleNodeObject>.FibonacciHeapNode node = heap.insert(item);
        ((Neo4JFibonacciCycleNodeObject) item).node = node;
    }

    @Override
    public void decreaseDistance(FibonacciCycleNodeObject item, int distance) {
        item.distance = distance;
        heap.decreaseKey(((Neo4JFibonacciCycleNodeObject) item).node, item);
    }

    @Override
    public FibonacciCycleNodeObject extractMin() {
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
