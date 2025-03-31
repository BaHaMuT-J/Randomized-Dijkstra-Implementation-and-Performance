package org.dijkstra.node;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CycleNode that = (CycleNode) obj;
        return this.OV == that.OV && this.ON == that.ON;
    }


    @Override
    public int hashCode() {
        return Objects.hash(OV, ON);
    }
}
