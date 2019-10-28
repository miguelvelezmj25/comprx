package edu.cmu.cs.mvelezce.instrument.region.utils.analysis.utils.inter;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.InstrumenterUtils;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionAnalyzer.BlockRegionAnalyzer;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.comparator.edge.EdgeComparator;
import edu.cmu.cs.mvelezce.instrument.region.utils.sootAsmMethodMatcher.SootAsmMethodMatcher;
import edu.cmu.cs.mvelezce.instrumenter.graph.MethodGraph;
import edu.cmu.cs.mvelezce.instrumenter.graph.block.MethodBlock;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BaseInterAnalysisUtils<T> extends BlockRegionAnalyzer<T> {

  private final CallGraph callGraph;
  private final SootAsmMethodMatcher sootAsmMethodMatcher;

  public BaseInterAnalysisUtils(
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
  protected String debugFileName(String methodName) {
    throw new UnsupportedOperationException("Implement");
  }

  @Override
  protected String getPrettyData(@Nullable JavaRegion region) {
    throw new UnsupportedOperationException("Implement");
  }

  @Override
  protected Set<MethodBlock> processBlock(
      MethodBlock block,
      JavaRegion region,
      MethodGraph graph,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    throw new UnsupportedOperationException("Implement");
  }

  protected abstract boolean canExpandDataUp(T firstRegionData, @Nullable T callerData);

  protected abstract boolean containsAll(
      T callerDataCriteriaToRemoveNestedData, T currentCallerData);

  public Map<SootMethod, List<Edge>> getCallerSootMethodsToEdges(SootMethod sootMethod) {
    Map<SootMethod, List<Edge>> callerSootMethodsToEdges = new HashMap<>();
    Iterator<Edge> edgesInto = this.callGraph.edgesInto(sootMethod);

    while (edgesInto.hasNext()) {
      Edge edge = edgesInto.next();
      SootMethod srcMethod = edge.src();
      SootClass srcClass = srcMethod.getDeclaringClass();
      String packageName = srcClass.getPackageName();

      if (!this.sootAsmMethodMatcher.getApplicationPackages().contains(packageName)) {
        throw new RuntimeException(
            "Apparently, JRE methods could be callers to application methods. So, we used to check the callers of those JRE methods to find application methods. Not sure if this is still   relevant now");
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

  public Map<MethodNode, Set<MethodBlock>> canPropagateUpToAllCallers(
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

  public boolean callerDataCriteriaConstainsAllCallerDataOfCallee(
      T callerDataCriteriaToRemoveNestedData,
      Map<SootMethod, List<Edge>> callerSootMethodsToEdges) {
    Queue<Map.Entry<SootMethod, List<Edge>>> worklist =
        new ArrayDeque<>(callerSootMethodsToEdges.entrySet());
    // TODO might need to add analyzed edges or soot methods
    while (!worklist.isEmpty()) {
      Map.Entry<SootMethod, List<Edge>> entry = worklist.poll();
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
        T currentCallerData = this.getData(callerRegion);

        if (currentCallerData == null) {
          //          Map<SootMethod, List<Edge>> x = this.getCallerSootMethodsToEdges(edge.src());
          //          worklist.addAll(x.entrySet());
          throw new UnsupportedOperationException("Implement");
        } else if (!this.containsAll(callerDataCriteriaToRemoveNestedData, currentCallerData)) {
          return false;
        }
      }
    }

    return true;
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

  private int getSrcOpcode(Unit srcUnit) {
    Integer opcode;

    if (srcUnit instanceof JInvokeStmt) {
      InvokeExpr invokeExpr = ((JInvokeStmt) srcUnit).getInvokeExpr();
      opcode = this.sootAsmMethodMatcher.getOpcode(invokeExpr.getClass());
    } else if (srcUnit instanceof JAssignStmt) {
      InvokeExpr invokeExpr = ((JAssignStmt) srcUnit).getInvokeExpr();
      opcode = this.sootAsmMethodMatcher.getOpcode(invokeExpr.getClass());
    } else {
      throw new RuntimeException(
          "This class type of src unit calls a method " + srcUnit.getClass());
    }

    if (opcode == null) {
      throw new RuntimeException("Could not find an opcode for the instruction in " + srcUnit);
    }

    return opcode;
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
}