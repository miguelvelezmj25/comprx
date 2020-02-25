package edu.cmu.cs.mvelezce.e2e.execute.time.gt;

import edu.cmu.cs.mvelezce.adapters.convert.BaseConvertAdapter;
import edu.cmu.cs.mvelezce.adapters.multithread.BaseMultithreadAdapter;
import edu.cmu.cs.mvelezce.adapters.runBenchC.BaseRunBenchCAdapter;
import edu.cmu.cs.mvelezce.compress.BaseCompression;
import edu.cmu.cs.mvelezce.compress.gt.GTCompression;
import edu.cmu.cs.mvelezce.e2e.execute.time.parser.E2ETimeExecutionParser;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class GroundTruthTimeExecutorTest {

  @Test
  public void multithread() throws IOException, InterruptedException {
    String programName = BaseMultithreadAdapter.PROGRAM_NAME;
    BaseCompression compression = new GTCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configs = compression.analyze(args);
    Executor executor = new GroundTruthTimeExecutor(programName, configs, 30000);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";
    executor.execute(args);
  }

  @Test
  public void convert() throws IOException, InterruptedException {
    String programName = BaseConvertAdapter.PROGRAM_NAME;
    BaseCompression compression = new GTCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configs = compression.analyze(args);
    Executor executor = new GroundTruthTimeExecutor(programName, configs, 30000);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i3";
    executor.execute(args);
  }

  @Test
  public void runBenchC() throws IOException, InterruptedException {
    String programName = BaseRunBenchCAdapter.PROGRAM_NAME;
    BaseCompression compression = new GTCompression(programName);
    String[] args = new String[0];
    Set<Set<String>> configs = compression.analyze(args);
    Executor executor =
        new GroundTruthTimeExecutor(programName, configs, 30000, E2ETimeExecutionParser.REAL);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";
    executor.execute(args);
  }
}
