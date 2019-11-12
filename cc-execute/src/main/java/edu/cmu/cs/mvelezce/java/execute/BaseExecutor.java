package edu.cmu.cs.mvelezce.java.execute;

import com.mijecu25.meme.utils.gc.GC;
import edu.cmu.cs.mvelezce.adapter.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.adapters.ExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.adapters.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.adapters.trivial.TrivialExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.parser.RawExecutionParser;
import edu.cmu.cs.mvelezce.utils.config.Options;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class BaseExecutor implements Executor {

  private final String programName;
  private final Set<Set<String>> configurations;
  private final RawExecutionParser rawExecutionParser;

  public BaseExecutor(String programName, Set<Set<String>> configurations) {
    this.programName = programName;
    this.configurations = configurations;

    this.rawExecutionParser = new RawExecutionParser(programName, this.outputDir());
  }

  @Override
  public void execute(String[] args) throws IOException, InterruptedException {
    Options.getCommandLine(args);

    String outputDir = this.outputDir() + "/" + this.programName;
    File file = new File(outputDir);

    Options.checkIfDeleteResult(file);

    if (file.exists()) {
      return;
    }

    int iterations = Options.getIterations();
    this.execute(iterations);
  }

  @Override
  public void execute(int iterations) throws InterruptedException, IOException {
    for (int i = 0; i < iterations; i++) {
      this.executeIteration(i);
    }
  }

  @Override
  public void executeProgram(String programClassPath, String mainClass, String[] configArgs)
      throws InterruptedException, IOException {
    ProcessBuilder builder = new ProcessBuilder();

    List<String> commandList = this.buildCommandAsList(programClassPath, mainClass, configArgs);
    builder.command(commandList);

    Process process = builder.start();

    com.mijecu25.meme.utils.execute.Executor.processOutput(process);
    com.mijecu25.meme.utils.execute.Executor.processError(process);

    process.waitFor();
  }

  @Override
  public void executeIteration(int iteration) throws InterruptedException, IOException {
    ExecutorAdapter adapter;

    switch (this.programName) {
      case TrivialExecutorAdapter.PROGRAM_NAME:
        adapter = new TrivialExecutorAdapter(this);
        break;
      case MeasureDiskOrderedScanAdapter.PROGRAM_NAME:
        adapter = new MeasureDiskOrderedScanAdapter(this);
        ((BaseMeasureDiskOrderedScanAdapter) adapter)
            .preProcess("../" + BaseMeasureDiskOrderedScanAdapter.ORIGINAL_ROOT_DIR);
        break;
      default:
        throw new RuntimeException("Could not find an adapter for " + this.programName);
    }

    for (Set<String> configuration : this.configurations) {
      adapter.execute(configuration);
      this.rawExecutionParser.logExecution(configuration, iteration);

      GC.gc(2000);
    }
  }

  private List<String> buildCommandAsList(
      String programClassPath, String mainClass, String[] configArgs) {
    List<String> commandList = new ArrayList<>();
    commandList.add("java");
    commandList.add("-Xmx26g");
    commandList.add("-Xms26g");
    commandList.add("-XX:+UseConcMarkSweepGC");
    //    commandList.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005");
    commandList.add("-cp");

    commandList.add(this.getClassPath(programClassPath));
    commandList.add(mainClass);
    List<String> configList = Arrays.asList(configArgs);
    commandList.addAll(configList);

    return commandList;
  }

  private String getClassPath(String programClassPath) {
    return com.mijecu25.meme.utils.execute.Executor.CLASS_PATH
        + com.mijecu25.meme.utils.execute.Executor.PATH_SEPARATOR
        + "../cc-analysis/"
        + com.mijecu25.meme.utils.execute.Executor.CLASS_PATH
        + com.mijecu25.meme.utils.execute.Executor.PATH_SEPARATOR
        + "../cc-utils/"
        + com.mijecu25.meme.utils.execute.Executor.CLASS_PATH
        + com.mijecu25.meme.utils.execute.Executor.PATH_SEPARATOR
        + "../../producer-consumer-4j/"
        + com.mijecu25.meme.utils.execute.Executor.CLASS_PATH
        + com.mijecu25.meme.utils.execute.Executor.PATH_SEPARATOR
        + programClassPath;
  }

  public RawExecutionParser getRawExecutionParser() {
    return rawExecutionParser;
  }
}
