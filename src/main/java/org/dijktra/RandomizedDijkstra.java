package org.dijktra;

import lombok.Getter;
import org.graph.ConstantDegreeGraph;
import org.graph.IntArrayGraph;
import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.*;

public class RandomizedDijkstra {

    @Getter
    protected class NodeWithDistance {
        private final String node;
        private final Integer distance;

        public NodeWithDistance(String node, Integer distance) {
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
            final NodeWithDistance other = (NodeWithDistance) obj;
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
    private ConstantDegreeGraph graph;
    private Map<String, Map<String, Integer>> adjacencyList;
    private String s;
    private final FibonacciHeap<NodeWithDistance> fHeap;
    private Map<String, FibonacciHeap.FibonacciHeapNode> heapNodeMap;

    public RandomizedDijkstra() {
        this.random = new Random();
        this.fHeap = new FibonacciHeap<>(Comparator.comparingInt(NodeWithDistance::getDistance));
        this.heapNodeMap = new HashMap<>();
    }

    public void print() {
        graph.print();
    }

    public void transform(IntArrayGraph originalGraph, Integer source) {
        Map<String, ConstantDegreeGraph> m = ConstantDegreeGraph.fromIntArrayGraphAndSource(originalGraph, source);
        Map.Entry<String, ConstantDegreeGraph> entry = m.entrySet().iterator().next();
        s = entry.getKey();
        graph = entry.getValue();
        adjacencyList = graph.getAdjacencyList();
    }

    public void transform(int size, Integer source) {
        transform(IntArrayGraph.getInstance(size), source);
    }

    private Set<String> getSetR() {
        Set<String> R = new HashSet<>();
        int n = adjacencyList.size();
        double k = Math.sqrt(Math.log(n) / Math.log(Math.log(n)));

        for (Map.Entry<String, Map<String, Integer>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            if (Objects.equals(node, s) || random.nextDouble() > 1.0/k) {
                continue;
            }
            R.add(node);
        }

        R.add(s);
        return R;
    }

    public void bundleDijkstra() {
    }

    private void relax(String node, Integer newDistance) {
    }
}
