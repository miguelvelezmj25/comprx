package edu.cmu.cs.mvelezce.e2e.processor.aggregator.instrument.idta;

import edu.cmu.cs.mvelezce.e2e.execute.instrument.idta.IDTAE2EInstrumentExecutor;
import edu.cmu.cs.mvelezce.e2e.processor.aggregator.E2EPerfAggregatorProcessor;
import edu.cmu.cs.mvelezce.java.execute.BaseExecutor;
import edu.cmu.cs.mvelezce.java.results.processed.PerfExecution;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IDTAE2EInstrumentPerfAggregatorProcessor extends E2EPerfAggregatorProcessor {

  private static final String OUTPUT_DIR = IDTAE2EInstrumentExecutor.OUTPUT_DIR;

  public IDTAE2EInstrumentPerfAggregatorProcessor(String programName) {
    this(programName, new HashMap<>(), 0);
  }

  IDTAE2EInstrumentPerfAggregatorProcessor(
      String programName,
      Map<Integer, Set<PerfExecution>> itersToProcessedPerfExecution,
      long addedTime) {
    super(programName, itersToProcessedPerfExecution, BaseExecutor.REAL, addedTime);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR + "/" + super.outputDir();
  }
}
