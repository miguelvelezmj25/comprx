package edu.cmu.cs.mvelezce.tool.instrumentation.java.transformer;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode.MethodBlock;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode.MethodGraph;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode.MethodGraphBuilder;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.tree.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by mvelezce on 4/21/17.
 */
public abstract class JavaRegionClassTransformer extends ClassTransformerBase {

    protected List<String> fileNames;
    protected Set<JavaRegion> regions;

    public JavaRegionClassTransformer(List<String> fileNames, Set<JavaRegion> regions) {
        this.fileNames = fileNames;
        this.regions = regions;
    }

    public abstract InsnList addInstructionsStartRegion(JavaRegion javaRegion);

    public abstract InsnList addInstructionsEndRegion(JavaRegion javaRegion);

    @Override
    public Set<ClassNode> transformClasses() throws IOException {
        Set<ClassNode> classNodes = new HashSet<>();

        for(String fileName : this.fileNames) {
            ClassNode classNode = this.readClass(fileName);
            this.transform(classNode);
            classNodes.add(classNode);
        }

        return classNodes;
    }

    @Override
    public void transform(ClassNode classNode) {
        String classCanonicalName = classNode.name;
        String classPackage = classCanonicalName.substring(0, classCanonicalName.lastIndexOf("/"));
        String className = classCanonicalName.substring(classCanonicalName.lastIndexOf("/") + 1);
        classPackage = classPackage.replaceAll("/", ".");
        className = className.replaceAll("/", ".");

        Set<JavaRegion> regionsInClass = this.getRegionsInClass(classPackage, className);

        for(MethodNode methodNode : classNode.methods) {
            boolean instrumentMethod = false;

            for(JavaRegion javaRegion : regionsInClass) {
                if(javaRegion.getRegionMethod().equals(methodNode.name)) {
                    instrumentMethod = true;
                    break;
                }
            }

            if(!instrumentMethod) {
                continue;
            }

            MethodGraph graph = MethodGraphBuilder.buildMethodGraph(methodNode);
            System.out.println(graph.toDotString(methodNode.name));
            List<JavaRegion> regionsInMethod = this.getRegionsInMethod(classPackage, className, methodNode.name);
            // TODO have to call this since looping through the instructions seems to set the index to 0. WEIRD
            methodNode.instructions.toArray();

            Map<AbstractInsnNode, JavaRegion> instructionsToRegion = new HashMap<>();

            for(JavaRegion region : regionsInMethod) {
                instructionsToRegion.put(methodNode.instructions.get(region.getStartBytecodeIndex()), region);
            }

            for(MethodBlock block : graph.getBlocks()) {
                List<AbstractInsnNode> blockInstructions = block.getInstructions();

                for(AbstractInsnNode instructionToStartInstrumenting : instructionsToRegion.keySet()) {
                    if(blockInstructions.contains(instructionToStartInstrumenting)) {
                        MethodBlock start = JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(graph, block);
                        MethodBlock end = graph.getImmediatePostDominator(start);
                        JavaRegion region = instructionsToRegion.get(instructionToStartInstrumenting);
                        region.setStartMethodBlock(start);
                        region.setEndMethodBlock(end);
                    }
                }
            }

            InsnList newInstructions = new InsnList();

            InsnList instructions = methodNode.instructions;
            ListIterator<AbstractInsnNode> instructionsIterator = instructions.iterator();

            while(instructionsIterator.hasNext()) {
                AbstractInsnNode instruction = instructionsIterator.next();

                if(instruction.getType() == AbstractInsnNode.LABEL) {
                    LabelNode currentLabelNode = (LabelNode) instruction;

                    for(JavaRegion javaRegion : regionsInMethod) {
                        if (javaRegion.getEndMethodBlock().getID().equals(currentLabelNode.getLabel().toString())) {
                            Label label = new Label();
                            label.info = currentLabelNode.getLabel() + "000end";
                            LabelNode endRegionLabelNode = new LabelNode(label);
                            this.updateLabels(newInstructions, currentLabelNode, endRegionLabelNode);

                            InsnList endRegionInstructions = new InsnList();
                            endRegionInstructions.add(endRegionLabelNode);
                            endRegionInstructions.add(this.addInstructionsEndRegion(javaRegion));
                            newInstructions.add(endRegionInstructions);
                        }
                    }

                    for(JavaRegion javaRegion : regionsInMethod) {
                        if(javaRegion.getStartMethodBlock().getID().equals(currentLabelNode.getLabel().toString())) {
                            Label label = new Label();
                            label.info = currentLabelNode.getLabel() + "000start";
                            LabelNode startRegionLabelNode = new LabelNode(label);
                            this.updateLabels(newInstructions, currentLabelNode, startRegionLabelNode);

                            InsnList startRegionInstructions = new InsnList();
                            startRegionInstructions.add(startRegionLabelNode);
                            startRegionInstructions.add(this.addInstructionsStartRegion(javaRegion));
                            newInstructions.add(startRegionInstructions);
                        }
                    }
                }

                newInstructions.add(instruction);
            }

            methodNode.instructions.clear();
            methodNode.instructions.add(newInstructions);
        }
    }

    public static MethodBlock getBlockToStartInstrumentingBeforeIt(MethodGraph methodGraph, MethodBlock start) {
        MethodBlock immediatePostDominator = MethodGraph.getImmediatePostDominator(methodGraph, start);
        Set<Set<MethodBlock>> stronglyConnectedComponents = MethodGraph.getStronglyConnectedComponents(methodGraph, start);
        Set<MethodBlock> problematicStronglyConnectedComponent = new HashSet<>();

        for(Set<MethodBlock> stronglyConnectedComponent : stronglyConnectedComponents) {
            if(stronglyConnectedComponent.size() > 1 && (stronglyConnectedComponent.contains(immediatePostDominator) || stronglyConnectedComponent.contains(start))) {
                problematicStronglyConnectedComponent = new HashSet<>(stronglyConnectedComponent);
                break;
            }
        }

        if(problematicStronglyConnectedComponent.isEmpty()) {
            return start;
        }

        // TODO change this name since it is now a new set
        problematicStronglyConnectedComponent.add(start);
        Iterator<MethodBlock> methodBlockIterator = problematicStronglyConnectedComponent.iterator();

        while(methodBlockIterator.hasNext()) {
            MethodBlock component = methodBlockIterator.next();
            MethodBlock immediateDominator = MethodGraph.getImmediateDominator(methodGraph, component);

            if(immediateDominator.getSuccessors().size() > 1 && immediateDominator.getSuccessors().contains(component)) {
                if(!problematicStronglyConnectedComponent.contains(immediateDominator)) {
                    problematicStronglyConnectedComponent.add(immediateDominator);
                    methodBlockIterator = problematicStronglyConnectedComponent.iterator();
                    continue;
                }
            }

            if(!problematicStronglyConnectedComponent.contains(immediateDominator)) {
                return component;
            }
        }

        throw new RuntimeException("Could not find out where to start instrumenting");
    }

    private Set<JavaRegion> getRegionsInClass(String regionPackage, String regionClass) {
        Set<JavaRegion> javaRegions = new HashSet<>();

        for(JavaRegion javaRegion : this.regions) {
            if(javaRegion.getRegionPackage().equals(regionPackage) && javaRegion.getRegionClass().equals(regionClass)) {
                javaRegions.add(javaRegion);
            }
        }

        return javaRegions;
    }

    private List<JavaRegion> getRegionsInMethod(String regionPackage, String regionClass, String regionMethod) {
        List<JavaRegion> javaRegions = new ArrayList<>();

        for(JavaRegion javaRegion : this.regions) {
            if(javaRegion.getRegionPackage().equals(regionPackage) && javaRegion.getRegionClass().equals(regionClass) && javaRegion.getRegionMethod().equals(regionMethod)) {
                javaRegions.add(javaRegion);
            }
        }

        javaRegions.sort((region1, region2) -> region2.getStartBytecodeIndex() - region1.getStartBytecodeIndex());

        return javaRegions;
    }

    private void updateLabels(InsnList newInstructions, LabelNode oldLabel, LabelNode newLabel) {
        int numberOfInstructions = newInstructions.size();
        AbstractInsnNode instruction = newInstructions.getFirst();

        for(int i = 0; i < numberOfInstructions; i++) {
            if(instruction.getType() == AbstractInsnNode.JUMP_INSN) {
                JumpInsnNode jumpInsnNode = (JumpInsnNode) instruction;

                if(jumpInsnNode.label == oldLabel) {
                    jumpInsnNode.label = newLabel;
                }
            }

            instruction = instruction.getNext();
        }
    }
}
