package edu.cmu.cs.mvelezce.tool.compression.dynamic.phosphor;

import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.BFPhosphorDTA;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.PhosphorDTA;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.implicit.ImplicitAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.implicit2.Implicit2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces.SubtracesAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.subtraces2.Subtraces2Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.trivial.TrivialAdapter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class BFPhosphorCompressionTest {

  @Test
  public void RunningExample() throws IOException, InterruptedException {
    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(DynamicRunningExampleAdapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }

  @Test
  public void Subtraces() throws IOException, InterruptedException {
    String programName = SubtracesAdapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(SubtracesAdapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }

  @Test
  public void Subtraces2() throws IOException, InterruptedException {
    String programName = Subtraces2Adapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(Subtraces2Adapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }

  @Test
  public void Implicit() throws IOException, InterruptedException {
    String programName = ImplicitAdapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(ImplicitAdapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }

  @Test
  public void Implicit2() throws IOException, InterruptedException {
    String programName = Implicit2Adapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(Implicit2Adapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }

  @Test
  public void Trivial() throws IOException, InterruptedException {
    String programName = TrivialAdapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(TrivialAdapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorDTA analysis = new BFPhosphorDTA(programName);
    analysis.analyze(args);
    //    Collection<SinkData> constraints = sinkData.values();
    //
    //    args = new String[2];
    //    args[0] = "-delres";
    //    args[1] = "-saveres";
    //
    //    Compression<Set<Set<Set<String>>>> compressor = new BFPhosphorCompression(programName,
    // options,
    //        constraints);
    //    Set<Set<Set<String>>> write = compressor.compressConfigurations(args);
    //
    //    args = new String[0];
    //
    //    compressor = new BFPhosphorCompression(programName);
    //    Set<Set<Set<String>>> read = compressor.compressConfigurations(args);
    //
    //    Assert.assertEquals(write, read);
  }
}
