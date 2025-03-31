package org.dijkstra.graph;

import java.util.HashSet;
import java.util.Random;

public class NeighbourArrayGraphGenerator {
	
	public int[][] neighbours;
	public int[][] weights;
	
	public void generateRandomGraph(int size, double p, Random random) {
		
		HashSet<Integer>[] neighboursList = generateList(size);

		// create random spanning tree
		generateSpanningTree(neighboursList, random);
		
		// add more random arcs
		addRandomArcs(neighboursList, random, p);

		// create neighbours array
		neighbours = createNeighboursArray(neighboursList);
		
		// fill weights with random values
		weights = createWeightsArray(neighbours, random);
	}
	
	@SuppressWarnings("unchecked")
	private HashSet<Integer>[] generateList(int size) {
		HashSet<Integer>[] neighboursList = new HashSet[size];
		for (int i = 0; i < size; ++i) {
			neighboursList[i] = new HashSet<Integer>();
		}
		return neighboursList;
	}
	
	private void generateSpanningTree(HashSet<Integer>[] neighboursList, Random random) {
		boolean[] nodes = new boolean[neighboursList.length];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = false;
		}
		int nodeNumber = nodes.length;
		boolean firstIteration = true;
		while(nodeNumber != 0) {
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
			neighboursList[v].add(w);
			neighboursList[w].add(v);
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
	
	private int[][] createNeighboursArray(HashSet<Integer>[] neighboursList) {
		int[][] neighbours = new int[neighboursList.length][];
		for (int i = 0; i < neighbours.length; ++i) {
			neighbours[i] = new int[neighboursList[i].size()];
			int j = 0;
			for (Integer neighbour : neighboursList[i]) {
				neighbours[i][j] = neighbour;
				++j;
			}
		}
		return neighbours;
	}
	
	private int[][] createWeightsArray(int[][] neighbours, Random random) {
		int[][] weights = new int[neighbours.length][neighbours.length];
		for (int i = 0; i < weights.length; ++i) {
			if (neighbours[i] == null) {
				continue;
			}
			for (int j = 0; j < neighbours[i].length; ++j) {
				int neighbor = neighbours[i][j];
				int weight = random.nextInt(neighbours.length*2) + 1; // get random weight that not zero
				weights[i][neighbor] = weight;
				weights[neighbor][i] = weight;
			}
		}
		return weights;
	}
	
	private void addRandomArcs(HashSet<Integer>[] neighboursList, Random random, Double p) {
		int size = neighboursList.length;
		for (int i = 0; i < size; ++i) {
			for (int j = i+1; j < size; ++j) {
				if (neighboursList[i].contains(j) || neighboursList[j].contains(i) || random.nextDouble() > p) {
					continue;
				}

				neighboursList[i].add(j);
				neighboursList[j].add(i);
			}
		}
	}
	
}
