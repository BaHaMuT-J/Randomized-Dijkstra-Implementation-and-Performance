package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibonacciObject;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

public class Neo4jFibonacciObject extends FibonacciObject {

    public FibonacciHeap<FibonacciObject>.FibonacciHeapNode node;

    public Neo4jFibonacciObject(String node, int distance) {
        super(node, distance);
    }
}
