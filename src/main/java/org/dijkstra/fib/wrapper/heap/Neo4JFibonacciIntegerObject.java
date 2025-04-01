package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibonacciIntegerObject;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

public class Neo4JFibonacciIntegerObject extends FibonacciIntegerObject {

    public FibonacciHeap<FibonacciIntegerObject>.FibonacciHeapNode node;

    public Neo4JFibonacciIntegerObject(int node, int distance) {
        super(node, distance);
    }

}
