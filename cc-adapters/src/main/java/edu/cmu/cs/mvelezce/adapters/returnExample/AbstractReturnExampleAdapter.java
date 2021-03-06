package edu.cmu.cs.mvelezce.adapters.returnExample;

import edu.cmu.cs.mvelezce.adapters.BaseAdapter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractReturnExampleAdapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "returnExample";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.ReturnExample";
  public static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B"};

  public AbstractReturnExampleAdapter() {
    // TODO check that we are passing empty string
    super(
        AbstractReturnExampleAdapter.PROGRAM_NAME,
        AbstractReturnExampleAdapter.MAIN_CLASS,
        AbstractReturnExampleAdapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(AbstractReturnExampleAdapter.OPTIONS);
  }
}
