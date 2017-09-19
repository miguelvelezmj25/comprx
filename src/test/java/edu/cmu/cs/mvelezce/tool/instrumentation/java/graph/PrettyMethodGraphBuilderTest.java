package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import counter.com.googlecode.pngtastic.Run;
import edu.cmu.cs.mvelezce.Example;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode.MethodTracer;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.bytecode.TraceClassInspector;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.ClassTransformer;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.DefaultBaseClassTransformer;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

public class PrettyMethodGraphBuilderTest {

    @Test
    public void runningExample() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        String path = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/running-example/target/classes";
        String className = Example.class.getCanonicalName();
        String methodName = "moo";
        ClassTransformer transformer = new DefaultBaseClassTransformer(path);
        ClassNode classNode = transformer.readClass(className);

        MethodNode methodNode = null;

        for(MethodNode method : classNode.methods) {
            if(!method.name.equals(methodName)) {
                continue;
            }

            methodNode = method;
            break;
        }

        TraceClassInspector classInspector = new TraceClassInspector(classNode.name);
        MethodTracer tracer = classInspector.visitClass();
        Printer printer = tracer.getPrinterForMethodSignature(methodNode.name + methodNode.desc);
        PrettyMethodGraphBuilder prettyBuilder = new PrettyMethodGraphBuilder(methodNode, printer);
        PrettyMethodGraph prettyGraph = prettyBuilder.build();

        System.out.println(prettyGraph.toDotStringVerbose(methodName));
    }

    @Test
    public void Graph0() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        String path = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
        String className = Example.class.getCanonicalName();
        String methodName = "moo";
        ClassTransformer transformer = new DefaultBaseClassTransformer(path);
        ClassNode classNode = transformer.readClass(className);

        MethodNode methodNode = null;

        for(MethodNode method : classNode.methods) {
            if(!method.name.equals(methodName)) {
                continue;
            }

            methodNode = method;
            break;
        }

        TraceClassInspector classInspector = new TraceClassInspector(classNode.name);
        MethodTracer tracer = classInspector.visitClass();
        Printer printer = tracer.getPrinterForMethodSignature(methodNode.name + methodNode.desc);
        PrettyMethodGraphBuilder prettyBuilder = new PrettyMethodGraphBuilder(methodNode, printer);
        PrettyMethodGraph prettyGraph = prettyBuilder.build();

        System.out.println(prettyGraph.toDotStringVerbose(methodName));
    }

    @Test
    public void colorCounter() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        String path = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter/out/production/pngtastic-counter";
        String className = Run.class.getCanonicalName();
        String methodName = "main";
        ClassTransformer transformer = new DefaultBaseClassTransformer(path);
        ClassNode classNode = transformer.readClass(className);

        MethodNode methodNode = null;

        for(MethodNode method : classNode.methods) {
            if(!method.name.equals(methodName)) {
                continue;
            }

            methodNode = method;
            break;
        }

        TraceClassInspector classInspector = new TraceClassInspector(classNode.name);
        MethodTracer tracer = classInspector.visitClass();
        Printer printer = tracer.getPrinterForMethodSignature(methodNode.name + methodNode.desc);
        PrettyMethodGraphBuilder prettyBuilder = new PrettyMethodGraphBuilder(methodNode, printer);
        PrettyMethodGraph prettyGraph = prettyBuilder.build();

        System.out.println(prettyGraph.toDotStringVerbose(methodName));
    }

}