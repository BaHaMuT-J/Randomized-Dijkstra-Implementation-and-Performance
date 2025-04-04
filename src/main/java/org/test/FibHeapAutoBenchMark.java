package org.test;

import org.test.binary.array.BinHeapIntegerArrayGraphAnalysisMain;
import org.test.binary.cycle.BinHeapCycleNodeGraphAnalysisMain;
import org.test.binary.set.BinHeapIntegerSetGraphAnalysisMain;
import org.test.fibonacci.cycle.FibHeapCycleNodeGraphAnalysisMain;
import org.test.fibonacci.set.FibHeapIntegerSetGraphAnalysisMain;
import org.test.fibonacci.array.FibHeapIntegerArrayGraphAnalysisMain;

public class FibHeapAutoBenchMark {

    public static void main(String[] args) {
        FibHeapCycleNodeGraphAnalysisMain.main(args);
        FibHeapIntegerSetGraphAnalysisMain.main(args);
        FibHeapIntegerArrayGraphAnalysisMain.main(args);

        BinHeapIntegerArrayGraphAnalysisMain.main(args);
        BinHeapIntegerSetGraphAnalysisMain.main(args);
        BinHeapCycleNodeGraphAnalysisMain.main(args);
    }
}
