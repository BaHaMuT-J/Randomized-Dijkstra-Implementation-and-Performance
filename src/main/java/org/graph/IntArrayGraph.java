package org.graph;

import lombok.Getter;

import java.util.*;

@Getter
public class IntArrayGraph {
    private final Integer[][] weights;
    private final Map<Integer, Set<Integer>> neighbors;

    public IntArrayGraph(Integer[][] weights, Map<Integer, Set<Integer>> neighbors) {
        this.weights = weights;
        this.neighbors = neighbors;
    }

    public void print() {
        for (Integer[] arr: weights) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(neighbors);
    }

    public static IntArrayGraph getInstance(int size) {
        Integer[][] weights = new Integer[size][size];
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();

        Random random = new Random(42);
        for (int from = 0; from < size; from++) {
            for (int to = 0; to < size; to++) {
                if (from == to || weights[from][to] != null || random.nextDouble() > 0.5) {
                    continue;
                }

                int weight = random.nextInt(100);
                weights[from][to] = weight;
                weights[to][from] = weight;

                Set<Integer> set = neighbors.getOrDefault(from, new HashSet<>());
                set.add(to);
                neighbors.put(from, set);

                set = neighbors.getOrDefault(to, new HashSet<>());
                set.add(from);
                neighbors.put(to, set);
            }
        }
        return new IntArrayGraph(weights, neighbors);
    }
}
