package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.constraint;

import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.ConfigConstraint;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.PhosphorDTA;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleForExample4.SimpleForExample4Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces.SubtracesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.throwIf.ThrowIfAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class DTAConstraintAnalysisTest {

  @Test
  public void Trivial() throws IOException, InterruptedException {
    String programName = TrivialAdapter.PROGRAM_NAME;
    List<String> options = TrivialAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);

    args = new String[0];
    DTAConstraintAnalysis constraintAnalysis = new DTAConstraintAnalysis(programName);
    Set<ConfigConstraint> read = constraintAnalysis.analyze(args);

    Assert.assertFalse(read.isEmpty());
  }

  @Test
  public void Subtraces() throws IOException, InterruptedException {
    String programName = SubtracesAdapter.PROGRAM_NAME;
    List<String> options = SubtracesAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);

    args = new String[0];
    DTAConstraintAnalysis constraintAnalysis = new DTAConstraintAnalysis(programName);
    Set<ConfigConstraint> read = constraintAnalysis.analyze(args);

    Assert.assertFalse(read.isEmpty());
  }

  @Test
  public void SimpleForExample4() throws IOException, InterruptedException {
    String programName = SimpleForExample4Adapter.PROGRAM_NAME;
    List<String> options = SimpleForExample4Adapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);

    args = new String[0];
    DTAConstraintAnalysis constraintAnalysis = new DTAConstraintAnalysis(programName);
    Set<ConfigConstraint> read = constraintAnalysis.analyze(args);

    Assert.assertFalse(read.isEmpty());
  }

  @Test
  public void MeasureDiskOrderedScan() throws IOException, InterruptedException {
    String programName = MeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = MeasureDiskOrderedScanAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);

    args = new String[0];
    DTAConstraintAnalysis constraintAnalysis = new DTAConstraintAnalysis(programName);
    Set<ConfigConstraint> read = constraintAnalysis.analyze(args);

    Assert.assertFalse(read.isEmpty());
  }

  @Test
  public void ThrowIf() throws IOException, InterruptedException {
    String programName = ThrowIfAdapter.PROGRAM_NAME;
    List<String> options = ThrowIfAdapter.getListOfOptions();
    Set<String> initialConfig = new HashSet<>();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PhosphorDTA analysis = new PhosphorDTA(programName, options, initialConfig);
    analysis.analyze(args);

    args = new String[0];
    DTAConstraintAnalysis constraintAnalysis = new DTAConstraintAnalysis(programName);
    Set<ConfigConstraint> read = constraintAnalysis.analyze(args);

    Assert.assertFalse(read.isEmpty());
  }
}
