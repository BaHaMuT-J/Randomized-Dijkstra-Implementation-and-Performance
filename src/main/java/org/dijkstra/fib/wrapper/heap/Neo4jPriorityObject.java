package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.PriorityObject;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

public class Neo4jPriorityObject extends PriorityObject {

    public FibonacciHeap<PriorityObject>.FibonacciHeapNode node;

    public Neo4jPriorityObject(int node, int distance) {
        super(node, distance);
    }

}
