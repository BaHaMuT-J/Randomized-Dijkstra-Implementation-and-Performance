# Randomized Dijkstra Implementation and Performance

This repository implements and benchmarks the **Randomized Dijkstra** algorithm as described in the [paper](https://arxiv.org/pdf/2307.04139). The implementation extends the existing work from [gabormakai](https://github.com/gabormakrai/dijkstra-performance) by adding support for randomized variants and performance comparisons.

In addition to integer array graph from gabormakai, I add adjacency list Graph using integer and [CycleNode](https://github.com/BaHaMuT-J/Randomized-Dijkstra-Implementation-and-Performance/blob/main/src/main/java/org/dijkstra/node/CycleNode.java) set and map. I create a Fibonacci heap wrapper and environment to be used with CycleNode graph in a similar style as the integer. Then I implement the **Randomized Dijkstra** for both integer and CycleNode. Finally, I benchmark the result and plot them in line plots

For further information, please refer to these additional links:

- [Implementation](Implementation.md)
- [Measurement](Measurement.md)
- [Results](Results.md)

## References
- [A Randomized Algorithm for Single-Source Shortest Path on Undirected Real-Weighted Graphs](https://arxiv.org/pdf/2307.04139)
- [Neo4j Fibonacci Heap](https://github.com/neo4j/neo4j/blob/3.5/community/graph-algo/src/main/java/org/neo4j/graphalgo/impl/util/FibonacciHeap.java)
- [gabormakai dijkstra-performance](https://github.com/gabormakrai/dijkstra-performance)