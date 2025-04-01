package org.test.fibonacci.set;

public class FibHeapIntegerSetGraphAnalysisMain extends DijkstraPerformanceFibHeapIntegerSet {
	
	public static void main(String[] args) {
		new FibHeapIntegerSetGraphAnalysisMain().run();
	}
	
	private void run() {
//		runWithConstantP(0.1);
//		runWithConstantP(0.3);
		runWithConstantP(0.5);
//		runWithConstantP(0.7);
//		runWithConstantP(0.9);
	}
	
	private void runWithConstantP(double p) {
		int n = 100;

		double[][] results = new double[n][];
		for (int i = 1; i <= n; ++i) {
			results[i-1] = parameterizedMeasurementIntegerSet(10 * i, p);
		}
//		for (int i = 0; i < n; ++i) {
//			if (results[i] == null) {
//				continue;
//			}
//			for (int j = 0; j < results[i].length; ++j) {
//				System.out.print(results[i][j]);
//				System.out.print(",");
//			}
//			System.out.println();
//		}
		
	}

}
