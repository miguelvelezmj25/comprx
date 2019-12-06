package edu.cmu.cs.mvelezce.e2e.execute.gt;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import edu.cmu.cs.mvelezce.utils.configurations.ConfigHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GroundTruthExecutorTest {

  @Test
  public void berkeleyDb() throws IOException, InterruptedException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = BaseMeasureDiskOrderedScanAdapter.getListOfOptions();
    Set<Set<String>> configurations = ConfigHelper.getRandomConfigs(options, 1_000);
    System.out.println("Sampling " + configurations.size() + " configs");

    Executor executor = new GroundTruthExecutor(programName, configurations);

    String[] args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    executor.execute(args);
  }
}
