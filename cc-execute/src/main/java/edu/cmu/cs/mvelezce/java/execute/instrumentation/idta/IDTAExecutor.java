package edu.cmu.cs.mvelezce.java.execute.instrumentation.idta;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.BaseExecutor;
import edu.cmu.cs.mvelezce.java.execute.adapters.ExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.instrumentation.adapters.measureDiskOrderedScan.InstrumentMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.instrumentation.adapters.trivial.InstrumentTrivialExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.instrumentation.parser.RawExecutionParser;
import edu.cmu.cs.mvelezce.utils.config.Options;

import java.io.IOException;
import java.util.*;

public class IDTAExecutor extends BaseExecutor {

  public static final String OUTPUT_DIR =
      "../cc-execute/" + Options.DIRECTORY + "/executor/java/idta/programs/instrumentation";

  public IDTAExecutor(String programName) {
    this(programName, new HashSet<>());
  }

  IDTAExecutor(String programName, Set<Set<String>> configurations) {
    super(programName, configurations, new RawExecutionParser(programName, OUTPUT_DIR));
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR;
  }

  @Override
  protected ExecutorAdapter getExecutorAdapter() {
    ExecutorAdapter adapter;

    switch (this.getProgramName()) {
      case InstrumentTrivialExecutorAdapter.PROGRAM_NAME:
        adapter = new InstrumentTrivialExecutorAdapter(this);
        break;
      case InstrumentMeasureDiskOrderedScanAdapter.PROGRAM_NAME:
        adapter = new InstrumentMeasureDiskOrderedScanAdapter(this);
        ((BaseMeasureDiskOrderedScanAdapter) adapter)
            .preProcess("../" + BaseMeasureDiskOrderedScanAdapter.ORIGINAL_ROOT_DIR);
        break;
      default:
        throw new RuntimeException("Could not find an adapter for " + this.getProgramName());
    }

    return adapter;
  }

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
}
