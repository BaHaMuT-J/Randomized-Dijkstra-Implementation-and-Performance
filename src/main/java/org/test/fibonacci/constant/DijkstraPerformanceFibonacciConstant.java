package org.test.fibonacci.constant;

import org.dijkstra.performance.ConstantDegreePerformanceTest;
import org.dijkstra.performance.ConstantDegreePerformanceEnvironment;
import org.dijkstra.performance.environment.constant.Neo4jConstantDegreeFibHeapEnvironment;
import org.dijkstra.performance.environment.constant.randomized.Neo4jConstantDegreeFibHeapRandomizedEnvironment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class DijkstraPerformanceFibonacciConstant {

    protected double[] parameterizedMeasurementConstantDegree(int size, double p) {
		System.out.printf("size: %d, p: %f\n", size, p);

		ConstantDegreePerformanceEnvironment environmentSequentialConstant = new Neo4jConstantDegreeFibHeapEnvironment(
				size,
				p,
				1,
				new Random(42));

		ConstantDegreePerformanceEnvironment environmentRandomConstant = new Neo4jConstantDegreeFibHeapRandomizedEnvironment(
				size,
				p,
				1,
				new Random(42)
		);

		ConstantDegreePerformanceTest testSequential = new ConstantDegreePerformanceTest(environmentSequentialConstant);
		double mSequential = testSequential.measurement(20, true, false, 3, 3);

		ConstantDegreePerformanceTest testRandomConstant = new ConstantDegreePerformanceTest(environmentRandomConstant);
		double mRandom = testRandomConstant.measurement(20, true, false, 3, 3);

		double[] result = new double[]{ size, p, mSequential, mRandom };

		writeToCSV(result);

		return result;
	}

	private void writeToCSV(double[] data) {
        String CSV_FILE = "src/main/java/org/test/result/Fibonacci_Constant.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
			writer.printf("%d,%.5f,%.5f,%.5f%n", (int) data[0], data[1], data[2], data[3]);
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}
}
