package org.dijktra;

import org.neo4j.graphalgo.impl.util.FibonacciHeap;

import java.util.*;

public class AdjacencyDijkstra {

    // Using PriorityQueue (Binary Heap)
    public static Map<String, Integer> dijkstra(Map<String, Map<String, Integer>> adjacencyList, String source) {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();


        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        pq.offer(new AbstractMap.SimpleEntry<>(source, 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> current = pq.poll();
            String u = current.getKey();

            if (visited.contains(u)) continue;
            visited.add(u);

            for (Map.Entry<String, Integer> neighbor : adjacencyList.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v = neighbor.getKey();
                int weight = neighbor.getValue();

                if (!visited.contains(v) && distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    pq.offer(new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }
        }
        return distances;
    }

    // Using FibonacciHeap
    public static Map<String, Integer> dijkstraFib(Map<String, Map<String, Integer>> adjacencyList, String source) {
        FibonacciHeap<Map.Entry<String, Integer>> fHeap = new FibonacciHeap<>(Map.Entry.comparingByValue());
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();


        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        fHeap.insert(new AbstractMap.SimpleEntry<>(source, 0));

        while (!fHeap.isEmpty()) {
            Map.Entry<String, Integer> current = fHeap.extractMin();
            String u = current.getKey();

            if (visited.contains(u)) continue;
            visited.add(u);

            for (Map.Entry<String, Integer> neighbor : adjacencyList.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v = neighbor.getKey();
                int weight = neighbor.getValue();

                if (!visited.contains(v) && distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    fHeap.insert(new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }
        }
        return distances;
    }

    // For randomized Dijkstra, run sequential Dijkstra until node in R is extracted from Heap
    public static Map.Entry<Map.Entry<Set<String>, String>, Map<String, Integer>> dijkstraStopR(Map<String, Map<String, Integer>> adjacencyList, String source, Set<String> R) {
        FibonacciHeap<Map.Entry<String, Integer>> fHeap = new FibonacciHeap<>(Map.Entry.comparingByValue());
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        String bundle = null;

        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        fHeap.insert(new AbstractMap.SimpleEntry<>(source, 0));

        while (!fHeap.isEmpty()) {
            Map.Entry<String, Integer> current = fHeap.extractMin();
            String u = current.getKey();

            if (visited.contains(u)) continue;
            visited.add(u);

            for (Map.Entry<String, Integer> neighbor : adjacencyList.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDistance = current.getValue() + weight;

                if (!visited.contains(v) && newDistance < distances.get(v)) {
                    distances.put(v, newDistance);
                    fHeap.insert(new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }

            if (R.contains(u)) {
                bundle = u;
                visited.remove(u);
                break;
            }
        }

        return Map.entry(Map.entry(visited, bundle), distances);
    }
}
