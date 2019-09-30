package edu.cmu.cs.mvelezce.adapter.adapters.measureDiskOrderedScan;

import edu.cmu.cs.mvelezce.adapter.adapters.BaseAdapter;
import edu.cmu.cs.mvelezce.adapter.utils.Executor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Berkeley DB */
public abstract class AbstractMeasureDiskOrderedScanAdapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "MeasureDiskOrderedScan";
  public static final String MAIN_CLASS = "com.sleepycat.analysis.MeasureDiskOrderedScan";
  public static final String ROOT_PACKAGE = "com.sleepycat";
  public static final String ORIGINAL_ROOT_DIR =
      "../performance-mapper-evaluation/original/berkeley-db";
  public static final String INSTRUMENTED_DIR_PATH =
      "../performance-mapper-evaluation/instrumented/berkeley-db";
  public static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/berkeley-db/target/classes";
  private static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/berkeley-db/target/classes";
  private static final String HOME_DIR = "tmp";
  private static final String[] OPTIONS = {
    "DUPLICATES",
    "SEQUENTIAL", /*"KEYSONLY",*/
    /*"FILELOGGINGLEVEL",*/ "JECACHESIZE", /*"LOCKING",*/
    "SHAREDCACHE",
    "REPLICATED"
  };

  public AbstractMeasureDiskOrderedScanAdapter() {
    // TODO check that we are passing empty string
    super(
        AbstractMeasureDiskOrderedScanAdapter.PROGRAM_NAME,
        AbstractMeasureDiskOrderedScanAdapter.MAIN_CLASS,
        "",
        AbstractMeasureDiskOrderedScanAdapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(AbstractMeasureDiskOrderedScanAdapter.OPTIONS);
  }

  //  public void preProcess() {
  //    ProcessBuilder builder = new ProcessBuilder();
  //
  //    List<String> commandList = new ArrayList<>();
  //    commandList.add("sudo");
  //    commandList.add("./clean.sh");
  //    builder.command(commandList);
  //    builder.directory(new File(ORIGINAL_ROOT_DIR));
  //
  //    try {
  //      Process process = builder.start();
  //
  //      Helper.processOutput(process);
  //      Helper.processError(process);
  //
  //      process.waitFor();
  //    }
  //    catch (IOException | InterruptedException e) {
  //      System.err.println("Could not clear the cache before running " + PROGRAM_NAME);
  //    }
  //  }

  public void preProcess() {
    try {
      this.removeDir();
      this.makeDir();
      this.clean();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Could not create the tmp folder to run " + PROGRAM_NAME);
    }
  }

  private void clean() throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder();

    List<String> commandList = new ArrayList<>();
    commandList.add("sudo");
    commandList.add("./clean.sh");
    builder.command(commandList);
    builder.directory(new File(ORIGINAL_ROOT_DIR));

    Process process = builder.start();

    Executor.processOutput(process);
    Executor.processError(process);

    process.waitFor();
  }

  private void makeDir() throws IOException, InterruptedException {
    List<String> commandList = new ArrayList<>();
    commandList.add("mkdir");
    commandList.add(HOME_DIR);

    runCommand(commandList);
  }

  private void removeDir() throws IOException, InterruptedException {
    List<String> commandList = new ArrayList<>();
    commandList.add("rm");
    commandList.add("-rf");
    commandList.add(HOME_DIR);

    runCommand(commandList);
  }

  private void runCommand(List<String> commandList) throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(commandList);

    Process process = builder.start();

    Executor.processOutput(process);
    Executor.processError(process);

    process.waitFor();
  }
}
