package org.dijkstra.performance;

public interface PerformanceEnvironment {
    public void generateGraph();
    public void runShortestPath();
    public int[] testPrevious(int randomSeed);
}
