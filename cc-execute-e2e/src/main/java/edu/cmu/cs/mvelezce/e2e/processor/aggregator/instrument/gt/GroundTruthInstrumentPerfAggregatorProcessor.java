package edu.cmu.cs.mvelezce.e2e.processor.aggregator.instrument.gt;

import edu.cmu.cs.mvelezce.e2e.execute.instrument.gt.GroundTruthInstrumentExecutor;
import edu.cmu.cs.mvelezce.e2e.processor.aggregator.E2EPerfAggregatorProcessor;
import edu.cmu.cs.mvelezce.java.execute.BaseExecutor;
import edu.cmu.cs.mvelezce.java.results.processed.PerfExecution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GroundTruthInstrumentPerfAggregatorProcessor extends E2EPerfAggregatorProcessor {

  private static final String OUTPUT_DIR = GroundTruthInstrumentExecutor.OUTPUT_DIR;

  public GroundTruthInstrumentPerfAggregatorProcessor(String programName) {
    this(programName, new HashMap<>());
  }

  GroundTruthInstrumentPerfAggregatorProcessor(
      String programName, Map<Integer, Set<PerfExecution>> itersToProcessedPerfExecution) {
    super(programName, itersToProcessedPerfExecution, BaseExecutor.REAL);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR + "/" + super.outputDir();
  }
}
