package edu.cmu.cs.mvelezce.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by miguelvelez on 2/11/17.
 */
public class HelperTest {
    @Test
    public void getNextConfiguration1() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");

        Set<Set<String>> configurations = Helper.getConfigurations(parameters);

        Assert.assertEquals(new HashSet<>(), Helper.getNextConfiguration(configurations, new HashSet<>()));
    }

    @Test
    public void getNextConfiguration2() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");

        Set<String> consider = new HashSet<>();
        consider.add("A");

        Set<Set<String>> configurations = Helper.getConfigurations(parameters);

        Helper.getNextConfiguration(configurations, consider);
        Set<String> nextConfiguration = new HashSet<>();
        nextConfiguration.add("A");

        Assert.assertEquals(nextConfiguration, Helper.getNextConfiguration(configurations, consider));
    }

    @Test
    public void getNextConfiguration3() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");

        Set<String> consider = new HashSet<>();
        consider.add("A");
        consider.add("B");

        Set<Set<String>> configurations = Helper.getConfigurations(parameters);

        Helper.getNextConfiguration(configurations, consider);
        Set<String> nextConfiguration = new HashSet<>();
        nextConfiguration.add("A");
        nextConfiguration.add("B");

        Assert.assertEquals(nextConfiguration, Helper.getNextConfiguration(configurations, consider));
    }

    @Test
    public void getNextConfiguration4() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");

        Set<String> consider = new HashSet<>();
        consider.add("A");
        consider.add("B");

        Set<Set<String>> configurations = Helper.getConfigurations(parameters);

        Helper.getNextConfiguration(configurations, consider);
        Set<String> nextConfiguration = new HashSet<>();
        nextConfiguration.add("A");

        Helper.getNextConfiguration(configurations, consider);

        Assert.assertEquals(nextConfiguration, Helper.getNextConfiguration(configurations, consider));
    }

    @Test
    public void getNextConfiguration5() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");

        Set<String> consider = new HashSet<>();

        Set<Set<String>> configurations = Helper.getConfigurations(parameters);

        Helper.getNextConfiguration(configurations, consider);
        Set<String> nextConfiguration = new HashSet<>();
        nextConfiguration.add("A");

        Assert.assertEquals(nextConfiguration, Helper.getNextConfiguration(configurations, consider));
    }

    @Test
    public void getConfigurations() throws Exception {
        Set<String> parameters = new HashSet<>();
        parameters.add("A");
        parameters.add("B");
        parameters.add("C");

        Set<Set<String>> configurations = new HashSet<>();
        Set<String> configuration = new HashSet<>();
        configuration.add("A");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("B");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("C");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("C");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("C");
        configuration.add("B");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        configuration.add("C");
        configurations.add(configuration);

        configuration = new HashSet<>();
        configurations.add(configuration);

        Assert.assertEquals(configurations, Helper.getConfigurations(parameters));
    }

}