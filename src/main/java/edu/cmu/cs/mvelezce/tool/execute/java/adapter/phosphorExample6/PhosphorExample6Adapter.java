package edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample6;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PhosphorExample6Adapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "Example6";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.Example6";
  public static final String ORIGINAL_CLASS_PATH = "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH = "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";


  private static final String[] OPTIONS = {"A", "B", "C"};

  public PhosphorExample6Adapter() {
    // TODO check why we are passing empty string
    super(PhosphorExample6Adapter.PROGRAM_NAME, PhosphorExample6Adapter.MAIN_CLASS, "",
        PhosphorExample6Adapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(PhosphorExample6Adapter.OPTIONS);
  }

  @Override
  public void execute(Set<String> configuration, int iteration)
      throws IOException, InterruptedException {
    String[] args = this.configurationAsMainArguments(configuration);
    String[] newArgs = new String[args.length + 1];

    newArgs[0] = iteration + "";
    System.arraycopy(args, 0, newArgs, 1, args.length);

    this.execute(PhosphorExample6Main.PHOSPHOR_EXAMPLE_6_MAIN, newArgs);
  }
}
