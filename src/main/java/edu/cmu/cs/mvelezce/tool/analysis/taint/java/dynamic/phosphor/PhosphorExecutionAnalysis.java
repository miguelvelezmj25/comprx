package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import edu.cmu.cs.mvelezce.cc.SinkEntry;
import edu.cmu.cs.mvelezce.cc.TaintInfo;
import edu.cmu.cs.mvelezce.cc.TaintLabel;
import edu.cmu.cs.mvelezce.tool.Helper;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class PhosphorExecutionAnalysis {

  private static final String PHOSPHOR_OUTPUT_DIR =
      BaseAdapter.USER_HOME
          + "/Documents/Programming/Java/Projects/phosphor/Phosphor/examples/implicit-optimized";

  private final String programName;

  PhosphorExecutionAnalysis(String programName) {
    this.programName = programName;
  }

  Set<ConfigConstraint> getSatisfiedConfigConstraintsByConfig(
      ConfigConstraint executedConfigConstraint) {
    Set<ConfigConstraint> satisfiedConfigConstraints = new HashSet<>();

    Map<String, Boolean> executedPartialConfig = executedConfigConstraint.getPartialConfig();
    Set<String> options = executedPartialConfig.keySet();
    Set<Set<String>> configs = Helper.getConfigurations(options);
    configs.remove(new HashSet<>());

    for (Set<String> config : configs) {
      ConfigConstraint configConstraint = new ConfigConstraint();

      for (String option : config) {
        configConstraint.addEntry(option, executedPartialConfig.get(option));
      }

      satisfiedConfigConstraints.add(configConstraint);
    }

    return satisfiedConfigConstraints;
  }

  Map<String, Map<Set<String>, List<Set<String>>>> analyzePhosphorResults()
      throws IOException {
    String dir = PHOSPHOR_OUTPUT_DIR + "/" + programName;
    Collection<File> serializedFiles = this.getSerializedFiles(dir);

    if (serializedFiles.size() != 1) {
      throw new RuntimeException("The directory " + dir + " must have 1 file.");
    }

    return this.readPhosphorTaintResults(serializedFiles.iterator().next());
  }

  private Collection<File> getSerializedFiles(String dir) {
    File dirFile = new File(dir);

    return FileUtils.listFiles(dirFile, null, false);
  }

  private Map<String, Map<Set<String>, List<Set<String>>>> readPhosphorTaintResults(
      File serializedFile) throws IOException {
    List<SinkEntry> sinkEntries = this.deserialize(serializedFile);
    Map<String, Map<Taint, List<Taint>>> analysisData = this.getDataFromAnalysis(sinkEntries);
    Map<String, Map<Set<TaintLabel>, List<Set<TaintLabel>>>> analysisDataWithLabels = this
        .getSinksToLabelData(analysisData);

    return this.changeLabelsToTaints(analysisDataWithLabels);
  }

  // TODO check catching and throwing
  private List<SinkEntry> deserialize(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    ObjectInputStream ois = new ObjectInputStream(fis);
    List<SinkEntry> sinkEntries;

    try {
      sinkEntries = (List<SinkEntry>) ois.readObject();

    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    ois.close();
    fis.close();

    return sinkEntries;
  }

  private Map<String, Map<Taint, List<Taint>>> getDataFromAnalysis(List<SinkEntry> sinkEntries) {
    Set<String> sinks = this.getSinksAnalysis(sinkEntries);
    Map<String, Map<Taint, List<Taint>>> sinksToTaintInfos = this.addSinksFromAnalysis(sinks);
    this.addCtxsFromAnalysis(sinksToTaintInfos, sinkEntries);
    this.addTaintsFromAnalysis(sinksToTaintInfos, sinkEntries);

    return sinksToTaintInfos;
  }

  private Set<String> getSinksAnalysis(List<SinkEntry> sinkEntries) {
    Set<String> sinks = new HashSet<>();

    for (SinkEntry sinkEntry : sinkEntries) {
      sinks.add(sinkEntry.getSink());
    }

    return sinks;
  }

  private Map<String, Map<Taint, List<Taint>>> addSinksFromAnalysis(Set<String> sinks) {
    Map<String, Map<Taint, List<Taint>>> sinksToTaintInfos = new HashMap<>();

    for (String sink : sinks) {
      sinksToTaintInfos.put(sink, new HashMap<>());
    }

    return sinksToTaintInfos;
  }

  private Map<String, Map<Set<TaintLabel>, List<Set<TaintLabel>>>> getSinksToLabelData(
      Map<String, Map<Taint, List<Taint>>> analysisData) {
    Map<String, Map<Set<TaintLabel>, List<Set<TaintLabel>>>> sinksToLabelData = new HashMap<>();

    for (Map.Entry<String, Map<Taint, List<Taint>>> entry : analysisData.entrySet()) {
      Map<Taint, List<Taint>> sinkData = entry.getValue();
      Map<Set<TaintLabel>, List<Set<TaintLabel>>> variabilityCtxsToLabels = this
          .getVariabilityCtxsToLabels(sinkData);
      sinksToLabelData.put(entry.getKey(), variabilityCtxsToLabels);
    }

    return sinksToLabelData;
  }

  private Map<String, Map<Set<String>, List<Set<String>>>> changeLabelsToTaints(
      Map<String, Map<Set<TaintLabel>, List<Set<TaintLabel>>>> sinksToLabelData) {
    Map<String, Map<Set<String>, List<Set<String>>>> sinksToTaints = new HashMap<>();

    for (Map.Entry<String, Map<Set<TaintLabel>, List<Set<TaintLabel>>>> entry : sinksToLabelData
        .entrySet()) {
      Map<Set<String>, List<Set<String>>> taintData = this
          .transformDataLabelsToTaints(entry.getValue());
      sinksToTaints.put(entry.getKey(), taintData);
    }

    return sinksToTaints;
  }

  private void addCtxsFromAnalysis(Map<String, Map<Taint, List<Taint>>> sinksToTaintInfos,
      List<SinkEntry> sinkEntries) {
    for (SinkEntry sinkEntry : sinkEntries) {
      TaintInfo taintInfo = sinkEntry.getTaintInfo();
      Taint ctx = taintInfo.getCtx();

      String sink = sinkEntry.getSink();
      Map<Taint, List<Taint>> taintInfos = sinksToTaintInfos.get(sink);
      taintInfos.put(ctx, new ArrayList<>());
    }
  }

  private void addTaintsFromAnalysis(Map<String, Map<Taint, List<Taint>>> sinksToTaintInfos,
      List<SinkEntry> sinkEntries) {
    for (SinkEntry sinkEntry : sinkEntries) {
      TaintInfo taintInfo = sinkEntry.getTaintInfo();
      Taint ctx = taintInfo.getCtx();

      String sink = sinkEntry.getSink();
      Map<Taint, List<Taint>> taintInfos = sinksToTaintInfos.get(sink);

      List<Taint> taints = taintInfos.get(ctx);
      Taint taint = taintInfo.getTaint();
      taints.add(taint);
    }
  }

  private Map<Set<TaintLabel>, List<Set<TaintLabel>>> getVariabilityCtxsToLabels(
      Map<Taint, List<Taint>> sinkData) {
    Map<Set<TaintLabel>, List<Set<TaintLabel>>> variabilityCtxsToLabels = new HashMap<>();

    for (Map.Entry<Taint, List<Taint>> entry : sinkData.entrySet()) {
      Taint variabilityCtxTaint = entry.getKey();
      Set<TaintLabel> variabilityCtx = this.getVariabilityCtx(variabilityCtxTaint);

      List<Taint> executionTaints = entry.getValue();
      List<Set<TaintLabel>> executionLabelSet = this.getExecutionLabelSet(executionTaints);

      variabilityCtxsToLabels.put(variabilityCtx, executionLabelSet);
    }

    return variabilityCtxsToLabels;
  }

  private Map<Set<String>, List<Set<String>>> transformDataLabelsToTaints(
      Map<Set<TaintLabel>, List<Set<TaintLabel>>> ctxsToTaintLabels) {
    Map<Set<String>, List<Set<String>>> ctxsToTaints = new HashMap<>();

    for (Map.Entry<Set<TaintLabel>, List<Set<TaintLabel>>> entry : ctxsToTaintLabels
        .entrySet()) {
      Set<String> ctx = this.transformLabelsToTaints(entry.getKey());
      List<Set<String>> taintSets = new ArrayList<>();

      for (Set<TaintLabel> LabelSet : entry.getValue()) {
        Set<String> taintSet = this.transformLabelsToTaints(LabelSet);
        taintSets.add(taintSet);
      }

      ctxsToTaints.put(ctx, taintSets);
    }

    return ctxsToTaints;
  }

  private Set<TaintLabel> getVariabilityCtx(Taint variabilityCtx) {
    if (variabilityCtx == null) {
      return new HashSet<>();
    }

    return variabilityCtx.getLabels();
  }

  private List<Set<TaintLabel>> getExecutionLabelSet(List<Taint> executionTaints) {
    List<Set<TaintLabel>> executionLabelSet = new ArrayList<>();

    for (Taint taint : executionTaints) {
      Set<TaintLabel> labels = taint.getLabels();
      Set<TaintLabel> executionLabels = new HashSet<>(labels);
      executionLabelSet.add(executionLabels);
    }

    return executionLabelSet;
  }

  private Set<String> transformLabelsToTaints(Set<TaintLabel> labels) {
    Set<String> strings = new HashSet<>();

    for (TaintLabel taintLabel : labels) {
      strings.add(taintLabel.getSource());
    }

    return strings;
  }

}
