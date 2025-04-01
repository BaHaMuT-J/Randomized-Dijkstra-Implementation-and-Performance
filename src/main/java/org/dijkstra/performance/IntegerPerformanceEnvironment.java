package org.dijkstra.performance;

public interface IntegerPerformanceEnvironment {
    public void generateGraph();
    public void runShortestPath();
    public int[] testPrevious(int randomSeed);
}
