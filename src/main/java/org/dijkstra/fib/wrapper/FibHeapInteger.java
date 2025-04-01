package org.dijkstra.fib.wrapper;

public interface FibHeapInteger<E extends FibonacciIntegerObject> {
    public void add(E item);
    public void decreasePriority(E item, int priority);
    public E extractMin();
    public void clear();
    public int size();
}
