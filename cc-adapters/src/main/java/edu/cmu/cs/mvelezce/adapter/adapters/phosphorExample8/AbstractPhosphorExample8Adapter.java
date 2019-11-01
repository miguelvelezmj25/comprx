package edu.cmu.cs.mvelezce.adapter.adapters.phosphorExample8;

import edu.cmu.cs.mvelezce.adapter.adapters.BaseAdapter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPhosphorExample8Adapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "Example8";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.Example8";
  public static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B", "C"};

  public AbstractPhosphorExample8Adapter() {
    // TODO check why we are passing empty string
    super(
        AbstractPhosphorExample8Adapter.PROGRAM_NAME,
        AbstractPhosphorExample8Adapter.MAIN_CLASS,
        AbstractPhosphorExample8Adapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(AbstractPhosphorExample8Adapter.OPTIONS);
  }
}
