package org.dijkstra.fib.wrapper.heap;

import org.dijkstra.fib.wrapper.FibonacciCycleNodeObject;
import org.dijkstra.node.CycleNode;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

public class Neo4JFibonacciCycleNodeObject extends FibonacciCycleNodeObject {

    public FibonacciHeap<FibonacciCycleNodeObject>.FibonacciHeapNode node;

    public Neo4JFibonacciCycleNodeObject(CycleNode node, int distance) {
        super(node, distance);
    }
}
