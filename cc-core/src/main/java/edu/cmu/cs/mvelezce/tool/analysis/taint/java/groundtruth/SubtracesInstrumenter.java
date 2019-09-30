package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth;

import edu.cmu.cs.mvelezce.tool.Options;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.BaseInstrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Packager;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode.MethodTransformer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

// TODO MIGUEL check what are the cases that we are not currently handling
public class SubtracesInstrumenter extends BaseInstrumenter {

  public static final String DIRECTORY =
      Options.DIRECTORY + "/analysis/spec/instrument/java/programs";

  SubtracesInstrumenter(String programName, String srcDir, String classDir) {
    super(programName, srcDir, classDir);

    System.err.println(
        "Check what are the cases that we are not currently handling when instrumenting for subtraces");
  }

  @Override
  public void instrument(String[] args)
      throws IOException, InterruptedException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException {
    Options.getCommandLine(args);

    File outputFile = new File(DIRECTORY + "/" + this.getProgramName());
    Options.checkIfDeleteResult(outputFile);

    if (outputFile.exists()) {
      return;
    }

    if (Options.checkIfDeleteResult()) {
      this.compile();
    }

    if (Options.checkIfSave()) {
      this.instrument();
    }
  }

  @Override
  public void instrument()
      throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    MethodTransformer transformer =
        new SubtracesMethodTransformer.Builder(this.getProgramName(), this.getClassDir())
            .setDebug(false)
            .build();
    transformer.transformMethods();
  }

  @Override
  public void compile() throws IOException, InterruptedException {
    Packager.packageJar(this.getSrcDir());
  }
}