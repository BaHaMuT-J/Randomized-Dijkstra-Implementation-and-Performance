package org.test;

import org.dijkstra.graph.NeighbourArrayGraphGenerator;
import org.dijkstra.performance.PerformanceEnvironment;
import org.dijkstra.performance.PerformanceTest;
import org.dijkstra.performance.environment.Neo4PriorityQueueEnvironment;

import java.util.Arrays;
import java.util.Random;

public class GraphGeneratorTest {

    private static void parameterizedMeasurement(int size, double p) {
        PerformanceEnvironment environmentNeo4jPriorityQueue = new Neo4PriorityQueueEnvironment(size, p, 20, new Random(42));

        int[] pPriorityQueue = environmentNeo4jPriorityQueue.testPrevious(42);
        PerformanceTest testPriorityQueue = new PerformanceTest(environmentNeo4jPriorityQueue);
        double mPriorityQueue = testPriorityQueue.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueue));

    }

    public static void main(String[] args) {
        NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
        generator.generateRandomGraph(5, 0.5, new Random(42));
        System.out.println(Arrays.deepToString(generator.neighbours));
        System.out.println(Arrays.deepToString(generator.weights));

        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurement(5, 0.5);
    }
}
