package edu.cmu.cs.mvelezce.java.execute.idta;

import edu.cmu.cs.mvelezce.adapter.adapters.trivial.BaseTrivialAdapter;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import edu.cmu.cs.mvelezce.utils.configurations.ConfigHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class IDTAExecutorTest {

  @Test
  public void trivial() throws IOException, InterruptedException {
    String programName = BaseTrivialAdapter.PROGRAM_NAME;
    List<String> options = BaseTrivialAdapter.getListOfOptions();
    Set<Set<String>> configurations = ConfigHelper.getConfigurations(options);

    Executor executor = new IDTAExecutor(programName, configurations);

    String[] args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i2";

    executor.execute(args);
  }
}
