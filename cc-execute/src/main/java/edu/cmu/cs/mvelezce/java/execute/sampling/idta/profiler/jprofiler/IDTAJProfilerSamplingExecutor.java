package edu.cmu.cs.mvelezce.java.execute.sampling.idta.profiler.jprofiler;

import com.mijecu25.meme.utils.execute.Executor;
import edu.cmu.cs.mvelezce.java.execute.BaseExecutor;
import edu.cmu.cs.mvelezce.java.execute.adapters.ExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.parser.BaseRawExecutionParser;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.convert.profiler.jprofiler.JProfilerSamplingConvertExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.indexFiles.profiler.jprofiler.JProfilerSamplingIndexFilesAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.measureDiskOrderedScan.profiler.jprofiler.JProfilerSamplingMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.multithread.profiler.jprofiler.JProfilerSamplingMultithreadExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.performance.profiler.jprofiler.JProfilerSamplingPerformanceExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.runBenchC.profiler.jprofiler.JProfilerSamplingRunBenchCExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.adapters.trivial.profiler.jprofiler.JProfilerSamplingTrivialExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.parser.profiler.jprofiler.RawJProfilerSamplingExecutionParser;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.RawJProfilerSamplingPerfExecution;
import edu.cmu.cs.mvelezce.utils.config.Options;

import java.io.IOException;
import java.util.*;

public class IDTAJProfilerSamplingExecutor extends BaseExecutor<RawJProfilerSamplingPerfExecution> {

  public static final String MAC_OS_X = "Mac OS X";
  public static final String LINUX = "Linux";

  public static final String OUTPUT_DIR =
      "../cc-execute/" + Options.DIRECTORY + "/executor/java/idta/programs/sampling/jprofiler";

  private final String threadStatus;

  public IDTAJProfilerSamplingExecutor(String programName, String threadStatus) {
    this(programName, new HashSet<>(), 0, threadStatus);
  }

  public IDTAJProfilerSamplingExecutor(
      String programName,
      Set<Set<String>> configurations,
      int waitAfterExecution,
      String threadStatus) {
    super(
        programName,
        configurations,
        new RawJProfilerSamplingExecutionParser(
            programName,
            OUTPUT_DIR + "/" + getMeasuredTimeFromThreadStatus(threadStatus),
            threadStatus),
        waitAfterExecution);

    this.threadStatus = threadStatus;
  }

  public IDTAJProfilerSamplingExecutor(
          String programName,
          Set<Set<String>> configurations,
          BaseRawExecutionParser<RawJProfilerSamplingPerfExecution> baseRawExecutionParser,
          int waitAfterExecution,
          String threadStatus) {
    super(
            programName,
            configurations,
            baseRawExecutionParser,
            waitAfterExecution);

    this.threadStatus = threadStatus;
  }

  public static String getMeasuredTimeFromThreadStatus(String threadStatus) {
    if (threadStatus.equals(RawJProfilerSamplingExecutionParser.ALL_THREAD_STATUS)) {
      return BaseExecutor.REAL;
    }

    if (threadStatus.equals(RawJProfilerSamplingExecutionParser.RUNNABLE_THREAD_STATUS)) {
      return BaseExecutor.USER;
    }

    throw new RuntimeException("Do not recognize thread status " + threadStatus);
  }

  public static String getOS() {
    return System.getProperty("os.name");
  }

  public String getThreadStatus() {
    return threadStatus;
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR + "/" + getMeasuredTimeFromThreadStatus(this.threadStatus);
  }

  @Override
  protected ExecutorAdapter getExecutorAdapter() {
    ExecutorAdapter adapter;

    switch (this.getProgramName()) {
      case JProfilerSamplingTrivialExecutorAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingTrivialExecutorAdapter(this);
        break;
      case JProfilerSamplingMeasureDiskOrderedScanAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingMeasureDiskOrderedScanAdapter(this);
        break;
      case JProfilerSamplingIndexFilesAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingIndexFilesAdapter(this);
        break;
      case JProfilerSamplingPerformanceExecutorAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingPerformanceExecutorAdapter(this);
        break;
      case JProfilerSamplingMultithreadExecutorAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingMultithreadExecutorAdapter(this);
        break;
      case JProfilerSamplingConvertExecutorAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingConvertExecutorAdapter(this);
        break;
      case JProfilerSamplingRunBenchCExecutorAdapter.PROGRAM_NAME:
        adapter = new JProfilerSamplingRunBenchCExecutorAdapter(this);
        break;
      default:
        throw new RuntimeException("Could not find an adapter for " + this.getProgramName());
    }

    return adapter;
  }

  public void executeProgram(
      String programClassPath, String mainClass, String agentPath, String[] configArgs)
      throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder();

    List<String> commandList =
        this.buildCommandAsList(programClassPath, mainClass, agentPath, configArgs);
    builder.command(commandList);

    Process process = builder.start();

    Executor.processOutput(process);
    Executor.processError(process);

    process.waitFor();
  }

  private List<String> buildCommandAsList(
      String programClassPath, String mainClass, String agentPath, String[] configArgs) {
    List<String> commandList = new ArrayList<>();
    commandList.add("java");
    commandList.add(agentPath);
    commandList.add("-Xmx12g");
    commandList.add("-Xms12g");
    commandList.add("-cp");
    commandList.add(programClassPath);
    commandList.add(mainClass);
    List<String> configList = Arrays.asList(configArgs);
    commandList.addAll(configList);

    return commandList;
  }
}
