package org.dijkstra.fib.wrapper;

import org.dijkstra.node.CycleNode;

/**
 * Customize object going into FibHeap for Dijkstra
 */
public class FibonacciObject implements Comparable<FibonacciObject> {

    public int distance; // distance from source to this vertex
    public CycleNode node; // vertex name (u_v)

    public FibonacciObject(CycleNode node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(FibonacciObject other) {
        if (distance > other.distance) {
            return +1;
        } else {
            return -1;
        }
    }
}
