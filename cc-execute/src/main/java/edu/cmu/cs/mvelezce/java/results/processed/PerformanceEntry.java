package edu.cmu.cs.mvelezce.java.results.processed;

import java.util.*;

/** Processed perf entry of several executions of the same config */
public class PerformanceEntry {

  private final Set<String> configuration;
  private final Map<UUID, Double> regionsToPerf;
  private final Map<UUID, Double> regionsToMin;
  private final Map<UUID, Double> regionsToMax;
  private final Map<UUID, Double> regionsToDiff;
  private final Map<UUID, Double> regionsToSampleVariance;
  private final Map<UUID, List<Double>> regionsToConfidenceInterval;
  private final Map<UUID, Double> regionsToCoefficientsOfVariation;
  private final Map<UUID, String> regionsToPerfHumanReadable;
  private final Map<UUID, String> regionsToMinHumanReadable;
  private final Map<UUID, String> regionsToMaxHumanReadable;
  private final Map<UUID, String> regionsToDiffHumanReadable;
  private final Map<UUID, String> regionsToSampleVarianceHumanReadable;
  private final Map<UUID, List<String>> regionsToConfidenceIntervalsHumanReadable;
  private final Map<UUID, String> regionsToCoefficientsOfVariationHumanReadable;

  // Dummy constructor for jackson xml
  private PerformanceEntry() {
    this.configuration = new HashSet<>();
    this.regionsToPerf = new HashMap<>();
    this.regionsToMin = new HashMap<>();
    this.regionsToMax = new HashMap<>();
    this.regionsToDiff = new HashMap<>();
    this.regionsToSampleVariance = new HashMap<>();
    this.regionsToConfidenceInterval = new HashMap<>();
    this.regionsToCoefficientsOfVariation = new HashMap<>();
    this.regionsToPerfHumanReadable = new HashMap<>();
    this.regionsToMinHumanReadable = new HashMap<>();
    this.regionsToMaxHumanReadable = new HashMap<>();
    this.regionsToDiffHumanReadable = new HashMap<>();
    this.regionsToSampleVarianceHumanReadable = new HashMap<>();
    this.regionsToConfidenceIntervalsHumanReadable = new HashMap<>();
    this.regionsToCoefficientsOfVariationHumanReadable = new HashMap<>();
  }

  public PerformanceEntry(
      Set<String> configuration,
      Map<UUID, Double> regionsToPerf,
      Map<UUID, Double> regionsToMin,
      Map<UUID, Double> regionsToMax,
      Map<UUID, Double> regionsToDiff,
      Map<UUID, Double> regionsToSampleVariance,
      Map<UUID, List<Double>> regionsToConfidenceInterval,
      Map<UUID, Double> regionsToCoefficientsOfVariation,
      Map<UUID, String> regionsToPerfHumanReadable,
      Map<UUID, String> regionsToMinHumanReadable,
      Map<UUID, String> regionsToMaxHumanReadable,
      Map<UUID, String> regionsToDiffHumanReadable,
      Map<UUID, String> regionsToSampleVarianceHumanReadable,
      Map<UUID, List<String>> regionsToConfidenceIntervalsHumanReadable,
      Map<UUID, String> regionsToCoefficientsOfVariationHumanReadable) {
    this.configuration = configuration;
    this.regionsToPerf = regionsToPerf;
    this.regionsToMin = regionsToMin;
    this.regionsToMax = regionsToMax;
    this.regionsToDiff = regionsToDiff;
    this.regionsToSampleVariance = regionsToSampleVariance;
    this.regionsToConfidenceInterval = regionsToConfidenceInterval;
    this.regionsToCoefficientsOfVariation = regionsToCoefficientsOfVariation;
    this.regionsToPerfHumanReadable = regionsToPerfHumanReadable;
    this.regionsToMinHumanReadable = regionsToMinHumanReadable;
    this.regionsToMaxHumanReadable = regionsToMaxHumanReadable;
    this.regionsToDiffHumanReadable = regionsToDiffHumanReadable;
    this.regionsToSampleVarianceHumanReadable = regionsToSampleVarianceHumanReadable;
    this.regionsToConfidenceIntervalsHumanReadable = regionsToConfidenceIntervalsHumanReadable;
    this.regionsToCoefficientsOfVariationHumanReadable =
        regionsToCoefficientsOfVariationHumanReadable;
  }

  public Set<String> getConfiguration() {
    return configuration;
  }

  public Map<UUID, Double> getRegionsToPerf() {
    return regionsToPerf;
  }

  public Map<UUID, Double> getRegionsToMin() {
    return regionsToMin;
  }

  public Map<UUID, Double> getRegionsToMax() {
    return regionsToMax;
  }

  public Map<UUID, Double> getRegionsToDiff() {
    return regionsToDiff;
  }

  public Map<UUID, Double> getRegionsToSampleVariance() {
    return regionsToSampleVariance;
  }

  public Map<UUID, List<Double>> getRegionsToConfidenceInterval() {
    return regionsToConfidenceInterval;
  }

  public Map<UUID, String> getRegionsToPerfHumanReadable() {
    return regionsToPerfHumanReadable;
  }

  public Map<UUID, String> getRegionsToMinHumanReadable() {
    return regionsToMinHumanReadable;
  }

  public Map<UUID, String> getRegionsToMaxHumanReadable() {
    return regionsToMaxHumanReadable;
  }

  public Map<UUID, String> getRegionsToDiffHumanReadable() {
    return regionsToDiffHumanReadable;
  }

  public Map<UUID, String> getRegionsToSampleVarianceHumanReadable() {
    return regionsToSampleVarianceHumanReadable;
  }

  public Map<UUID, List<String>> getRegionsToConfidenceIntervalsHumanReadable() {
    return regionsToConfidenceIntervalsHumanReadable;
  }

  public Map<UUID, Double> getRegionsToCoefficientsOfVariation() {
    return regionsToCoefficientsOfVariation;
  }

  public Map<UUID, String> getRegionsToCoefficientsOfVariationHumanReadable() {
    return regionsToCoefficientsOfVariationHumanReadable;
  }
}
