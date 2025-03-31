package org.dijkstra.graph;

import java.util.*;

public class NeighbourSetGraphGenerator {
    public Map<Integer, Set<Integer>> neighbours;
    public Map<Integer, Map<Integer, Integer>> weights;

    public void generateRandomGraph(int size, double p, Random random) {
        neighbours = new HashMap<>();
        weights = new HashMap<>();

        generateSet(size);

        generateSpanningTree(size, random);

        addRandomArcs(size, random, p);

        createWeightsMap(size, random);
    }

    private void generateSet(int size) {
        for (int i = 0; i < size; ++i) {
            neighbours.put(i, new HashSet<Integer>());
        }
    }

    private void generateSpanningTree(int size, Random random) {
        boolean[] nodes = new boolean[size];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = false;
        }
        int nodeNumber = nodes.length;
        boolean firstIteration = true;
        while (nodeNumber != 0) {
            int v = random.nextInt(nodes.length);
            int w = random.nextInt(nodes.length);
            if (v == w) {
                continue;
            }
            if (firstIteration) {
                firstIteration = false;
            } else if ((nodes[v] && nodes[w]) || (!nodes[v] && !nodes[w])) {
                continue;
            }

            Set<Integer> neighbors = neighbours.get(v);
            neighbors.add(w);
            neighbours.put(v, neighbors);

            neighbors = neighbours.get(w);
            neighbors.add(v);
            neighbours.put(w, neighbors);

            if (!nodes[v]) {
                nodes[v] = true;
                --nodeNumber;
            }
            if (!nodes[w]) {
                nodes[w] = true;
                --nodeNumber;
            }
        }
    }

    private void addRandomArcs(int size, Random random, Double p) {
        for (int i = 0; i < size; ++i) {
            for (int j = i+1; j < size; ++j) {
                Set<Integer> neighborsI = neighbours.get(i);
                Set<Integer> neighborsJ = neighbours.get(j);

                if (neighborsI.contains(j) || neighborsJ.contains(i) || random.nextDouble() > p) {
                    continue;
                }

                neighborsI.add(j);
                neighbours.put(i, neighborsI);
                neighborsJ.add(i);
                neighbours.put(j, neighborsJ);
            }
        }
    }

    private void createWeightsMap(int size, Random random) {
        for (int i = 0; i < size; ++i) {
            Set<Integer> neighborsI = neighbours.get(i);
            if (neighborsI.isEmpty()) {
                continue;
            }

            Map<Integer, Integer> weightsI = new HashMap<>();
            for (Integer neighbor : neighborsI) {
                int weight = random.nextInt(size*2) + 1; // get random weight that not zero
                weightsI.put(neighbor, weight);
            }
            weights.put(i, weightsI);
        }
    }
}
