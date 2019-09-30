package edu.cmu.cs.mvelezce.tool.performance.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mvelezce on 3/9/17.
 */
public class PerformanceModelTest {
    @Test
    public void evaluate() throws Exception {

    }

    @Test
    public void testEvaluate1() {
//        List<Map<Set<String>, Double>> blocks = new ArrayList<>();
//        Map<Set<String>, Double> block = new HashMap<>();
//        Set<String> configuration = new HashSet<>();
//        block.put(configuration, 0.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        block.put(configuration, 3.0);
//
//        blocks.add(block);
//
//        block = new HashMap<>();
//        configuration = new HashSet<>();
//        block.put(configuration, 0.0);
//
//        configuration = new HashSet<>();
//        configuration.add("B");
//        block.put(configuration, 1.0);
//
//        blocks.add(block);
//
//        block = new HashMap<>();
//        configuration = new HashSet<>();
//        block.put(configuration, 6.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        block.put(configuration, 6.0);
//
//        blocks.add(block);
//
//        // TODO
//        PerformanceModel pm = new PerformanceModel(blocks);
//
//        long performance = 6;
//        configuration = new HashSet<>();
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 10;
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 9;
//        configuration = new HashSet<>();
//        configuration.add("A");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 7;
//        configuration = new HashSet<>();
//        configuration.add("B");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
    }

    @Test
    public void testEvaluate2() {
//        List<Map<Set<String>, Double>> blocks = new ArrayList<>();
//        Map<Set<String>, Double> block = new HashMap<>();
//        Set<String> configuration = new HashSet<>();
//        block.put(configuration, 0.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        block.put(configuration, 3.0);
//
//        configuration = new HashSet<>();
//        configuration.add("B");
//        block.put(configuration, 3.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        block.put(configuration, 3.0);
//
//        blocks.add(block);
//
//        block = new HashMap<>();
//        configuration = new HashSet<>();
//        block.put(configuration, 0.0);
//
//        configuration = new HashSet<>();
//        configuration.add("B");
//        block.put(configuration, 1.0);
//
//        blocks.add(block);
//
//        block = new HashMap<>();
//        configuration = new HashSet<>();
//        block.put(configuration, 6.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        block.put(configuration, 6.0);
//
//        configuration = new HashSet<>();
//        configuration.add("B");
//        block.put(configuration, 6.0);
//
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        block.put(configuration, 6.0);
//
//        blocks.add(block);
//
//        // TODO
//        PerformanceModel pm = new PerformanceModel(blocks);
//
//        long performance = 6;
//        configuration = new HashSet<>();
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 10;
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 9;
//        configuration = new HashSet<>();
//        configuration.add("A");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
//
//        performance = 10;
//        configuration = new HashSet<>();
//        configuration.add("B");
//        Assert.assertEquals(performance, pm.evaluate(configuration), 0);
    }

    @Test
    public void testCalculateConfigurationInfluence1() {
        // Longest configuration
        Set<String> longestConfiguration = new HashSet<>();

        // Configurations to performancemodel
        Map<Set<String>, Double> configurationsToPerformance = new HashMap<>();
        configurationsToPerformance.put(longestConfiguration, 0.0);

        // Memoization storage
        Map<Set<String>, Double> memoizationStore = new HashMap<>();

        // Influence
        double influence = 0.0;

        // TODO
//        // Assert
//        Assert.assertEquals(influence, PerformanceModel.calculateConfigurationInfluence(longestConfiguration, configurationsToPerformance, memoizationStore), 0);
    }

    @Test
    public void testCalculateConfigurationInfluence2() {
        // Longest configuration
        Set<String> longestConfiguration = new HashSet<>();
        longestConfiguration.add("A");

        // Configurations to performancemodel
        Map<Set<String>, Double> configurationsToPerformance = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        configurationsToPerformance.put(configuration, 0.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configurationsToPerformance.put(configuration, 5.0);

        // Memoization storage
        Map<Set<String>, Double> memoizationStore = new HashMap<>();

        // Influence
        double influence = 5;

        // TODO
//        // Assert
//        Assert.assertEquals(influence, PerformanceModel.calculateConfigurationInfluence(longestConfiguration, configurationsToPerformance, memoizationStore), 0);
    }

    @Test
    public void testCalculateConfigurationInfluence3() {
        // Longest configuration
        Set<String> longestConfiguration = new HashSet<>();
        longestConfiguration.add("A");
        longestConfiguration.add("B");

        // Configurations to performancemodel
        Map<Set<String>, Double> configurationsToPerformance = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        configurationsToPerformance.put(configuration, 0.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configurationsToPerformance.put(configuration, 5.0);

        // {B} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        configurationsToPerformance.put(configuration, 5.0);

        // {A, B} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configurationsToPerformance.put(configuration, 5.0);

        // Memoization storage
        Map<Set<String>, Double> memoizationStore = new HashMap<>();

        // Influence
        double influence = -5;

        // TODO
//        // Assert
//        Assert.assertEquals(influence, PerformanceModel.calculateConfigurationInfluence(longestConfiguration, configurationsToPerformance, memoizationStore), 0);
    }

    @Test
    public void testCalculateConfigurationInfluence4() {
        // Longest configuration
        Set<String> longestConfiguration = new HashSet<>();
        longestConfiguration.add("A");
        longestConfiguration.add("B");

        // Configurations to performancemodel
        Map<Set<String>, Double> configurationsToPerformance = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        configurationsToPerformance.put(configuration, 0.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configurationsToPerformance.put(configuration, 0.0);

        // {B} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        configurationsToPerformance.put(configuration, 0.0);

        // {A, B} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configurationsToPerformance.put(configuration, 5.0);

        // Memoization storage
        Map<Set<String>, Double> memoizationStore = new HashMap<>();

        // Influence
        double influence = 5;

        // TODO
//        // Assert
//        Assert.assertEquals(influence, PerformanceModel.calculateConfigurationInfluence(longestConfiguration, configurationsToPerformance, memoizationStore), 0);
    }

    @Test
    public void testCalculateConfigurationInfluence5() {
        // Longest configuration
        Set<String> longestConfiguration = new HashSet<>();
        longestConfiguration.add("A");

        // Configurations to performancemodel
        Map<Set<String>, Double> configurationsToPerformance = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        configurationsToPerformance.put(configuration, 3.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configurationsToPerformance.put(configuration, 5.0);

        // Memoization storage
        Map<Set<String>, Double> memoizationStore = new HashMap<>();

        // Influence
        double influence = 2;

        // TODO
//        // Assert
//        Assert.assertEquals(influence, PerformanceModel.calculateConfigurationInfluence(longestConfiguration, configurationsToPerformance, memoizationStore), 0);
    }

    @Test
    public void testCalculateInfluence1() {
        Map<Set<String>, Double> regionTable = new HashMap<>();

        // Influences
        Map<Set<String>, Double> configurationToInfluence = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        regionTable.put(configuration, 0.0);
        // Influence
        configurationToInfluence.put(configuration, 0.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, 3.0);

        // {B} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, 3.0);

        // {C} configuration
        configuration = new HashSet<>();
        configuration.add("C");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, 3.0);

        // {AB} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, -3.0);

        // {AC} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("C");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, -3.0);

        // {BC} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        configuration.add("C");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, -3.0);

        // {ABC} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configuration.add("C");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, 3.0);

        // TODO
//        // Assert
//        Assert.assertEquals(configurationToInfluence, PerformanceModel.calculateConfigurationsInfluence(regionTable));
    }

    @Test
    public void testCalculateInfluence2() {
        Map<Set<String>, Double> regionTable = new HashMap<>();

        // Influences
        Map<Set<String>, Double> configurationToInfluence = new HashMap<>();

        // {} configuration
        Set<String> configuration = new HashSet<>();
        regionTable.put(configuration, 2.0);
        // Influence
        configurationToInfluence.put(configuration, 2.0);

        // {A} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        regionTable.put(configuration, 3.0);
        // Influence
        configurationToInfluence.put(configuration, 1.0);

        // {B} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        regionTable.put(configuration, 4.0);
        // Influence
        configurationToInfluence.put(configuration, 2.0);

        // {C} configuration
        configuration = new HashSet<>();
        configuration.add("C");
        regionTable.put(configuration, 2.0);
        // Influence
        configurationToInfluence.put(configuration, 0.0);

        // {AB} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        regionTable.put(configuration, 6.0);
        // Influence
        configurationToInfluence.put(configuration, 1.0);

        // {AC} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("C");
        regionTable.put(configuration, 6.0);
        // Influence
        configurationToInfluence.put(configuration, 3.0);

        // {BC} configuration
        configuration = new HashSet<>();
        configuration.add("B");
        configuration.add("C");
        regionTable.put(configuration, 9.0);
        // Influence
        configurationToInfluence.put(configuration, 5.0);

        // {ABC} configuration
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configuration.add("C");
        regionTable.put(configuration, 16.0);
        // Influence
        configurationToInfluence.put(configuration, 2.0);

        // TODO
//        // Assert
//        Assert.assertEquals(configurationToInfluence, PerformanceModel.calculateConfigurationsInfluence(regionTable));
    }


}