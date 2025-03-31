package org.dijkstra.node;

public class CycleNode implements Comparable<CycleNode> {
    public int OV; // original vertex
    public int ON; // original neighbor

    public CycleNode(int ov, int on) {
        OV = ov;
        ON = on;
    }

    @Override
    public String toString() {
        return "(" + OV + ", " + ON + ")";
    }

    @Override
    public int compareTo(CycleNode other) {
        int ovCompare = Integer.compare(this.OV, other.OV);
        if (ovCompare != 0) {
            return ovCompare;
        }
        return Integer.compare(this.ON, other.ON);
    }
}
