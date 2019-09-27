package edu.cmu.cs.mvelezce.tool.execute.java.adapter.variabilityContext2;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class VariabilityContext2Adapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "variabilityContext2";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.VariabilityContext2";
  public static final String ORIGINAL_CLASS_PATH = "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH = "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B", "C"};

  public VariabilityContext2Adapter() {
    // TODO check that we are passing empty string
    super(VariabilityContext2Adapter.PROGRAM_NAME, VariabilityContext2Adapter.MAIN_CLASS, "",
        VariabilityContext2Adapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(VariabilityContext2Adapter.OPTIONS);
  }

  @Override
  public void execute(Set<String> configuration, int iteration)
      throws IOException, InterruptedException {
    String[] args = this.configurationAsMainArguments(configuration);
    String[] newArgs = new String[args.length + 1];

    newArgs[0] = iteration + "";
    System.arraycopy(args, 0, newArgs, 1, args.length);

    this.execute(VariabilityContext2Main.VARIABILITY_CONTEXT_2_MAIN, newArgs);
  }
}
