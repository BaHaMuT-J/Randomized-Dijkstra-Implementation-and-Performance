package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibHeapInteger;
import org.dijkstra.fib.wrapper.FibonacciIntegerObject;

import java.util.TreeSet;

public class BinaryHeapInteger implements FibHeapInteger<FibonacciIntegerObject> {

    TreeSet<FibonacciIntegerObject> tree = new TreeSet<>();

    @Override
    public void add(FibonacciIntegerObject item) {
        tree.add(item);
    }

    @Override
    public void decreasePriority(FibonacciIntegerObject item, int priority) {
        tree.remove(item);
        item.priority = priority;
        tree.add(item);
    }

    @Override
    public FibonacciIntegerObject extractMin() {
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
