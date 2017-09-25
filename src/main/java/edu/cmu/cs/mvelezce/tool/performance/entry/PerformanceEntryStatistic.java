package edu.cmu.cs.mvelezce.tool.performance.entry;

import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.*;

/**
 * Created by mvelezce on 7/5/17.
 */
public class PerformanceEntryStatistic extends DefaultPerformanceEntry {
    private boolean measured;

    private Map<Region, List<Long>> regionsToRawMinMax = new LinkedHashMap<>();
    private Map<Region, Long> regionsToRawMean = new LinkedHashMap<>();
    private Map<Region, Double> regionsToRawStd = new LinkedHashMap<>();

    private Map<Region, List<Double>> regionsToRawMinMaxHumanReadable = new LinkedHashMap<>();
    private Map<Region, Double> regionsToRawMeanHumanReadable = new LinkedHashMap<>();
    private Map<Region, Double> regionsToRawStdHumanReadable = new LinkedHashMap<>();

    private Map<Region, List<Long>> regionsToProcessedMinMax = new LinkedHashMap<>();
    private Map<Region, Long> regionsToProcessedMean = new LinkedHashMap<>();
    private Map<Region, Double> regionsToProcessedStd = new LinkedHashMap<>();

    private Map<Region, List<Double>> regionsToProcessedMinMaxHumanReadable = new LinkedHashMap<>();
    private Map<Region, Double> regionsToProcessedMeanHumanReadable = new LinkedHashMap<>();
    private Map<Region, Double> regionsToProcessedStdHumanReadable = new LinkedHashMap<>();

    public PerformanceEntryStatistic(boolean measured, Set<String> configuration) {
        super(configuration);

        this.measured = measured;
    }

    private void calculateRawMinMax(int index, List<DefaultPerformanceEntry> performanceEntries) {
        for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
            Iterator<Map.Entry<Region, Long>> performanceEntryIterator = performanceEntry.getRegionsToRawPerformance().entrySet().iterator();
            Iterator<Map.Entry<Region, List<Long>>> regionsToRawMinMaxMeanIterator = this.regionsToRawMinMax.entrySet().iterator();

            while (performanceEntryIterator.hasNext() && regionsToRawMinMaxMeanIterator.hasNext()) {
                Map.Entry<Region, Long> entry1 = performanceEntryIterator.next();
                Map.Entry<Region, List<Long>> entry2 = regionsToRawMinMaxMeanIterator.next();

                if(!entry1.getKey().getRegionID().equals(entry2.getKey().getRegionID())) {
                    throw new RuntimeException("The ids do not match");
                }

                List<Long> minMax = this.regionsToRawMinMax.get(entry2.getKey());
                long value;

                if(index == 0) {
                    value = minMax.get(index);
                    value = Math.min(value, entry1.getValue());

                }
                else {
                    value = minMax.get(index);
                    value = Math.max(value, entry1.getValue());
                }

                minMax.remove(index);
                minMax.add(index, value);
            }

            if(performanceEntryIterator.hasNext() && !regionsToRawMinMaxMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the regions iterator");
            }

            if(!performanceEntryIterator.hasNext() && regionsToRawMinMaxMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the performance iterator");
            }
        }
    }

    private void calculateProcessedMinMax(int index, List<DefaultPerformanceEntry> performanceEntries) {
        for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
            Iterator<Map.Entry<Region, Long>> performanceEntryIterator = performanceEntry.getRegionsToProcessedPerformance().entrySet().iterator();
            Iterator<Map.Entry<Region, List<Long>>> regionsToProcessedMinMaxMeanIterator = this.regionsToProcessedMinMax.entrySet().iterator();

            while (performanceEntryIterator.hasNext() && regionsToProcessedMinMaxMeanIterator.hasNext()) {
                Map.Entry<Region, Long> entry1 = performanceEntryIterator.next();
                Map.Entry<Region, List<Long>> entry2 = regionsToProcessedMinMaxMeanIterator.next();

                if(!entry1.getKey().getRegionID().equals(entry2.getKey().getRegionID())) {
                    throw new RuntimeException("The ids do not match");
                }

                List<Long> minMax = this.regionsToProcessedMinMax.get(entry2.getKey());
                long value;

                if(index == 0) {
                    value = minMax.get(index);
                    value = Math.min(value, entry1.getValue());

                }
                else {
                    value = minMax.get(index);
                    value = Math.max(value, entry1.getValue());
                }

                minMax.remove(index);
                minMax.add(index, value);
            }

            if(performanceEntryIterator.hasNext() && !regionsToProcessedMinMaxMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the regions iterator");
            }

            if(!performanceEntryIterator.hasNext() && regionsToProcessedMinMaxMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the performance iterator");
            }
        }
    }

    public void calculateMinMax(List<DefaultPerformanceEntry> performanceEntries) {
        // Raw data
        for(Region region : performanceEntries.iterator().next().getRegionsToRawPerformance().keySet()) {
            List<Long> minMax = new ArrayList<>();
            minMax.add(Long.MAX_VALUE);
            minMax.add(Long.MIN_VALUE);

            this.regionsToRawMinMax.put(region, minMax);
        }

        int minIndex = 0;
        this.calculateRawMinMax(minIndex, performanceEntries);

        int maxIndex = 1;
        this.calculateRawMinMax(maxIndex, performanceEntries);

        for(Map.Entry<Region, List<Long>> entry : this.regionsToRawMinMax.entrySet()) {
            List<Double> minMax = new ArrayList<>();

            minMax.add(0, DefaultPerformanceEntry.toSeconds(entry.getValue().get(0)));
            minMax.add(1, DefaultPerformanceEntry.toSeconds(entry.getValue().get(1)));

            this.regionsToRawMinMaxHumanReadable.put(entry.getKey(), minMax);
        }

        // Processed data
        for(Region region : performanceEntries.iterator().next().getRegionsToProcessedPerformance().keySet()) {
            List<Long> minMax = new ArrayList<>();
            minMax.add(Long.MAX_VALUE);
            minMax.add(Long.MIN_VALUE);

            this.regionsToProcessedMinMax.put(region, minMax);
        }

        minIndex = 0;
        this.calculateProcessedMinMax(minIndex, performanceEntries);

        maxIndex = 1;
        this.calculateProcessedMinMax(maxIndex, performanceEntries);

        for(Map.Entry<Region, List<Long>> entry : this.regionsToProcessedMinMax.entrySet()) {
            List<Double> minMax = new ArrayList<>();

            minMax.add(0, DefaultPerformanceEntry.toSeconds(entry.getValue().get(0)));
            minMax.add(1, DefaultPerformanceEntry.toSeconds(entry.getValue().get(1)));

            this.regionsToProcessedMinMaxHumanReadable.put(entry.getKey(), minMax);
        }
    }

    public void calculateMean(List<DefaultPerformanceEntry> performanceEntries) {
        // Raw data
        for(Region region : performanceEntries.iterator().next().getRegionsToRawPerformance().keySet()) {
            this.regionsToRawMean.put(region, 0L);
        }

        for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
            Iterator<Map.Entry<Region, Long>> performanceEntryIterator = performanceEntry.getRegionsToRawPerformance().entrySet().iterator();
            Iterator<Map.Entry<Region, Long>> regionsToMeanIterator = this.regionsToRawMean.entrySet().iterator();

            while (performanceEntryIterator.hasNext() && regionsToMeanIterator.hasNext()) {
                Map.Entry<Region, Long> entry1 = performanceEntryIterator.next();
                Map.Entry<Region, Long> entry2 = regionsToMeanIterator.next();

                if(!entry1.getKey().getRegionID().equals(entry2.getKey().getRegionID())) {
                    throw new RuntimeException("The ids do not match");
                }

                long sum = this.regionsToRawMean.get(entry2.getKey());
                sum += entry1.getValue();
                this.regionsToRawMean.put(entry2.getKey(), sum);
            }

            if(performanceEntryIterator.hasNext() && !regionsToMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the regions to mean iterator");
            }

            if(!performanceEntryIterator.hasNext() && regionsToMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the performance entry iterator");
            }
        }

        for(Map.Entry<Region, Long> regionToMean : this.regionsToRawMean.entrySet()) {
            long sum = regionToMean.getValue();
            sum /= performanceEntries.size();
            this.regionsToRawMean.put(regionToMean.getKey(), sum);
        }

        // Processed data
        for(Region region : performanceEntries.iterator().next().getRegionsToProcessedPerformance().keySet()) {
            this.regionsToProcessedMean.put(region, 0L);
        }

        for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
            Iterator<Map.Entry<Region, Long>> performanceEntryIterator = performanceEntry.getRegionsToProcessedPerformance().entrySet().iterator();
            Iterator<Map.Entry<Region, Long>> regionsToMeanIterator = this.regionsToProcessedMean.entrySet().iterator();

            while (performanceEntryIterator.hasNext() && regionsToMeanIterator.hasNext()) {
                Map.Entry<Region, Long> entry1 = performanceEntryIterator.next();
                Map.Entry<Region, Long> entry2 = regionsToMeanIterator.next();

                if(!entry1.getKey().getRegionID().equals(entry2.getKey().getRegionID())) {
                    throw new RuntimeException("The ids do not match");
                }

                long sum = this.regionsToProcessedMean.get(entry2.getKey());
                sum += entry1.getValue();
                this.regionsToProcessedMean.put(entry2.getKey(), sum);
            }

            if(performanceEntryIterator.hasNext() && !regionsToMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the regions to mean iterator");
            }

            if(!performanceEntryIterator.hasNext() && regionsToMeanIterator.hasNext()) {
                throw new RuntimeException("There were items left in the performance entry iterator");
            }
        }

        for(Map.Entry<Region, Long> regionToMean : this.regionsToProcessedMean.entrySet()) {
            long sum = regionToMean.getValue();
            sum /= performanceEntries.size();
            this.regionsToProcessedMean.put(regionToMean.getKey(), sum);
        }

        this.regionsToRawMeanHumanReadable = PerformanceEntryStatistic.toHumanReadable(this.regionsToRawMean);
        this.regionsToProcessedMeanHumanReadable = PerformanceEntryStatistic.toHumanReadable(this.regionsToProcessedMean);
    }

    public void calculateStd(List<DefaultPerformanceEntry> performanceEntries) {
        // Raw data
        for(Region region : performanceEntries.iterator().next().getRegionsToRawPerformance().keySet()) {
            this.regionsToRawStd.put(region, 0.0);
        }

        for(Map.Entry<Region, Double> regionToRawStd : this.regionsToRawStd.entrySet()) {
            Region region = regionToRawStd.getKey();
            List<Double> rawEntries = new ArrayList<>();

            for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
                for(Map.Entry<Region, Long> regionToRaw : performanceEntry.getRegionsToRawPerformance().entrySet()) {
                    if(!region.getRegionID().equals(regionToRaw.getKey().getRegionID())) {
                        continue;
                    }

                    rawEntries.add(regionToRaw.getValue().doubleValue());

                    break;
                }
            }

            double[] values = new double[rawEntries.size()];

            for(int i = 0; i < values.length; i++) {
                values[i] = rawEntries.get(i);
            }

            Mean meanCalc = new Mean();
            double mean = meanCalc.evaluate(values);

            StandardDeviation stdCalc = new StandardDeviation(false);
            double std = stdCalc.evaluate(values, mean);

            this.regionsToRawStd.put(region, std);
        }

        // Process data
        for(Region region : performanceEntries.iterator().next().getRegionsToProcessedPerformance().keySet()) {
            this.regionsToProcessedStd.put(region, 0.0);
        }

        for(Map.Entry<Region, Double> regionToProcessedStd : this.regionsToProcessedStd.entrySet()) {
            Region region = regionToProcessedStd.getKey();
            List<Double> rawEntries = new ArrayList<>();

            for(DefaultPerformanceEntry performanceEntry : performanceEntries) {
                for(Map.Entry<Region, Long> regionToProcessed : performanceEntry.getRegionsToProcessedPerformance().entrySet()) {
                    if(!region.getRegionID().equals(regionToProcessed.getKey().getRegionID())) {
                        continue;
                    }

                    rawEntries.add(regionToProcessed.getValue().doubleValue());

                    break;
                }
            }

            double[] values = new double[rawEntries.size()];

            for(int i = 0; i < values.length; i++) {
                values[i] = rawEntries.get(i);
            }

            Mean meanCalc = new Mean();
            double mean = meanCalc.evaluate(values);

            StandardDeviation stdCalc = new StandardDeviation(false);
            double std = stdCalc.evaluate(values, mean);

            this.regionsToProcessedStd.put(region, std);
        }

        this.regionsToRawStdHumanReadable = PerformanceEntryStatistic.stdToHumanReadable(this.regionsToRawStd);
        this.regionsToProcessedStdHumanReadable = PerformanceEntryStatistic.stdToHumanReadable(this.regionsToProcessedStd);
    }

    /**
     * The assumption is that the values in the map are in nanoseconds and we transform them to seconds
     *
     * @param regionsToPerformance
     * @return
     */
    public static Map<Region, Double> stdToHumanReadable(Map<Region, Double> regionsToPerformance) {
        Map<Region, Double> result = new LinkedHashMap<>();

        for(Map.Entry<Region, Double> entry : regionsToPerformance.entrySet()) {
            result.put(entry.getKey(), PerformanceEntryStatistic.toSeconds(entry.getValue()));
        }

        return result;
    }

    /**
     * The assumption is that the value of type long is in nanoseconds.
     *
     * @param nanoSeconds
     * @return
     */
    public static double toSeconds(double nanoSeconds) {
        return nanoSeconds / 1000000000.0;
    }

    public boolean isMeasured() {
        return measured;
    }

    public Map<Region, Double> getRegionsToProcessedStd() {
        return regionsToProcessedStd;
    }
}