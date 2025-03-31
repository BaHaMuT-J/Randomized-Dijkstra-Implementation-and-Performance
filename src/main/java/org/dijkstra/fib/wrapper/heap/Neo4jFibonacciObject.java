package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibonacciObject;
import org.dijkstra.node.CycleNode;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

public class Neo4jFibonacciObject extends FibonacciObject {

    public FibonacciHeap<FibonacciObject>.FibonacciHeapNode node;

    public Neo4jFibonacciObject(CycleNode node, int distance) {
        super(node, distance);
    }
}
