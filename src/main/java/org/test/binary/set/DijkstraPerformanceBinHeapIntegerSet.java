package org.test.binary.set;

import org.dijkstra.performance.IntegerPerformanceEnvironment;
import org.dijkstra.performance.IntegerPerformanceTest;
import org.dijkstra.performance.environment.binary.array.randomized.BinHeapIntegerArrayRandomizedEnvironment;
import org.dijkstra.performance.environment.binary.array.sequential.BinHeapIntegerArraySequentialEnvironment;
import org.dijkstra.performance.environment.binary.set.randomized.BinHeapIntegerSetRandomizedEnvironment;
import org.dijkstra.performance.environment.binary.set.sequential.BinHeapIntegerSetSequentialEnvironment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class DijkstraPerformanceBinHeapIntegerSet {

	protected double[] parameterizedMeasurementIntegerArray(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		IntegerPerformanceEnvironment environmentSequentialIntegerSet = new BinHeapIntegerSetSequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		IntegerPerformanceEnvironment environmentRandomIntegerSet = new BinHeapIntegerSetRandomizedEnvironment(
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
		String CSV_FILE = "src/main/java/org/test/result/Binary_Integer_Set.csv";
		try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
			writer.printf("%d,%.5f,%.5f,%.5f%n", (int) data[0], data[1], data[2], data[3]);
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}

	protected void testPreviousArray(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		IntegerPerformanceEnvironment environmentSequentialIntegerArray = new BinHeapIntegerArraySequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		IntegerPerformanceEnvironment environmentRandomIntegerArray = new BinHeapIntegerArrayRandomizedEnvironment(
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
