package edu.cmu.cs.mvelezce.instrument.region.utils.propagation.inter;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.InstrumenterUtils;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionAnalyzer.BlockRegionAnalyzer;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.comparator.edge.EdgeComparator;
import edu.cmu.cs.mvelezce.instrument.region.utils.graphBuilder.MethodGraphBuilder;
import edu.cmu.cs.mvelezce.instrument.region.utils.sootAsmMethodMatcher.SootAsmMethodMatcher;
import edu.cmu.cs.mvelezce.instrumenter.graph.MethodGraph;
import edu.cmu.cs.mvelezce.instrumenter.graph.block.MethodBlock;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BaseInterExpander<T> extends BlockRegionAnalyzer<T> {

  private final CallGraph callGraph;
  private final SootAsmMethodMatcher sootAsmMethodMatcher;

  public BaseInterExpander(
      String programName,
      String debugDir,
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, T> regionsToData,
      CallGraph callGraph,
      SootAsmMethodMatcher sootAsmMethodMatcher) {
    super(programName, debugDir, options, blockRegionMatcher, regionsToData);

    this.callGraph = callGraph;
    this.sootAsmMethodMatcher = sootAsmMethodMatcher;
  }

  @Override
  public boolean processBlocks(MethodNode methodNode, ClassNode classNode) {
    MethodGraph graph = MethodGraphBuilder.getMethodGraph(methodNode, classNode);

    if (!graph.isConnectedToExit(graph.getEntryBlock())) {
      throw new RuntimeException(
          "This graph is not connected to the exit block "
              + classNode.name
              + " - "
              + methodNode.name);
    }

    Set<MethodBlock> entrySuccs = graph.getEntryBlock().getSuccessors();

    if (entrySuccs.size() > 1) {
      throw new RuntimeException(
          "The entry node of this graph has multiple successors "
              + classNode.name
              + " - "
              + methodNode.name);
    }

    MethodBlock firstBlock = entrySuccs.iterator().next();
    LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions =
        this.getBlockRegionMatcher().getMethodNodesToRegionsInBlocks().get(methodNode);
    JavaRegion firstRegion = blocksToRegions.get(firstBlock);

    if (firstRegion == null) {
      return false;
    }

    T firstRegionData = this.getData(firstRegion);

    // Collecting this information will make the propagation step much simpler, since we do not need
    // to recalculate blocks to propagate
    Map<MethodNode, Set<MethodBlock>> methodsToBlocksToPropagate =
        this.getMethodsToBlocksToPropagate(firstRegionData, methodNode);

    // If the map is empty, we cannot propagate
    if (methodsToBlocksToPropagate.isEmpty()) {
      return false;
    }

    return this.expandDataUp(firstRegionData, methodsToBlocksToPropagate);
  }

  private boolean expandDataUp(
      T firstRegionData, Map<MethodNode, Set<MethodBlock>> methodsToBlocksToPropagate) {
    boolean expandedDataUp = false;

    for (Map.Entry<MethodNode, Set<MethodBlock>> entry : methodsToBlocksToPropagate.entrySet()) {
      MethodNode callerMethodNode = entry.getKey();
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions =
          this.getBlockRegionMatcher().getMethodNodesToRegionsInBlocks().get(callerMethodNode);
      Set<MethodBlock> callerBlocks = entry.getValue();

      for (MethodBlock callerBlock : callerBlocks) {
        JavaRegion callerRegion = blocksToRegions.get(callerBlock);
        T callerData = this.getData(callerRegion);

        if (firstRegionData.equals(callerData) || this.containsAll(callerData, firstRegionData)) {
          continue;
        }

        if (callerRegion == null) {
          SootMethod callerSootMethod = this.sootAsmMethodMatcher.getSootMethod(callerMethodNode);

          if (callerSootMethod == null) {
            throw new RuntimeException("Could not find a soot method for " + callerMethodNode.name);
          }

          SootClass callerSootClass = callerSootMethod.getDeclaringClass();

          callerRegion =
              new JavaRegion.Builder(
                      callerSootClass.getPackageName(),
                      callerSootClass.getShortName(),
                      InstrumenterUtils.getSootMethodSignature(callerSootMethod))
                  .build();
          blocksToRegions.put(callerBlock, callerRegion);
        }

        T newData = this.mergeData(firstRegionData, callerData);
        this.addRegionToData(callerRegion, newData);
        expandedDataUp = true;
      }
    }

    return expandedDataUp;
  }

  private Map<MethodNode, Set<MethodBlock>> getMethodsToBlocksToPropagate(
      T firstRegionData, MethodNode methodNode) {
    Map<SootMethod, List<Edge>> callerSootMethodsToEdges =
        this.getCallerSootMethodsToEdges(methodNode);

    if (callerSootMethodsToEdges.isEmpty()) {
      return new HashMap<>();
    }

    return this.canPropagateUpToAllCallers(firstRegionData, callerSootMethodsToEdges);
  }

  private Map<SootMethod, List<Edge>> getCallerSootMethodsToEdges(MethodNode methodNode) {
    SootMethod sootMethod = this.sootAsmMethodMatcher.getSootMethod(methodNode);

    if (sootMethod == null) {
      throw new RuntimeException("Could not find a soot method for " + methodNode.name);
    }

    return this.getCallerSootMethodsToEdges(sootMethod);
  }

  private Map<MethodNode, Set<MethodBlock>> canPropagateUpToAllCallers(
      T firstRegionData, Map<SootMethod, List<Edge>> callerSootMethodsToEdges) {
    Map<MethodNode, Set<MethodBlock>> methodsToBlocksToPropagate = new HashMap<>();

    for (Map.Entry<SootMethod, List<Edge>> entry : callerSootMethodsToEdges.entrySet()) {
      SootMethod sootMethod = entry.getKey();
      MethodNode methodNode = this.sootAsmMethodMatcher.getMethodNode(sootMethod);
      LinkedHashMap<MethodBlock, JavaRegion> blocks =
          this.getBlockRegionMatcher().getMethodNodesToRegionsInBlocks().get(methodNode);
      List<Edge> edges = entry.getValue();

      for (int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);
        AbstractInsnNode callerInsn = this.getCallerInsn(edge, i);
        MethodBlock callerBlock = this.getCallerBlock(blocks.keySet(), callerInsn);
        JavaRegion callerRegion = blocks.get(callerBlock);
        T callerData = this.getData(callerRegion);

        if (!this.canExpandDataUp(firstRegionData, callerData)) {
          methodsToBlocksToPropagate.clear();
          return methodsToBlocksToPropagate;
        }

        methodsToBlocksToPropagate.putIfAbsent(methodNode, new HashSet<>());
        Set<MethodBlock> blocksToPropagateTo = methodsToBlocksToPropagate.get(methodNode);
        blocksToPropagateTo.add(callerBlock);
      }
    }

    return methodsToBlocksToPropagate;
  }

  private MethodBlock getCallerBlock(Set<MethodBlock> blocks, AbstractInsnNode callerInsn) {
    for (MethodBlock block : blocks) {
      if (block.getInstructions().contains(callerInsn)) {
        return block;
      }
    }

    throw new RuntimeException(
        "Could not find the block containing the instruction " + callerInsn.getOpcode());
  }

  private AbstractInsnNode getCallerInsn(Edge edge, int invokeIndex) {
    SootMethod srcSootMethod = edge.src();
    MethodNode srcMethodNode = this.sootAsmMethodMatcher.getMethodNode(srcSootMethod);

    if (srcMethodNode == null) {
      throw new RuntimeException("Could not find a method node for " + srcSootMethod);
    }

    ListIterator<AbstractInsnNode> insnIter = srcMethodNode.instructions.iterator();

    int srcOpcode = this.getSrcOpcode(edge.srcUnit());
    int invokeCount = 0;
    SootMethod tgtSootMethod = edge.tgt();
    SootClass tgtSootClass = tgtSootMethod.getDeclaringClass();
    String tgtPackageName = tgtSootClass.getPackageName();
    String tgtClassName = tgtSootClass.getShortName();
    String tgtQualifiedClassName = tgtPackageName + "." + tgtClassName;
    String tgtSootMethodSignature = InstrumenterUtils.getSootMethodSignature(tgtSootMethod);

    while (insnIter.hasNext()) {
      AbstractInsnNode insn = insnIter.next();

      if (insn.getOpcode() != srcOpcode) {
        continue;
      }

      if (!this.matchesMethodInvocation(insn, tgtQualifiedClassName, tgtSootMethodSignature)) {
        continue;
      }

      if (invokeCount != invokeIndex) {
        invokeCount++;

        continue;
      }

      return insn;
    }

    throw new RuntimeException("Could not find the instruction " + edge);
  }

  private boolean matchesMethodInvocation(
      AbstractInsnNode invokeInsn, String tgtQualifiedClassName, String tgtMethodSignature) {
    if (!(invokeInsn instanceof MethodInsnNode)) {
      throw new RuntimeException(
          "This seems to be an invoke instruction that we needs to handle " + invokeInsn);
    }

    MethodInsnNode methodInsnNode = ((MethodInsnNode) invokeInsn);

    if (!methodInsnNode.owner.replace("/", ".").equals(tgtQualifiedClassName)) {
      return false;
    }

    return (methodInsnNode.name + methodInsnNode.desc).equals(tgtMethodSignature);
  }

  private int getSrcOpcode(Unit srcUnit) {
    if (!(srcUnit instanceof JInvokeStmt)) {
      throw new RuntimeException(
          "Expected this statement to be a method invocation, but was " + srcUnit.getClass());
    }

    InvokeExpr invokeExpr = ((JInvokeStmt) srcUnit).getInvokeExpr();
    Integer opcode = this.sootAsmMethodMatcher.getOpcode(invokeExpr.getClass());

    if (opcode == null) {
      throw new RuntimeException("Could not find an opcode for " + invokeExpr.getClass());
    }

    return opcode;
  }

  private Map<SootMethod, List<Edge>> getCallerSootMethodsToEdges(SootMethod sootMethod) {
    Map<SootMethod, List<Edge>> callerSootMethodsToEdges = new HashMap<>();
    Iterator<Edge> edgesInto = this.callGraph.edgesInto(sootMethod);

    while (edgesInto.hasNext()) {
      Edge edge = edgesInto.next();
      SootMethod srcMethod = edge.src();
      SootClass srcClass = srcMethod.getDeclaringClass();
      String packageName = srcClass.getPackageName();

      if (!this.sootAsmMethodMatcher.getApplicationPackages().contains(packageName)) {
        throw new RuntimeException(
            "Apparently, JRE methods could be callers to application methods. So, we used to check the callers of those JRE methods to find application methods. Not sure if this is still relevant now");
      }

      callerSootMethodsToEdges.putIfAbsent(srcMethod, new ArrayList<>());
      List<Edge> edges = callerSootMethodsToEdges.get(srcMethod);
      edges.add(edge);
    }

    for (Map.Entry<SootMethod, List<Edge>> entry : callerSootMethodsToEdges.entrySet()) {
      entry.getValue().sort(EdgeComparator.getInstance());
    }

    return callerSootMethodsToEdges;
  }

  @Override
  protected Set<MethodBlock> processBlock(
      MethodBlock block,
      JavaRegion region,
      MethodGraph graph,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    throw new UnsupportedOperationException("Implement");
  }

  @Override
  protected String debugFileName(String methodName) {
    throw new UnsupportedOperationException("Implement");
    //    return "expandData/" + methodName;
  }

  protected abstract boolean canExpandDataUp(T firstRegionData, @Nullable T callerData);

  protected abstract T mergeData(T firstRegionData, @Nullable T callerData);

  protected abstract boolean containsAll(@Nullable T callerData, T firstRegionData);
}
