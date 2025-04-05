# Experimental Setup

## Integer Array and Integer Set Graphs

For measurements, I use a similar way as [gabormakrai](https://github.com/gabormakrai/dijkstra-performance) by generating random graphs with:

- number of vertices `n = {10, 20, ..., 1000}`
- edge probability `p = {0.1, 0.3, 0.5, 0.7, 0.9}`

For each pair `(n', p') ∈ n × p`, I:

1. Run both **sequential** and **randomized** Dijkstra algorithms **20 times** with a random source vertex.
2. Measure the **running time** of each run.
3. **Remove the 3 lowest and 3 highest times** to handle outliers.
4. **Take the average** of the remaining **14 values** as the running time.

## CycleNode Graph

Due to **longer running times** and **hardware limitations**, I only use:

- `n = {10, 20, ..., 500}`

For each configuration:

1. Run both variants **10 times**.
2. **Remove the 2 lowest and 2 highest times**.
3. **Take the average** of the remaining **6 values** as the running time.
