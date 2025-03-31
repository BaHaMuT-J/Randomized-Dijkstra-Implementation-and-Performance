package org.test;

import org.dijkstra.graph.ConstantDegreeGraph;
import org.dijkstra.graph.NeighbourArrayGraphGenerator;
import org.dijkstra.graph.NeighbourSetGraphGenerator;
import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.ConstantDegreePerformanceEnvironment;
import org.dijkstra.performance.ConstantDegreePerformanceTest;
import org.dijkstra.performance.PerformanceEnvironment;
import org.dijkstra.performance.PerformanceTest;
import org.dijkstra.performance.environment.Neo4jConstantDegreeFibHeapEnvironment;
import org.dijkstra.performance.environment.Neo4jConstantDegreeFibHeapRandomizedEnvironment;
import org.dijkstra.performance.environment.Neo4jPriorityQueueEnvironment;
import org.dijkstra.performance.environment.Neo4jSetPriorityQueueEnvironment;

import java.util.*;

public class GraphGeneratorTest {

    private static void parameterizedMeasurement(int size, double p) {
        PerformanceEnvironment environmentNeo4jPriorityQueue = new Neo4jPriorityQueueEnvironment(size, p, 20, new Random(42));

        int[] pPriorityQueue = environmentNeo4jPriorityQueue.testPrevious(42);
        PerformanceTest testPriorityQueue = new PerformanceTest(environmentNeo4jPriorityQueue);
        double mPriorityQueue = testPriorityQueue.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueue));
    }

    private static void parameterizedMeasurementSet(int size, double p) {
        PerformanceEnvironment environmentNeo4jPriorityQueueSet = new Neo4jSetPriorityQueueEnvironment(size, p, 20, new Random(42));

        int[] pPriorityQueueSet = environmentNeo4jPriorityQueueSet.testPrevious(42);
        PerformanceTest testPriorityQueueSet = new PerformanceTest(environmentNeo4jPriorityQueueSet);
        double mPriorityQueueSet = testPriorityQueueSet.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueueSet));
    }

    private static void parameterizedMeasurementConstantDegree(int size, double p) {
        ConstantDegreePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jConstantDegreeFibHeapEnvironment(size, p, 20, new Random(42));

        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
        ConstantDegreePerformanceTest constantDegreeTest = new ConstantDegreePerformanceTest(environmentNeo4jFibHeapConstant);
        double mConstantDegree = constantDegreeTest.measurement(20, true, false, 3, 3);

//        System.out.println("*********************************************************");
//
//        // Test previous map
//        List<CycleNode> sortedList = new ArrayList<>(previousConstantDegreeMap.keySet());
//        Collections.sort(sortedList);
//        for (CycleNode cycleNode : sortedList) {
//            System.out.println(cycleNode + " " + previousConstantDegreeMap.get(cycleNode));
//        }
    }

    private static void parameterizedMeasurementConstantDegreeRandomized(int size, double p) {
        ConstantDegreePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jConstantDegreeFibHeapRandomizedEnvironment(size, p, 20, new Random(42));

        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
        ConstantDegreePerformanceTest constantDegreeTest = new ConstantDegreePerformanceTest(environmentNeo4jFibHeapConstant);
        double mConstantDegree = constantDegreeTest.measurement(20, true, false, 3, 3);

//        System.out.println("*********************************************************");
//
//        // Test previous map
//        List<CycleNode> sortedList = new ArrayList<>(previousConstantDegreeMap.keySet());
//        Collections.sort(sortedList);
//        for (CycleNode cycleNode : sortedList) {
//            System.out.println(cycleNode + " " + previousConstantDegreeMap.get(cycleNode));
//        }
    }

    public static void main(String[] args) {
        int size = 20;
        double p = 0.5;
        System.out.println("size : " + size + " | p : " + p);

//        NeighbourArrayGraphGenerator generator = new NeighbourArrayGraphGenerator();
//        generator.generateRandomGraph(10, 0.5, new Random(42));
//        System.out.println(Arrays.deepToString(generator.neighbours));
//        System.out.println(Arrays.deepToString(generator.weights));
//
//        System.out.println("*********************************************************");

//        GraphGeneratorTest.parameterizedMeasurement(10, 0.5);
//
//        System.out.println("*********************************************************");

//        NeighbourSetGraphGenerator generator2 = new NeighbourSetGraphGenerator();
//        generator2.generateRandomGraph(10, 0.5, new Random(42));
//        System.out.println(generator2.neighbours);
//        System.out.println(generator2.weights);
//
//        System.out.println("*********************************************************");

//        GraphGeneratorTest.parameterizedMeasurementSet(10, 0.5);
//
//        System.out.println("*********************************************************");

//        NeighbourSetGraphGenerator generator2 = new NeighbourSetGraphGenerator();
//        generator2.generateRandomGraph(10, 0.5, new Random(42));
//        System.out.println(generator2.neighbours);
//        System.out.println(generator2.weights);
//
//        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurementConstantDegree(size, p);

        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurementConstantDegreeRandomized(size, p);
    }
}
