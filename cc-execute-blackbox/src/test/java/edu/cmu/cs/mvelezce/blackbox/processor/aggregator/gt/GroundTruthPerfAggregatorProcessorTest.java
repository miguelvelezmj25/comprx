package edu.cmu.cs.mvelezce.blackbox.processor.aggregator.gt;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.analysis.Analysis;
import edu.cmu.cs.mvelezce.blackbox.execute.gt.GroundTruthExecutor;
import edu.cmu.cs.mvelezce.java.execute.BaseExecutor;
import edu.cmu.cs.mvelezce.java.results.processed.ProcessedPerfExecution;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class GroundTruthPerfAggregatorProcessorTest {

  @Test
  public void berkeleyDB() throws IOException, InterruptedException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    BaseExecutor<ProcessedPerfExecution> executor = new GroundTruthExecutor(programName);
    Map<Integer, Set<ProcessedPerfExecution>> itersToResults =
        executor.getRawExecutionParser().readResults();
    Analysis perfAggregatorProcessor =
        new GroundTruthPerfAggregatorProcessor(programName, itersToResults);

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";
    perfAggregatorProcessor.analyze(args);
  }
}
