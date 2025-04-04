package org.test.binary.cycle;

import org.dijkstra.performance.CycleNodePerformanceEnvironment;
import org.dijkstra.performance.CycleNodePerformanceTest;
import org.dijkstra.performance.environment.binary.cycle.randomized.BinHeapCycleNodeRandomizedEnvironment;
import org.dijkstra.performance.environment.binary.cycle.sequential.BinHeapCycleNodeSequentialEnvironment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class DijkstraPerformanceBinHeapCycleNode {

    protected double[] parameterizedMeasurementConstantDegree(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		CycleNodePerformanceEnvironment environmentSequentialConstant = new BinHeapCycleNodeSequentialEnvironment(
				size,
				p,
				1,
				new Random(42));

		CycleNodePerformanceEnvironment environmentRandomConstant = new BinHeapCycleNodeRandomizedEnvironment(
				size,
				p,
				1,
				new Random(42)
		);

		CycleNodePerformanceTest testSequential = new CycleNodePerformanceTest(environmentSequentialConstant);
		double mSequential = testSequential.measurement(10, true, false, 2, 2);

		CycleNodePerformanceTest testRandomConstant = new CycleNodePerformanceTest(environmentRandomConstant);
		double mRandom = testRandomConstant.measurement(10, true, false, 2, 2);

		double[] result = new double[]{ size, p, mSequential, mRandom };

//		writeToCSV(result);

		return result;
	}

	private void writeToCSV(double[] data) {
        String CSV_FILE = "src/main/java/org/test/result/Binary_CycleNode.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
			writer.printf("%d,%.5f,%.5f,%.5f%n", (int) data[0], data[1], data[2], data[3]);
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}
