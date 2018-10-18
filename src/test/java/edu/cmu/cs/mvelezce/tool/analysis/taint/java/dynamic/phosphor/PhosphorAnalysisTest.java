package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.DynamicAnalysis;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleMain;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class PhosphorAnalysisTest {

  @Test
  public void analyze_PhosphorRunningExample() throws IOException {
    String programName = DynamicRunningExampleMain.PROGRAM_NAME;

    String[] args = new String[0];

    DynamicAnalysis analysis = new PhosphorAnalysis(programName);
    analysis.analyze(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toConfig_forEmptyConstraint() {
    Map<String, Boolean> emptyConstraintToEvaluate = new HashMap<>();

    PhosphorAnalysis.toConfig(emptyConstraintToEvaluate);
  }

  @Test
  public void toConfig_forConstraint_NotA_NotB() {
    Map<String, Boolean> constraintToEvaluate = this.buildConstraint_notA_notB();

    Set<String> config = PhosphorAnalysis.toConfig(constraintToEvaluate);
    Assert.assertTrue(config.isEmpty());
  }

  @Test
  public void toConfig_forConstraint_A_notB() {
    Map<String, Boolean> constraintToEvaluate = this.buildConstraint_A_notB();

    Set<String> expectedConfig = new HashSet<>();
    expectedConfig.add("A");

    Set<String> config = PhosphorAnalysis.toConfig(constraintToEvaluate);
    Assert.assertEquals(expectedConfig, config);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNextConstraint_forEmptyConstraintToEvaluate() {
    Set<Map<String, Boolean>> emptyConstraintToEvaluate = new HashSet<>();

    PhosphorAnalysis.getNextConstraint(emptyConstraintToEvaluate);
  }

  @Test
  public void getNextConstraint_forConstraint_notA_B() {
    Map<String, Boolean> constraint_notA_b = this.buildConstraint_notA_B();

    Set<Map<String, Boolean>> constraintsToEvaluate = new HashSet<>();
    constraintsToEvaluate.add(constraint_notA_b);

    Set<String> expectedConfig = new HashSet<>();
    expectedConfig.add("B");

    Pair<Map<String, Boolean>, Set<String>> results = PhosphorAnalysis
        .getNextConstraint(constraintsToEvaluate);

    Assert.assertEquals(constraint_notA_b, results.getLeft());
    Assert.assertEquals(expectedConfig, results.getRight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildConstraints_forEmptyTaintsAtSink() {
    Set<String> emptyTaintsAtSink = new HashSet<>();

    PhosphorAnalysis.buildConstraints(emptyTaintsAtSink);
  }

  @Test
  public void buildConstraints_forTaints_A_B() {
    Map<String, Boolean> constraint_notA_notB = this.buildConstraint_notA_notB();
    Map<String, Boolean> constraint_notA_B = this.buildConstraint_notA_B();
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A_B = this.buildConstraint_A_B();

    HashSet<Map<String, Boolean>> expectedConstraints = new HashSet<>();
    expectedConstraints.add(constraint_notA_notB);
    expectedConstraints.add(constraint_notA_B);
    expectedConstraints.add(constraint_A_notB);
    expectedConstraints.add(constraint_A_B);

    Set<String> taintsAtSink = new HashSet<>();
    taintsAtSink.add("A");
    taintsAtSink.add("B");

    Set<Map<String, Boolean>> constraints = PhosphorAnalysis.buildConstraints(taintsAtSink);

    Assert.assertEquals(expectedConstraints, constraints);
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateConstraints_forEmptyTaintsAtSinks() {
    Set<Set<String>> emptyTaintsAtSinks = new HashSet<>();

    PhosphorAnalysis.calculateConstraints(emptyTaintsAtSinks);
  }

  @Test
  public void calculateConstraints_forTaints_A_B() {
    Map<String, Boolean> constraint_notA_notB = this.buildConstraint_notA_notB();
    Map<String, Boolean> constraint_notA_B = this.buildConstraint_notA_B();
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A_B = this.buildConstraint_A_B();

    HashSet<Map<String, Boolean>> expectedConstraints = new HashSet<>();
    expectedConstraints.add(constraint_notA_notB);
    expectedConstraints.add(constraint_notA_B);
    expectedConstraints.add(constraint_A_notB);
    expectedConstraints.add(constraint_A_B);

    Set<String> taintsAtSink = new HashSet<>();
    taintsAtSink.add("A");
    taintsAtSink.add("B");

    Set<Set<String>> taintsAtSinks = new HashSet<>();
    taintsAtSinks.add(taintsAtSink);

    Set<Map<String, Boolean>> constraints = PhosphorAnalysis.calculateConstraints(taintsAtSinks);

    Assert.assertEquals(expectedConstraints, constraints);
  }

  @Test
  public void getExploredConstraints_forExplored_notA_notB() {
    Map<String, Boolean> constraint_notA_notB = this.buildConstraint_notA_notB();
    Map<String, Boolean> constraint_notA_B = this.buildConstraint_notA_B();
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A_B = this.buildConstraint_A_B();
    Map<String, Boolean> constraint_notA = this.buildConstraint_notA();
    Map<String, Boolean> constraint_A = this.buildConstraint_A();

    Set<Map<String, Boolean>> currentConstraints = new HashSet<>();
    currentConstraints.add(constraint_notA_notB);
    currentConstraints.add(constraint_notA_B);
    currentConstraints.add(constraint_A_notB);
    currentConstraints.add(constraint_A_B);
    currentConstraints.add(constraint_notA);
    currentConstraints.add(constraint_A);

    Set<Map<String, Boolean>> exploredConstraints = new HashSet<>();
    exploredConstraints.add(constraint_notA_notB);

    Set<Map<String, Boolean>> currentExploredConstraints = PhosphorAnalysis
        .getExploredConstraints(currentConstraints, exploredConstraints);

    Assert.assertEquals(2, currentExploredConstraints.size());
    Assert.assertTrue(currentConstraints.contains(constraint_notA));
    Assert.assertTrue(currentConstraints.contains(constraint_notA_notB));
  }

  @Test
  public void dynamicRunningExample() throws IOException, InterruptedException {
    Set<String> initialConfig = new HashSet<>();

    Set<String> options = new HashSet<>();
    options.add("A");
    options.add("B");

    String programName = DynamicRunningExampleMain.PROGRAM_NAME;
    PhosphorAnalysis analysis = new PhosphorAnalysis(programName);
    analysis.dynamicAnalysis(initialConfig, options);
  }

  @Test
  public void getAllCombinationsOfConstraints_for3Constraints() {
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A = this.buildConstraint_A();
    Map<String, Boolean> constraint_notB = this.buildConstraint_notB();

    Set<Map<String, Boolean>> expectedExploringConstraints = new HashSet<>();
    expectedExploringConstraints.add(constraint_A_notB);
    expectedExploringConstraints.add(constraint_A);
    expectedExploringConstraints.add(constraint_notB);

    Set<Map<String, Boolean>> exploringConstraints = PhosphorAnalysis
        .getAllCombinationsOfConstraints(constraint_A_notB);

    Assert.assertEquals(3, exploringConstraints.size());
    Assert.assertEquals(expectedExploringConstraints, exploringConstraints);
  }

  @Test
  public void runPhosphorAnalysis_forDynamicRunningExample()
      throws IOException, InterruptedException {
    String programName = DynamicRunningExampleMain.PROGRAM_NAME;
    PhosphorAnalysis analysis = new PhosphorAnalysis(programName);
    Set<String> config = new HashSet<>();

    analysis.runPhosphorAnalysis(config);
  }

  @Test
  public void calculateConstraintsPerSink_for2Sinks() {
    Map<String, Set<String>> sinksToTaints = new HashMap<>();
    String sink0 = "0";
    String sink1 = "1";

    Set<String> taints0 = new HashSet<>();
    taints0.add("A");
    sinksToTaints.put(sink0, taints0);

    Set<String> taints1 = new HashSet<>();
    taints1.add("A");
    taints1.add("B");
    sinksToTaints.put(sink1, taints1);

    Set<Map<String, Boolean>> expectedConstraintsSink0 = new HashSet<>();
    Map<String, Boolean> constraint_notA = this.buildConstraint_notA();
    Map<String, Boolean> constraint_A = this.buildConstraint_A();
    expectedConstraintsSink0.add(constraint_notA);
    expectedConstraintsSink0.add(constraint_A);

    Set<Map<String, Boolean>> expectedConstraintsSink1 = new HashSet<>();
    Map<String, Boolean> constraint_notA_notB = this.buildConstraint_notA_notB();
    Map<String, Boolean> constraint_notA_B = this.buildConstraint_notA_B();
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A_B = this.buildConstraint_A_B();
    expectedConstraintsSink1.add(constraint_notA_notB);
    expectedConstraintsSink1.add(constraint_notA_B);
    expectedConstraintsSink1.add(constraint_A_notB);
    expectedConstraintsSink1.add(constraint_A_B);

    PhosphorAnalysis analysis = new PhosphorAnalysis(DynamicRunningExampleMain.PROGRAM_NAME);
    analysis.calculateConstraintsPerSink(sinksToTaints);
    Map<String, Set<Map<String, Boolean>>> sinksToConstraints = analysis.getSinksToConstraints();

    Assert.assertEquals(2, sinksToConstraints.size());

    Assert.assertTrue(sinksToConstraints.containsKey(sink0));
    Set<Map<String, Boolean>> constraintsAtSink0 = sinksToConstraints.get(sink0);
    Assert.assertEquals(expectedConstraintsSink0, constraintsAtSink0);

    Assert.assertTrue(sinksToConstraints.containsKey(sink1));
    Set<Map<String, Boolean>> constraintsAtSink1 = sinksToConstraints.get(sink1);
    Assert.assertEquals(expectedConstraintsSink1, constraintsAtSink1);

  }

  @Test
  public void toConstraint_forAllOptionsFalse() {
    Map<String, Boolean> expectedConstraint = this.buildConstraint_notA_notB();

    Set<String> config = new HashSet<>();

    Set<String> options = new HashSet<>();
    options.add("A");
    options.add("B");

    Map<String, Boolean> constraint = PhosphorAnalysis.toConstraint(config, options);

    Assert.assertEquals(expectedConstraint, constraint);
  }

  @Test
  public void toConstraint_forAllOptionsTrue() {
    Map<String, Boolean> expectedConstraint = this.buildConstraint_A_B();

    Set<String> config = new HashSet<>();
    config.add("A");
    config.add("B");

    Set<String> options = new HashSet<>();
    options.add("A");
    options.add("B");

    Map<String, Boolean> constraint = PhosphorAnalysis.toConstraint(config, options);

    Assert.assertEquals(expectedConstraint, constraint);
  }

  @Test
  public void getConfigsForCC_for2Sinks() {
    String sink0 = "0";
    String sink1 = "1";

    Map<String, Set<Map<String, Boolean>>> sinksToConstraints = new HashMap<>();

    Set<Map<String, Boolean>> constraintsSink0 = new HashSet<>();
    Map<String, Boolean> constraint_notA = this.buildConstraint_notA();
    Map<String, Boolean> constraint_A = this.buildConstraint_A();
    constraintsSink0.add(constraint_notA);
    constraintsSink0.add(constraint_A);
    sinksToConstraints.put(sink0, constraintsSink0);

    Set<Map<String, Boolean>> constraintsSink1 = new HashSet<>();
    Map<String, Boolean> constraint_notA_notB = this.buildConstraint_notA_notB();
    Map<String, Boolean> constraint_notA_B = this.buildConstraint_notA_B();
    Map<String, Boolean> constraint_A_notB = this.buildConstraint_A_notB();
    Map<String, Boolean> constraint_A_B = this.buildConstraint_A_B();
    constraintsSink1.add(constraint_notA_notB);
    constraintsSink1.add(constraint_notA_B);
    constraintsSink1.add(constraint_A_notB);
    constraintsSink1.add(constraint_A_B);
    sinksToConstraints.put(sink1, constraintsSink1);

    PhosphorAnalysis analysis = new PhosphorAnalysis(DynamicRunningExampleMain.PROGRAM_NAME);
    analysis.addSinksToConstraints(sinksToConstraints);

    Set<Set<String>> expectedConfigs = new HashSet<>();
    expectedConfigs.add(buildConfig_notA_notB());
    expectedConfigs.add(buildConfig_notA_B());
    expectedConfigs.add(buildConfig_A_notB());
    expectedConfigs.add(buildConfig_A_B());

    Set<Set<String>> ccConfigs = analysis.getConfigsForCC();

    Assert.assertEquals(expectedConfigs, ccConfigs);
  }

  @Test
  public void pickNextConstraint_forOneConstraint() {
    Map<String, Boolean> constraint_A = this.buildConstraint_A();

    Set<Map<String, Boolean>> constraintsToEvaluate = new HashSet<>();
    constraintsToEvaluate.add(constraint_A);

    Map<String, Boolean> nextConstraint = PhosphorAnalysis
        .pickNextConstraint(constraintsToEvaluate);

    Assert.assertEquals(constraint_A, nextConstraint);
  }

  private Set<String> buildConfig_A_B() {
    Set<String> config = new HashSet<>();
    config.add("A");
    config.add("B");

    return config;
  }

  private Set<String> buildConfig_A_notB() {
    Set<String> config = new HashSet<>();
    config.add("A");

    return config;
  }

  private Set<String> buildConfig_notA_B() {
    Set<String> config = new HashSet<>();
    config.add("B");

    return config;
  }

  private Set<String> buildConfig_notA_notB() {
    return new HashSet<>();
  }

  private Map<String, Boolean> buildConstraint_notB() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("B", false);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_notA() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", false);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_A() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", true);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_notA_notB() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", false);
    constraint.put("B", false);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_notA_B() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", false);
    constraint.put("B", true);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_A_notB() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", true);
    constraint.put("B", false);

    return constraint;
  }

  private Map<String, Boolean> buildConstraint_A_B() {
    Map<String, Boolean> constraint = new HashMap<>();
    constraint.put("A", true);
    constraint.put("B", true);

    return constraint;
  }

}