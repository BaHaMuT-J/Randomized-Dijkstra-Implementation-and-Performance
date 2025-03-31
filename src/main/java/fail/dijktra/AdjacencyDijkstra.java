package fail.dijktra;

import fail.neo4j.graphalgo.impl.util.FibonacciHeap;

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

    // Using FibHeap
    public static Map<String, Integer> dijkstraFib(Map<String, Map<String, Integer>> adjacencyList, String source) {
        FibonacciHeap<Map.Entry<String, Integer>> fHeap = new FibonacciHeap<>(Map.Entry.comparingByValue());
        Map<String, FibonacciHeap.FibonacciHeapNode> heapNodeMap = new HashMap<>();
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();


        for (String node : adjacencyList.keySet()) {
            int dist = (node.equals(source) ? 0 : Integer.MAX_VALUE);
            distances.put(node, dist);
            FibonacciHeap.FibonacciHeapNode insertedNode = fHeap.insert(new AbstractMap.SimpleEntry<>(node, dist));
            heapNodeMap.put(node, insertedNode);
        }

        while (!fHeap.isEmpty()) {
            Map.Entry<String, Integer> current = fHeap.extractMin();
            String u = current.getKey();
            heapNodeMap.remove(u);
            System.out.println("Extracted " + u);

            if (visited.contains(u)) {
                System.out.println("Continue");
                continue;
            }
            visited.add(u);

            for (Map.Entry<String, Integer> neighbor : adjacencyList.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v = neighbor.getKey();
                int weight = neighbor.getValue();

                if (!visited.contains(v) && distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    fHeap.decreaseKey(heapNodeMap.get(v), new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }
        }
        return distances;
    }

    // For randomized Dijkstra, run sequential Dijkstra until node in R is extracted from Heap
    public static Map.Entry<Map.Entry<Set<String>, String>, Map<String, Integer>> dijkstraStopR(Map<String, Map<String, Integer>> adjacencyList, String source, Set<String> R) {
        System.out.println("DijkstraStopR source: " + source);
        FibonacciHeap<Map.Entry<String, Integer>> fHeap = new FibonacciHeap<>(Map.Entry.comparingByValue());
        Map<String, Integer> distances = new HashMap<>();
        Map<String, Integer> dist = new HashMap<>();
        String bundle = null;

        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        fHeap.insert(new AbstractMap.SimpleEntry<>(source, 0));

        while (!fHeap.isEmpty()) {
            Map.Entry<String, Integer> current = fHeap.extractMin();
            String u = current.getKey();

            if (dist.containsKey(u)) continue;
            dist.put(u, current.getValue());

            for (Map.Entry<String, Integer> neighbor : adjacencyList.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDistance = current.getValue() + weight;

                if (!dist.containsKey(v) && newDistance < distances.get(v)) {
                    distances.put(v, newDistance);
                    fHeap.insert(new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }

            if (R.contains(u)) {
                bundle = u;
                break;
            }
        }

        Set<String> ball = dist.keySet();
        ball.remove(bundle);

        return Map.entry(Map.entry(ball, bundle), dist);
    }
}
