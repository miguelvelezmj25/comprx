package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dummy.DummyAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.measureDiskOrderedScan.MeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.InvalidGraphException;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodBlock;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodGraph;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.asm.CFGBuilder;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.DefaultClassTransformer;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode.BaseMethodTransformer;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class SubtracesMethodTransformer extends BaseMethodTransformer {

  private final String programName;
  private final Adapter programAdapter;

  private SubtracesMethodTransformer(Builder builder)
      throws NoSuchMethodException, MalformedURLException, IllegalAccessException,
          InvocationTargetException {
    super(new DefaultClassTransformer(builder.classDir), builder.debug);
    this.programName = builder.programName;

    this.programAdapter = this.getProgramAdapter(programName);
  }

  private Adapter getProgramAdapter(String programName) {
    switch (programName) {
      case MeasureDiskOrderedScanAdapter.PROGRAM_NAME:
        return new MeasureDiskOrderedScanAdapter();
      default:
        return new DummyAdapter();
    }
  }

  @Override
  protected String getProgramName() {
    return this.programName;
  }

  @Override
  protected String getDebugDir() {
    return SubtracesInstrumenter.DIRECTORY;
  }

  @Override
  public Set<MethodNode> getMethodsToInstrument(ClassNode classNode) {
    Set<MethodNode> methodsToInstrument = new HashSet<>();

    for (MethodNode methodNode : classNode.methods) {
      if (classNode.name.equals("org/apache/lucene/core/util/packed/PackedInts")
          && methodNode.name.equals("fastestFormatAndBits")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals(
              "org/apache/lucene/core/codecs/blocktree/BlockTreeTermsWriter$TermsWriter")
          && methodNode.name.equals("pushTerm")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("org/apache/lucene/core/index/DefaultIndexingChain")
          && methodNode.name.equals("getOrAddField")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("org/apache/lucene/core/util/ByteBlockPool")
          && methodNode.name.equals("append")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("org/apache/lucene/core/analysis/CharArrayMap")
          && methodNode.name.equals("getSlot")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("org/apache/lucene/core/store/LockStressTest")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("org/apache/lucene/core/store/LockVerifyServer")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("com/sleepycat/je/tree/IN")
          && methodNode.name.equals("addToMainCache")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("com/sleepycat/je/evictor/Evictor")
          && methodNode.name.equals("getNextTarget")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      // TODO issues with subtraces labeling
      if (classNode.name.equals("com/sleepycat/je/cleaner/OffsetList")
          && methodNode.name.equals("toArray")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("com/sleepycat/je/log/FileReader")
          && methodNode.name.equals("readData")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      if (classNode.name.equals("com/sleepycat/je/tree/INTargetRep$Sparse")
          && methodNode.name.equals("copy")) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      // TODO handle methods with try catch blocks
      if (!methodNode.tryCatchBlocks.isEmpty()) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      // TODO handle methods with throw instructions
      boolean hasThrow = false;
      InsnList insnList = methodNode.instructions;
      ListIterator<AbstractInsnNode> insnListIter = insnList.iterator();

      while (insnListIter.hasNext()) {
        AbstractInsnNode insnNode = insnListIter.next();

        if (insnNode.getOpcode() == Opcodes.ATHROW) {
          hasThrow = true;
          break;
        }
      }

      if (hasThrow) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      // TODO handle methods with switch instructions
      boolean hasSwitch = false;
      insnList = methodNode.instructions;
      insnListIter = insnList.iterator();

      while (insnListIter.hasNext()) {
        AbstractInsnNode insnNode = insnListIter.next();
        int opcode = insnNode.getOpcode();

        if (opcode == Opcodes.TABLESWITCH || opcode == Opcodes.LOOKUPSWITCH) {
          hasSwitch = true;
          break;
        }
      }

      if (hasSwitch) {
        System.err.println("Ignoring A LOT of cases where we do not instrument");
        continue;
      }

      // Check if there is a jump instruction
      insnList = methodNode.instructions;
      insnListIter = insnList.iterator();

      while (insnListIter.hasNext()) {
        AbstractInsnNode insnNode = insnListIter.next();

        if (insnNode instanceof JumpInsnNode) {
          try {
            CFGBuilder.getCfg(methodNode, classNode);
          } catch (InvalidGraphException ige) {
            System.err.println(
                "Ignoring "
                    + methodNode.name
                    + " from "
                    + classNode.name
                    + " since the graph is invalid");
            break;
          }

          methodsToInstrument.add(methodNode);
          break;
        }
      }
    }

    if (this.isMainClass(classNode)) {
      System.err.println("This code to find the main class of the program is not correct");

      if (!classNode.name.equals("org/apache/lucene/core/store/LockVerifyServer")
          && !classNode.name.equals("org/apache/lucene/core/store/LockStressTest")) {
        MethodNode mainMethod = this.getMainMethod(classNode);
        methodsToInstrument.add(mainMethod);
      }
    }

    return methodsToInstrument;
  }

  private boolean isMainClass(ClassNode classNode) {
    if (programAdapter.getMainClass().isEmpty()) {

      try {
        getMainMethod(classNode);

        return true;
      } catch (RuntimeException re) {
        return false;
      }
    } else {
      String mainClass = programAdapter.getMainClass();
      mainClass = mainClass.replaceAll("\\.", "/");

      return classNode.name.equals(mainClass);
    }
  }

  private MethodNode getMainMethod(ClassNode classNode) {
    for (MethodNode methodNode : classNode.methods) {
      if (methodNode.name.equals("main") && methodNode.desc.equals("([Ljava/lang/String;)V")) {
        return methodNode;
      }
    }

    throw new RuntimeException("Could not find main method in " + classNode.name);
  }

  @Override
  public void transformMethod(MethodNode methodNode, ClassNode classNode) {
    String labelPrefix = classNode.name + "." + methodNode.name + methodNode.desc;
    //    this.instrumentCFDs(methodNode, labelPrefix);
    this.instrumentCFDEval(methodNode, labelPrefix);
    methodNode.visitMaxs(200, 200);
    // TODO do we need to instrument the end?
    this.instrumentIPDs(methodNode, classNode, labelPrefix);
    this.instrumentEndMain(methodNode, classNode);
  }

  private void instrumentCFDEval(MethodNode methodNode, String labelPrefix) {
    InsnList insnList = methodNode.instructions;
    ListIterator<AbstractInsnNode> insnListIter = insnList.iterator();
    int decisionCount = 0;

    while (insnListIter.hasNext()) {
      AbstractInsnNode insnNode = insnListIter.next();

      if (!this.isCFD(insnNode.getOpcode())) {
        continue;
      }

      InsnList loggingInsnList =
          this.getCFDEvalLoggingInsnList(labelPrefix, decisionCount, insnNode.getOpcode());
      insnList.insertBefore(insnNode, loggingInsnList);

      decisionCount++;
    }
  }

  private InsnList getCFDEvalLoggingInsnList(String labelPrefix, int decisionCount, int opcode) {
    InsnList loggingInsnList;

    switch (opcode) {
      case Opcodes.IFEQ:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFEQEval");
        break;
      case Opcodes.IFNE:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFNEEval");
        break;
      case Opcodes.IFLT:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFLTEval");
        break;
      case Opcodes.IFGE:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFGEEval");
        break;
      case Opcodes.IFGT:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFGTEval");
        break;
      case Opcodes.IFLE:
        loggingInsnList =
            this.getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFLEEval");
        break;
      case Opcodes.IF_ICMPEQ:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPEQEval");
        break;
      case Opcodes.IF_ICMPNE:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPNEEval");
        break;
      case Opcodes.IF_ICMPLT:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPLTEval");
        break;
      case Opcodes.IF_ICMPGE:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPGEEval");
        break;
      case Opcodes.IF_ICMPGT:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPGTEval");
        break;
      case Opcodes.IF_ICMPLE:
        loggingInsnList =
            this.getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ICMPLEEval");
        break;
      case Opcodes.IF_ACMPEQ:
        loggingInsnList =
            getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ACMPEQEval");
        break;
      case Opcodes.IF_ACMPNE:
        loggingInsnList =
            getIF_XXMP_COND_LoggingInsnList(labelPrefix, decisionCount, "logIF_ACMPNEEval");
        break;
      case Opcodes.IFNULL:
        loggingInsnList = getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFNULLEval");
        break;
      case Opcodes.IFNONNULL:
        loggingInsnList =
            getIF_COND_LoggingInsnList(labelPrefix, decisionCount, "logIFNONNULLEval");
        break;
      default:
        throw new UnsupportedOperationException("Implement opcode: " + opcode);
    }

    return loggingInsnList;
  }

  private InsnList getIF_COND_LoggingInsnList(
      String labelPrefix, int decisionCount, String methodName) {
    return getCFGEvalInsnList(labelPrefix, decisionCount, methodName, Opcodes.DUP);
  }

  private InsnList getIF_XXMP_COND_LoggingInsnList(
      String labelPrefix, int decisionCount, String methodName) {
    return getCFGEvalInsnList(labelPrefix, decisionCount, methodName, Opcodes.DUP2);
  }

  private InsnList getCFGEvalInsnList(
      String labelPrefix, int decisionCount, String methodName, int dupOpcode) {
    InsnList loggingInsnList = new InsnList();

    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);

    loggingInsnList.add(new InsnNode(dupOpcode));
    loggingInsnList.add(new LdcInsnNode(this.getDecisionLabelPrefix(labelPrefix, decisionCount)));
    loggingInsnList.add(
        new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            SubtracesLogger.INTERNAL_NAME,
            methodName,
            methodDescriptor,
            false));

    return loggingInsnList;
  }

  //  // TODO do not instrumented exit at return multiple times
  //  private void instrumentEndOfMethod(MethodNode methodNode, ClassNode classNode,
  //      String labelPrefix) {
  //    MethodGraph cfg = CFGBuilder.getCfg(methodNode, classNode);
  //    MethodBlock exitBlock = cfg.getExitBlock();
  //    Set<MethodBlock> preds = exitBlock.getPredecessors();
  //
  //    for (MethodBlock pred : preds) {
  //      List<AbstractInsnNode> instructions = pred.getInstructions();
  //      AbstractInsnNode returnInsnNode = this.getReturnInsnNode(instructions);
  //      InsnList savingInstructions = this.getIPDExitNodeLoggingInsnList(labelPrefix);
  //      methodNode.instructions.insertBefore(returnInsnNode, savingInstructions);
  //    }
  //  }

  private void instrumentEndMain(MethodNode methodNode, ClassNode classNode) {
    System.err.println("pass the main method to add this logic, or add the method manually");
    if (!methodNode.name.equals("main") || !methodNode.desc.equals("([Ljava/lang/String;)V")) {
      return;
    }

    MethodGraph cfg = CFGBuilder.getCfg(methodNode, classNode);
    MethodBlock exitBlock = cfg.getExitBlock();
    Set<MethodBlock> preds = exitBlock.getPredecessors();

    for (MethodBlock pred : preds) {
      List<AbstractInsnNode> instructions = pred.getInstructions();
      AbstractInsnNode returnInsnNode = this.getReturnInsnNode(instructions);
      InsnList savingInstructions = this.getEndMainLogginInsnList();
      methodNode.instructions.insertBefore(returnInsnNode, savingInstructions);
    }
  }

  //  private InsnList getEndOfMethodLogginInsnList() {
  //    InsnList saveInsnList = new InsnList();
  //
  //    String methodName = "exitAtReturn";
  //    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);
  //
  //    saveInsnList
  //        .add(new MethodInsnNode(Opcodes.INVOKESTATIC, SubtracesLogger.INTERNAL_NAME,
  //            methodName, methodDescriptor, false));
  //
  //    return saveInsnList;
  //  }

  // TODO check that this logic works in methods with multiple returns
  private InsnList getEndMainLogginInsnList() {
    InsnList saveInsnList = new InsnList();

    String methodName = "saveTrace";
    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);

    saveInsnList.add(
        new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            SubtracesLogger.INTERNAL_NAME,
            methodName,
            methodDescriptor,
            false));

    return saveInsnList;
  }

  private void instrumentIPDs(MethodNode methodNode, ClassNode classNode, String labelPrefix) {
    MethodGraph cfg = CFGBuilder.getCfg(methodNode, classNode);
    MethodBlock exitBlock = cfg.getExitBlock();
    InsnList insnList = methodNode.instructions;
    ListIterator<AbstractInsnNode> insnListIter = insnList.iterator();
    int decisionCount = 0;
    boolean instrumentedIpdExitBlock = false;

    while (insnListIter.hasNext()) {
      AbstractInsnNode insnNode = insnListIter.next();

      if (!this.isCFD(insnNode.getOpcode())) {
        continue;
      }

      MethodBlock methodBlockWithJumpInsn = this.getMethodBlockWithJumpInsn(insnNode, cfg);
      Set<MethodBlock> succs = methodBlockWithJumpInsn.getSuccessors();

      if (succs.size() < 2) {
        throw new UnsupportedOperationException(
            "In "
                + methodNode.name
                + ", the method block with the jump instruction does not have at least 2 successors. "
                + "Possibly, the control-flow decision has an empty body");
      }

      if (succs.contains(exitBlock)) {
        throw new UnsupportedOperationException("How can the successor be the exit node?");
      }

      MethodBlock ipd = cfg.getImmediatePostDominator(methodBlockWithJumpInsn);

      if (ipd.equals(exitBlock)) {
        if (instrumentedIpdExitBlock) {
          decisionCount++;
          continue;
        }

        this.instrumentIPDExitNode(cfg, labelPrefix, insnList);
        instrumentedIpdExitBlock = true;
      } else {
        this.instrumentNormalIPD(
            methodBlockWithJumpInsn, ipd, cfg, labelPrefix, decisionCount, insnList);
      }

      decisionCount++;
      cfg = CFGBuilder.getCfg(methodNode, classNode);
    }
  }

  private void instrumentNormalIPD(
      MethodBlock methodBlockWithJumpInsn,
      MethodBlock ipd,
      MethodGraph cfg,
      String labelPrefix,
      int decisionCount,
      InsnList insnList) {
    AbstractInsnNode ipdLabelInsn = ipd.getInstructions().get(0);
    Set<MethodBlock> reachables = cfg.getReachableBlocks(methodBlockWithJumpInsn, ipd);
    reachables.remove(ipd);

    LabelNode newIPDLabelNode = this.getLabelNode();
    InsnList newIPDLoggingInsnList =
        this.getNewIPDLoggingInsnList(newIPDLabelNode, labelPrefix, decisionCount);
    insnList.insertBefore(ipd.getInstructions().get(0), newIPDLoggingInsnList);

    for (MethodBlock reachable : reachables) {
      List<AbstractInsnNode> reachableInstructions = reachable.getInstructions();
      AbstractInsnNode reachableLastInstruction =
          reachableInstructions.get(reachableInstructions.size() - 1);

      if (!(reachableLastInstruction instanceof JumpInsnNode)) {
        continue;
      }

      if (!((JumpInsnNode) reachableLastInstruction).label.equals(ipdLabelInsn)) {
        continue;
      }

      JumpInsnNode newJumpInstruction =
          new JumpInsnNode(reachableLastInstruction.getOpcode(), newIPDLabelNode);
      insnList.insertBefore(reachableLastInstruction, newJumpInstruction);
      insnList.remove(reachableLastInstruction);
    }
  }

  private void instrumentIPDExitNode(MethodGraph cfg, String labelPrefix, InsnList insnList) {
    Set<MethodBlock> exitPreds = cfg.getExitBlock().getPredecessors();

    for (MethodBlock pred : exitPreds) {
      List<AbstractInsnNode> instructions = pred.getInstructions();
      AbstractInsnNode returnInsnNode = this.getReturnInsnNode(instructions);
      InsnList loggingInsnList = this.getIPDExitNodeLoggingInsnList(labelPrefix);
      insnList.insertBefore(returnInsnNode, loggingInsnList);
    }
  }

  private AbstractInsnNode getReturnInsnNode(List<AbstractInsnNode> instructions) {
    for (int i = (instructions.size() - 1); i >= 0; i--) {
      AbstractInsnNode insnNode = instructions.get(i);
      int opcode = insnNode.getOpcode();

      if (opcode == Opcodes.RET || (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
        return insnNode;
      }
    }

    throw new RuntimeException(
        "The predecessor of the exit node did not have a return instruction");
  }

  private boolean isCFD(int opcode) {
    // TODO add table switch, lookup switch
    return (opcode >= Opcodes.IFEQ && opcode <= Opcodes.IF_ACMPNE)
        || opcode == Opcodes.IFNULL
        || opcode == Opcodes.IFNONNULL;
  }

  private LabelNode getLabelNode() {
    Label label = new Label();
    return new LabelNode(label);
  }

  private InsnList getIPDExitNodeLoggingInsnList(String labelPrefix) {
    InsnList loggingInsnList = new InsnList();

    String methodName = "exitAtReturn";
    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);

    loggingInsnList.add(new LdcInsnNode(labelPrefix));
    loggingInsnList.add(
        new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            SubtracesLogger.INTERNAL_NAME,
            methodName,
            methodDescriptor,
            false));

    return loggingInsnList;
  }

  private InsnList getNewIPDLoggingInsnList(
      LabelNode labelNode, String decision, int decisionCount) {
    InsnList loggingInsnList = new InsnList();

    String methodName = "exitDecision";
    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);

    loggingInsnList.add(labelNode);
    loggingInsnList.add(new LdcInsnNode(this.getDecisionLabelPrefix(decision, decisionCount)));
    loggingInsnList.add(
        new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            SubtracesLogger.INTERNAL_NAME,
            methodName,
            methodDescriptor,
            false));

    return loggingInsnList;
  }

  private MethodBlock getMethodBlockWithJumpInsn(AbstractInsnNode insnNode, MethodGraph cfg) {
    MethodBlock entryBlock = cfg.getEntryBlock();
    MethodBlock exitBlock = cfg.getExitBlock();
    Set<MethodBlock> methodBlocks = cfg.getBlocks();

    for (MethodBlock methodBlock : methodBlocks) {
      if (methodBlock.equals(entryBlock) || methodBlock.equals(exitBlock)) {
        continue;
      }

      List<AbstractInsnNode> instructions = methodBlock.getInstructions();
      AbstractInsnNode instruction = instructions.get(instructions.size() - 1);

      if (insnNode.equals(instruction)) {
        return methodBlock;
      }
    }

    throw new RuntimeException(
        "Could not fine the jump instruction as the last instruction of a method block. Possibly, "
            + "the instructions in not in the last position of the method block, which might be "
            + "that the control-flow decision does not have a body.");
  }

  private void instrumentCFDs(MethodNode methodNode, String labelPrefix) {
    InsnList insnList = methodNode.instructions;
    ListIterator<AbstractInsnNode> insnListIter = insnList.iterator();

    int decisionCount = 0;

    while (insnListIter.hasNext()) {
      AbstractInsnNode insnNode = insnListIter.next();

      if (!this.isCFD(insnNode.getOpcode())) {
        continue;
      }

      InsnList loggingInsnList = this.getCFDLoggingInsnList(labelPrefix, decisionCount);
      insnList.insertBefore(insnNode, loggingInsnList);

      decisionCount++;
    }
  }

  private InsnList getCFDLoggingInsnList(String decision, int decisionCount) {
    InsnList loggingInsnList = new InsnList();

    String methodName = "enterDecision";
    String methodDescriptor = SubtracesLogger.getMethodDescriptor(methodName);

    loggingInsnList.add(new LdcInsnNode(this.getDecisionLabelPrefix(decision, decisionCount)));
    loggingInsnList.add(
        new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            SubtracesLogger.INTERNAL_NAME,
            methodName,
            methodDescriptor,
            false));

    return loggingInsnList;
  }

  private String getDecisionLabelPrefix(String decision, int decisionCount) {
    return decision + "." + decisionCount;
  }

  public static class Builder {

    private final String programName;
    private final String classDir;

    private boolean debug = false;

    public Builder(String programName, String classDir) {
      this.programName = programName;
      this.classDir = classDir;
    }

    public Builder setDebug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public SubtracesMethodTransformer build()
        throws InvocationTargetException, NoSuchMethodException, MalformedURLException,
            IllegalAccessException {
      return new SubtracesMethodTransformer(this);
    }
  }
}