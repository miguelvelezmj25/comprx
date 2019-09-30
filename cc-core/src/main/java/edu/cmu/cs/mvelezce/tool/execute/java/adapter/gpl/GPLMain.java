package edu.cmu.cs.mvelezce.tool.execute.java.adapter.gpl;

import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by miguelvelez on 4/30/17.
 */
public class GPLMain {

    public static final String GPL_MAIN = GPLMain.class.getCanonicalName();

    public static void main(String[] args) throws InterruptedException, IOException {
        String programName = args[0];
        String mainClass = args[1];
        String[] gplArgs = Arrays.copyOfRange(args, 2, args.length);

        if(mainClass.equals("edu.cmu.cs.mvelezce.Main")) {
            Region program = new Region.Builder(Regions.PROGRAM_REGION_ID).build();
            Regions.enter(program.getRegionID());
//            Main.main(gplArgs);
            Regions.exit(program.getRegionID());
        }

        Set<String> performanceConfiguration = GPLAdapter.adaptConfigurationToPerformanceMeasurement(gplArgs);
        Executor executor = new ConfigCrusherExecutor();
//        executor.writeToFile(programName, performanceConfiguration, Regions.getRegionsToProcessedPerformance());
        throw new RuntimeException("Check this main");
    }
}
