package edu.cmu.cs.mvelezce.java.execute.instrumentation.adapters.trivial;

import edu.cmu.cs.mvelezce.adapters.trivial.BaseTrivialAdapter;
import edu.cmu.cs.mvelezce.java.execute.Executor;
import edu.cmu.cs.mvelezce.java.execute.adapters.ExecutorAdapter;

import java.io.IOException;
import java.util.Set;

public class InstrumentTrivialExecutorAdapter extends BaseTrivialAdapter implements ExecutorAdapter {

  private final Executor executor;

  public InstrumentTrivialExecutorAdapter(Executor executor) {
    this.executor = executor;
  }

  @Override
  public void execute(Set<String> configuration) throws IOException, InterruptedException {
    String[] configAsArgs = this.configurationAsMainArguments(configuration);
    this.executor.executeProgram(
        "../" + BaseTrivialAdapter.INSTRUMENTED_CLASS_PATH,
        ExecutorAdapter.EXECUTOR_MAIN_CLASS_PREFIX + "Trivial",
        configAsArgs);
  }
}
