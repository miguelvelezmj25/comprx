package edu.cmu.cs.mvelezce.tool;

import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.runningexample.RunningExampleMain;
import edu.cmu.cs.mvelezce.tool.performance.model.PerformanceModel;
import org.junit.Test;

public class ConfigCrusherTest {

    @Test
    public void runningExample() throws Exception {
        String programName = RunningExampleMain.PROGRAM_NAME;
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/running-example";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/running-example/target/classes";
        String entry = "edu.cmu.cs.mvelezce.Example";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i5";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void colorCounter() throws Exception {
        String programName = "pngtasticColorCounter";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-counter/out/production/pngtastic-counter";
        String entry = "counter.com.googlecode.pngtastic.Run";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i5";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void optimizer() throws Exception {
        String programName = "pngtasticOptimizer";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-optimizer";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/pngtastic-optimizer/out/production/pngtastic-optimizer";
        String entry = "optimizer.com.googlecode.pngtastic.Run";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i5";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void kanzi() throws Exception {
        String programName = "kanzi";
        String entry = "kanzi.Run";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/kanzi";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/kanzi/target/classes";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i5";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void prevayler() throws Exception {
        String programName = "prevayler";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/prevayler/target/classes";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/prevayler";
        String entry = "org.prevayler.demos.demo1.PrimeNumbers";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i5";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void regions12() throws Exception {
        String programName = "regions12";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
        String entry = "edu.cmu.cs.mvelezce.Regions12";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i1";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

    @Test
    public void regions16() throws Exception {
        String programName = "regions16";
        String srcDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy";
        String classDir = BaseAdapter.USER_HOME + "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy";
        String entry = "edu.cmu.cs.mvelezce.Regions16";

        String[] args = new String[0];

        args = new String[3];
        args[0] = "-delres";
        args[1] = "-saveres";
        args[2] = "-i1";

        ConfigCrusher approach = new ConfigCrusher(programName, srcDir, classDir, entry);
        approach.compile();
        PerformanceModel performanceModel = approach.run(args);

        performanceModel.toString();
    }

}