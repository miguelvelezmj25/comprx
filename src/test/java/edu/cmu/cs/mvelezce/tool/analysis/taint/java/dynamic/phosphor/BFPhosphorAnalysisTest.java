package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.alldynamic.AllDynamicAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.example1.Example1Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.ifOr2.IfOr2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.implicit.ImplicitAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.implicit2.Implicit2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.multifacets.MultiFacetsAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.orContext.OrContextAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.orContext2.OrContext2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.orContext3.OrContext3Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.orContext6.OrContext6Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample2.PhosphorExample2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample3.PhosphorExample3Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.phosphorExample8.PhosphorExample8Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.prevayler.PrevaylerAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleForExample2.SimpleForExample2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleForExample5.SimpleForExample5Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.simpleexample1.SimpleExample1Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces.SubtracesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces2.Subtraces2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.variabilityContext1.VariabilityContext1Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.variabilityContext2.VariabilityContext2Adapter;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class BFPhosphorAnalysisTest {

  @Test
  public void dynamicAll() {
    Collection<File> files =
        FileUtils.listFiles(
            new File(
                "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/phosphor-examples/src/main/java/edu/cmu/cs/mvelezce/analysis"),
            new String[] {"java"},
            true);

    Set<String> programsWithErrors = new HashSet<>();

    for (File file : files) {
      String programName = file.getName();
      programName = programName.replace(".java", "");
      System.out.println(programName);

      try {
        String mainClass = AllDynamicAdapter.MAIN_PACKAGE + "." + programName;
        List<String> options = AllDynamicAdapter.getListOfOptions();

        String[] args = new String[1];
        //        String[] args = new String[2];
        args[0] = "-saveres";
        //        args[1] = "-delres";

        BFPhosphorDTA analysis = new BFPhosphorDTA(programName, mainClass, options);
        analysis.analyze(args);
      } catch (Exception e) {
        programsWithErrors.add(programName);
      }
    }

    System.out.println();
    System.out.println("####################################");
    System.out.println("Programs with errors");
    System.out.println(programsWithErrors);
  }

  @Test
  public void RunningExample() throws IOException, InterruptedException {
    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
    List<String> options = DynamicRunningExampleAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces() throws IOException, InterruptedException {
    String programName = SubtracesAdapter.PROGRAM_NAME;
    List<String> options = SubtracesAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Subtraces2() throws IOException, InterruptedException {
    String programName = Subtraces2Adapter.PROGRAM_NAME;
    List<String> options = Subtraces2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Implicit() throws IOException, InterruptedException {
    String programName = ImplicitAdapter.PROGRAM_NAME;
    List<String> options = ImplicitAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Implicit2() throws IOException, InterruptedException {
    String programName = Implicit2Adapter.PROGRAM_NAME;
    List<String> options = Implicit2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Trivial() throws IOException, InterruptedException {
    String programName = TrivialAdapter.PROGRAM_NAME;
    List<String> options = TrivialAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readDynamicRunningExample() throws IOException, InterruptedException {
  //    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void simpleExample1() throws IOException, InterruptedException {
    String programName = SimpleExample1Adapter.PROGRAM_NAME;
    List<String> options = SimpleExample1Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void example1() throws IOException, InterruptedException {
    String programName = Example1Adapter.PROGRAM_NAME;
    List<String> options = Example1Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void example2() throws IOException, InterruptedException {
    String programName = PhosphorExample2Adapter.PROGRAM_NAME;
    List<String> options = PhosphorExample2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void example3() throws IOException, InterruptedException {
    String programName = PhosphorExample3Adapter.PROGRAM_NAME;
    List<String> options = PhosphorExample3Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readExample3() throws IOException, InterruptedException {
  //    String programName = PhosphorExample3Adapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void ifOr2() throws IOException, InterruptedException {
    String programName = IfOr2Adapter.PROGRAM_NAME;
    List<String> options = IfOr2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readIfOr2() throws IOException, InterruptedException {
  //    String programName = IfOr2Adapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void orContext() throws IOException, InterruptedException {
    String programName = OrContextAdapter.PROGRAM_NAME;
    List<String> options = OrContextAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readOrContext() throws IOException, InterruptedException {
  //    String programName = OrContextAdapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void orContext2() throws IOException, InterruptedException {
    String programName = OrContext2Adapter.PROGRAM_NAME;
    List<String> options = OrContext2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readOrContext2() throws IOException, InterruptedException {
  //    String programName = OrContext2Adapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void orContext3() throws IOException, InterruptedException {
    String programName = OrContext3Adapter.PROGRAM_NAME;
    List<String> options = OrContext3Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readOrContext3() throws IOException, InterruptedException {
  //    String programName = OrContext3Adapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void multiFacet() throws IOException, InterruptedException {
    String programName = MultiFacetsAdapter.PROGRAM_NAME;
    List<String> options = MultiFacetsAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void simpleForExample2() throws IOException, InterruptedException {
    String programName = SimpleForExample2Adapter.PROGRAM_NAME;
    List<String> options = SimpleForExample2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void simpleForExample5() throws IOException, InterruptedException {
    String programName = SimpleForExample5Adapter.PROGRAM_NAME;
    List<String> options = SimpleForExample5Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void variabilityContext1() throws IOException, InterruptedException {
    String programName = VariabilityContext1Adapter.PROGRAM_NAME;
    List<String> options = VariabilityContext1Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void variabilityContext2() throws IOException, InterruptedException {
    String programName = VariabilityContext2Adapter.PROGRAM_NAME;
    List<String> options = VariabilityContext2Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void example8() throws IOException, InterruptedException {
    String programName = PhosphorExample8Adapter.PROGRAM_NAME;
    List<String> options = PhosphorExample8Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  //  @Test
  //  public void readExample8() throws IOException, InterruptedException {
  //    String programName = PhosphorExample8Adapter.PROGRAM_NAME;
  //
  //    String[] args = new String[0];
  //    BFPhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
  //    Map<JavaRegion, SinkData> read = analysis.analyze(args);
  //    PhosphorAnalysis.printProgramConstraints(read);
  //  }

  @Test
  public void orContext6() throws IOException, InterruptedException {
    String programName = OrContext6Adapter.PROGRAM_NAME;
    List<String> options = OrContext6Adapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void Prevayler() throws IOException, InterruptedException {
    String programName = PrevaylerAdapter.PROGRAM_NAME;
    List<String> options = PrevaylerAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }

  @Test
  public void MeasureDiskOrderedScan() throws IOException, InterruptedException {
    String programName = MeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = MeasureDiskOrderedScanAdapter.getListOfOptions();

    // Program arguments
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    BFPhosphorDTA analysis = new BFPhosphorDTA(programName, options);
    analysis.analyze(args);
  }
}
