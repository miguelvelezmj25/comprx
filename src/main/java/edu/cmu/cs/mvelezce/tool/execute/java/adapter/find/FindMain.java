package edu.cmu.cs.mvelezce.tool.execute.java.adapter.find;

import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Main;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class FindMain extends BaseMain {

    public static final String FIND_MAIN = FindMain.class.getCanonicalName();

    public FindMain(String programName, String iteration, String[] args) {
        super(programName, iteration, args);
    }

    public static void main(String[] args) throws Exception {
        String programName = args[0];
        String mainClass = args[1];
        String iteration = args[2];
        String[] sleepArgs = Arrays.copyOfRange(args, 3, args.length);

        Main main = new FindMain(programName, iteration, sleepArgs);
        main.execute(mainClass, sleepArgs);
        main.logExecution();
    }

    @Override
    public void logExecution() throws java.io.IOException {
        Adapter adapter = new FindAdapter();
        Set<String> configuration = adapter.configurationAsSet(this.getArgs());

        ConfigCrusherExecutor executor = new ConfigCrusherExecutor(this.getProgramName());
        Map<String, Long> results = executor.getResults();
        executor.writeToFile(this.getIteration(), configuration, results);
    }

    @Override
    public void execute(String mainClass, String[] args) throws Exception {
        if(mainClass.contains("Main")) {
            Region program = new Region(Regions.PROGRAM_REGION_ID);
            Regions.enter(program.getRegionID());
            org.unix4j.find.Main.main(args);
            Regions.exit(program.getRegionID());
        }
        else {
            throw new RuntimeException("Could not find the main class " + mainClass);
        }

    }
}