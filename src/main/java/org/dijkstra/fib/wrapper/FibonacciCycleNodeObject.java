package org.dijkstra.fib.wrapper;

import org.dijkstra.node.CycleNode;

/**
 * Customize object going into FibHeapCycleNode for Dijkstra
 */
public class FibonacciCycleNodeObject implements Comparable<FibonacciCycleNodeObject> {

    public int distance; // distance from source to this vertex
    public CycleNode node; // vertex name (u_v)

    public FibonacciCycleNodeObject(CycleNode node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(FibonacciCycleNodeObject other) {
        if (distance > other.distance) {
            return +1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "[distance=" + distance + ", node=" + node + "]";
    }
}
