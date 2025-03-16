package org.dijktra;

import lombok.Getter;
import org.graph.ConstantDegreeGraph;
import org.graph.IntArrayGraph;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomizedDijkstra {

    @Getter
    protected class HeapObject {
        private final String node;
        private final Integer distance;

        public HeapObject(String node, Integer distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public boolean equals( Object obj )
        {
            if ( this == obj )
            {
                return true;
            }
            if ( obj == null )
            {
                return false;
            }
            if ( getClass() != obj.getClass() )
            {
                return false;
            }
            final HeapObject other = (HeapObject) obj;
            if ( node == null )
            {
                if ( other.node != null )
                {
                    return false;
                }
            }
            else if ( !node.equals( other.node ) )
            {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode()
        {
            return node == null ? 23 : 14 ^ node.hashCode();
        }
    }

    private final Random random;
    private final ConstantDegreeGraph graph;
    private final FibonacciHeap<HeapObject> fHeap;
    private Map<String, FibonacciHeap.FibonacciHeapNode> heapNodeMap;

    public RandomizedDijkstra() {
        this.random = new Random();
        this.graph = ConstantDegreeGraph.fromIntArrayGraph(IntArrayGraph.getInstance(4));
        this.fHeap = new FibonacciHeap<>(Comparator.comparingInt(HeapObject::getDistance));
        this.heapNodeMap = new HashMap<>();
    }

    public void print() {
        graph.print();
    }

    private void relax(String node, Integer newDistance) {
    }
}
