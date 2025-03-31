package org.dijkstra.graph;

import org.dijkstra.node.CycleNode;

import java.util.*;

public class ConstantDegreeGraph {
    public Set<CycleNode> nodes;
    public Map<CycleNode, Set<CycleNode>> neighbours;
    public Map<CycleNode, Map<CycleNode, Integer>> weights;

    public void transformGraph(NeighbourSetGraphGenerator generatedGraph) {
        Map<Integer, Set<Integer>> originalNeighbours = generatedGraph.neighbours;
        Map<Integer, Map<Integer, Integer>> originalWeights = generatedGraph.weights;

        generateCycleNodesAndNeighbours(originalNeighbours);

        generateWeights(originalWeights);

        // Test generateCycleNodesAndNeighbours
        List<CycleNode> sortedList = new ArrayList<>(nodes);
        Collections.sort(sortedList);
        for (CycleNode cycleNode : sortedList) {
            System.out.println(cycleNode);
            System.out.println(neighbours.get(cycleNode));
            System.out.println(weights.get(cycleNode));
        }
    }

    private void generateCycleNodesAndNeighbours(Map<Integer, Set<Integer>> originalNeighbours) {
        nodes = new HashSet<>();
        neighbours = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> entry : originalNeighbours.entrySet()) {
            Integer OV = entry.getKey();
            CycleNode firstNode = null;
            CycleNode previous = null;

            Set<Integer> originalSet = entry.getValue();
            for (Integer ON : originalSet) {
                CycleNode cycleNode = new CycleNode(OV, ON);
                nodes.add(cycleNode);
                addReversedCycleNodes(cycleNode);

                // Form a cycle by linking this node with previous node
                if (previous != null) {
                    addNeighbors(previous, cycleNode);
                } else {
                    firstNode = cycleNode;
                }
                previous = cycleNode;
            }

            // If there are >1 nodes in this cycle, link the last node with the first node
            if (originalSet.size() > 1) {
                addNeighbors(previous, firstNode);
            }
        }
    }

    private void addReversedCycleNodes(CycleNode cycleNode) {
        CycleNode reversed = new CycleNode(cycleNode.ON, cycleNode.OV);
        Set<CycleNode> neighbors = new HashSet<>();
        neighbors.add(reversed);
        neighbours.put(cycleNode, neighbors);
    }

    private void addNeighbors(CycleNode u, CycleNode v) {
        Set<CycleNode> neighbors = neighbours.get(u);
        neighbors.add(v);
        neighbours.put(u, neighbors);

        neighbors = neighbours.get(v);
        neighbors.add(u);
        neighbours.put(v, neighbors);
    }

    private void generateWeights(Map<Integer, Map<Integer, Integer>> originalWeights) {
        weights = new HashMap<>();
        for (CycleNode u : nodes) {
            Map<CycleNode, Integer> weightsU = new HashMap<>();
            for (CycleNode v : neighbours.get(u)) {
                if (u.OV == v.OV) {
                    weightsU.put(v, 0);
                } else {
                    weightsU.put(v, originalWeights.get(u.OV).get(v.OV));
                }
            }
            weights.put(u, weightsU);
        }
    }
}
