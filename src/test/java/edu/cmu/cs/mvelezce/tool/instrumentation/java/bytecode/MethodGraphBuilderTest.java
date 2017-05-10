package edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode;

import edu.cmu.cs.mvelezce.java.programs.*;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.transformer.ClassTransformerReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by mvelezce on 5/3/17.
 */
public class MethodGraphBuilderTest {

    @Test
    public void testBuildMethodGraph1() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep1.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(8, methodGraph.getBlockCount());
                Assert.assertEquals(7, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph1Dash1() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep1Dash1.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                System.out.println(methodGraph.toDotString(method.name));
                Assert.assertEquals(7, methodGraph.getBlockCount());
                Assert.assertEquals(5, methodGraph.getEdgeCount());
            }
        }
    }

    @Test
    public void testBuildMethodGraph2() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep2.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                System.out.println(methodGraph.toDotString(method.name));
                Assert.assertEquals(11, methodGraph.getBlockCount());
                Assert.assertEquals(10, methodGraph.getEdgeCount());
            }
        }
    }

    @Test
    public void testBuildMethodGraph3() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep3.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(13, methodGraph.getBlockCount());
                Assert.assertEquals(13, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph4() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep4.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(8, methodGraph.getBlockCount());
                Assert.assertEquals(7, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph5() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep5.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(10, methodGraph.getBlockCount());
                Assert.assertEquals(8, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph6() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep6.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(10, methodGraph.getBlockCount());
                Assert.assertEquals(8, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph7() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep7.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(13, methodGraph.getBlockCount());
                Assert.assertEquals(13, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph8() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep8.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(13, methodGraph.getBlockCount());
                Assert.assertEquals(13, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph9() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep9.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(13, methodGraph.getBlockCount());
                Assert.assertEquals(13, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

    @Test
    public void testBuildMethodGraph10() throws IOException {
        ClassTransformerReader reader = new ClassTransformerReader();
        ClassNode classNode = reader.readClass(Sleep10.FILENAME);

        List<MethodNode> methods = classNode.methods;

        for(MethodNode method : methods) {
            if(method.name.equals("main")) {
                MethodGraph methodGraph = MethodGraphBuilder.buildMethodGraph(method);
                Assert.assertEquals(13, methodGraph.getBlockCount());
                Assert.assertEquals(13, methodGraph.getEdgeCount());
                System.out.println(methodGraph.toDotString(method.name));
            }
        }
    }

}