package edu.cmu.cs.mvelezce.java.processor.execution.sampling.profiler.jprofiler.idta;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.java.execute.sampling.idta.profiler.jprofiler.IDTAJProfilerSamplingExecutor;
import edu.cmu.cs.mvelezce.java.processor.execution.sampling.profiler.jprofiler.JProfilerSamplingExecutionProcessor;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.RawJProfilerSamplingPerfExecution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IDTAJProfilerSamplingExecutionProcessor extends JProfilerSamplingExecutionProcessor {

  private static final String OUTPUT_DIR = IDTAJProfilerSamplingExecutor.OUTPUT_DIR;

  public IDTAJProfilerSamplingExecutionProcessor(String programName) {
    this(programName, new HashMap<>(), new HashSet<>());
  }

  IDTAJProfilerSamplingExecutionProcessor(
      String programName,
      Map<Integer, Set<RawJProfilerSamplingPerfExecution>> itersToRawPerfExecs,
      Set<JavaRegion> regions) {
    super(programName, itersToRawPerfExecs, regions);
  }

  @Override
  public String outputDir() {
    return OUTPUT_DIR;
  }
}
