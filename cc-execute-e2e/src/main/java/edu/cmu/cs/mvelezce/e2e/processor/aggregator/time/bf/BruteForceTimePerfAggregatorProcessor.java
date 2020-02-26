package edu.cmu.cs.mvelezce.e2e.processor.aggregator.time.bf;

import edu.cmu.cs.mvelezce.e2e.execute.time.bf.BruteForceTimeExecutor;
import edu.cmu.cs.mvelezce.java.processor.aggregator.PerfAggregatorProcessor;
import edu.cmu.cs.mvelezce.java.results.processed.PerfExecution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BruteForceTimePerfAggregatorProcessor extends PerfAggregatorProcessor {

  private static final String OUTPUT_DIR = BruteForceTimeExecutor.OUTPUT_DIR;

  public BruteForceTimePerfAggregatorProcessor(String programName) {
    this(programName, new HashMap<>());
  }

  BruteForceTimePerfAggregatorProcessor(
      String programName, Map<Integer, Set<PerfExecution>> itersToProcessedPerfExecution) {
    super(programName, itersToProcessedPerfExecution);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR;
  }
}
