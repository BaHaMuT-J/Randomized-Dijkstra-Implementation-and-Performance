package org.dijkstra.performance;

import java.util.Arrays;

public class ConstantDegreePerformanceTest {

    private final ConstantDegreePerformanceEnvironment environment;

    public long[] startTimes;
    public long[] graphGenerationTimes;
    public long[] endTimes;

    public ConstantDegreePerformanceTest(ConstantDegreePerformanceEnvironment environment) {
        this.environment = environment;
    }

    public double measurement(int repeats, boolean printAverageTimes, boolean printOutInnerResults, int skipLow, int skipHigh) {
        startTimes = new long[repeats];
        graphGenerationTimes = new long[repeats];
        endTimes = new long[repeats];

        for (int i = 0; i < repeats; ++i) {
            startTimes[i] = System.nanoTime();
            environment.generateGraph();
            graphGenerationTimes[i] = System.nanoTime();
            environment.runShortestPath();
            endTimes[i] = System.nanoTime();
        }

        double[] times = new double[repeats];

        double averageShortestPathTime = 0.0;
        for (int i = 0; i < repeats; ++i) {
            double time = (endTimes[i] - graphGenerationTimes[i])/1000000.0;
            averageShortestPathTime += time;
            times[i] = time;
            if (printOutInnerResults) {
                System.out.println("" + i + ". run: " + startTimes[i] + "," + graphGenerationTimes[i] + "," + endTimes[i] + "->" + (endTimes[i] - graphGenerationTimes[i])/1000000.0);
            }
        }
        averageShortestPathTime /= (double)repeats;

        Arrays.sort(times);
//        System.out.println("sorted times: " + Arrays.toString(times));

        double averageShortestPathWithoutExrtremes = 0.0;
        for (int i = skipLow; i < repeats - skipHigh; ++i) {
            averageShortestPathWithoutExrtremes += times[i];
        }
        averageShortestPathWithoutExrtremes /= (double)(repeats - skipHigh - skipLow);

        if (printAverageTimes) {
            System.out.println("AverageShortestPathTime: " + averageShortestPathTime + " seconds");
            System.out.println("AverageShortestPathTimeWithoutExtremes: " + averageShortestPathWithoutExrtremes + " seconds");
        }

        return averageShortestPathWithoutExrtremes;
    }
}
