package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibHeapCycleNode;
import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;

import java.util.TreeSet;

public class BinaryHeapCycleNode implements FibHeapCycleNode<FibonacciCycleNodeObject> {

    TreeSet<FibonacciCycleNodeObject> tree = new TreeSet<>();

    @Override
    public void add(FibonacciCycleNodeObject item) {
        tree.add(item);
    }

    @Override
    public void decreaseDistance(FibonacciCycleNodeObject item, int distance) {
        tree.remove(item);
        item.distance = distance;
        tree.add(item);
    }

    @Override
    public FibonacciCycleNodeObject extractMin() {
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
