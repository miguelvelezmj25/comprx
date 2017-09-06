package edu.cmu.cs.mvelezce.tool.execute.java.adapter.runningexample;

import edu.cmu.cs.mvelezce.Example;
import edu.cmu.cs.mvelezce.Sleep31;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.DefaultExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.DefaultMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class RunningExampleMain extends DefaultMain {

    public static final String RUNNING_EXAMPLE_MAIN = RunningExampleMain.class.getCanonicalName();

    public RunningExampleMain(String programName, String iteration, String[] args) {
        super(programName, iteration, args);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String programName = args[0];
        String mainClass = args[1];
        String iteration = args[2];
        String[] sleepArgs = Arrays.copyOfRange(args, 3, args.length);

        if(mainClass.contains("Example")) {
            Region program = new Region(Regions.PROGRAM_REGION_ID);
            Regions.enter(program.getRegionID());
            Example.main(sleepArgs);
            Regions.exit(program.getRegionID());
        }
        else {
            throw new RuntimeException("Could not find the main class " + mainClass);
        }

        Main main = new RunningExampleMain(programName, iteration, sleepArgs);
        main.logExecution();
    }

    @Override
    public void logExecution() throws IOException {
        Adapter adapter = new RunningExampleAdapter();
        Set<String> configuration = adapter.configurationAsSet(this.getArgs());

        Executor executor = new DefaultExecutor(this.getProgramName());
        executor.writeToFile(this.getIteration(), configuration, Regions.getExecutedRegionsTrace());
    }
}