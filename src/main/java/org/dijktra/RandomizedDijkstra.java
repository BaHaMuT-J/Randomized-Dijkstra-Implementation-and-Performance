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
        public boolean equals( Object obj ) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            final NodeWithDistance other = (NodeWithDistance) obj;
            if ( node == null ) {
                if ( other.node != null ) {
                    return false;
                }
            }
            else if ( !node.equals( other.node ) ) {
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
    private Map<String, Set<String>> Bundle;
    private Map<String, Set<String>> Ball;
    Map<String, Integer> d;
    private String s;
    private final FibonacciHeap<NodeWithDistance> fHeap;
    private Map<String, FibonacciHeap.FibonacciHeapNode> heapNodeMap;

    public RandomizedDijkstra() {
        this.random = new Random(42);
        this.Bundle = new HashMap<>();
        this.Ball = new HashMap<>();
        this.d = new HashMap<>();
        this.fHeap = new FibonacciHeap<>(Comparator.comparingInt(NodeWithDistance::getDistance));
        this.heapNodeMap = new HashMap<>();
    }

    public RandomizedDijkstra(IntArrayGraph originalGraph, Integer source) {
        this();
        transform(originalGraph, source);
    }

    public RandomizedDijkstra(int size, Integer source) {
        this();
        transform(IntArrayGraph.getInstance(size), source);
    }

    public void print() {
        graph.print();
    }

    private void transform(IntArrayGraph originalGraph, Integer source) {
        Map<String, ConstantDegreeGraph> m = ConstantDegreeGraph.fromIntArrayGraphAndSource(originalGraph, source);
        Map.Entry<String, ConstantDegreeGraph> entry = m.entrySet().iterator().next();
        s = entry.getKey();
        graph = entry.getValue();
        adjacencyList = graph.getAdjacencyList();
    }

    private void transform(int size, Integer source) {
        transform(IntArrayGraph.getInstance(size), source);
    }

    private double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public Map.Entry<Set<String>, Set<String>> getSetR() {
        Set<String> R = new HashSet<>();
        Set<String> notR = new HashSet<>();
        int n = adjacencyList.size();
        double k = Math.sqrt(log2(n) / log2(log2(n))); // Math.sqrt(Math.log(n) / Math.log(Math.log(n)));

        for (Map.Entry<String, Map<String, Integer>> entry : adjacencyList.entrySet()) {
            String node = entry.getKey();
            if (Objects.equals(node, s) || random.nextDouble() <= 1.0/k) {
                R.add(node);
            } else {
                notR.add(node);
            }
        }

        return Map.entry(R, notR);
    }

    public void formBundleAndBall(Set<String> R, Set<String> notR) {
        for (String u : R) {
            // for every vertex u in R, b(u) = u
            Set<String> bundle = Bundle.getOrDefault(u, new HashSet<>());
            bundle.add(u);
            Bundle.put(u, bundle);
        }

        for (String v : notR) {
            // for every vertex v not in R, run Dijkstra using v as a source
            Map.Entry<Set<String>, String> entry = AdjacencyDijkstra.dijkstraStopR(adjacencyList, v, R);

            // vertex u is closet node in R from v
            String u = entry.getValue();

            // v is bundled to u
            Set<String> bundle = Bundle.getOrDefault(u, new HashSet<>());
            bundle.add(v);
            Bundle.put(entry.getValue(), bundle);

            // for all vertices v meet before u, they are include in ball(v)
            Set<String> verticesReachedBeforeU = entry.getKey();
            Set<String> ball = Ball.getOrDefault(v, new HashSet<>());
            ball.addAll(verticesReachedBeforeU);
            Ball.put(v, ball);
        }
    }

    public void bundleDijkstra() {
        Map.Entry<Set<String>, Set<String>> entryR = getSetR();
        Set<String> R = entryR.getKey();
        Set<String> notR = entryR.getValue();

        formBundleAndBall(R, notR);

        d.put(s, 0);

        for (String u : R) {
            NodeWithDistance nd = new NodeWithDistance(u, (Objects.equals(u, s) ? 0 : Integer.MAX_VALUE));
            FibonacciHeap<NodeWithDistance>.FibonacciHeapNode fNode = fHeap.insert(nd);
            heapNodeMap.put(u, fNode);
        }

//        fHeap.decreaseKey(heapNodeMap.get(s), new NodeWithDistance(s, 0));

        while (!fHeap.isEmpty()) {
            NodeWithDistance nd = fHeap.extractMin();
            String u = nd.getNode();
            int d_u = nd.getDistance();

            for (String v : Bundle.get(u)) {
                relax(v, d_u /* + dist(u, v) */);
                Set<String> ball_v = Ball.get(v);
                for (String y : ball_v) {
                    relax(y, d.getOrDefault(y, Integer.MAX_VALUE) /* + dist(y, v) */);
                }
                ball_v.add(v);
                for (String z2 : ball_v) {
                    for (Map.Entry<String, Integer> entry : adjacencyList
                            .get(z2)
                            .entrySet()) {
                        String z1 = entry.getKey();
                        int w_z1_z2 = entry.getValue();
                        relax(v, d.get(z1) + w_z1_z2 /* + dist(y, z1) */);
                    }
                }
            }

            for (String x : Bundle.get(u)) {
                for (Map.Entry<String, Integer> entry : adjacencyList
                        .get(x)
                        .entrySet()) {
                    String y = entry.getKey();
                    int w_x_y = entry.getValue();
                    relax(y, d.get(y) + w_x_y);
                    for (String z1 : Ball.get(y)) {
                        relax(z1, d.get(x) + w_x_y /* + dist(y, z1) */);
                    }
                }
            }
        }

        // Test
        List<String> sortedR = new ArrayList<>(R);
        Collections.sort(sortedR);
        System.out.println("R is " + sortedR);

        List<String> sortedNotR = new ArrayList<>(notR);
        Collections.sort(sortedNotR);
        System.out.println("notR is " + sortedNotR);

        System.out.println(sortedR.size());
        System.out.println(sortedNotR.size());

        System.out.println("Bundle is " + Bundle);
        System.out.println("Ball is " + Ball);
    }

    private void relax(String node, Integer newDistance) {
    }
}
