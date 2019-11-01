package edu.cmu.cs.mvelezce.adapter.adapters.trivial;

import edu.cmu.cs.mvelezce.adapter.adapters.BaseAdapter;

import java.util.Arrays;
import java.util.List;

public class BaseTrivialAdapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "trivial";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.Trivial";
  public static final String ROOT_PACKAGE = "edu.cmu.cs.mvelezce.analysis";
  public static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_DIR_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples";
  public static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B"};

  public BaseTrivialAdapter() {
    // TODO check why are we passing empty string. Empty string represents the directory of the
    // class files to execute.
    super(
        BaseTrivialAdapter.PROGRAM_NAME,
        BaseTrivialAdapter.MAIN_CLASS,
        BaseTrivialAdapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(BaseTrivialAdapter.OPTIONS);
  }
}
