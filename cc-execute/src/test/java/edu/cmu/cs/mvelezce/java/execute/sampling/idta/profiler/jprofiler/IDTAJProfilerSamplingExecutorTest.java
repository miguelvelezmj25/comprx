package edu.cmu.cs.mvelezce.java.execute.sampling.idta.profiler.jprofiler;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.adapters.trivial.BaseTrivialAdapter;
import edu.cmu.cs.mvelezce.compress.BaseCompression;
import edu.cmu.cs.mvelezce.compress.idta.naive.IDTANaiveCompression;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class IDTAJProfilerSamplingExecutorTest {

  @Test
  public void trivial() throws IOException, InterruptedException {
    String programName = BaseTrivialAdapter.PROGRAM_NAME;
    BaseCompression compression = new IDTANaiveCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configurations = compression.analyze(args);

    Executor executor = new IDTAJProfilerSamplingExecutor(programName, configurations);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    executor.execute(args);
  }

  @Test
  public void berkeleyDb() throws IOException, InterruptedException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    BaseCompression compression = new IDTANaiveCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configurations = compression.analyze(args);

    Executor executor = new IDTAJProfilerSamplingExecutor(programName, configurations);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i2";

    executor.execute(args);
  }
}
