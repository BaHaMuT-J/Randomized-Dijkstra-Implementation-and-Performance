package org.test.fibonacci.cycle;

public class FibHeapCycleNodeGraphAnalysisMain extends DijkstraPerformanceFibHeapCycleNode {
	
	public static void main(String[] args) {
		new FibHeapCycleNodeGraphAnalysisMain().run();
	}
	
	private void run() {
//		runWithConstantP(0.1);
//		runWithConstantP(0.3);
		runWithConstantP(0.5);
//		runWithConstantP(0.7);
//		runWithConstantP(0.9);
	}
	
	private void runWithConstantP(double p) {
		int n = 99;
		
		double[][] results = new double[n][];
		for (int i = 0; i < n; ++i) {
			results[i] = parameterizedMeasurementConstantDegree(10 + 10 * i, p);
		}
		for (int i = 0; i < n; ++i) {
			if (results[i] == null) {
				continue;
			}
			for (int j = 0; j < results[i].length; ++j) {
				System.out.print(results[i][j]);
				System.out.print(",");
			}
			System.out.println();
		}
		
	}

}
