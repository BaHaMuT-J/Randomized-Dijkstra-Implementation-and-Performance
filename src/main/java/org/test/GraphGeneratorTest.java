package org.test;

import org.dijkstra.node.CycleNode;
import org.dijkstra.performance.CycleNodePerformanceEnvironment;
import org.dijkstra.performance.CycleNodePerformanceTest;
import org.dijkstra.performance.IntegerPerformanceEnvironment;
import org.dijkstra.performance.IntegerPerformanceTest;
import org.dijkstra.performance.environment.cycle.sequential.Neo4jFibHeapCycleNodeSequentialEnvironment;
import org.dijkstra.performance.environment.cycle.randomized.Neo4jFibHeapCycleNodeRandomizedEnvironment;
import org.dijkstra.performance.environment.array.Neo4JFibHeapIntegerArraySequentialEnvironmentInteger;
import org.dijkstra.performance.environment.set.Neo4JFibHeapIntegerSetSequentialEnvironmentInteger;

import java.util.*;

public class GraphGeneratorTest {

    private static void parameterizedMeasurement(int size, double p) {
        IntegerPerformanceEnvironment environmentNeo4jPriorityQueue = new Neo4JFibHeapIntegerArraySequentialEnvironmentInteger(size, p, 20, new Random(42));

        int[] pPriorityQueue = environmentNeo4jPriorityQueue.testPrevious(42);
        IntegerPerformanceTest testPriorityQueue = new IntegerPerformanceTest(environmentNeo4jPriorityQueue);
        double mPriorityQueue = testPriorityQueue.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueue));
    }

    private static void parameterizedMeasurementSet(int size, double p) {
        IntegerPerformanceEnvironment environmentNeo4jPriorityQueueSet = new Neo4JFibHeapIntegerSetSequentialEnvironmentInteger(size, p, 20, new Random(42));

        int[] pPriorityQueueSet = environmentNeo4jPriorityQueueSet.testPrevious(42);
        IntegerPerformanceTest testPriorityQueueSet = new IntegerPerformanceTest(environmentNeo4jPriorityQueueSet);
        double mPriorityQueueSet = testPriorityQueueSet.measurement(20, true, false, 3, 3);

        System.out.println(Arrays.toString(pPriorityQueueSet));
    }

    private static void parameterizedMeasurementConstantDegree(int size, double p) {
        CycleNodePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jFibHeapCycleNodeSequentialEnvironment(size, p, 20, new Random(42));

//        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
        CycleNodePerformanceTest constantDegreeTest = new CycleNodePerformanceTest(environmentNeo4jFibHeapConstant);
        double mConstantDegree = constantDegreeTest.measurement(20, true, true, 3, 3);

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
        CycleNodePerformanceEnvironment environmentNeo4jFibHeapConstant = new Neo4jFibHeapCycleNodeRandomizedEnvironment(size, p, 20, new Random(42));

        Map<CycleNode, CycleNode> previousConstantDegreeMap = environmentNeo4jFibHeapConstant.testPrevious(42);
//        CycleNodePerformanceTest constantDegreeTest = new CycleNodePerformanceTest(environmentNeo4jFibHeapConstant);
//        double mConstantDegree = constantDegreeTest.measurement(20, true, true, 3, 3);

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
        int size = 270;
        double p = 0.1;
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

//        GraphGeneratorTest.parameterizedMeasurementConstantDegree(size, p);

        System.out.println("*********************************************************");

        GraphGeneratorTest.parameterizedMeasurementConstantDegreeRandomized(size, p);
    }
}
