package edu.cmu.cs.mvelezce.e2e.execute.time.pw;

import edu.cmu.cs.mvelezce.e2e.execute.time.E2ETimeExecutor;
import edu.cmu.cs.mvelezce.e2e.execute.time.parser.E2ETimeExecutionParser;

import java.util.HashSet;
import java.util.Set;

public class PairWiseTimeExecutor extends E2ETimeExecutor {

  public static final String OUTPUT_DIR = E2ETimeExecutor.OUTPUT_DIR + "/java/programs/pw";

  public PairWiseTimeExecutor(String programName, E2ETimeExecutionParser e2ETimeExecutionParser) {
    super(programName, new HashSet<>(), e2ETimeExecutionParser, -1);
  }

  PairWiseTimeExecutor(
      String programName, Set<Set<String>> configurations, int waitAfterExecution) {
    super(
        programName,
        configurations,
        new E2ETimeExecutionParser(programName, OUTPUT_DIR),
        waitAfterExecution);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR;
  }
}
