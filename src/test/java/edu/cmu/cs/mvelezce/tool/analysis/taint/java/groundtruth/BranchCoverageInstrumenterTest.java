package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth;

import edu.cmu.cs.mvelezce.tool.instrumentation.java.Utils;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode.MethodTransformer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class BranchCoverageInstrumenterTest {

  @Test
  public void runningExample()
      throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
    String pathToClasses = "../performance-mapper-evaluation/original/phosphor-examples/target/classes";
    Set<String> classesToTransform = new HashSet<>();
    classesToTransform
        .add(Utils.getASMPackageAndClassName("edu/cmu/cs/mvelezce", "RunningExample"));

    MethodTransformer transformer = new BranchCoverageInstrumenter(pathToClasses,
        classesToTransform);
    transformer.transformMethods();
  }
}