package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.asm;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.ifand.IfAndAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.ifor.IfOrAdapter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Utils;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodGraph;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodGraphBuilder;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.junit.Test;

public class CFGBuilderTest {

  @Test
  public void RunningExample()
      throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
    Set<ClassNode> classNodes = this
        .getClassNodes(DynamicRunningExampleAdapter.ORIGINAL_CLASS_PATH);
    ClassNode classNode = this.getClassNode(classNodes, DynamicRunningExampleAdapter.PROGRAM_NAME);
    MethodNode methodNode = this.getMainMethod(classNode);
    String owner = classNode.name;

    MethodGraphBuilder cfgBuilder = new CFGBuilder(owner);
    MethodGraph graph = cfgBuilder.build(methodNode);
    System.out.println(graph.toDotString(Utils.getClassName(classNode)));
  }

  @Test
  public void IfAnd()
      throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
    Set<ClassNode> classNodes = this.getClassNodes(IfAndAdapter.ORIGINAL_CLASS_PATH);
    ClassNode classNode = this.getClassNode(classNodes, IfAndAdapter.PROGRAM_NAME);
    MethodNode methodNode = this.getMainMethod(classNode);
    String owner = classNode.name;

    MethodGraphBuilder cfgBuilder = new CFGBuilder(owner);
    MethodGraph graph = cfgBuilder.build(methodNode);
    System.out.println(graph.toDotString(Utils.getClassName(classNode)));
  }

  @Test
  public void IfOr()
      throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
    Set<ClassNode> classNodes = this.getClassNodes(IfOrAdapter.ORIGINAL_CLASS_PATH);
    ClassNode classNode = this.getClassNode(classNodes, IfOrAdapter.PROGRAM_NAME);
    MethodNode methodNode = this.getMainMethod(classNode);
    String owner = classNode.name;

    MethodGraphBuilder cfgBuilder = new CFGBuilder(owner);
    MethodGraph graph = cfgBuilder.build(methodNode);
    System.out.println(graph.toDotString(Utils.getClassName(classNode)));
  }

  private MethodNode getMainMethod(ClassNode classNode) {
    List<MethodNode> methodNodes = classNode.methods;

    for (MethodNode methodNode : methodNodes) {
      if (methodNode.name.equals("main") && methodNode.desc.equals("([Ljava/lang/String;)V")) {
        return methodNode;
      }
    }

    throw new RuntimeException("Could not find the main method");
  }

  private ClassNode getClassNode(Set<ClassNode> classNodes, String programName) {
    for (ClassNode classNode : classNodes) {
      if (classNode.name.contains(programName)) {
        return classNode;
      }
    }

    throw new RuntimeException("Could not find a class node for Running Example");
  }

  private Set<ClassNode> getClassNodes(String pathToClasses) {
    throw new UnsupportedOperationException("Implement");
//      throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
//    MethodTransformer transformer = new SubtracesMethodTransformer.Builder("fds", pathToClasses).build();
//    return transformer.getClassTransformer().readClasses();
  }
}