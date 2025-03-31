package org.dijkstra.fib.wrapper;

public class PriorityObject implements Comparable<PriorityObject> {

    public int priority;
    public int node;

    public PriorityObject(int node, int priority) {
        this.node = node;
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityObject o) {
        if (priority > o.priority) {
            return +1;
        } else {
            return -1;
        }
    }
}
