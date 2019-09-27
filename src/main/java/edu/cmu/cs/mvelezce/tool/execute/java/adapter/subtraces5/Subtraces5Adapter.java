package edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces5;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Subtraces5Adapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "subtraces5";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.Subtraces5";
  public static final String ORIGINAL_CLASS_PATH = "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH = "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B", "C"};

  public Subtraces5Adapter() {
    // TODO check that we are passing empty string
    super(Subtraces5Adapter.PROGRAM_NAME, Subtraces5Adapter.MAIN_CLASS, "",
        Subtraces5Adapter.getListOfOptions());
  }

  // TODO abstract method in base adapter?
  public static List<String> getListOfOptions() {
    return Arrays.asList(Subtraces5Adapter.OPTIONS);
  }

  @Override
  public void execute(Set<String> configuration, int iteration)
      throws IOException, InterruptedException {
    String[] args = this.configurationAsMainArguments(configuration);
    String[] newArgs = new String[args.length + 1];

    newArgs[0] = iteration + "";
    System.arraycopy(args, 0, newArgs, 1, args.length);

    this.execute(Subtraces5Main.SUBTRACES_5_MAIN, newArgs);
  }
}
