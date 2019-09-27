package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.constructor.ConstructorAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.implicit.ImplicitAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.indexFiles.IndexFilesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.nesting.NestingAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.orContext.OrContextAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample2.PhosphorExample2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample3.PhosphorExample3Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.prevayler.PrevaylerAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleForExample6.SimpleForExample6Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.sound.SoundAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces.SubtracesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces2.Subtraces2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces6.Subtraces6Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces7.Subtraces7Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhosphorDTATest {

  @Test
  public void Constructor() throws IOException, InterruptedException {
    String programName = ConstructorAdapter.PROGRAM_NAME;
    List<String> options = ConstructorAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Trivial() throws IOException, InterruptedException {
    String programName = TrivialAdapter.PROGRAM_NAME;
    List<String> options = TrivialAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Nesting() throws IOException, InterruptedException {
    String programName = NestingAdapter.PROGRAM_NAME;
    List<String> options = NestingAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void SimpleForExample6() throws IOException, InterruptedException {
    String programName = SimpleForExample6Adapter.PROGRAM_NAME;
    List<String> options = SimpleForExample6Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Prevayler() throws IOException, InterruptedException {
    String programName = PrevaylerAdapter.PROGRAM_NAME;
    List<String> options = PrevaylerAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces() throws IOException, InterruptedException {
    String programName = SubtracesAdapter.PROGRAM_NAME;
    List<String> options = SubtracesAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces2() throws IOException, InterruptedException {
    String programName = Subtraces2Adapter.PROGRAM_NAME;
    List<String> options = Subtraces2Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces6() throws IOException, InterruptedException {
    String programName = Subtraces6Adapter.PROGRAM_NAME;
    List<String> options = Subtraces6Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();
    initialConfig.add("A");
    //    initialConfig.add("B");

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces7() throws IOException, InterruptedException {
    String programName = Subtraces7Adapter.PROGRAM_NAME;
    List<String> options = Subtraces7Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();
    initialConfig.add("A");

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Implicit() throws IOException, InterruptedException {
    String programName = ImplicitAdapter.PROGRAM_NAME;
    List<String> options = ImplicitAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();
    initialConfig.add("A");
    initialConfig.add("B");

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void dynamicRunningExample() throws IOException, InterruptedException {
    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
    List<String> options = DynamicRunningExampleAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void orContext() throws IOException, InterruptedException {
    String programName = OrContextAdapter.PROGRAM_NAME;
    List<String> options = OrContextAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void example2() throws IOException, InterruptedException {
    String programName = PhosphorExample2Adapter.PROGRAM_NAME;
    List<String> options = PhosphorExample2Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void example3() throws IOException, InterruptedException {
    String programName = PhosphorExample3Adapter.PROGRAM_NAME;
    List<String> options = PhosphorExample3Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void Sound() throws IOException, InterruptedException {
    String programName = SoundAdapter.PROGRAM_NAME;
    List<String> options = SoundAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void MeasureDiskOrderedScan() throws IOException, InterruptedException {
    String programName = MeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = MeasureDiskOrderedScanAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  @Test
  public void indexFiles() throws IOException, InterruptedException {
    String programName = IndexFilesAdapter.PROGRAM_NAME;
    List<String> options = IndexFilesAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readDynamicRunningExample() throws IOException, InterruptedException {
  //    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
  //    String[] args = new String[0];
  //
  //    PhosphorAnalysis analysis = new PhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  //  @Test(expected = IllegalArgumentException.class)
  //  public void getNextConstraint_forEmptyConstraint() {
  //    Set<Constraint> emptyConstraints = new HashSet<>();
  //
  //    PhosphorAnalysis.getNextConstraint(emptyConstraints);
  //  }

  //  @Test
  //  public void getNextConstraint_forConstraint_notA_B() {
  //    Constraint expectedConstraint = ConstraintTest.buildConstraint_notA_B();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(expectedConstraint);
  //
  //    Constraint nextConstraint = PhosphorAnalysis.getNextConstraint(constraints);
  //
  //    Assert.assertEquals(expectedConstraint, nextConstraint);
  //  }

  //  @Test
  //  public void getNextConstraint_forConstraintsEmptyPivot() {
  //    Constraint constraint_A = ConstraintTest.buildConstraint_A();
  //    Constraint constraint_notB = ConstraintTest.buildConstraint_notB();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_A);
  //    constraints.add(constraint_notB);
  //
  //    Constraint expectedConstraint = ConstraintTest.buildConstraint_A_notB();
  //
  //    Constraint nextConstraint = PhosphorAnalysis.getNextConstraint(constraints);
  //
  //    Assert.assertTrue(expectedConstraint.isEqualTo(nextConstraint));
  //  }

  //  @Test
  //  public void getNextConstraint_forConstraintsNonEmptyPivotCannotMerge() {
  //    Constraint constraint_A_B = ConstraintTest.buildConstraint_A_B();
  //    Constraint constraint_notB = ConstraintTest.buildConstraint_notB();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_A_B);
  //    constraints.add(constraint_notB);
  //
  //    Constraint nextConstraint = PhosphorAnalysis.getNextConstraint(constraints);
  //
  //    Assert.assertTrue(
  //        constraint_A_B.isEqualTo(nextConstraint) || constraint_notB.isEqualTo(nextConstraint));
  //  }

  //  @Test
  //  public void getNextConstraint_forPivotWithDiffValues() {
  //    Constraint constraint_A_B = ConstraintTest.buildConstraint_A_B();
  //    Constraint constraint_notB_C = ConstraintTest.buildConstraint_notB_C();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_A_B);
  //    constraints.add(constraint_notB_C);
  //
  //    Constraint nextConstraint = PhosphorAnalysis.getNextConstraint(constraints);
  //
  //    Assert.assertTrue(
  //        constraint_A_B.isEqualTo(nextConstraint) ||
  // constraint_notB_C.isEqualTo(nextConstraint));
  //  }

  //  @Test
  //  public void getNextConstraint_forConstraintsNonEmptyPivot() {
  //    Constraint constraint_A_notB = ConstraintTest.buildConstraint_A_notB();
  //    Constraint constraint_notB_C = ConstraintTest.buildConstraint_notB_C();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_A_notB);
  //    constraints.add(constraint_notB_C);
  //
  //    Map<String, Boolean> expectedConstraintAsConfigWithValues = new HashMap<>();
  //    expectedConstraintAsConfigWithValues.put("A", true);
  //    expectedConstraintAsConfigWithValues.put("B", false);
  //    expectedConstraintAsConfigWithValues.put("C", true);
  //    Constraint expectedConstraint = new Constraint(expectedConstraintAsConfigWithValues);
  //
  //    Constraint nextConstraint = PhosphorAnalysis.getNextConstraint(constraints);
  //
  //    Assert.assertTrue(expectedConstraint.isEqualTo(nextConstraint));
  //  }

  //  @Test
  //  public void getConstraintsFromAnalysis_for2Sinks() {
  //    String sink0 = "0";
  //    String sink1 = "1";
  //    String A = "A";
  //    String B = "B";
  //
  //    Set<String> config = new HashSet<>();
  //    config.add(A);
  //
  //    Map<String, Set<String>> sinksToTaints = new HashMap<>();
  //    Map<String, Set<String>> sinksToCtxs = new HashMap<>();
  //
  //    Set<String> taints0 = new HashSet<>();
  //    taints0.add(A);
  //    sinksToTaints.put(sink0, taints0);
  //
  //    Set<String> ctx0 = new HashSet<>();
  //    sinksToCtxs.put(sink0, ctx0);
  //
  //    Set<String> taints1 = new HashSet<>();
  //    taints1.add(A);
  //    taints1.add(B);
  //    sinksToTaints.put(sink1, taints1);
  //
  //    Set<String> ctx1 = new HashSet<>();
  //    ctx1.add(A);
  //    sinksToCtxs.put(sink1, ctx1);
  //
  //    Pair<Map<String, Set<String>>, Map<String, Set<String>>> sinksToTaintsResults = Pair
  //        .of(sinksToTaints, sinksToCtxs);
  //
  //    PhosphorAnalysis analysis = new PhosphorAnalysis(DynamicRunningExampleAdapter.PROGRAM_NAME);
  //    Set<Constraint> constraintsFromAnalysis = analysis
  //        .getConstraintsFromAnalysis(sinksToTaintsResults, config);
  //
  //    Assert.assertEquals(4, constraintsFromAnalysis.size());
  //  }

  //  @Test
  //  public void removeInvalidConstraints_forNoInvalidConstraint() {
  //    Constraint constraint0 = ConstraintTest.buildConstraint_A();
  //    Constraint constraint1 = new Constraint(ConstraintTest.buildPartialConfig_A(),
  //        ConstraintTest.buildPartialConfig_A_notB());
  //    Constraint constraint2 = new Constraint(ConstraintTest.buildPartialConfig_notA(),
  //        ConstraintTest.buildPartialConfig_notA());
  //
  //    Set<Constraint> constrains = new HashSet<>();
  //    constrains.add(constraint0);
  //    constrains.add(constraint1);
  //    constrains.add(constraint2);
  //
  //    Set<Constraint> expectedConstraints = new HashSet<>(constrains);
  //
  //    PhosphorAnalysis.removeInvalidConstraints(constrains);
  //
  //    Assert.assertEquals(expectedConstraints, constrains);
  //  }

  //  @Test
  //  public void removeInvalidConstraints_forInvalidConstraint() {
  //    Constraint constraint0 = ConstraintTest.buildConstraint_A();
  //    Constraint constraint1 = new Constraint(ConstraintTest.buildPartialConfig_A(),
  //        ConstraintTest.buildPartialConfig_notA_B());
  //    Constraint constraint2 = new Constraint(ConstraintTest.buildPartialConfig_notA(),
  //        ConstraintTest.buildPartialConfig_A());
  //
  //    Set<Constraint> constrains = new HashSet<>();
  //    constrains.add(constraint0);
  //    constrains.add(constraint1);
  //    constrains.add(constraint2);
  //
  //    Set<Constraint> expectedConstraints = new HashSet<>();
  //    expectedConstraints.add(constraint0);
  //
  //    PhosphorAnalysis.removeInvalidConstraints(constrains);
  //
  //    Assert.assertEquals(expectedConstraints, constrains);
  //  }

  //  @Test
  //  public void removeAllSubConstraints_forRemovingSubConstraints() {
  //    Constraint constraint_notA_notB = ConstraintTest.buildConstraint_notA_notB();
  //    Constraint constraint_notB = ConstraintTest.buildConstraint_notB();
  //    Constraint constraint_notA = ConstraintTest.buildConstraint_notA();
  //    Constraint constraint_A_B = ConstraintTest.buildConstraint_A_B();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_notA_notB);
  //    constraints.add(constraint_notA);
  //    constraints.add(constraint_notB);
  //    constraints.add(constraint_A_B);
  //
  //    PhosphorAnalysis.removeAllSubConstraints(constraints, constraint_notA_notB);
  //
  //    Assert.assertEquals(1, constraints.size());
  //    Assert.assertTrue(constraints.contains(constraint_A_B));
  //  }

  //  @Test
  //  public void removeAllSubConstraints_forRemoveAll() {
  //    Constraint constraint_notA_notB = ConstraintTest.buildConstraint_notA_notB();
  //    Constraint constraint_notB = ConstraintTest.buildConstraint_notB();
  //    Constraint constraint_notA = ConstraintTest.buildConstraint_notA();
  //
  //    Set<Constraint> constraints = new HashSet<>();
  //    constraints.add(constraint_notA_notB);
  //    constraints.add(constraint_notA);
  //    constraints.add(constraint_notB);
  //
  //    PhosphorAnalysis.removeAllSubConstraints(constraints, constraint_notA_notB);
  //
  //    Assert.assertTrue(constraints.isEmpty());
  //  }

  //  @Test
  //  public void removeAllSubConstraints_forEqualConstraints() {
  //    Constraint constraint_A_B = ConstraintTest.buildConstraint_A_B();
  //    Constraint constraint_A_notB = ConstraintTest.buildConstraint_A_notB();
  //    Constraint constraint_notA_notB = ConstraintTest.buildConstraint_notA_notB();
  //    Constraint constraint_notA_B = ConstraintTest.buildConstraint_notA_B();
  //
  //    Set<Constraint> constraintsFromAnalysis = new HashSet<>();
  //    constraintsFromAnalysis.add(constraint_A_B);
  //    constraintsFromAnalysis.add(constraint_A_notB);
  //    constraintsFromAnalysis.add(constraint_notA_B);
  //    constraintsFromAnalysis.add(constraint_notA_notB);
  //
  //    Set<Constraint> exploredConstraints = new HashSet<>();
  //    exploredConstraints.add(constraint_notA_B);
  //    exploredConstraints.add(constraint_notA_notB);
  //
  //    PhosphorAnalysis.removeAllSubConstraints(constraintsFromAnalysis, exploredConstraints);
  //
  //    Assert.assertEquals(2, constraintsFromAnalysis.size());
  //    Assert.assertTrue(constraintsFromAnalysis.contains(constraint_A_B));
  //    Assert.assertTrue(constraintsFromAnalysis.contains(constraint_A_notB));
  //  }

  //  @Test
  //  public void removeAllSubConstraints_forSubsetConstraints() {
  //    Constraint constraint_A_B = ConstraintTest.buildConstraint_A_B();
  //    Constraint constraint_A_notB = ConstraintTest.buildConstraint_A_notB();
  //    Constraint constraint_notA_notB = ConstraintTest.buildConstraint_notA_notB();
  //    Constraint constraint_notA_B = ConstraintTest.buildConstraint_notA_B();
  //    Constraint constraint_A = ConstraintTest.buildConstraint_A();
  //    Constraint constraint_notA = ConstraintTest.buildConstraint_notA();
  //
  //    Set<Constraint> constraintsFromAnalysis = new HashSet<>();
  //    constraintsFromAnalysis.add(constraint_A_B);
  //    constraintsFromAnalysis.add(constraint_A);
  //    constraintsFromAnalysis.add(constraint_notA);
  //
  //    Set<Constraint> exploredConstraints = new HashSet<>();
  //    exploredConstraints.add(constraint_A_B);
  //    exploredConstraints.add(constraint_A_notB);
  //    exploredConstraints.add(constraint_notA_B);
  //    exploredConstraints.add(constraint_notA_notB);
  //
  //    PhosphorAnalysis.removeAllSubConstraints(constraintsFromAnalysis, exploredConstraints);
  //
  //    Assert.assertTrue(constraintsFromAnalysis.isEmpty());
  //  }

  //  @Test
  //  public void getConfigsForCC_for2Sinks() {
  //    String sink0 = "0";
  //    String sink1 = "1";
  //
  //    Map<String, Set<Map<String, Boolean>>> sinksToConstraints = new HashMap<>();
  //
  //    Set<Map<String, Boolean>> constraintsSink0 = new HashSet<>();
  //    Map<String, Boolean> constraint_notA = ConstraintTest.buildPartialConfig_notA();
  //    Map<String, Boolean> constraint_A = ConstraintTest.buildPartialConfig_A();
  //    constraintsSink0.add(constraint_notA);
  //    constraintsSink0.add(constraint_A);
  //    sinksToConstraints.put(sink0, constraintsSink0);
  //
  //    Set<Map<String, Boolean>> constraintsSink1 = new HashSet<>();
  //    Map<String, Boolean> constraint_notA_notB = ConstraintTest.buildPartialConfig_notA_notB();
  //    Map<String, Boolean> constraint_notA_B = ConstraintTest.buildPartialConfig_notA_B();
  //    Map<String, Boolean> constraint_A_notB = ConstraintTest.buildPartialConfig_A_notB();
  //    Map<String, Boolean> constraint_A_B = ConstraintTest.buildPartialConfig_A_B();
  //    constraintsSink1.add(constraint_notA_notB);
  //    constraintsSink1.add(constraint_notA_B);
  //    constraintsSink1.add(constraint_A_notB);
  //    constraintsSink1.add(constraint_A_B);
  //    sinksToConstraints.put(sink1, constraintsSink1);
  //
  //    PhosphorAnalysis analysis = new PhosphorAnalysis(DynamicRunningExampleAdapter.PROGRAM_NAME);
  //    analysis.addSinksToConstraints(sinksToConstraints);
  //
  //    Set<Set<String>> expectedConfigs = new HashSet<>();
  //    expectedConfigs.add(buildConfig_notA_notB());
  //    expectedConfigs.add(buildConfig_notA_B());
  //    expectedConfigs.add(buildConfig_A_notB());
  //    expectedConfigs.add(buildConfig_A_B());
  //
  //    Set<Set<String>> ccConfigs = analysis.getConfigsForCC();
  //
  //    Assert.assertEquals(expectedConfigs, ccConfigs);
  //  }

  //  private Set<String> buildConfig_A_B() {
  //    Set<String> config = new HashSet<>();
  //    config.add("A");
  //    config.add("B");
  //
  //    return config;
  //  }
  //
  //  private Set<String> buildConfig_A_notB() {
  //    Set<String> config = new HashSet<>();
  //    config.add("A");
  //
  //    return config;
  //  }
  //
  //  private Set<String> buildConfig_notA_B() {
  //    Set<String> config = new HashSet<>();
  //    config.add("B");
  //
  //    return config;
  //  }
  //
  //  private Set<String> buildConfig_notA_notB() {
  //    return new HashSet<>();
  //  }
}
