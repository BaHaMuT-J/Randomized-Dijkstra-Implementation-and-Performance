package org.jmh.benchmark;

import org.dijktra.AdjacencyDijkstra;
import org.dijktra.RandomizedDijkstra;
import org.graph.ConstantDegreeGraph;
import org.graph.IntArrayGraph;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BenchMarkRunner {

    @Setup(Level.Invocation)
    public static void setUp() {

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public static void test() {

    }

    public static void main(String[] args) {
        // Initialize basic integer graph
        IntArrayGraph graph = IntArrayGraph.getInstance(8);
        graph.print();

        // Test transformation
        Map<String, ConstantDegreeGraph> m = ConstantDegreeGraph.fromIntArrayGraphAndSource(graph, 0);
        Map.Entry<String, ConstantDegreeGraph> firstElt = m.entrySet().iterator().next();
        String s = firstElt.getKey();
        ConstantDegreeGraph cGraph = firstElt.getValue();
        Map<String, Map<String, Integer>> adjacencyList = cGraph.getAdjacencyList();

        // Test sequential Dijkstra
        Map<String, Integer> shortestPaths = AdjacencyDijkstra.dijkstraFib(adjacencyList, s);
        List<String> sortedKeys = new ArrayList<>(shortestPaths.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            System.out.println("Shortest distance to " + key + " is " + shortestPaths.get(key));
        }

        // Construct randomized Dijkstra environment
        RandomizedDijkstra randomizedDijkstra = new RandomizedDijkstra(graph, 0);

        // Test forming set R
        Set<String> R = randomizedDijkstra.getSetR();
        List<String> sortedR = new ArrayList<>(R);
        Collections.sort(sortedR);
        System.out.println("R is " + sortedR);
        System.out.println(sortedKeys.size());
        System.out.println(sortedR.size());
    }
}
