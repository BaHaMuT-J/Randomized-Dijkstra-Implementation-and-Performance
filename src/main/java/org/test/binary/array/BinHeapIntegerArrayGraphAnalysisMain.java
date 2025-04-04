package org.test.binary.array;

public class BinHeapIntegerArrayGraphAnalysisMain extends DijkstraPerformanceBinHeapIntegerArray {

	public static void main(String[] args) {
		new BinHeapIntegerArrayGraphAnalysisMain().run();
	}

	private void run() {
		runWithConstantP(0.1);
		runWithConstantP(0.3);
		runWithConstantP(0.5);
		runWithConstantP(0.7);
		runWithConstantP(0.9);
	}

	private void runWithConstantP(double p) {
		int n = 100;

		double[][] results = new double[n][];
		for (int i = 1; i <= n; ++i) {
			results[i-1] = parameterizedMeasurementIntegerArray(10 * i, p);
		}

	}

}
