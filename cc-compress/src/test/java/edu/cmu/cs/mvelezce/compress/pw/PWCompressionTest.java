package edu.cmu.cs.mvelezce.compress.pw;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.compress.BaseCompression;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class PWCompressionTest {

  @Test
  public void berkeleyDb() throws IOException, InterruptedException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = BaseMeasureDiskOrderedScanAdapter.getListOfOptions();
    BaseCompression compression = new PWCompression(programName, options);

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";
    Set<Set<String>> configs = compression.analyze(args);
    System.out.println("Configs " + configs.size());
  }
}