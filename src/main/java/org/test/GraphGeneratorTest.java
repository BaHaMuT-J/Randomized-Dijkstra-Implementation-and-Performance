package org.test;

import org.dijkstra.algo.fibonacci.randomized.FibHeapIntegerSetRandomizedDijkstra;
import org.dijkstra.algo.fibonacci.sequential.FibHeapIntegerSetSequentialDijkstra;
import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.CycleNodePerformanceEnvironment;
import org.dijkstra.performance.CycleNodePerformanceTest;
import org.dijkstra.performance.IntegerPerformanceEnvironment;
import org.dijkstra.performance.IntegerPerformanceTest;
import org.dijkstra.performance.environment.cycle.sequential.Neo4jFibHeapCycleNodeSequentialEnvironment;
import org.dijkstra.performance.environment.cycle.randomized.Neo4jFibHeapCycleNodeRandomizedEnvironment;
import org.dijkstra.performance.environment.array.sequential.Neo4JFibHeapIntegerArraySequentialEnvironment;
import org.dijkstra.performance.environment.set.randomized.Neo4JFibHeapIntegerSetRandomizedEnvironment;
import org.dijkstra.performance.environment.set.sequential.Neo4JFibHeapIntegerSetSequentialEnvironment;

import java.util.*;

public class GraphGeneratorTest {

    private static void parameterizedMeasurement(int size, double p) {
        IntegerPerformanceEnvironment environmentNeo4jPriorityQueue = new Neo4JFibHeapIntegerArraySequentialEnvironment(size, p, 20, new Random(42));

        int[] pPriorityQueue = environmentNeo4jPriorityQueue.testPrevious(42);
        IntegerPerformanceTest testPriorityQueue = new IntegerPerformanceTest(environmentNeo4jPriorityQueue);
        double mPriorityQueue = testPriorityQueue.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueue));
    }

    private static void parameterizedMeasurementIntegerSetSequential(int size, double p) {
        IntegerPerformanceEnvironment environmentNeo4jPriorityQueueSet = new Neo4JFibHeapIntegerSetSequentialEnvironment(size, p, 1, new Random(42));

        int[] pPriorityQueueSet = environmentNeo4jPriorityQueueSet.testPrevious(42);
//        IntegerPerformanceTest testPriorityQueueSet = new IntegerPerformanceTest(environmentNeo4jPriorityQueueSet);
//        double mPriorityQueueSet = testPriorityQueueSet.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueueSet));

        int[][] previous = new int[pPriorityQueueSet.length][];
        for (int i = 0; i < pPriorityQueueSet.length; i++) {
            previous[i] = FibHeapIntegerSetSequentialDijkstra.shortestPath(pPriorityQueueSet, i);
        }
        System.out.println(Arrays.deepToString(previous));
    }

    private static void parameterizedMeasurementIntegerSetRandomized(int size, double p) {
        IntegerPerformanceEnvironment environmentNeo4jPriorityQueueSet = new Neo4JFibHeapIntegerSetRandomizedEnvironment(size, p, 1, new Random(42));

        int[] pPriorityQueueSet = environmentNeo4jPriorityQueueSet.testPrevious(42);
//        IntegerPerformanceTest testPriorityQueueSet = new IntegerPerformanceTest(environmentNeo4jPriorityQueueSet);
//        double mPriorityQueueSet = testPriorityQueueSet.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueueSet));

        int[][] previous = new int[pPriorityQueueSet.length][];
        for (int i = 0; i < pPriorityQueueSet.length; i++) {
            previous[i] = FibHeapIntegerSetRandomizedDijkstra.shortestPath(pPriorityQueueSet, i);
        }
        System.out.println(Arrays.deepToString(previous));
    }

    private static void parameterizedMeasurementCycleNodeSequential(int size, double p) {
        CycleNodePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jFibHeapCycleNodeSequentialEnvironment(size, p, 20, new Random(42));

        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
//        CycleNodePerformanceTest constantDegreeTest = new CycleNodePerformanceTest(environmentNeo4jFibHeapConstant);
//        double mConstantDegree = constantDegreeTest.measurement(20, true, true, 3, 3);

        System.out.println("*********************************************************");

        // Test previous map
        List<CycleNode> sortedList = new ArrayList<>(previousConstantDegreeMap.keySet());
        Collections.sort(sortedList);
        for (CycleNode cycleNode : sortedList) {
            System.out.println(cycleNode + " " + previousConstantDegreeMap.get(cycleNode));
        }
    }

    private static void parameterizedMeasurementCycleNodeRandomized(int size, double p) {
        CycleNodePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jFibHeapCycleNodeRandomizedEnvironment(size, p, 20, new Random(42));

        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
//        CycleNodePerformanceTest constantDegreeTest = new CycleNodePerformanceTest(environmentNeo4jFibHeapConstant);
//        double mConstantDegree = constantDegreeTest.measurement(20, true, true, 3, 3);

        System.out.println("*********************************************************");

        // Test previous map
        List<CycleNode> sortedList = new ArrayList<>(previousConstantDegreeMap.keySet());
        Collections.sort(sortedList);
        for (CycleNode cycleNode : sortedList) {
            System.out.println(cycleNode + " " + previousConstantDegreeMap.get(cycleNode));
        }
    }

    public static void main(String[] args) {
        int size = 10;
        double p = 0.5;
        System.out.println("size : " + size + " | p : " + p);

//        GraphGeneratorTest.parameterizedMeasurement(10, 0.5);
//
//        System.out.println("*********************************************************");

//        GraphGeneratorTest.parameterizedMeasurementIntegerSetSequential(10, 0.5);
//
//        System.out.println("*********************************************************");
//
//        GraphGeneratorTest.parameterizedMeasurementIntegerSetRandomized(10, 0.5);
//
//        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurementCycleNodeSequential(size, p);

        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurementCycleNodeRandomized(size, p);
    }
}
