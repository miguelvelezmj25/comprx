package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import java.util.ListIterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

public abstract class BaseMethodGraphBuilder implements MethodGraphBuilder {

  @Override
  public MethodGraph build(MethodNode methodNode) {
    MethodGraph graph = new MethodGraph();

    if (methodNode.instructions.size() == 0) {
      return graph;
    }

    int instructionsInMethodNodeCount = this.getNumberOfInstructionsInMethodNode(methodNode);

    this.addBlocks(graph, methodNode);
    this.addInstructions(graph, methodNode);

    int instructionsInGraphCount = this.getNumberOfInstructionsInGraph(graph);

    if (instructionsInMethodNodeCount != instructionsInGraphCount) {
      throw new RuntimeException(
          "The number of instructions in the method does not match the total number of" +
              " instructions in the graph");
    }

    this.addEdges(graph, methodNode);
    this.connectEntryNode(graph, methodNode);
    this.connectExitNode(graph);

//        System.out.println(graph.toDotString(methodNode.name));

    this.checkEntryAndExitBlocks(graph);
    this.processTryCatchBlocks(graph, methodNode);

    return graph;
  }

  private void processTryCatchBlocks(MethodGraph graph, MethodNode methodNode) {
    for (TryCatchBlockNode tryCatchBlockNode : methodNode.tryCatchBlocks) {
      AbstractInsnNode handler = tryCatchBlockNode.handler;
      MethodBlock block = graph.getMethodBlock(handler);

      if (block.getPredecessors().isEmpty()) {
        block.setCatchWithImplicitThrow(true);
      }
    }
  }

  private void checkEntryAndExitBlocks(MethodGraph graph) {
    if (!graph.getEntryBlock().getPredecessors().isEmpty()) {
      throw new RuntimeException("The entry block has predecessors");
    }

    if (!graph.getExitBlock().getSuccessors().isEmpty()) {
      throw new RuntimeException("The exit block has successors");
    }

    Set<MethodBlock> exitPreds = graph.getExitBlock().getPredecessors();

    for (MethodBlock block : exitPreds) {
      if (!block.isWithReturn() && !graph.isWithWhileTrue()) {
        throw new RuntimeException("A block(" + block.getID()
            + ") connected to the exit block does not have a return instruction");
      }
    }
  }

  private int getNumberOfInstructionsInGraph(MethodGraph graph) {
    int count = 0;

    for (MethodBlock block : graph.getBlocks()) {
      count += block.getInstructions().size();
    }

    return count;
  }

  private int getNumberOfInstructionsInMethodNode(MethodNode methodNode) {
    int count = 0;
    ListIterator<AbstractInsnNode> instructionIter = methodNode.instructions.iterator();

    while (instructionIter.hasNext()) {
      instructionIter.next();
      count++;
    }

    return count;
  }

  @Override
  public void connectEntryNode(MethodGraph graph, MethodNode methodNode) {
    AbstractInsnNode instruction = methodNode.instructions.getFirst();

    if (instruction.getType() != AbstractInsnNode.LABEL) {
      throw new RuntimeException("The first instruction of the method node is not a label.");
    }

    LabelNode labelNode = (LabelNode) instruction;
    MethodBlock firstBlock = graph.getMethodBlock(labelNode);
    graph.addEdge(graph.getEntryBlock(), firstBlock);
  }

  @Override
  public void connectExitNode(MethodGraph graph) {
    for (MethodBlock methodBlock : graph.getBlocks()) {
      for (AbstractInsnNode instruction : methodBlock.getInstructions()) {
        int opcode = instruction.getOpcode();

        if (opcode == Opcodes.RET || (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
          methodBlock.setWithReturn(true);
          graph.addEdge(methodBlock, graph.getExitBlock());
        }
      }
    }

    // TODO do not hard code 3. This can happen if the method has a while(true) loop. Then there is no return
    if (graph.getBlockCount() == 3) {
      for(MethodBlock block : graph.getBlocks()) {
        if(block.isWithReturn()) {
          return;
        }
      }

      throw new UnsupportedOperationException(
          "There seems to be a special case for graphs with 3 blocks. Test it");
//      Set<MethodBlock> blocks = graph.addBlocks();
//      blocks.remove(graph.getEntryBlock());
//      blocks.remove(graph.getExitBlock());
//
//      graph.addEdge(blocks.iterator().next(), graph.getExitBlock());
//      graph.setWithWhileTrue(true);
    }
  }

}
