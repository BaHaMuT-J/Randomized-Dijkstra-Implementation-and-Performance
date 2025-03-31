package org.dijkstra.node;

public class CycleNode {
    public int OV; // original vertex
    public int ON; // original neighbor

    public CycleNode(int ov, int on) {
        OV = ov;
        ON = on;
    }
}
