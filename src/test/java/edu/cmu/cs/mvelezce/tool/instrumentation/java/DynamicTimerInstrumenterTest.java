package edu.cmu.cs.mvelezce.tool.instrumentation.java;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.region.PhosphorStaticResultAnalysis;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.constructor.ConstructorAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleForExample6.SimpleForExample6Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces.SubtracesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.region.timer.DynamicConfigCrusherTimerRegionInstrumenter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class DynamicTimerInstrumenterTest {

  private void compile(String srcDir, String classDir) throws IOException, InterruptedException {
    Instrumenter compiler = new CompileInstrumenter(srcDir, classDir);
    compiler.compile();
  }

  @Test
  public void trivial()
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
          InterruptedException {
    String programName = TrivialAdapter.PROGRAM_NAME;
    String entry = TrivialAdapter.MAIN_CLASS;
    String rootPackage = TrivialAdapter.ROOT_PACKAGE;
    String srcDir = TrivialAdapter.INSTRUMENTED_DIR_PATH;
    String classDir = TrivialAdapter.INSTRUMENTED_CLASS_PATH;

    this.compile(srcDir, classDir);

    String[] args = new String[0];

    PhosphorStaticResultAnalysis analysis = new PhosphorStaticResultAnalysis(programName);
    Map<JavaRegion, Set<Set<String>>> regionsToInfluencingTaints = analysis.analyze(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    Instrumenter instrumenter =
        new DynamicConfigCrusherTimerRegionInstrumenter(
            programName, entry, rootPackage, classDir, regionsToInfluencingTaints);
    instrumenter.instrument(args);
  }

  @Test
  public void constructor()
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
          InterruptedException {
    String programName = ConstructorAdapter.PROGRAM_NAME;
    String entry = ConstructorAdapter.MAIN_CLASS;
    String rootPackage = ConstructorAdapter.ROOT_PACKAGE;
    String srcDir = ConstructorAdapter.INSTRUMENTED_DIR_PATH;
    String classDir = ConstructorAdapter.INSTRUMENTED_CLASS_PATH;

    //    this.compile(srcDir, classDir);

    String[] args = new String[0];

    PhosphorStaticResultAnalysis analysis = new PhosphorStaticResultAnalysis(programName);
    Map<JavaRegion, Set<Set<String>>> regionsToInfluencingTaints = analysis.analyze(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    Instrumenter instrumenter =
        new DynamicConfigCrusherTimerRegionInstrumenter(
            programName, entry, rootPackage, classDir, regionsToInfluencingTaints);
    instrumenter.instrument(args);
  }

  @Test
  public void simpleForExample6()
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
          InterruptedException {
    String programName = SimpleForExample6Adapter.PROGRAM_NAME;
    String entry = SimpleForExample6Adapter.MAIN_CLASS;
    String rootPackage = SimpleForExample6Adapter.ROOT_PACKAGE;
    String srcDir = SimpleForExample6Adapter.INSTRUMENTED_DIR_PATH;
    String classDir = SimpleForExample6Adapter.INSTRUMENTED_CLASS_PATH;

    this.compile(srcDir, classDir);

    String[] args = new String[0];

    PhosphorStaticResultAnalysis analysis = new PhosphorStaticResultAnalysis(programName);
    throw new UnsupportedOperationException("Implement");
    //    Map<JavaRegion, InfluencingTaints> decisionsToInfluencingTaints = analysis.analyze(args);
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Instrumenter instrumenter = new DynamicConfigCrusherTimerRegionInstrumenter(programName,
    // entry,
    //        rootPackage, classDir, decisionsToInfluencingTaints);
    //    instrumenter.instrument(args);
  }

  @Test
  public void measureDiskOrderedScan()
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
          InterruptedException {
    String programName = MeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    String entry = MeasureDiskOrderedScanAdapter.MAIN_CLASS;
    String rootPackage = MeasureDiskOrderedScanAdapter.ROOT_PACKAGE;
    String srcDir = MeasureDiskOrderedScanAdapter.INSTRUMENTED_DIR_PATH;
    String classDir = MeasureDiskOrderedScanAdapter.INSTRUMENTED_CLASS_PATH;

    //    this.compile(srcDir, classDir);

    String[] args = new String[0];

    PhosphorStaticResultAnalysis analysis = new PhosphorStaticResultAnalysis(programName);
    Map<JavaRegion, Set<Set<String>>> decisionsToInfluencingTaints = analysis.analyze(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    Instrumenter instrumenter =
        new DynamicConfigCrusherTimerRegionInstrumenter(
            programName, entry, rootPackage, classDir, decisionsToInfluencingTaints);
    instrumenter.instrument(args);
  }

  @Test
  public void subtraces()
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
          InterruptedException {
    String programName = SubtracesAdapter.PROGRAM_NAME;
    PhosphorStaticResultAnalysis analysis = new PhosphorStaticResultAnalysis(programName);
    String[] args = new String[0];
    Map<JavaRegion, Set<Set<String>>> regionsToInfluencingTaints = analysis.analyze(args);

    String srcDir = SubtracesAdapter.INSTRUMENTED_DIR_PATH;
    String classDir = SubtracesAdapter.INSTRUMENTED_CLASS_PATH;
    this.compile(srcDir, classDir);

    String entry = SubtracesAdapter.MAIN_CLASS;
    String rootPackage = SubtracesAdapter.ROOT_PACKAGE;
    Instrumenter instrumenter =
        new DynamicConfigCrusherTimerRegionInstrumenter(
            programName, entry, rootPackage, classDir, regionsToInfluencingTaints);
    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";
    instrumenter.instrument(args);
  }
}
