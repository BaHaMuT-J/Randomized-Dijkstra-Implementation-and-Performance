import org.dijkstra.node.CycleNode;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCycleNodeCompareTo {

    @Test
    public void testEquals() {
        CycleNode zero_zero = new CycleNode(0, 0);

        assertEquals(0, zero_zero.compareTo(zero_zero));
    }

    @Test
    public void testHigherOV() {
        CycleNode one_one = new CycleNode(1, 1);
        CycleNode zero_zero = new CycleNode(0, 0);
        CycleNode zero_one = new CycleNode(0, 1);
        CycleNode zero_two = new CycleNode(0, 2);

        assertEquals(1, one_one.compareTo(zero_zero));
        assertEquals(1, one_one.compareTo(zero_one));
        assertEquals(1, one_one.compareTo(zero_two));
    }

    @Test
    public void testLowerOV() {
        CycleNode zero_one = new CycleNode(0, 1);
        CycleNode one_zero = new CycleNode(1, 0);
        CycleNode one_one = new CycleNode(1, 1);
        CycleNode one_two = new CycleNode(1, 2);

        assertEquals(-1, zero_one.compareTo(one_zero));
        assertEquals(-1, zero_one.compareTo(one_one));
        assertEquals(-1, zero_one.compareTo(one_two));
    }

    @Test
    public void testEqualOVHigherON() {
        CycleNode zero_one = new CycleNode(0, 1);
        CycleNode zero_zero = new CycleNode(0, 0);

        assertEquals(1, zero_one.compareTo(zero_zero));
    }

    @Test
    public void testEqualOVLowerON() {
        CycleNode zero_zero = new CycleNode(0, 0);
        CycleNode zero_one = new CycleNode(0, 1);

        assertEquals(-1, zero_zero.compareTo(zero_one));
    }
}
