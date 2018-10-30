package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.util.Printer;

import java.util.List;

// TODO figure how to structure better the building of a graph and then the pretty graph
/**
 * Created by mvelezce on 5/3/17.
 */
public class PrettyMethodGraphBuilder extends BaseMethodGraphBuilder {

    private PrettyMethodGraph prettyGraph;
    private MethodGraph graph;
    private Printer printer;

    public PrettyMethodGraphBuilder(MethodNode methodNode, Printer printer) {
        DefaultMethodGraphBuilder builder = new DefaultMethodGraphBuilder();
        this.graph = builder.build(methodNode);
        this.printer = printer;
        this.prettyGraph = new PrettyMethodGraph();
    }

    @Override
    public PrettyMethodGraph build(MethodNode methodNode) {
        this.buildBlocks(this.graph, methodNode);
        this.addInstructions(this.graph, methodNode);
        this.addEdges(this.graph, methodNode);
        this.connectEntryNode(this.graph, methodNode);
        this.connectExitNode(this.graph);

        return this.prettyGraph;
    }

    @Override
    public void addEdges(MethodGraph graph, MethodNode methodNode) {
        for(MethodBlock block : this.graph.getBlocks()) {
            if(block == this.graph.getEntryBlock() || block == this.graph.getExitBlock()) {
                continue;
            }

            MethodBlock prettyBlock = this.prettyGraph.getMethodBlock(block.getID());

            for(MethodBlock succ : block.getSuccessors()) {
                MethodBlock prettySucc = this.prettyGraph.getMethodBlock(succ.getID());
                this.prettyGraph.addEdge(prettyBlock, prettySucc);
            }
        }
    }

    @Override
    public void buildBlocks(MethodGraph graph, MethodNode methodNode) {
        for(MethodBlock block : this.graph.getBlocks()) {
            if(block == this.graph.getEntryBlock() || block == this.graph.getExitBlock()) {
                continue;
            }

            PrettyMethodBlock prettyBlock = new PrettyMethodBlock(block.getID());
            this.prettyGraph.addMethodBlock(prettyBlock);
        }
    }

    @Override
    public void addInstructions(MethodGraph graph, MethodNode methodNode) {
        for(MethodBlock block : this.graph.getBlocks()) {
            if(block == this.graph.getEntryBlock() || block == this.graph.getExitBlock()) {
                continue;
            }

            List<AbstractInsnNode> blockInstructions = block.getInstructions();
            InsnList methodInstructions = methodNode.instructions;
            int startIndex = methodInstructions.indexOf(blockInstructions.get(0));
            // Exclusive
            int endIndex = startIndex + blockInstructions.size();

            PrettyMethodBlock prettyBlock = (PrettyMethodBlock) this.prettyGraph.getMethodBlock(block.getID());
            List<String> prettyInstructions = prettyBlock.getPrettyInstructions();
            List<Object> printerInstructions = this.printer.getText();

            int offset = 0;

            for(Object string : printerInstructions) {
                if(string.toString().contains("TRYCATCHBLOCK")) {
                    offset++;
                }
                else {
                    break;
                }
            }

            if(startIndex != 0) {
                startIndex += offset;
            }

            endIndex += offset;

            for(int i = startIndex; i < endIndex; i++) {
                String instruction = printerInstructions.get(i).toString().trim();
                prettyInstructions.add(instruction);
            }

        }
    }

    @Override
    public void connectEntryNode(MethodGraph graph, MethodNode methodNode) {
        MethodBlock entry = this.graph.getEntryBlock();
        MethodBlock prettyEntry = this.prettyGraph.getEntryBlock();

        for(MethodBlock succ : entry.getSuccessors()) {
            MethodBlock prettyBlock = this.prettyGraph.getMethodBlock(succ.getID());
            this.prettyGraph.addEdge(prettyEntry, prettyBlock);
        }
    }

    @Override
    public void connectExitNode(MethodGraph graph) {
        MethodBlock exit = this.graph.getExitBlock();
        MethodBlock prettyExit = this.prettyGraph.getExitBlock();

        for(MethodBlock pred : exit.getPredecessors()) {
            MethodBlock prettyBlock = this.prettyGraph.getMethodBlock(pred.getID());
            this.prettyGraph.addEdge(prettyBlock, prettyExit);
        }
    }
}
