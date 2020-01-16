package edu.cmu.cs.mvelezce.java.processor.aggregator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.analysis.Analysis;
import edu.cmu.cs.mvelezce.java.results.processed.PerformanceEntry;
import edu.cmu.cs.mvelezce.java.results.processed.ProcessedPerfExecution;
import edu.cmu.cs.mvelezce.utils.config.Options;
import edu.cmu.cs.mvelezce.utils.stats.SummaryStatisticsMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public abstract class PerfAggregatorProcessor implements Analysis<Set<PerformanceEntry>> {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0000000");

  private final Map<Integer, Set<ProcessedPerfExecution>> itersToProcessedPerfExecution;
  private final String outputDir;

  public PerfAggregatorProcessor(
      String programName, Map<Integer, Set<ProcessedPerfExecution>> itersToProcessedPerfExecution) {
    this.itersToProcessedPerfExecution = itersToProcessedPerfExecution;

    this.outputDir = this.outputDir() + "/" + programName + "/execution/averaged";
  }

  @Override
  public Set<PerformanceEntry> analyze(String[] args) throws IOException {
    Options.getCommandLine(args);

    File file = new File(this.outputDir);

    Options.checkIfDeleteResult(file);

    if (file.exists()) {
      return this.readFromFile(file);
    }

    Set<PerformanceEntry> performanceEntries = this.analyze();

    if (Options.checkIfSave()) {
      this.writeToFile(performanceEntries);
    }

    return performanceEntries;
  }

  @Override
  public Set<PerformanceEntry> analyze() {
    Set<PerformanceEntry> perfEntries = new HashSet<>();
    Set<Set<String>> configs = this.getConfigs();

    for (Set<String> config : configs) {
      System.out.println("Config " + config);
      PerformanceEntry performanceEntry = this.averageExecs(config);
      perfEntries.add(performanceEntry);
    }

    return perfEntries;
  }

  private PerformanceEntry averageExecs(Set<String> config) {
    Collection<Set<ProcessedPerfExecution>> allProcessedPerfExecutions =
        this.itersToProcessedPerfExecution.values();

    SummaryStatisticsMap<UUID> regionsToStats = this.addRegions(config, allProcessedPerfExecutions);
    this.addAllExecutions(regionsToStats, config, allProcessedPerfExecutions);

    Map<UUID, Double> regionsToPerf = regionsToStats.getEntriesToData();
    Map<UUID, Double> regionsToMin = regionsToStats.getEntriesToMin();
    Map<UUID, Double> regionsToMax = regionsToStats.getEntriesToMax();
    Map<UUID, Double> regionsToDiff = regionsToStats.getEntriesToDiff(regionsToMin, regionsToMax);
    Map<UUID, Double> regionsToSampleVariance = regionsToStats.getEntriesToSampleVariance();
    Map<UUID, List<Double>> regionsToConfidenceInterval =
        regionsToStats.getEntriesToConfidenceInterval();

    return new PerformanceEntry(
        config,
        regionsToPerf,
        regionsToMin,
        regionsToMax,
        regionsToDiff,
        regionsToSampleVariance,
        regionsToConfidenceInterval,
        this.toHumanReadable(regionsToPerf),
        this.toHumanReadable(regionsToMin),
        this.toHumanReadable(regionsToMax),
        this.toHumanReadable(regionsToDiff),
        this.toHumanReadableSampleVariance(regionsToSampleVariance),
        this.toHumanReadableCI(regionsToConfidenceInterval));
  }

  private Map<UUID, String> toHumanReadableSampleVariance(
      Map<UUID, Double> regionsToSampleVariance) {
    Map<UUID, String> regionsToHumanReadableData = new HashMap<>();

    for (Map.Entry<UUID, Double> entry : regionsToSampleVariance.entrySet()) {
      double data = entry.getValue();
      data = data / 1E18;
      regionsToHumanReadableData.put(entry.getKey(), DECIMAL_FORMAT.format(data));
    }

    return regionsToHumanReadableData;
  }

  private Map<UUID, String> toHumanReadable(Map<UUID, Double> regionsToData) {
    Map<UUID, String> regionsToHumanReadableData = new HashMap<>();

    for (Map.Entry<UUID, Double> entry : regionsToData.entrySet()) {
      double data = entry.getValue();
      data = data / 1E9;
      regionsToHumanReadableData.put(entry.getKey(), DECIMAL_FORMAT.format(data));
    }

    return regionsToHumanReadableData;
  }

  private Map<UUID, List<String>> toHumanReadableCI(
      Map<UUID, List<Double>> regionsToConfidenceIntervals) {
    Map<UUID, List<String>> regionsToHumanReadableCI = new HashMap<>();

    for (Map.Entry<UUID, List<Double>> entry : regionsToConfidenceIntervals.entrySet()) {
      List<Double> confidenceInterval = entry.getValue();
      double lower = confidenceInterval.get(0) / 1E9;
      double higher = confidenceInterval.get(1) / 1E9;
      List<String> confidenceIntervalHumanReadable = new ArrayList<>();
      confidenceIntervalHumanReadable.add(DECIMAL_FORMAT.format(lower));
      confidenceIntervalHumanReadable.add(DECIMAL_FORMAT.format(higher));
      regionsToHumanReadableCI.put(entry.getKey(), confidenceIntervalHumanReadable);
    }

    return regionsToHumanReadableCI;
  }

  private void addAllExecutions(
      SummaryStatisticsMap<UUID> regionsToStats,
      Set<String> config,
      Collection<Set<ProcessedPerfExecution>> allProcessedPerfExecutions) {
    for (Set<ProcessedPerfExecution> processedPerfExecutions : allProcessedPerfExecutions) {
      for (ProcessedPerfExecution processedPerfExecution : processedPerfExecutions) {
        if (!processedPerfExecution.getConfiguration().equals(config)) {
          continue;
        }

        for (Map.Entry<String, Long> entry : processedPerfExecution.getRegionsToPerf().entrySet()) {
          UUID region = UUID.fromString(entry.getKey());
          SummaryStatistics stats = regionsToStats.get(region);
          stats.addValue(entry.getValue());
        }

        break;
      }
    }
  }

  private SummaryStatisticsMap<UUID> addRegions(
      Set<String> config, Collection<Set<ProcessedPerfExecution>> allProcessedPerfExecutions) {
    SummaryStatisticsMap<UUID> regionsToPerf = new SummaryStatisticsMap<>();

    for (Set<ProcessedPerfExecution> processedPerfExecutions : allProcessedPerfExecutions) {
      for (ProcessedPerfExecution processedPerfExecution : processedPerfExecutions) {
        if (!processedPerfExecution.getConfiguration().equals(config)) {
          continue;
        }

        for (String region : processedPerfExecution.getRegionsToPerf().keySet()) {
          regionsToPerf.putIfAbsent(UUID.fromString(region));
        }

        break;
      }
    }

    return regionsToPerf;
  }

  private Set<Set<String>> getConfigs() {
    Set<Set<String>> configs = new HashSet<>();
    Collection<Set<ProcessedPerfExecution>> allProcessedPerfExecutions =
        this.itersToProcessedPerfExecution.values();

    for (Set<ProcessedPerfExecution> processedPerfExecutions : allProcessedPerfExecutions) {

      for (ProcessedPerfExecution processedPerfExecution : processedPerfExecutions) {
        configs.add(processedPerfExecution.getConfiguration());
      }
    }

    return configs;
  }

  @Override
  public void writeToFile(Set<PerformanceEntry> results) throws IOException {
    for (PerformanceEntry performanceEntry : results) {
      String outputFile = this.outputDir + "/" + UUID.randomUUID() + Options.DOT_JSON;
      File file = new File(outputFile);
      file.getParentFile().mkdirs();

      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(file, performanceEntry);
    }
  }

  @Override
  public Set<PerformanceEntry> readFromFile(File dir) throws IOException {
    Collection<File> files = FileUtils.listFiles(dir, new String[] {"json"}, false);
    Set<PerformanceEntry> entries = new HashSet<>();

    for (File file : files) {
      ObjectMapper mapper = new ObjectMapper();
      PerformanceEntry performanceEntry =
          mapper.readValue(file, new TypeReference<PerformanceEntry>() {});
      entries.add(performanceEntry);
    }

    return entries;
  }
}
