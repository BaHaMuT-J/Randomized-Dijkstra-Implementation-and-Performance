package org.jmh.benchmark;

import org.graph.IntArrayGraph;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BenchMarkRunner {

    @Setup(Level.Invocation)
    public static void setUp() {

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public static void test() {

    }

    public static void main(String[] args) {
        IntArrayGraph graph = IntArrayGraph.getInstance(4);
        graph.print();
    }
}
