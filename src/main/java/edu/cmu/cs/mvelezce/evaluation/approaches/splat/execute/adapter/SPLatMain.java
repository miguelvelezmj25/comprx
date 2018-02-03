package edu.cmu.cs.mvelezce.evaluation.approaches.splat.execute.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.evaluation.approaches.splat.Coverage;
import edu.cmu.cs.mvelezce.tool.Options;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class SPLatMain {

    public static final String DIRECTORY = Options.DIRECTORY + "/compression/java/programs/splat";

    private String programName;
    private Map<Set<String>, Set<Set<String>>> configsToCovered = new HashMap<>();

    public SPLatMain(String programName) {
        this.programName = programName;
    }

    public abstract Set<Set<String>> getSPLatConfigurations() throws InterruptedException;

    public void writeToFileCoverage() throws IOException {
        if(this.configsToCovered.isEmpty()) {
            throw new RuntimeException("The coverage map is empty");
        }
        List<Coverage> coverageList = new ArrayList<>();

        for(Map.Entry<Set<String>, Set<Set<String>>> entry : this.configsToCovered.entrySet()) {
            Coverage coverage = new Coverage(entry.getKey(), entry.getValue());
            coverageList.add(coverage);
        }

        ObjectMapper mapper = new ObjectMapper();
        String outputFile = SPLatMain.DIRECTORY + "/" + this.programName + "/coverage" + Options.DOT_JSON;
        File file = new File(outputFile);
        file.getParentFile().mkdirs();

        mapper.writeValue(file, coverageList);
    }

    public String getProgramName() {
        return programName;
    }

    public void setConfigsToCovered(Map<Set<String>, Set<Set<String>>> configsToCovered) {
        this.configsToCovered = configsToCovered;
    }
}
