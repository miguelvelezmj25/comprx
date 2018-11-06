package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth;

import edu.cmu.cs.mvelezce.tool.instrumentation.java.Utils;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodBlock;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodGraph;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.asm.CFGBuilder;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.BaseClassTransformer;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.DefaultClassTransformer;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode.BaseMethodTransformer;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.analysis.AnalyzerException;

public class BranchCoverageInstrumenter extends BaseMethodTransformer {

  public BranchCoverageInstrumenter(String pathToClasses)
      throws NoSuchMethodException, MalformedURLException, IllegalAccessException, InvocationTargetException {
    super(new DefaultClassTransformer(pathToClasses));
  }

  public BranchCoverageInstrumenter(String pathToClasses, Set<String> classesToAnalyze)
      throws NoSuchMethodException, MalformedURLException, IllegalAccessException, InvocationTargetException {
    super(new BranchCoverageClassTransformer(pathToClasses, classesToAnalyze));
  }

  @Override
  public Set<MethodNode> getMethodsToInstrument(ClassNode classNode) {
    Set<MethodNode> methodsToInstrument = new HashSet<>();

    for (MethodNode methodNode : classNode.methods) {
      InsnList insnList = methodNode.instructions;
      Iterator<AbstractInsnNode> insnIter = insnList.iterator();

      while (insnIter.hasNext()) {
        AbstractInsnNode insnNode = insnIter.next();
        int opcode = insnNode.getOpcode();

        if (opcode >= Opcodes.IFEQ && opcode <= Opcodes.IF_ACMPNE) {
          methodsToInstrument.add(methodNode);
          break;
        }
      }
    }

    return methodsToInstrument;
  }

  @Override
  public void transformMethod(MethodNode methodNode, ClassNode classNode) {
    MethodGraph graph = this.getCFG(methodNode, classNode);

    String packageName = Utils.getPackageName(classNode);
    String className = Utils.getClassName(classNode);
    String methodNameAndSignature = methodNode.name + methodNode.desc;

    InsnList insnList = methodNode.instructions;
    Iterator<AbstractInsnNode> insnIter = insnList.iterator();
    int decisionCount = 0;

//    System.out.println(graph.toDotString(methodNode.name));

    while (insnIter.hasNext()) {
      AbstractInsnNode insnNode = insnIter.next();
      int opcode = insnNode.getOpcode();

      if (opcode >= Opcodes.IFEQ && opcode <= Opcodes.IF_ACMPNE) {
        decisionCount++;
        InsnList loggingInsnList;

        switch (opcode) {
          case Opcodes.IFEQ:
            loggingInsnList = this
                .getIFEQLoggingInsnList(packageName, className, methodNameAndSignature,
                    decisionCount);
            break;
          case Opcodes.IFLE:
            loggingInsnList = this
                .getIFLELoggingInsnList(packageName, className, methodNameAndSignature,
                    decisionCount);
            break;
          case Opcodes.IF_ICMPLE:
            loggingInsnList = this
                .getIFICMPLELoggingInsnList(packageName, className, methodNameAndSignature,
                    decisionCount);
            break;
          default:
            throw new UnsupportedOperationException("Implement");
        }

        insnList.insertBefore(insnNode, loggingInsnList);
      }
    }

    this.addInsnsEndMainMethod(methodNode, graph, insnList);
  }

  private void addInsnsEndMainMethod(MethodNode methodNode, MethodGraph graph, InsnList insnList) {
    if (methodNode.name.contains("main")) {
      MethodBlock exitBlock = graph.getExitBlock();
      Set<MethodBlock> preds = exitBlock.getPredecessors();

      for (MethodBlock pred : preds) {
        AbstractInsnNode predInsn = pred.getInstructions().get(0);
        insnList.insertBefore(predInsn, this.getSavingInstructions());
      }
    }
  }

  private InsnList getIFLELoggingInsnList(String packageName, String className,
      String methodNameAndSignature, int decisionCount) {
    InsnList loggingInsns = new InsnList();
    this.addIFCONDInsnsBeforeMethod(loggingInsns, packageName, className, methodNameAndSignature,
        decisionCount);

    String methodName = "logIFLEDecision";
    String methodDescriptor = BranchCoverageLogger.getMethodDescriptor(methodName);
    this.addInsnsForMethodCall(loggingInsns, methodName, methodDescriptor);

    return loggingInsns;
  }

  private InsnList getIFEQLoggingInsnList(String packageName, String className,
      String methodNameAndSignature, int decisionCount) {
    InsnList loggingInsns = new InsnList();
    this.addIFCONDInsnsBeforeMethod(loggingInsns, packageName, className, methodNameAndSignature,
        decisionCount);

    String methodName = "logIFEQDecision";
    String methodDescriptor = BranchCoverageLogger.getMethodDescriptor(methodName);
    this.addInsnsForMethodCall(loggingInsns, methodName, methodDescriptor);

    return loggingInsns;
  }

  private InsnList getIFICMPLELoggingInsnList(String packageName, String className,
      String methodNameAndSignature, int decisionCount) {
    InsnList loggingInsns = new InsnList();
    this.addIFICMPCONDInsnsBeforeMethod(loggingInsns, packageName, className, methodNameAndSignature,
        decisionCount);

    String methodName = "logIFICMPLEDecision";
    String methodDescriptor = BranchCoverageLogger.getMethodDescriptor(methodName);
    this.addInsnsForMethodCall(loggingInsns, methodName, methodDescriptor);

    return loggingInsns;
  }

  private void addInsnsForMethodCall(InsnList loggingInsns, String methodName,
      String methodDescriptor) {
    loggingInsns
        .add(new MethodInsnNode(Opcodes.INVOKESTATIC, BranchCoverageLogger.INTERNAL_NAME,
            methodName, methodDescriptor, false));
  }

  private void addIFCONDInsnsBeforeMethod(InsnList loggingInsns, String packageName, String
      className,
      String methodNameAndSignature, int decisionCount) {
    loggingInsns.add(new InsnNode(Opcodes.DUP));
    loggingInsns
        .add(new LdcInsnNode(packageName + "/" + className + "." + methodNameAndSignature));
    loggingInsns.add(new IntInsnNode(Opcodes.SIPUSH, decisionCount));
  }

  private void addIFICMPCONDInsnsBeforeMethod(InsnList loggingInsns, String packageName, String
      className,
      String methodNameAndSignature, int decisionCount) {
    loggingInsns.add(new InsnNode(Opcodes.DUP2));
    loggingInsns
        .add(new LdcInsnNode(packageName + "/" + className + "." + methodNameAndSignature));
    loggingInsns.add(new IntInsnNode(Opcodes.SIPUSH, decisionCount));
  }

  private InsnList getSavingInstructions() {
    InsnList saveInsnList = new InsnList();

    String methodName = "saveExecutedDecisions";
    String methodDescriptor = BranchCoverageLogger.getMethodDescriptor(methodName);

    saveInsnList
        .add(new MethodInsnNode(Opcodes.INVOKESTATIC, BranchCoverageLogger.INTERNAL_NAME,
            methodName, methodDescriptor, false));

    return saveInsnList;
  }

  private MethodGraph getCFG(MethodNode methodNode, ClassNode classNode) {
    CFGBuilder cfgBuilder = new CFGBuilder(classNode.name, methodNode);
    MethodGraph graph;

    try {
      graph = cfgBuilder.buildCFG();
    }
    catch (AnalyzerException ae) {
      throw new RuntimeException(
          "Could not build a control flow graph for method :" + methodNode.name, ae);
    }

    return graph;
  }

  private static class BranchCoverageClassTransformer extends BaseClassTransformer {

    BranchCoverageClassTransformer(String pathToClasses,
        Set<String> classesToTransform)
        throws InvocationTargetException, NoSuchMethodException, MalformedURLException, IllegalAccessException {
      super(pathToClasses, classesToTransform);
    }
  }
}