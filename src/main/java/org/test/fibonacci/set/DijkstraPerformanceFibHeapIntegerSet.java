package org.test.fibonacci.set;

import org.dijkstra.performance.CycleNodePerformanceEnvironment;
import org.dijkstra.performance.CycleNodePerformanceTest;
import org.dijkstra.performance.IntegerPerformanceEnvironment;
import org.dijkstra.performance.IntegerPerformanceTest;
import org.dijkstra.performance.environment.cycle.randomized.Neo4jFibHeapCycleNodeRandomizedEnvironment;
import org.dijkstra.performance.environment.cycle.sequential.Neo4jFibHeapCycleNodeSequentialEnvironment;
import org.dijkstra.performance.environment.set.randomized.Neo4JFibHeapIntegerSetRandomizedEnvironment;
import org.dijkstra.performance.environment.set.sequential.Neo4JFibHeapIntegerSetSequentialEnvironment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class DijkstraPerformanceFibHeapIntegerSet {

    protected double[] parameterizedMeasurementIntegerSet(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		IntegerPerformanceEnvironment environmentSequentialIntegerSet = new Neo4JFibHeapIntegerSetSequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		IntegerPerformanceEnvironment environmentRandomIntegerSet = new Neo4JFibHeapIntegerSetRandomizedEnvironment(
				size,
				p,
				1,
				new Random(42)
		);

		IntegerPerformanceTest testSequential = new IntegerPerformanceTest(environmentSequentialIntegerSet);
		double mSequential = testSequential.measurement(20, true, false, 3, 3);

		IntegerPerformanceTest testRandom = new IntegerPerformanceTest(environmentRandomIntegerSet);
		double mRandom = testRandom.measurement(20, true, false, 3, 3);

		double[] result = new double[]{ size, p, mSequential, mRandom };

		writeToCSV(result);

		return result;
	}

	private void writeToCSV(double[] data) {
        String CSV_FILE = "src/main/java/org/test/result/Fibonacci_Integer_Set.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
			writer.printf("%d,%.5f,%.5f,%.5f%n", (int) data[0], data[1], data[2], data[3]);
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}
