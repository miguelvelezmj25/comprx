package edu.cmu.cs.mvelezce.instrument.idta.transform;

import com.mijecu25.meme.utils.gc.GC;
import com.mijecu25.meme.utils.monitor.memory.MemoryMonitor;
import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.explorer.idta.partition.Partitioning;
import edu.cmu.cs.mvelezce.instrument.idta.transform.instrumentation.IDTAMethodInstrumenter;
import edu.cmu.cs.mvelezce.instrument.region.transformer.RegionTransformer;
import edu.cmu.cs.mvelezce.instrument.region.utils.analysis.utils.inter.BaseInterAnalysisUtils;
import edu.cmu.cs.mvelezce.instrument.region.utils.analysis.utils.inter.idta.IDTAInterAnalysisUtils;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.instructionRegionMatcher.dynamic.DynamicInstructionRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.inter.BaseInterExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.inter.idta.BaseIDTAInterExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.down.BaseDownIntraExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.down.idta.IDTADownIntraExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.idta.BaseIDTAExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.up.BaseUpIntraExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.up.idta.IDTAUpIntraExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.removeRegions.inter.BaseRemoveNestedRegionsInter;
import edu.cmu.cs.mvelezce.instrument.region.utils.removeRegions.inter.idta.IDTARemoveNestedRegionsInter;
import edu.cmu.cs.mvelezce.instrument.region.utils.removeRegions.intra.BaseRemoveNestedRegionsIntra;
import edu.cmu.cs.mvelezce.instrument.region.utils.removeRegions.intra.idta.IDTARemoveNestedRegionsIntra;
import edu.cmu.cs.mvelezce.instrument.region.utils.sootAsmMethodMatcher.SootAsmMethodMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.startEndBlocksSetter.BaseStartEndRegionBlocksSetter;
import edu.cmu.cs.mvelezce.instrument.region.utils.startEndBlocksSetter.idta.IDTAStartEndRegionBlocksSetter;
import edu.cmu.cs.mvelezce.instrumenter.transform.classnode.DefaultClassTransformer;
import edu.cmu.cs.mvelezce.utils.config.Options;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class IDTAMethodTransformer extends RegionTransformer<Partitioning> {

  public static final String DEBUG_DIR =
      "../cc-instrument/" + Options.DIRECTORY + "/instrument/idta/java/programs";

  private final BaseIDTAExpander baseIDTAExpander;
  private final BaseUpIntraExpander<Partitioning> upIntraExpander;
  private final BaseDownIntraExpander<Partitioning> downIntraExpander;
  private final BaseInterExpander<Partitioning> interExpander;
  private final BaseStartEndRegionBlocksSetter<Partitioning> startEndRegionBlocksSetter;
  private final BaseRemoveNestedRegionsInter<Partitioning> removeNestedRegionsInter;
  private final IDTAMethodInstrumenter idtaMethodInstrumenter;
  private final CallGraph callGraph = null;
  private final SootAsmMethodMatcher sootAsmMethodMatcher = null;
  //  private final GlobalPartitionsImplicationCleaner globalPartitionsImplicationCleaner;

  private IDTAMethodTransformer(Builder builder)
      throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
    super(
        builder.programName,
        new DefaultClassTransformer(builder.classDir),
        builder.mainClass,
        builder.debug,
        builder.regionsToPartitions,
        new DynamicInstructionRegionMatcher());

    Options.checkIfDeleteResult(new File(DEBUG_DIR + "/" + builder.programName));

    this.baseIDTAExpander = BaseIDTAExpander.getInstance();
    this.baseIDTAExpander.init(this.getRegionsToData().values());

    this.upIntraExpander =
        new IDTAUpIntraExpander(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            this.baseIDTAExpander);
    this.downIntraExpander =
        new IDTADownIntraExpander(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            this.baseIDTAExpander);

    BaseRemoveNestedRegionsIntra<Partitioning> idtaRemoveNestedRegionsIntra =
        new IDTARemoveNestedRegionsIntra(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            this.baseIDTAExpander);
    this.startEndRegionBlocksSetter =
        new IDTAStartEndRegionBlocksSetter(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            idtaRemoveNestedRegionsIntra,
            this.baseIDTAExpander);

    //    this.sootAsmMethodMatcher = SootAsmMethodMatcher.getInstance();
    //    this.callGraph = SootCallGraphBuilder.buildCallGraph(builder.mainClass, builder.classDir);
    BaseInterAnalysisUtils<Partitioning> baseInterAnalysisUtils =
        new IDTAInterAnalysisUtils(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            this.callGraph,
            this.sootAsmMethodMatcher,
            this.baseIDTAExpander);

    this.removeNestedRegionsInter =
        new IDTARemoveNestedRegionsInter(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            this.sootAsmMethodMatcher,
            this.callGraph,
            baseInterAnalysisUtils,
            this.baseIDTAExpander);

    this.interExpander =
        new BaseIDTAInterExpander(
            builder.programName,
            DEBUG_DIR,
            builder.options,
            this.getBlockRegionMatcher(),
            this.getRegionsToData(),
            baseInterAnalysisUtils,
            this.sootAsmMethodMatcher,
            this.baseIDTAExpander);

    this.idtaMethodInstrumenter = builder.idtaMethodInstrumenter;

    //    this.globalPartitionsImplicationCleaner =
    //        new GlobalPartitionsImplicationCleaner(
    //            builder.programName,
    //            DEBUG_DIR,
    //            builder.options,
    //            this.getBlockRegionMatcher(),
    //            this.getRegionsToData(),
    //            this.baseIDTAExpander);
  }

  @Override
  protected String getDebugDir() {
    return DEBUG_DIR;
  }

  /**
   * Not instrumenting regions anymore
   *
   * @param methodNode
   * @param classNode
   */
  @Override
  public void transformMethod(MethodNode methodNode, ClassNode classNode) {
    //    LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions =
    //        this.getBlockRegionMatcher().getMethodNodesToRegionsInBlocks().get(methodNode);
    //    this.idtaMethodInstrumenter.instrument(methodNode, classNode, blocksToRegions);
  }

  /**
   * Only propagating regions intraprocedurally
   *
   * @param classNodes
   */
  @Override
  protected void transformRegions(Set<ClassNode> classNodes) {
    //    this.sootAsmMethodMatcher.init(this.callGraph, classNodes);
    //
    //    boolean propagatedRegions = true;
    //    boolean mustExitNextIter = false;
    //    classNodes = this.sootAsmMethodMatcher.getClassNodesToConsider();
    //
    //    while (propagatedRegions) {
    //      boolean propagatedIntra = this.propagateRegionsIntra(classNodes);
    //      MemoryMonitor.printMemoryUsage("Memory:");
    //      boolean propagatedInter = this.propagateRegionsInter(classNodes);
    //      MemoryMonitor.printMemoryUsage("Memory:");
    //      propagatedRegions = propagatedIntra | propagatedInter;
    //
    //      if (mustExitNextIter && (propagatedIntra || propagatedInter)) {
    //        throw new RuntimeException(
    //            "Expected to exit in the next iteration, since we did not propagate inter, but,
    // somehow we propagated intra: "
    //                + propagatedIntra
    //                + " or propagated inter: "
    //                + propagatedInter);
    //      }
    //
    //      if (!propagatedIntra) {
    //        mustExitNextIter = true;
    //      }
    //    }

    this.propagateRegionsIntra(classNodes);

    try {
      GC.gc(5_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    MemoryMonitor.printMemoryUsage("Memory:");

    //    this.removeNestedRegionsInter(classNodes);

    try {
      GC.gc(5_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    MemoryMonitor.printMemoryUsage("Memory:");

    this.setStartAndEndBlocks(classNodes);
    //    this.cleanImpliedPartitions(classNodes);
  }

  //  private void cleanImpliedPartitions(Set<ClassNode> classNodes) {
  //    int classNodesCount = classNodes.size();
  //    int processedClassNodesCount = 0;
  //
  //    for (ClassNode classNode : classNodes) {
  //      processedClassNodesCount++;
  //      System.out.println(
  //          "Class nodes still to clean implied partitions: "
  //              + (classNodesCount - processedClassNodesCount));
  //      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);
  //
  //      if (methodsToProcess.isEmpty()) {
  //        continue;
  //      }
  //
  //      for (MethodNode methodNode : methodsToProcess) {
  //        System.out.println("Cleaning partitions " + classNode.name + " - " + methodNode.name);
  //        long startTime = System.nanoTime();
  //        this.globalPartitionsImplicationCleaner.processBlocks(methodNode, classNode);
  //        long endTime = System.nanoTime();
  //        System.out.println("Time taken: " + ((endTime - startTime) / 1E6));
  //
  //        if (this.debug()) {
  //          this.globalPartitionsImplicationCleaner.debugBlockData(methodNode, classNode);
  //        }
  //      }
  //    }
  //  }

  //  private void removeNestedRegionsInter(Set<ClassNode> classNodes) {
  //    int classNodesCount = classNodes.size();
  //    int processedClassNodesCount = 0;
  //
  //    for (ClassNode classNode : classNodes) {
  //      processedClassNodesCount++;
  //      System.out.println(
  //          "Class nodes still to remove inter: " + (classNodesCount - processedClassNodesCount));
  //
  //      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);
  //
  //      if (methodsToProcess.isEmpty()) {
  //        continue;
  //      }
  //
  //      for (MethodNode methodNode : methodsToProcess) {
  //        System.out.println(
  //            "Removing nested regions inter " + classNode.name + " - " + methodNode.name);
  //        long startTime = System.nanoTime();
  //        this.removeNestedRegionsInter.processBlocks(methodNode, classNode);
  //        long endTime = System.nanoTime();
  //        System.out.println("Time taken: " + ((endTime - startTime) / 1E6));
  //      }
  //    }
  //
  //    if (!this.debug()) {
  //      return;
  //    }
  //
  //    for (ClassNode classNode : classNodes) {
  //      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);
  //
  //      if (methodsToProcess.isEmpty()) {
  //        continue;
  //      }
  //
  //      for (MethodNode methodNode : methodsToProcess) {
  //        this.removeNestedRegionsInter.debugBlockData(methodNode, classNode);
  //      }
  //    }
  //  }
  //
  //  private boolean propagateRegionsInter(Set<ClassNode> classNodes) {
  //    int classNodesCount = classNodes.size();
  //    int processedClassNodesCount = 0;
  //    boolean propagatedRegions = false;
  //
  //    //    for (ClassNode classNode : classNodes) {
  //    //      processedClassNodesCount++;
  //    //      System.out.println(
  //    //          "Class nodes still to propagate inter: " + (classNodesCount -
  //    // processedClassNodesCount));
  //    //      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);
  //    //
  //    //      if (methodsToProcess.isEmpty()) {
  //    //        continue;
  //    //      }
  //    //
  //    //      for (MethodNode methodNode : methodsToProcess) {
  //    //        System.out.println("Processing inter " + classNode.name + " - " +
  // methodNode.name);
  //    //        long startTime = System.nanoTime();
  //    //        propagatedRegions = propagatedRegions | this.expandRegionsInter(methodNode,
  //    // classNode);
  //    //        long endTime = System.nanoTime();
  //    //        System.out.println("Time taken: " + ((endTime - startTime) / 1E6));
  //    //      }
  //    //    }
  //
  //    return propagatedRegions;
  //  }
  //
  //  private boolean expandRegionsInter(MethodNode methodNode, ClassNode classNode) {
  //    return this.interExpander.processBlocks(methodNode, classNode);
  //  }

  private void setStartAndEndBlocks(Set<ClassNode> classNodes) {
    int classNodesCount = classNodes.size();
    int processedClassNodesCount = 0;

    for (ClassNode classNode : classNodes) {
      processedClassNodesCount++;
      System.out.println(
          "Class nodes still to set start and end blocks: "
              + (classNodesCount - processedClassNodesCount));
      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);

      if (methodsToProcess.isEmpty()) {
        continue;
      }

      for (MethodNode methodNode : methodsToProcess) {
        System.out.println(
            "Setting start and end blocks " + classNode.name + " - " + methodNode.name);
        long startTime = System.nanoTime();
        this.startEndRegionBlocksSetter.processBlocks(methodNode, classNode);
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + ((endTime - startTime) / 1E6) + " ms");

        if (this.debug()) {
          this.startEndRegionBlocksSetter.debugBlockData(methodNode, classNode);
        }
      }
    }
  }

  private boolean propagateRegionsIntra(Set<ClassNode> classNodes) {
    int classNodesCount = classNodes.size();
    int processedClassNodesCount = 0;
    boolean propagatedRegions = false;

    for (ClassNode classNode : classNodes) {
      processedClassNodesCount++;
      System.out.println(
          "Class nodes still to propagate intra: " + (classNodesCount - processedClassNodesCount));
      Set<MethodNode> methodsToProcess = this.getMethodsToInstrument(classNode);

      if (methodsToProcess.isEmpty()) {
        continue;
      }

      for (MethodNode methodNode : methodsToProcess) {
        System.out.println("Processing intra " + classNode.name + " - " + methodNode.name);
        long startTime = System.nanoTime();
        propagatedRegions = propagatedRegions | this.expandRegionsIntra(methodNode, classNode);
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + ((endTime - startTime) / 1E6) + " ms");
      }
    }

    return propagatedRegions;
  }

  private boolean expandRegionsIntra(MethodNode methodNode, ClassNode classNode) {
    boolean propagatedRegions = false;
    boolean updatedBlocks = true;

    while (updatedBlocks) {
      updatedBlocks = this.upIntraExpander.processBlocks(methodNode, classNode);
      updatedBlocks = updatedBlocks | this.downIntraExpander.processBlocks(methodNode, classNode);

      if (updatedBlocks) {
        propagatedRegions = true;
      }
    }

    if (this.debug()) {
      this.upIntraExpander.debugBlockData(methodNode, classNode);
      //      this.upIntraExpander.validateAllBlocksHaveRegions(methodNode, classNode);
    }

    return propagatedRegions;
  }

  public static class Builder {

    private final String programName;
    private final String mainClass;
    private final String classDir;
    private final Map<JavaRegion, Partitioning> regionsToPartitions;
    private final Set<String> options;
    private final IDTAMethodInstrumenter idtaMethodInstrumenter;

    private boolean debug = false;

    public Builder(
        String programName,
        String mainClass,
        String classDir,
        Set<String> options,
        Map<JavaRegion, Partitioning> regionsToPartitions,
        IDTAMethodInstrumenter idtaMethodInstrumenter) {
      this.programName = programName;
      this.mainClass = mainClass;
      this.classDir = classDir;
      this.regionsToPartitions = regionsToPartitions;
      this.options = options;
      this.idtaMethodInstrumenter = idtaMethodInstrumenter;
    }

    public Builder setDebug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public IDTAMethodTransformer build()
        throws InvocationTargetException, NoSuchMethodException, IOException,
            IllegalAccessException {
      return new IDTAMethodTransformer(this);
    }
  }
}
