package edu.cmu.cs.mvelezce.tool.execute.java;

import edu.cmu.cs.mvelezce.evaluation.approaches.bruteforce.execute.BruteForceExecutor;
import edu.cmu.cs.mvelezce.tool.compression.Compression;
import edu.cmu.cs.mvelezce.tool.compression.simple.SimpleCompression;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.runningexample.RunningExampleMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ConfigCrusherExecutorTest {

  @Test
  public void sleep1() throws Exception {
    String programName = "sleep1";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
    String entryPoint = "edu.cmu.cs.mvelezce.Sleep1";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
  }

  @Test
  public void runningExample1() throws Exception {
    String programName = RunningExampleMain.PROGRAM_NAME;
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/running-example/target/classes";
    String entryPoint = "edu.cmu.cs.mvelezce.Example";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);
    configurations = BruteForceExecutor.getBruteForceConfigurations(configurations);

//                configurations.clear();
//        Set<String> n = new HashSet<>();
//        n.add("A");
//        configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void runningExample() throws Exception {
    String programName = RunningExampleMain.PROGRAM_NAME;
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/running-example/target/classes";
    String entryPoint = "edu.cmu.cs.mvelezce.Example";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void colorCounter1() throws Exception {
    String programName = "pngtasticColorCounter";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter/out/production/pngtastic-counter";
    String entryPoint = "counter.com.googlecode.pngtastic.Run";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);
    configurations = BruteForceExecutor.getBruteForceConfigurations(configurations);

//        configurations.clear();
//        Set<String> n = new HashSet<>();
////        n.add("A");
//        configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void colorCounter() throws Exception {
    String programName = "pngtasticColorCounter";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter/out/production/pngtastic-counter";
    String entryPoint = "counter.com.googlecode.pngtastic.Run";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void colorCounter2() throws Exception {
    String programName = "pngtasticColorCounter";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter/out/production/pngtastic-counter";
    String entryPoint = "counter.com.googlecode.pngtastic.Run";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> conf = new HashSet<>();
    configurations.add(conf);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void optimizer() throws Exception {
    String programName = "pngtasticOptimizer";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-optimizer/out/production/pngtastic-optimizer";
    String entryPoint = "optimizer.com.googlecode.pngtastic.Run";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void prevayler() throws Exception {
    String programName = "prevayler";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/prevayler/target/classes";
    String entryPoint = "org.prevayler.demos.demo1.PrimeNumbers";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void prevayler1() throws Exception {
    String programName = "prevayler";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/prevayler/target/classes";
    String entryPoint = "org.prevayler.demos.demo1.PrimeNumbers";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("FILEAGETHRESHOLD");
    n.add("DEEPCOPY");
    n.add("MONITOR");
    n.add("DISKSYNC");
    n.add("SNAPSHOTSERIALIZER");
    n.add("CLOCK");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void kanzi() throws Exception {
    String programName = "kanzi";
    String entryPoint = "kanzi.Run";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/kanzi/target/classes";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void grep() throws Exception {
    String programName = "grep";
    String entryPoint = "org.unix4j.grep.Main";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/grep/target/classes";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void email() throws Exception {
    String programName = "email";
    String entryPoint = "family.PL_Interface_impl";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/email/target/classes";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void grep1() throws Exception {
    String programName = "grep";
    String entryPoint = "org.unix4j.grep.Main";
//        String classDirectory = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/grep/target/classes";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/grep/target/classes";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);
    configurations.clear();

    Set<String> n = new HashSet<>();
    n.add("COUNT");
    n.add("MATCHINGFILES");
    n.add("IGNORECASE");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void kanzi1() throws Exception {
    String programName = "kanzi";
    String entryPoint = "kanzi.Run";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/kanzi/target/classes";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("CHECKSUM");
    n.add("BLOCKSIZE");
    n.add("TRANSFORM");
    n.add("VERBOSE");
    n.add("LEVEL");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void optimizer1() throws Exception {
    String programName = "pngtasticOptimizer";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-optimizer/out/production/pngtastic-optimizer";
    String entryPoint = "optimizer.com.googlecode.pngtastic.Run";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);
    configurations = BruteForceExecutor.getBruteForceConfigurations(configurations);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void regions12() throws Exception {
    String programName = "regions12";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
    String entryPoint = "edu.cmu.cs.mvelezce.Regions12";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("A");
    n.add("B");
    n.add("C");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void regions13() throws Exception {
    String programName = "regions13";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
    String entryPoint = "edu.cmu.cs.mvelezce.Regions13";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("A");
    n.add("B");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void regions14() throws Exception {
    String programName = "regions14";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
    String entryPoint = "edu.cmu.cs.mvelezce.Regions14";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("A");
    n.add("B");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void sort() throws Exception {
    String programName = "sort";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/sort/target/classes";
    String entryPoint = "org.unix4j.sort.Main";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i5";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void density() throws Exception {
    String programName = "density";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/density/target/classes";
    String entryPoint = "at.favre.tools.dconvert.Main";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void density1() throws Exception {
    String programName = "density";
    String classDirectory = BaseAdapter.USER_HOME
        + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/density/target/classes";
    String entryPoint = "at.favre.tools.dconvert.Main";

    // Program arguments
    String[] args = new String[0];

    Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    Set<Set<String>> configurations = compression.compressConfigurations(args);

    configurations.clear();
    Set<String> n = new HashSet<>();
    n.add("PLATFORM");
    n.add("UPSCALINGALGORITHM");
    n.add("OUTPUTCOMPRESSIONGMODE");
    n.add("DOWNSCALINGALGORITHM");
    configurations.add(n);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i1";

    Executor executor = new ConfigCrusherExecutor(programName, entryPoint, classDirectory,
        configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    measuredPerformance.size();
  }

  @Test
  public void trivial() throws Exception {
    String programName = TrivialAdapter.PROGRAM_NAME;
    String classDirectory = TrivialAdapter.INSTRUMENTED_CLASS_PATH;
    String entryPoint = TrivialAdapter.MAIN_CLASS;

    // Program arguments
    String[] args = new String[0];

    // TODO MIGUEL use compression algo
    //        Compression<Set<Set<String>>> compression = new SimpleCompression(programName);
    //        Set<Set<String>> configurations = compression.compressConfigurations(args);
    Set<Set<String>> configurations = new HashSet<>();

    Set<String> config = new HashSet<>();
    configurations.add(config);

    config = new HashSet<>();
    config.add("A");
    config.add("B");
    configurations.add(config);

    args = new String[3];
    args[0] = "-delres";
    args[1] = "-saveres";
    args[2] = "-i2";

    Executor executor =
        new ConfigCrusherExecutor(programName, entryPoint, classDirectory, configurations);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);
    System.out.println();
    //    measuredPerformance.size();
  }

}