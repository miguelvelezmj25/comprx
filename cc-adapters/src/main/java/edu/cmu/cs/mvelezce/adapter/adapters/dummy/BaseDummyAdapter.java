package edu.cmu.cs.mvelezce.adapter.adapters.dummy;

import edu.cmu.cs.mvelezce.adapter.adapters.BaseAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BaseDummyAdapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "dummy";
  private static final String MAIN_CLASS = "";
  private static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
  private static final String INSTRUMENTED_CLASS_PATH =
      "../performance-mapper-evaluation/instrumented/phosphor-examples/target/classes";

  public BaseDummyAdapter() {
    // TODO check that we are passing empty string
    super(BaseDummyAdapter.PROGRAM_NAME, BaseDummyAdapter.MAIN_CLASS, "", new ArrayList<>());
  }

  // TODO abstract method in base adapter?
  public static List<String> getListOfOptions() {
    throw new UnsupportedOperationException("Should not execute");
  }

  @Override
  public void execute(Set<String> configuration, int iteration)
      throws IOException, InterruptedException {
    throw new UnsupportedOperationException("Should not execute");
  }
}