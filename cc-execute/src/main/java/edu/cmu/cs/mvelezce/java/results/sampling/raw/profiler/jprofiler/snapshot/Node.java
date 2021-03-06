package edu.cmu.cs.mvelezce.java.results.sampling.raw.profiler.jprofiler.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Node implements JProfilerSnapshotEntry {

  private final boolean leaf;
  private final String className;
  private final String methodName;
  private final String methodSignature;
  private final long time;
  private final int count;
  private final int lineNumber;
  private final double percent;
  private final boolean filtered;
  private final List<Node> nodes;

  // Dummy constructor for jackson xml
  private Node() {
    this.leaf = false;
    this.className = "";
    this.methodName = "";
    this.methodSignature = "";
    this.time = 0;
    this.count = 0;
    this.lineNumber = 0;
    this.percent = 0.0;
    this.filtered = false;
    this.nodes = new ArrayList<>();
  }

  public Node(
      boolean leaf,
      String className,
      String methodName,
      String methodSignature,
      long time,
      int count,
      int lineNumber,
      double percent,
      boolean filtered,
      List<Node> nodes) {
    this.leaf = leaf;
    this.className = className;
    this.methodName = methodName;
    this.methodSignature = methodSignature;
    this.time = time;
    this.count = count;
    this.lineNumber = lineNumber;
    this.percent = percent;
    this.filtered = filtered;
    this.nodes = nodes;
  }

  @Override
  public boolean getLeaf() {
    return leaf;
  }

  @Override
  public String getMethodName() {
    return methodName;
  }

  @Override
  public String getMethodSignature() {
    return methodSignature;
  }

  @Override
  public long getTime() {
    return time;
  }

  @Override
  public int getCount() {
    return count;
  }

  @Override
  public int getLineNumber() {
    return lineNumber;
  }

  @Override
  public double getPercent() {
    return percent;
  }

  @Override
  public List<Node> getNodes() {
    return nodes;
  }

  @Override
  public boolean getFiltered() {
    return filtered;
  }

  @JsonProperty("class")
  @Override
  public String getClassName() {
    return className;
  }
}
