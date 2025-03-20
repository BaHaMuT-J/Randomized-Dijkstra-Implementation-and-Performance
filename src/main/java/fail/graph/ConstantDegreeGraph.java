package fail.graph;

import lombok.Getter;

import java.util.*;

@Getter
public class ConstantDegreeGraph {
    private final Map<String, Map<String, Integer>> adjacencyList;

    public ConstantDegreeGraph(Map<String, Map<String, Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public void print() {
        for (Map.Entry<String, Map<String, Integer>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static ConstantDegreeGraph fromIntArrayGraph(IntArrayGraph graph) {
        Integer[][] weights = graph.getWeights();
        Map<Integer, Set<Integer>> neighbors = graph.getNeighbors();
        Map<String, Map<String, Integer>> newAdjList = new HashMap<>();

        for (Map.Entry<Integer, Set<Integer>> entry : neighbors.entrySet()) {
            int v = entry.getKey();
            Set<Integer> vNeighbors = entry.getValue();
            List<String> cycle = new ArrayList<>();

            for (int w : vNeighbors) {
                String node = v + "_" + w;
                newAdjList.putIfAbsent(node, new HashMap<>());
                cycle.add(node);
            }

            int cycleSize = cycle.size();
            for (int i = 0; i < cycleSize; i++) {
                String a = cycle.get(i);
                String b = cycle.get((i + 1) % cycleSize);
                newAdjList.get(a).put(b, 0);
                newAdjList.get(b).put(a, 0);
            }
        }

        for (int u = 0; u < weights.length; u++) {
            for (int v = u + 1; v < weights.length; v++) {
                if (weights[u][v] != null) {
                    String uNode = u + "_" + v;
                    String vNode = v + "_" + u;
                    newAdjList.get(uNode).put(vNode, weights[u][v]);
                    newAdjList.get(vNode).put(uNode, weights[u][v]);
                }
            }
        }

        return new ConstantDegreeGraph(newAdjList);
    }

    public static Map<String, ConstantDegreeGraph> fromIntArrayGraphAndSource(IntArrayGraph graph, Integer source) {
        Integer[][] weights = graph.getWeights();
        Map<Integer, Set<Integer>> neighbors = graph.getNeighbors();
        Map<String, Map<String, Integer>> newAdjList = new HashMap<>();

        String s = null;
        for (Map.Entry<Integer, Set<Integer>> entry : neighbors.entrySet()) {
            int v = entry.getKey();
            Set<Integer> vNeighbors = entry.getValue();
            List<String> cycle = new ArrayList<>();

            for (int w : vNeighbors) {
                String node = v + "_" + w;
                if (v == source && s == null) {
                    s = node;
                }
                newAdjList.putIfAbsent(node, new HashMap<>());
                cycle.add(node);
            }

            int cycleSize = cycle.size();
            for (int i = 0; i < cycleSize; i++) {
                String a = cycle.get(i);
                String b = cycle.get((i + 1) % cycleSize);
                newAdjList.get(a).put(b, 0);
                newAdjList.get(b).put(a, 0);
            }
        }

        for (int u = 0; u < weights.length; u++) {
            for (int v = u + 1; v < weights.length; v++) {
                if (weights[u][v] != null) {
                    String uNode = u + "_" + v;
                    String vNode = v + "_" + u;
                    newAdjList.get(uNode).put(vNode, weights[u][v]);
                    newAdjList.get(vNode).put(uNode, weights[u][v]);
                }
            }
        }

        Map<String, ConstantDegreeGraph> res = new HashMap<>();
        res.put(s, new ConstantDegreeGraph(newAdjList));
        return res;
    }
}

