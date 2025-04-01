package org.dijkstra.fib.wrapper;

public class FibonacciIntegerObject implements Comparable<FibonacciIntegerObject> {

    public int priority;
    public int node;

    public FibonacciIntegerObject(int node, int priority) {
        this.node = node;
        this.priority = priority;
    }

    @Override
    public int compareTo(FibonacciIntegerObject o) {
        if (priority > o.priority) {
            return +1;
        } else {
            return -1;
        }
    }
}
