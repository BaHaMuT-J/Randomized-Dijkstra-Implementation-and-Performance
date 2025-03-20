package fail.org.jmh.benchmark;

import fail.org.dijktra.AdjacencyDijkstra;
import fail.org.dijktra.RandomizedDijkstra;
import fail.org.graph.ConstantDegreeGraph;
import fail.org.graph.IntArrayGraph;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BenchMarkRunner {

    private static Map<String, Map<String, Integer>> adjacencyList;
    private static String s;
    private static RandomizedDijkstra randomizedDijkstra;

    @Setup(Level.Invocation)
    public static void setUp() {
        // Initialize basic integer graph
        IntArrayGraph graph = IntArrayGraph.getInstance(1024);

        // Transformation and set up sequential Dijkstra environment
        Map<String, ConstantDegreeGraph> m = ConstantDegreeGraph.fromIntArrayGraphAndSource(graph, 0);
        Map.Entry<String, ConstantDegreeGraph> firstElt = m.entrySet().iterator().next();
        s = firstElt.getKey();
        ConstantDegreeGraph cGraph = firstElt.getValue();
        adjacencyList = cGraph.getAdjacencyList();

        // Construct randomized Dijkstra environment
        randomizedDijkstra = new RandomizedDijkstra(graph, 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public static void testSequentialDijkstra() {
        AdjacencyDijkstra.dijkstra(adjacencyList, s); // 1316468880
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public static void testBunDleDijkstra() {
        randomizedDijkstra.bundleDijkstra(); // 54921.932
    }

    public static void main(String[] args) {
        // Initialize basic integer graph
        IntArrayGraph graph = IntArrayGraph.getInstance(1024);
//        graph.print();

        // Test transformation
        Map<String, ConstantDegreeGraph> m = ConstantDegreeGraph.fromIntArrayGraphAndSource(graph, 0);
        Map.Entry<String, ConstantDegreeGraph> firstElt = m.entrySet().iterator().next();
        String s = firstElt.getKey();
        ConstantDegreeGraph cGraph = firstElt.getValue();
        Map<String, Map<String, Integer>> adjacencyList = cGraph.getAdjacencyList();

        // Test sequential Dijkstra
//        Map<String, Integer> shortestPaths = AdjacencyDijkstra.dijkstraFib(adjacencyList, s);
//        List<String> sortedKeys = new ArrayList<>(shortestPaths.keySet());
//        Collections.sort(sortedKeys);
//        for (String key : sortedKeys) {
//            System.out.println("Shortest distance to " + key + " is " + shortestPaths.get(key));
//        }

        // Construct randomized Dijkstra environment
        RandomizedDijkstra randomizedDijkstra = new RandomizedDijkstra(graph, 0);

        // Test bundleDijkstra
        randomizedDijkstra.bundleDijkstra();
        System.out.println();
        randomizedDijkstra.print();
    }
}
