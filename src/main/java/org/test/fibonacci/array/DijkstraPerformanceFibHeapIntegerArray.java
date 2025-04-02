package org.test.fibonacci.array;

import org.dijkstra.performance.IntegerPerformanceEnvironment;
import org.dijkstra.performance.IntegerPerformanceTest;
import org.dijkstra.performance.environment.array.randomized.Neo4JFibHeapIntegerArrayRandomizedEnvironment;
import org.dijkstra.performance.environment.array.sequential.Neo4JFibHeapIntegerArraySequentialEnvironment;
import org.dijkstra.performance.environment.set.randomized.Neo4JFibHeapIntegerSetRandomizedEnvironment;
import org.dijkstra.performance.environment.set.sequential.Neo4JFibHeapIntegerSetSequentialEnvironment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class DijkstraPerformanceFibHeapIntegerArray {

	protected double[] parameterizedMeasurementIntegerArray(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		IntegerPerformanceEnvironment environmentSequentialIntegerArray = new Neo4JFibHeapIntegerArraySequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		IntegerPerformanceEnvironment environmentRandomIntegerArray = new Neo4JFibHeapIntegerArrayRandomizedEnvironment(
				size,
				p,
				1,
				new Random(42)
		);

		IntegerPerformanceTest testSequential = new IntegerPerformanceTest(environmentSequentialIntegerArray);
		double mSequential = testSequential.measurement(20, true, false, 3, 3);

		IntegerPerformanceTest testRandom = new IntegerPerformanceTest(environmentRandomIntegerArray);
		double mRandom = testRandom.measurement(20, true, false, 3, 3);

		double[] result = new double[]{ size, p, mSequential, mRandom };

		writeToCSV(result);

		return result;
	}

	private void writeToCSV(double[] data) {
		String CSV_FILE = "src/main/java/org/test/result/Fibonacci_Integer_Array.csv";
		try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
			writer.printf("%d,%.5f,%.5f,%.5f%n", (int) data[0], data[1], data[2], data[3]);
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}

	protected void testPreviousArray(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		IntegerPerformanceEnvironment environmentSequentialIntegerArray = new Neo4JFibHeapIntegerArraySequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		IntegerPerformanceEnvironment environmentRandomIntegerArray = new Neo4JFibHeapIntegerArrayRandomizedEnvironment(
				size,
				p,
				1,
				new Random(42)
		);

		int[] previousSequentialIntegerArray = environmentSequentialIntegerArray.testPrevious(42);
		int[] previousRandomIntegerArray = environmentRandomIntegerArray.testPrevious(42);

		System.out.println(Arrays.toString(previousSequentialIntegerArray));
		System.out.println(Arrays.toString(previousRandomIntegerArray));
	}
}
