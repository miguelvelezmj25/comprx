package edu.cmu.cs.mvelezce.adapter.adapters.orContext2;

import edu.cmu.cs.mvelezce.adapter.adapters.BaseAdapter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractOrContext2Adapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "OrContext2";
  public static final String MAIN_CLASS = "edu.cmu.cs.mvelezce.analysis.OrContext2";
  public static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  public static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  private static final String[] OPTIONS = {"A", "B", "C"};

  public AbstractOrContext2Adapter() {
    // TODO check why we are passing empty string
    super(
        AbstractOrContext2Adapter.PROGRAM_NAME,
        AbstractOrContext2Adapter.MAIN_CLASS,
        "",
        AbstractOrContext2Adapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(AbstractOrContext2Adapter.OPTIONS);
  }
}
