package com.google.gson.benchmark;

public class Benchmark {

    private static final int REPS = 500000;

    public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    BagOfPrimitivesDeserializationBenchmark benchmark = new BagOfPrimitivesDeserializationBenchmark();
    CollectionsDeserializationBenchmark benchmark_2 = new CollectionsDeserializationBenchmark();
    SerializationBenchmark benchmark_3 = new SerializationBenchmark();
    try {
      benchmark.setUp();
      benchmark_2.setUp();
      benchmark_3.setUp();
    } catch (Exception e) {
      System.err.println("Exception in setUp: " + e);
    }
    benchmark.main(REPS);
    benchmark_2.main(REPS);
    benchmark_3.main(REPS);

    long end = System.currentTimeMillis();
    System.out.println("time: " + (end - start));
  }
}
