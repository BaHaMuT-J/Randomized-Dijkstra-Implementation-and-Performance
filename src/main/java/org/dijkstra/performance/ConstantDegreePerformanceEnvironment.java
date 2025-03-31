package org.dijkstra.performance;

import org.dijkstra.node.CycleNode;

import java.util.Map;

public interface ConstantDegreePerformanceEnvironment {
    public void generateGraph();
    public void runShortestPath();
    public Map<CycleNode, CycleNode> testPrevious(int randomSeed);
}
