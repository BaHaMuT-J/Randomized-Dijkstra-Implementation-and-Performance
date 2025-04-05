# Graph and Algorithm Implementation

My implementation of the graph is extended from [gabormakrai](https://github.com/gabormakrai/dijkstra-performance).  
He has built a graph using integer arrays and a heap wrapper for both Binary and Fibonacci heaps.

I extended it by creating **two new adjacency list graphs** using an integer and [`CycleNode`](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/node/CycleNode.java) set and map, which is a new class representing the transformed Constant-Degree graph in the original paper.

I then modified the **Fibonacci heap wrapper** to work with both integer arrays and sets, and created a new heap class that works with my [`CycleNode`](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/node/CycleNode.java) graph.

With some modifications, the Dijkstra method from gabormakrai can now return both:

- an integer array `d` where `d(v)` is the shortest distance from source vertex `s` to vertex `v`, and
- an integer array `previous` where `previous[v]` is the node before `v` in the shortest path from `s` to `v`.

However, the **randomized Dijkstra** method from the paper does **not provide a way to construct** this `previous` array.

Therefore, I implemented **two new maps**:

- `firstInBallMap`, where `firstInBallMap.get(v)` (for $v \notin R$) is the first vertex extracted from the heap during the bundle construction process.
- `previousBallMap`, where `previousBallMap.get(v).get(w)` (for $v \notin R, w \in Ball(v) \cup \{ b(v) \}$) is the node before $w$ in the shortest path from $v$ to $w$.

> The process to form these two maps takes **O(1)** time in the sequential Dijkstra algorithm, so it does not affect the overall time complexity of the randomized algorithm.

With this data, I implement the **randomized Dijkstra** method for all graph structures.

- [Integer array](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/algo/randomized/FibHeapIntegerArrayRandomizedDijkstra.java)
- [Integer set](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/algo/randomized/FibHeapIntegerSetRandomizedDijkstra.java)
- [CycleNode set](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/algo/randomized/FibHeapCycleNodeRandomizedDijkstra.java)