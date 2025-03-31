package org.dijkstra.fib.wrapper;

public interface PriorityQueue<E extends PriorityObject> {
    public void add(E item);
    public void decreasePriority(E item, int priority);
    public E extractMin();
    public void clear();
    public int size();
}
