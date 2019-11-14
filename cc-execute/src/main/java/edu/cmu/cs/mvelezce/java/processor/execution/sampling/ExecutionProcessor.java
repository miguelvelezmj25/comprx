package edu.cmu.cs.mvelezce.java.processor.execution.sampling;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.java.processor.execution.BaseExecutionProcessor;
import edu.cmu.cs.mvelezce.java.results.processed.ProcessedPerfExecution;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.RawPerfExecution;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.snapshot.Hotspot;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.snapshot.JProfilerSnapshotEntry;
import edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.snapshot.Node;

import java.util.*;

public abstract class ExecutionProcessor extends BaseExecutionProcessor<RawPerfExecution> {

  private final Map<String, String> fullyQualifiedMethodsToRegionIds = new HashMap<>();
  private final Map<JProfilerSnapshotEntry, String> snapshotEntriesToFullyQualifiedMethods =
      new HashMap<>();

  public ExecutionProcessor(
      String programName,
      Map<Integer, Set<RawPerfExecution>> itersToRawPerfExecutions,
      Set<JavaRegion> regions) {
    super(programName, itersToRawPerfExecutions);

    this.getFullyQualifiedMethodsToRegionIds(regions);
    this.getSnapshotEntriesToFullyQualifiedMethods();
  }

  private void getSnapshotEntriesToFullyQualifiedMethods() {
    for (Map.Entry<Integer, Set<RawPerfExecution>> entry :
        this.getItersToRawPerfExecutions().entrySet()) {
      for (RawPerfExecution perfExec : entry.getValue()) {
        for (Hotspot hotspot : perfExec.getHotspots()) {
          String fullyQualifiedName =
              this.getHotspotFullyQualifiedName(
                  hotspot.getClassName(), hotspot.getMethodName(), hotspot.getMethodSignature());
          this.snapshotEntriesToFullyQualifiedMethods.putIfAbsent(hotspot, fullyQualifiedName);

          Deque<Node> worklist = new ArrayDeque<>(hotspot.getNodes());

          while (!worklist.isEmpty()) {
            Node node = worklist.pop();

            fullyQualifiedName =
                this.getHotspotFullyQualifiedName(
                    node.getClassName(), node.getMethodName(), node.getMethodSignature());
            this.snapshotEntriesToFullyQualifiedMethods.putIfAbsent(node, fullyQualifiedName);

            worklist.addAll(node.getNodes());
          }
        }
      }
    }
  }

  private void getFullyQualifiedMethodsToRegionIds(Set<JavaRegion> regions) {
    for (JavaRegion region : regions) {
      String fullyQualifiedName =
          this.getRegionFullyQualifiedName(
              region.getRegionPackage(),
              region.getRegionClass(),
              region.getRegionMethodSignature());
      this.fullyQualifiedMethodsToRegionIds.put(fullyQualifiedName, region.getId().toString());
    }
  }

  private String getRegionFullyQualifiedName(
      String packageName, String className, String methodSignature) {
    return packageName + "." + className + "." + methodSignature;
  }

  private String getHotspotFullyQualifiedName(
      String className, String methodName, String methodSignature) {
    return className + "." + methodName + methodSignature;
  }

  @Override
  protected ProcessedPerfExecution getProcessedPerfExec(RawPerfExecution rawPerfExecution) {
    Map<String, Long> regionToPerf = this.process(rawPerfExecution.getHotspots());
    Set<String> config = rawPerfExecution.getConfiguration();

    return new ProcessedPerfExecution(config, regionToPerf);
  }

  private Map<String, Long> process(List<Hotspot> hotspots) {
    Map<String, Long> regionsToPerf = this.addRegions();
    this.addPerfs(regionsToPerf, hotspots);

    return regionsToPerf;
  }

  private void addPerfs(Map<String, Long> regionsToPerf, List<Hotspot> hotspots) {
    Deque<JProfilerSnapshotEntry> stack = new ArrayDeque<>();

    for (Hotspot hotspot : hotspots) {
      stack.push(hotspot);

      while (!stack.isEmpty()) {
        JProfilerSnapshotEntry entry = stack.pop();

        String fullyQualifiedName = this.snapshotEntriesToFullyQualifiedMethods.get(entry);
        String region = this.fullyQualifiedMethodsToRegionIds.get(fullyQualifiedName);

        if (region == null) {
          for (Node node : entry.getNodes()) {
            stack.push(node);
          }
        } else {
          long currentTime = regionsToPerf.get(region);
          currentTime += entry.getTime();
          regionsToPerf.put(region, currentTime);
        }
      }
    }
  }

  private Map<String, Long> addRegions() {
    Map<String, Long> regionsToPerf = new HashMap<>();

    for (String id : this.fullyQualifiedMethodsToRegionIds.values()) {
      regionsToPerf.put(id, 0L);
    }

    return regionsToPerf;
  }
}
