package edu.cmu.cs.mvelezce.utils.stats;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.DecimalFormat;
import java.util.*;

public class DescriptiveStatisticsMap<T> {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");

  private final Map<T, DescriptiveStatistics> map = new HashMap<>();

  public void putIfAbsent(T entry) {
    this.map.putIfAbsent(entry, new DescriptiveStatistics());
  }

  public void put(T entry, DescriptiveStatistics descriptiveStats) {
    this.map.put(entry, descriptiveStats);
  }

  public DescriptiveStatistics get(T type) {
    return this.map.get(type);
  }

  public Map<T, DescriptiveStatistics> getMap() {
    return map;
  }

  public Map<T, Double> getEntriesToData() {
    Map<T, Double> typesToData = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      typesToData.put(entry.getKey(), entry.getValue().getPercentile(50.0));
    }

    return typesToData;
  }

  public Map<T, Double> getEntriesToMin() {
    Map<T, Double> typesToMin = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      typesToMin.put(entry.getKey(), entry.getValue().getMin());
    }

    return typesToMin;
  }

  public Map<T, Double> getEntriesToMax() {
    Map<T, Double> typesToMax = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      typesToMax.put(entry.getKey(), entry.getValue().getMax());
    }

    return typesToMax;
  }

  public Map<T, Double> getEntriesToDiff(Map<T, Double> entriesToMin, Map<T, Double> entriesToMax) {
    Map<T, Double> entriesToDiff = new HashMap<>();

    for (T entry : entriesToMin.keySet()) {
      entriesToDiff.put(entry, 0.0);
    }

    for (T entry : entriesToDiff.keySet()) {
      double max = entriesToMax.get(entry);
      double min = entriesToMin.get(entry);
      double diff = max - min;

      //      if (diff >= 1E9) {
      //        System.err.println(
      //            "The difference between the min and max executions of entry "
      //                + entry
      //                + " is greater than 1 sec. It is "
      //                + (diff / 1E9)
      //                + " in ["
      //                + (min / 1E9)
      //                + " - "
      //                + (max / 1E9)
      //                + "]");
      //      }

      entriesToDiff.put(entry, diff);
    }

    return entriesToDiff;
  }

  public Map<T, List<Double>> getEntriesToConfidenceInterval() {
    Map<T, List<Double>> entriesToConfidenceInterval = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      DescriptiveStatistics stats = entry.getValue();

      if (stats.getN() == 1) {
        continue;
      }

      TDistribution tDist = new TDistribution(stats.getN() - 1);
      double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - 0.95) / 2);
      double ciValue = critVal * Math.sqrt(stats.getVariance()) / Math.sqrt(stats.getN());
      double lowerCI = Math.max(0, stats.getMean() - ciValue);
      double higherCI = stats.getMean() + ciValue;

      List<Double> confidenceInterval = new ArrayList<>();
      confidenceInterval.add(lowerCI);
      confidenceInterval.add(higherCI);
      entriesToConfidenceInterval.put(entry.getKey(), confidenceInterval);
    }

    return entriesToConfidenceInterval;
  }

  public Map<T, Double> getEntriesToSampleVariance() {
    Map<T, Double> entriesToSampleVariance = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      entriesToSampleVariance.put(entry.getKey(), entry.getValue().getVariance());
    }

    return entriesToSampleVariance;
  }

  public Map<T, Double> getCoefficientsOfVariation() {
    Map<T, Double> typesToCoefficientsOfVariation = new HashMap<>();

    for (Map.Entry<T, DescriptiveStatistics> entry : this.map.entrySet()) {
      DescriptiveStatistics descriptiveStats = entry.getValue();
      double mean = descriptiveStats.getMean();
      double coefficient = 0.0;

      if (mean >= 1E7) {
        double standardDeviation = descriptiveStats.getStandardDeviation();
        coefficient =
            Math.max(standardDeviation / mean, 0.0) * (1 + (1.0 / (4.0 * descriptiveStats.getN())));
      }

      if (coefficient > 1.0) {
        double[] sortedValues = descriptiveStats.getSortedValues();

        if (sortedValues[sortedValues.length - 1] > 1E8) {
          String[] prettySortedValues = new String[sortedValues.length];

          for (int i = 0; i < sortedValues.length; i++) {
            prettySortedValues[i] = DECIMAL_FORMAT.format(sortedValues[i] / 1E9);
          }

          System.err.println(
              "The coefficient of variation of "
                  + entry.getKey()
                  + " is "
                  + DECIMAL_FORMAT.format(coefficient)
                  + ". Values"
                  + Arrays.toString(prettySortedValues));
        }
      }

      typesToCoefficientsOfVariation.put(entry.getKey(), coefficient);
    }

    return typesToCoefficientsOfVariation;
  }
}
