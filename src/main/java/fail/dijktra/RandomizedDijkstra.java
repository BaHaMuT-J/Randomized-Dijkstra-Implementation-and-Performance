package fail.dijktra;

import lombok.Getter;
import fail.graph.ConstantDegreeGraph;
import fail.graph.IntArrayGraph;
import fail.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.*;

public class RandomizedDijkstra {

    @Getter
    protected static class NodeWithDistance {
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
    private final Map<String, String> b;
    private final Map<String, Set<String>> Bundle;
    private final Map<String, Set<String>> Ball;
    private final Map<String, Integer> d;
    private final Map<String, Map<String, Integer>> dist;
    private String s;
    private final FibonacciHeap<NodeWithDistance> fHeap;
    private final Map<String, FibonacciHeap<NodeWithDistance>.FibonacciHeapNode> heapNodeMap;

    public RandomizedDijkstra() {
        this.random = new Random(42);
        this.b = new HashMap<>();
        this.Bundle = new HashMap<>();
        this.Ball = new HashMap<>();
        this.d = new HashMap<>();
        this.dist = new HashMap<>();
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
        List<String> sortedKeys = new ArrayList<>(d.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            System.out.println("Shortest distance to " + key + " is " + d.get(key));
        }
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
            b.put(u, u);

            // add u in Bundle(u)
            Set<String> bundle = Bundle.getOrDefault(u, new HashSet<>());
            bundle.add(u);
            Bundle.put(u, bundle);

            // distance for vertices in R need only itself
            Map<String, Integer> dist_u = new HashMap<>();
            dist_u.put(u, 0);
            dist.put(u, dist_u);
        }

        for (String v : notR) {
            // for every vertex v not in R, run Dijkstra using v as a source
            Map.Entry<Map.Entry<Set<String>, String>, Map<String, Integer>> bigEntry = AdjacencyDijkstra.dijkstraStopR(adjacencyList, v, R);
            Map.Entry<Set<String>, String> entry = bigEntry.getKey();

            // vertex u is closet node in R from v
            String u = entry.getValue();
            b.put(v, u);

            // v is bundled to u
            Set<String> bundle = Bundle.getOrDefault(u, new HashSet<>());
            bundle.add(v);
            Bundle.put(u, bundle);

            // for all vertices v meet before u, they are include in Ball(v)
            Set<String> verticesReachedBeforeU = entry.getKey();
            Ball.put(v, verticesReachedBeforeU);

            // distance from vertex v to each vertex in Ball(v)
            dist.put(v, bigEntry.getValue());
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

        while (!fHeap.isEmpty()) {
            NodeWithDistance nd = fHeap.extractMin();
            String u = nd.getNode();
            int d_u = nd.getDistance();
            heapNodeMap.remove(u);
            System.out.println("Extracted " + u);

            for (String v : Bundle.getOrDefault(u, new HashSet<>())) {
                Map<String, Integer> dist_v = dist.get(v);
                relax(v, d_u + dist_v.get(u), R);
                Set<String> ball_v = Ball.getOrDefault(v, new HashSet<>());
                for (String y : ball_v) {
                    int newDistance = !d.containsKey(y) ?
                            Integer.MAX_VALUE
                            : d.get(y) + dist_v.get(y);
                    relax(v, newDistance, R);
                }
                ball_v.add(v);
                for (String z2 : ball_v) {
                    for (Map.Entry<String, Integer> entry : adjacencyList
                            .get(z2)
                            .entrySet()) {
                        String z1 = entry.getKey();
                        int w_z1_z2 = entry.getValue();
                        int newDistance = !d.containsKey(z1) ?
                                Integer.MAX_VALUE
                                : d.get(z1) + w_z1_z2 + dist_v.get(z2);
                        relax(v, newDistance, R);
                    }
                }
                ball_v.remove(v);
            }

            for (String x : Bundle.getOrDefault(u, new HashSet<>())) {
                for (Map.Entry<String, Integer> entry : adjacencyList
                        .get(x)
                        .entrySet()) {
                    String y = entry.getKey();
                    int w_x_y = entry.getValue();
                    int newDistance = !d.containsKey(x) ?
                            Integer.MAX_VALUE
                            : d.get(x) + w_x_y;
                    relax(y, newDistance, R);
                    for (String z1 : Ball.getOrDefault(y, new HashSet<>())) {
                        newDistance = !d.containsKey(x) ?
                                Integer.MAX_VALUE
                                : d.get(x) + w_x_y + dist.get(y).get(z1);
                        relax(z1, newDistance, R);
                    }
                }
            }
        }
    }

    private void relax(String v, Integer D, Set<String> R) {
        if (D < d.getOrDefault(v, Integer.MAX_VALUE)) {
            d.put(v, D);
            if (heapNodeMap.containsKey(v)) {
                fHeap.decreaseKey(heapNodeMap.get(v), new NodeWithDistance(v, D));
            } else if (!R.contains(v)) {
                int newDistance = !d.containsKey(v) ?
                        Integer.MAX_VALUE
                        : d.get(v) + dist.get(v).get(b.get(v));
                relax(b.get(v), newDistance, R);
            }
        }
    }
}
