package org.dijkstra.graph;

import java.util.*;

public class ConstantDegreeGraph {
    public int[][] neighbours;
    public int[][] weights;

    public void transformGraph(NeighbourArrayGraphGenerator originalGraph) {
        int n = originalGraph.neighbours.length;
        List<int[]> newEdges = new ArrayList<>();
        List<Integer> newWeights = new ArrayList<>();
        Map<Integer, List<Integer>> vertexMapping = new HashMap<>();

        // Step 1: Create cycles for each vertex
        for (int v = 0; v < n; v++) {
            int degree = originalGraph.neighbours[v].length;
            List<Integer> cycleNodes = new ArrayList<>();

            // Assign new indices for each neighbor
            for (int i = 0; i < degree; i++) {
                cycleNodes.add(newEdges.size());
            }
            vertexMapping.put(v, cycleNodes);

            // Create cycle edges
            for (int i = 0; i < degree; i++) {
                int next = (i + 1) % degree;
                newEdges.add(new int[]{cycleNodes.get(i), cycleNodes.get(next)});
                newWeights.add(0); // Zero-weight edges
            }
        }
        System.out.println("newEdges : " + Arrays.deepToString(newEdges.toArray()));
        System.out.println("newWeights : " + Arrays.deepToString(newWeights.toArray()));
        System.out.println("vertexMapping : " + vertexMapping);

        // Step 2: Connect corresponding vertices for each original edge
        for (int v = 0; v < n; v++) {
            int[] neighboursV = originalGraph.neighbours[v];
            int[] weightsV = originalGraph.weights[v];
            List<Integer> cycleNodesV = vertexMapping.get(v);

            for (int i = 0; i < neighboursV.length; i++) {
                int u = neighboursV[i];
                int weight = weightsV[u];
                List<Integer> cycleNodesU = vertexMapping.get(u);

                // Connect corresponding nodes in cycles
                newEdges.add(new int[]{cycleNodesV.get(i), cycleNodesU.get(findIndex(originalGraph.neighbours[u], v))});
                newWeights.add(weight);
            }
        }

        // Convert lists to arrays
        neighbours = new int[newEdges.size()][];
        weights = new int[newEdges.size()][];

        for (int i = 0; i < newEdges.size(); i++) {
            neighbours[i] = new int[]{newEdges.get(i)[0], newEdges.get(i)[1]};
            weights[i] = new int[]{newWeights.get(i)};
        }
    }

    private int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        return -1;
    }
}
