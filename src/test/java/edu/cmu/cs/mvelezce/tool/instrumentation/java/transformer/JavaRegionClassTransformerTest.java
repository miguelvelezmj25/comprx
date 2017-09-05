package edu.cmu.cs.mvelezce.tool.instrumentation.java.transformer;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Instrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.TimerInstrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodBlock;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodGraph;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mvelezce on 5/11/17.
 */
public class JavaRegionClassTransformerTest {

    @Test
    public void testSleep9() throws IOException, ParseException, InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String program = "Sleep9";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Program arguments
        String[] args = new String[0];

        Map<JavaRegion, Set<String>> partialRegionsToOptions = null; // TODO make change since interface changed ProgramAnalysis.analyze(program, args);
        Instrumenter instrumenter = new TimerInstrumenter(srcDirectory, classDirectory, partialRegionsToOptions.keySet());
        instrumenter.instrument(args);
    }

    @Test
    public void testCalculateASMStartIndex1() throws IOException, ParseException {
        String directory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String programName = "Dummy3";
        String[] args = new String[0];

        Map<JavaRegion, Set<String>> partialRegionsToOptions = null; // TODO make change since interface changed ProgramAnalysis.analyze(programName, args, JavaPipeline.LOADTIME_DATABASE, JavaPipeline.TEST_COLLECTION);
        Set<JavaRegion> regions = partialRegionsToOptions.keySet();

        JavaRegionClassTransformerTimer timer = new JavaRegionClassTransformerTimer(directory, regions);
        ClassNode classNode = timer.readClass("edu.cmu.cs.mvelezce." + programName);
        MethodNode methodNode = null;

        for(MethodNode method : classNode.methods) {
            if(method.name.equals("main")) {
                methodNode = method;
                break;
            }
        }

        List<JavaRegion> regionList = new ArrayList<>();

        for(JavaRegion region : regions) {
            if(region.getRegionMethod().equals("main")) {
                regionList.add(region);
            }
        }

        timer.calculateASMStartIndex(regionList, methodNode);

        for(JavaRegion region : regionList) {
            System.out.println(region.getRegionMethod() + " " + region.getStartBytecodeIndex());
        }
    }

    @Test
    public void testGetWhereToStartInstrumenting1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, f);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(x, a);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToEndInstrumenting1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, f);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(x, a);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToStartInstrumenting2() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToEndInstrumenting2() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToStartInstrumenting3() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(y, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, x);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToEndInstrumenting3() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(y, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, x);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToStartInstrumenting4() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(x, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToEndInstrumenting4() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToStartInstrumenting5() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
    }

    @Test
    public void testGetWhereToEndInstrumenting5() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
    }

    @Test
    public void testGetWhereToStartInstrumenting6() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, x);
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(f, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, f));
        Assert.assertEquals(x, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToEndInstrumenting6() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, x);
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(x, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, f));
        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
    }

    @Test
    public void testGetWhereToStartInstrumenting7() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");

        // Add vertices
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(h, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, f);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToEndInstrumenting7() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");

        // Add vertices
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(h, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, f);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToStartInstrumenting8() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(h, f);
        methodGraph.addEdge(g, i);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, g));
    }

    @Test
    public void testGetWhereToEndInstrumenting8() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(h, f);
        methodGraph.addEdge(g, i);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(i, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, g));
    }

    @Test
    public void testGetWhereToStartInstrumenting9() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(b, f);
        methodGraph.addEdge(f, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToEndInstrumenting9() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(b, f);
        methodGraph.addEdge(f, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToStartInstrumenting10() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(b, f);
        methodGraph.addEdge(f, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToEndInstrumenting10() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(b, f);
        methodGraph.addEdge(f, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToStartInstrumenting11() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(c, f);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToEndInstrumenting11() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(c, f);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(f, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToStartInstrumenting12() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(x, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToEndInstrumenting12() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, c));
    }

    @Test
    public void testGetWhereToStartInstrumenting13() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(e, f);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(x, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, e));
    }

    @Test
    public void testGetWhereToEndInstrumenting13() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");
        MethodBlock y = new MethodBlock("Y");

        // Add vertices
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(y);
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(d, x);
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(x, y);
        methodGraph.addEdge(y, c);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(e, f);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, x));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(f, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, e));
    }

    @Test
    public void testGetWhereToStartInstrumenting14() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");
        MethodBlock j = new MethodBlock("J");
        MethodBlock k = new MethodBlock("K");
        MethodBlock l = new MethodBlock("L");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);
        methodGraph.addMethodBlock(j);
        methodGraph.addMethodBlock(k);
        methodGraph.addMethodBlock(l);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(g, k);
        methodGraph.addEdge(h, i);
        methodGraph.addEdge(i, j);
        methodGraph.addEdge(j, h);
        methodGraph.addEdge(k, j);
        methodGraph.addEdge(k, l);
        methodGraph.addEdge(j, l);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, d));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, g));
        Assert.assertEquals(k, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, k));
        Assert.assertEquals(j, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, j));
    }

    @Test
    public void testGetWhereToEndInstrumenting14() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");
        MethodBlock j = new MethodBlock("J");
        MethodBlock k = new MethodBlock("K");
        MethodBlock l = new MethodBlock("L");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);
        methodGraph.addMethodBlock(j);
        methodGraph.addMethodBlock(k);
        methodGraph.addMethodBlock(l);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(g, k);
        methodGraph.addEdge(h, i);
        methodGraph.addEdge(i, j);
        methodGraph.addEdge(j, h);
        methodGraph.addEdge(k, j);
        methodGraph.addEdge(k, l);
        methodGraph.addEdge(j, l);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, d));
        Assert.assertEquals(l, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, g));
        Assert.assertEquals(l, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, k));
        Assert.assertEquals(l, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, j));
    }

    @Test
    public void testGetWhereToStartInstrumenting15() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");
        MethodBlock j = new MethodBlock("J");
        MethodBlock k = new MethodBlock("K");
        MethodBlock l = new MethodBlock("L");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);
        methodGraph.addMethodBlock(j);
        methodGraph.addMethodBlock(k);
        methodGraph.addMethodBlock(l);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(g, k);
        methodGraph.addEdge(h, i);
        methodGraph.addEdge(i, j);
        methodGraph.addEdge(j, h);
        methodGraph.addEdge(k, j);
        methodGraph.addEdge(j, l);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(a, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, d));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, g));
        Assert.assertEquals(j, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, j));
    }

    @Test
    public void testGetWhereToEndInstrumenting15() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");
        MethodBlock h = new MethodBlock("H");
        MethodBlock i = new MethodBlock("I");
        MethodBlock j = new MethodBlock("J");
        MethodBlock k = new MethodBlock("K");
        MethodBlock l = new MethodBlock("L");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(i);
        methodGraph.addMethodBlock(j);
        methodGraph.addMethodBlock(k);
        methodGraph.addMethodBlock(l);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, d);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(g, k);
        methodGraph.addEdge(h, i);
        methodGraph.addEdge(i, j);
        methodGraph.addEdge(j, h);
        methodGraph.addEdge(k, j);
        methodGraph.addEdge(j, l);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, a));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, d));
        Assert.assertEquals(j, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, g));
        Assert.assertEquals(l, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, j));
    }

    @Test
    public void testGetWhereToStartInstrumenting16() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(d, f);
        methodGraph.addEdge(e, g);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, b);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(b, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(d, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToEndInstrumenting16() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock g = new MethodBlock("G");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(c);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(d, f);
        methodGraph.addEdge(e, g);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, b);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }
        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, b));
        Assert.assertEquals(g, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, d));
    }

    @Test
    public void testGetWhereToStartInstrumenting17() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, a);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(e, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(c, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, c));
        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToStartInstrumentingBeforeIt(methodGraph, e));
    }

    @Test
    public void testGetWhereToEndInstrumenting17() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock f = new MethodBlock("F");
        MethodBlock x = new MethodBlock("X");

        // Add vertices
        methodGraph.addMethodBlock(x);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);

        // Add edges
        methodGraph.addEdge(x, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, a);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(e, d);

        System.out.println(methodGraph.toDotString("test"));

        for(MethodBlock block : methodGraph.getBlocks()) {
            if(block.getSuccessors().size() > 1) {
                System.out.println(block.getID());
            }
        }

        Assert.assertEquals(e, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, c));
        Assert.assertEquals(f, JavaRegionClassTransformer.getBlockToEndInstrumentingBeforeIt(methodGraph, e));
    }

}