package org.dijkstra.fib.wrapper;

public interface FibHeap<E extends FibonacciObject> {
    public void add(E item);
    public void decreaseDistance(E item, int distance);
    public E extractMin();
    public void clear();
    public int size();
}
