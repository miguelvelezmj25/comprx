package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mvelezce on 5/3/17.
 */
public class MethodGraphTest {

    @Test
    public void testMethods2() {
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

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, f);
        methodGraph.addEdge(d, g);
        methodGraph.addEdge(e, h);
        methodGraph.addEdge(f, h);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(h, i);
        methodGraph.addEdge(i, j);

        System.out.println(methodGraph.toDotString("test"));
        MethodBlock result = methodGraph.getImmediateDominator(b);
        Assert.assertEquals(a, result);

        result = methodGraph.getImmediatePostDominator(b);
        Assert.assertEquals(h, result);
    }

    @Test
    public void testMethods1() {
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
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, f);

        System.out.println(methodGraph.toDotString("test"));
//        Assert.assertEquals(a, methodGraph.getImmediateDominator(b));
        Assert.assertEquals(e, methodGraph.getImmediatePostDominator(b));
    }

    @Test
    public void testGetStronglyConnectedComponents1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));
        Assert.assertEquals(3, methodGraph.getStronglyConnectedComponents(a).size());
    }

    @Test
    public void testGetStronglyConnectedComponents2() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));
        Assert.assertEquals(5, methodGraph.getStronglyConnectedComponents(a).size());
    }

    @Test
    public void testGetStronglyConnectedComponents3() {
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
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(f, a);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(c, b);

        System.out.println(methodGraph.toDotString("test"));
        Assert.assertEquals(5, methodGraph.getStronglyConnectedComponents(f).size());
    }

    @Test
    public void testGetStronglyConnectedComponents4() {
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
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);

        Assert.assertEquals(4, methodGraph.getStronglyConnectedComponents(a).size());
    }

    @Test
    public void testGetStronglyConnectedComponents5() {
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
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, h);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, d);

        Assert.assertEquals(5, methodGraph.getStronglyConnectedComponents(a).size());
    }

    @Test
    public void testReverseGraph1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        MethodGraph reversedGraph = methodGraph.reverseGraph();
        MethodGraph normalGraph = reversedGraph.reverseGraph();

        Assert.assertEquals(methodGraph.getEntryBlock().getID(), normalGraph.getEntryBlock().getID());
        Assert.assertEquals(methodGraph.getExitBlock().getID(), normalGraph.getExitBlock().getID());
        Assert.assertEquals(methodGraph.getBlockCount(), normalGraph.getBlockCount());

        for(MethodBlock methodBlock : methodGraph.getBlocks()) {
            MethodBlock normalMethodBlock = normalGraph.getMethodBlock(methodBlock.getID());
            Assert.assertEquals(methodBlock.getPredecessors().size(), normalMethodBlock.getPredecessors().size());
            Assert.assertEquals(methodBlock.getSuccessors().size(), normalMethodBlock.getSuccessors().size());
        }
    }

    @Test
    public void testReverseGraph2() {
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
        methodGraph.addEdge(f, a);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, g);

        MethodGraph reversedGraph = methodGraph.reverseGraph();
        MethodGraph normalGraph = reversedGraph.reverseGraph();

        Assert.assertEquals(methodGraph.getEntryBlock().getID(), normalGraph.getEntryBlock().getID());
        Assert.assertEquals(methodGraph.getExitBlock().getID(), normalGraph.getExitBlock().getID());
        Assert.assertEquals(methodGraph.getBlockCount(), normalGraph.getBlockCount());

        for(MethodBlock methodBlock : methodGraph.getBlocks()) {
            MethodBlock normalMethodBlock = normalGraph.getMethodBlock(methodBlock.getID());
            Assert.assertEquals(methodBlock.getPredecessors().size(), normalMethodBlock.getPredecessors().size());
            Assert.assertEquals(methodBlock.getSuccessors().size(), normalMethodBlock.getSuccessors().size());
        }
    }

    @Test
    public void testGetDominators1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock entry = methodGraph.getEntryBlock();
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock exit = methodGraph.getExitBlock();

        // Add vertices
        methodGraph.addMethodBlock(entry);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(exit);

        // Add edges
        methodGraph.addEdge(entry, a);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, exit);

        System.out.println(methodGraph.toDotString("test"));

        // Expected
        Map<MethodBlock, Set<MethodBlock>> expected = new HashMap<>();
        Set<MethodBlock> dominators = new HashSet<>();
        dominators.add(entry);
        expected.put(entry, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        expected.put(a, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        dominators.add(b);
        expected.put(b, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        dominators.add(c);
        expected.put(c, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        dominators.add(d);
        expected.put(d, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        dominators.add(d);
        dominators.add(e);
        expected.put(e, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(a);
        dominators.add(d);
        dominators.add(e);
        dominators.add(exit);
        expected.put(exit, dominators);

        Map<MethodBlock, Set<MethodBlock>> result = methodGraph.getDominators();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetDominators2() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock entry = methodGraph.getEntryBlock();
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");
        MethodBlock exit = methodGraph.getExitBlock();

        // Add vertices
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(a);

        // Add edges
        methodGraph.addEdge(e, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(b, a);

        System.out.println(methodGraph.toDotString("test"));

        // Expected
        Map<MethodBlock, Set<MethodBlock>> expected = new HashMap<>();
        Set<MethodBlock> dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(e);
        expected.put(e, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(e);
        dominators.add(d);
        expected.put(d, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(e);
        dominators.add(d);
        dominators.add(c);
        expected.put(c, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(e);
        dominators.add(d);
        dominators.add(b);
        expected.put(b, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(e);
        dominators.add(d);
        dominators.add(a);
        expected.put(a, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        expected.put(entry, dominators);

        Map<MethodBlock, Set<MethodBlock>> result = methodGraph.getDominators();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetDominators3() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock entry = new MethodBlock("entry");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");

        // Add vertices
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);

        // Add edges
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);

        System.out.println(methodGraph.toDotString("test"));

        // Expected
        Map<MethodBlock, Set<MethodBlock>> expected = new HashMap<>();
        Set<MethodBlock> dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(b);
        expected.put(b, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(c);
        dominators.add(b);
        expected.put(c, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        dominators.add(b);
        dominators.add(c);
        dominators.add(d);
        expected.put(d, dominators);

        dominators = new HashSet<>();
        dominators.add(entry);
        expected.put(entry, dominators);

        Map<MethodBlock, Set<MethodBlock>> result = methodGraph.getDominators();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetImmediateDominator1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));
        MethodBlock result = methodGraph.getImmediateDominator(d);

        Assert.assertEquals(a, result);
    }

    @Test
    public void testGetImmediateDominator2() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(a);

        // Add edges
        methodGraph.addEdge(e, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(b, a);

        System.out.println(methodGraph.toDotString("test"));
        MethodBlock result = methodGraph.getImmediateDominator(a);

        Assert.assertEquals(d, result);
    }

    @Test
    public void testGetImmediatePostDominator1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(d.getID(), methodGraph.getImmediatePostDominator(a).getID());
    }

    @Test
    public void testGetImmediatePostDominator2() {
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
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(f, a);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, g);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(e, methodGraph.getImmediatePostDominator(f));
    }

    @Test
    public void testGetImmediatePostDominator3() {
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
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(g);

        // Add edges
        methodGraph.addEdge(f, a);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(c, a);
        methodGraph.addEdge(e, g);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(e, methodGraph.getImmediatePostDominator(f));
    }

    @Test
    public void testGetImmediatePostDominator4() {
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
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(f, a);
        methodGraph.addEdge(f, c);
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(c, b);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(b, methodGraph.getImmediatePostDominator(f));
    }

    @Test
    public void testGetImmediatePostDominator5() {
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
        methodGraph.addMethodBlock(i);
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);
        methodGraph.addMethodBlock(d);

        // Add edges
        methodGraph.addEdge(i, a);
        methodGraph.addEdge(i, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, h);
        methodGraph.addEdge(g, e);
        methodGraph.addEdge(h, d);
        methodGraph.addEdge(a, h);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(d, methodGraph.getImmediatePostDominator(i));
    }

    @Test
    public void testGetImmediatePostDominator6() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        // TODO this might be the case where we need to
        // Assert
        Assert.assertEquals(c, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator7() {
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
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(d, f);
        methodGraph.addEdge(c, f);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, f);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(f, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator8() {
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
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(d, f);
        methodGraph.addEdge(c, f);
        methodGraph.addEdge(e, c);
        methodGraph.addEdge(c, e);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(f, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator9() {
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
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(e, methodGraph.getImmediatePostDominator(b));
    }

    @Test
    public void testGetImmediatePostDominator10() {
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
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(c, f);
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, b);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(f, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator11() {
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
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(e, h);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, h);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(c, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator12() {
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
        methodGraph.addEdge(b, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, c);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(d, a);
        methodGraph.addEdge(c, f);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(f, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator13() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(d, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator14() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock a = new MethodBlock("A");
        MethodBlock b = new MethodBlock("B");
        MethodBlock c = new MethodBlock("C");
        MethodBlock d = new MethodBlock("D");
        MethodBlock e = new MethodBlock("E");

        // Add vertices
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(a, d);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(c, d);
        methodGraph.addEdge(d, e);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(d, methodGraph.getImmediatePostDominator(b));
    }

    @Test
    public void testGetImmediatePostDominator15() {
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
        methodGraph.addMethodBlock(a);
        methodGraph.addMethodBlock(b);
        methodGraph.addMethodBlock(c);
        methodGraph.addMethodBlock(d);
        methodGraph.addMethodBlock(e);
        methodGraph.addMethodBlock(f);
        methodGraph.addMethodBlock(g);
        methodGraph.addMethodBlock(h);

        // Add edges
        methodGraph.addEdge(a, b);
        methodGraph.addEdge(b, c);
        methodGraph.addEdge(c, e);
        methodGraph.addEdge(e, h);
        methodGraph.addEdge(b, d);
        methodGraph.addEdge(d, e);
        methodGraph.addEdge(e, f);
        methodGraph.addEdge(f, g);
        methodGraph.addEdge(g, d);

        System.out.println(methodGraph.toDotString("test"));

        // Assert
        Assert.assertEquals(e, methodGraph.getImmediatePostDominator(b));
    }

    @Test
    public void testGetImmediatePostDominator16() {
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
        Assert.assertEquals(c, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator17() {
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
        Assert.assertEquals(b, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator18() {
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
        Assert.assertEquals(b, methodGraph.getImmediatePostDominator(a));
    }

    @Test
    public void testGetImmediatePostDominator19() {
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
        Assert.assertEquals(d, methodGraph.getImmediatePostDominator(b));
        Assert.assertEquals(g, methodGraph.getImmediatePostDominator(d));
    }

    @Test
    public void testAddMethodBlock() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build methodBlock
        MethodBlock methodBlock = new MethodBlock("A");

        // Add block
        methodGraph.addMethodBlock(methodBlock);

        // Assert
        Assert.assertEquals(2, methodGraph.getBlockCount());
    }

    @Test
    public void testAddEdge1() {
        // Build methodGraph
        MethodGraph methodGraph = new MethodGraph();

        // Build block
        MethodBlock source = new MethodBlock("A");
        MethodBlock end = new MethodBlock("Z");

        // Add vertices
        methodGraph.addMethodBlock(source);
        methodGraph.addMethodBlock(end);

        // Add edge
        methodGraph.addEdge(source, end);

        // Assert
        Assert.assertEquals(end, source.getSuccessors().iterator().next());
        Assert.assertEquals(source, end.getPredecessors().iterator().next());
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testAddEdge2() {
//        // Build methodGraph
//        MethodGraph methodGraph = new MethodGraph();
//
//        // Build block
//        MethodBlock source = new MethodBlock(new Label());
//        MethodBlock target1 = new MethodBlock(new Label());
//        MethodBlock target2 = new MethodBlock(new Label());
//        MethodBlock target3 = new MethodBlock(new Label());
//
//        // Add vertices
//        methodGraph.addMethodBlock(source);
//        methodGraph.addMethodBlock(target1);
//        methodGraph.addMethodBlock(target2);
//
//        // Add edges
//        methodGraph.addEdge(source, target1);
//        methodGraph.addEdge(source, target2);
//
//        // Assert exception thrown
//        methodGraph.addEdge(source, target3);
//    }

}