package edu.cmu.cs.mvelezce.java.processor.aggregator.idta;

import edu.cmu.cs.mvelezce.java.execute.instrumentation.idta.IDTAExecutor;
import edu.cmu.cs.mvelezce.java.processor.aggregator.PerfAggregatorProcessor;
import edu.cmu.cs.mvelezce.java.results.processed.ProcessedPerfExecution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IDTAPerfAggregatorProcessor extends PerfAggregatorProcessor {

  private static final String OUTPUT_DIR = IDTAExecutor.OUTPUT_DIR;

  public IDTAPerfAggregatorProcessor(String programName) {
    this(programName, new HashMap<>());
  }

  IDTAPerfAggregatorProcessor(
      String programName, Map<Integer, Set<ProcessedPerfExecution>> itersToProcessedPerfExecution) {
    super(programName, itersToProcessedPerfExecution);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR;
  }
}
