package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.Options;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.serialize.RegionToInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public abstract class BaseDynamicRegionAnalysis<S> extends BaseDynamicAnalysis<Map<JavaRegion, S>> implements
    DynamicRegionAnalysis<S> {

  protected static final String DIRECTORY = Options.DIRECTORY + "/analysis/java/dynamic/programs";

//  private final String programName;
//  private final Set<String> options;
//  private final Set<String> initialConfig;

  public BaseDynamicRegionAnalysis(String programName, Set<String> options,
      Set<String> initialConfig) {
    super(programName, options, initialConfig);
  }

  @Override
  public Map<JavaRegion, S> analyze(String[] args) throws IOException, InterruptedException {
    Options.getCommandLine(args);

    String outputFile = this.outputDir();
    File file = new File(outputFile);

    Options.checkIfDeleteResult(file);

    if (file.exists()) {
      Collection<File> files = FileUtils.listFiles(file, null, true);

      if (files.size() != 1) {
        throw new RuntimeException(
            "We expected to find 1 file in the directory, but that is not the case "
                + outputFile);
      }

      return this.readFromFile(files.iterator().next());
    }

    Map<JavaRegion, S> regionsToTypes = this.analyze();

    if (Options.checkIfSave()) {
      this.writeToFile(regionsToTypes);
    }

    return regionsToTypes;
  }

  @Override
  public void writeToFile(Map<JavaRegion, S> regionsToInfo) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String outputFile = this.outputDir() + "/" + this.getProgramName() + Options.DOT_JSON;
    File file = new File(outputFile);
    file.getParentFile().mkdirs();

    List<RegionToInfo> decisionsAndInfos = new ArrayList<>();

    for (Map.Entry<JavaRegion, S> entry : regionsToInfo.entrySet()) {
      RegionToInfo<S> regionToInfo = new RegionToInfo<>(entry.getKey(), entry.getValue());
      decisionsAndInfos.add(regionToInfo);
    }

    mapper.writeValue(file, decisionsAndInfos);
  }

  @Override
  public Map<Region, Set<Set<String>>> transform(
      Map<? extends Region, Set<Set<String>>> regionsToOptionSet) {
    throw new UnsupportedOperationException(
        "This method is only used to change a subset of regions to regions. Consider changing it to an static method");
  }

  public static Map<Region, Set<Set<String>>> changToRegions(
      Map<? extends Region, Set<Set<String>>> regionsToOptionSet) {
    Map<Region, Set<Set<String>>> result = new HashMap<>();

    for (Map.Entry<? extends Region, Set<Set<String>>> entry : regionsToOptionSet.entrySet()) {
      Region region = new Region.Builder(entry.getKey().getRegionID()).build();
      result.put(region, entry.getValue());
    }

    return result;
  }

  protected int getDecisionOrder(String sink) {
    int indexOfLastDot = sink.lastIndexOf(".");

    return Integer.valueOf(sink.substring(indexOfLastDot + 1));
  }

  protected String getMethodSignature(String sink) {
    int indexOfFirstDot = sink.indexOf(".");
    int indexOfLastDot = sink.lastIndexOf(".");

    return sink.substring(indexOfFirstDot + 1, indexOfLastDot);
  }

  protected String getClassName(String sink) {
    int indexOfDot = sink.indexOf(".");
    String packageAndClass = sink.substring(0, indexOfDot);
    int indexOfLastSlash = packageAndClass.lastIndexOf("/");

    return packageAndClass.substring(indexOfLastSlash + 1);
  }

  protected String getPackageName(String sink) {
    int indexOfDot = sink.indexOf(".");
    String packageAndClass = sink.substring(0, indexOfDot);
    int indexOfLastSlash = packageAndClass.lastIndexOf("/");
    String packageName = packageAndClass.substring(0, indexOfLastSlash);

    return packageName.replace("/", ".");
  }

//  public String getProgramName() {
//    return this.programName;
//  }
//
//  public Set<String> getOptions() {
//    return options;
//  }
//
//  public Set<String> getInitialConfig() {
//    return initialConfig;
//  }
}
