package edu.cmu.cs.mvelezce.e2e.execute.instrument.random;

import edu.cmu.cs.mvelezce.adapters.indexFiles.BaseIndexFilesAdapter;
import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.compress.BaseCompression;
import edu.cmu.cs.mvelezce.compress.random.exclude.NumberRandomExcludeCompression;
import edu.cmu.cs.mvelezce.e2e.execute.time.random.RandomTimeExecutorTest;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class RandomInstrumentExecutorTest {

  @Test
  public void berkeleyDb() throws IOException, InterruptedException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    BaseCompression compression = new NumberRandomExcludeCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configs = compression.analyze(args);
    configs = RandomTimeExecutorTest.getNumberOfConfigs(configs, 200);

    Executor executor = new RandomInstrumentExecutor(programName, configs, 30000);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";
    executor.execute(args);
  }

  @Test
  public void lucene() throws IOException, InterruptedException {
    String programName = BaseIndexFilesAdapter.PROGRAM_NAME;
    BaseCompression compression = new NumberRandomExcludeCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configs = compression.analyze(args);
    configs = RandomTimeExecutorTest.getNumberOfConfigs(configs, 200);

    Executor executor = new RandomInstrumentExecutor(programName, configs, 30000);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";
    executor.execute(args);
  }
}
