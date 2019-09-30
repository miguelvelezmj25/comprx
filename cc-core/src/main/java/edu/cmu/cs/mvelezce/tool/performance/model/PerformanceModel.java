package edu.cmu.cs.mvelezce.tool.performance.model;

import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.performance.entry.DefaultPerformanceEntry;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO time can be in seconds, milliseonds, minutes,.... That affects the type of the block.

/**
 * Created by mvelezce on 3/9/17.
 */
public class PerformanceModel {

    private long baseTime = 0;
    private double baseTimeHumanReadable = 0.0;
    //    private double baseStd = 0;
//    private double baseStdHumanReadable = 0.0;
    private Map<Region, Map<Set<String>, Long>> regionsToPerformanceTables = new HashMap<>();
    private Map<Region, Map<Set<String>, Double>> regionsToPerformanceTablesHumanReadable = new HashMap<>();
//    private Map<Region, Map<Set<String>, Double>> regionsToStdTables = new HashMap<>();
//    private Map<Region, Map<Set<String>, Double>> regionsToStdTablesHumanReadable = new HashMap<>();

    public PerformanceModel() {
        ;
    }

    public PerformanceModel(long baseTime, Map<Region, Map<Set<String>, Long>> regionsToPerformanceTables) {
        this.baseTime = baseTime;
        this.regionsToPerformanceTables = regionsToPerformanceTables;

        this.baseTimeHumanReadable = DefaultPerformanceEntry.toHumanReadable(DefaultPerformanceEntry.toSeconds(this.baseTime));
        this.regionsToPerformanceTablesHumanReadable = PerformanceModel.toHumanReadablePerformance(this.regionsToPerformanceTables);
    }

    /**
     * The assumption is that the values in the tables are in nanoseconds.
     *
     * @param regionsToPerformanceTables
     * @return
     */
    private static Map<Region, Map<Set<String>, Double>> toHumanReadablePerformance(Map<Region, Map<Set<String>, Long>> regionsToPerformanceTables) {
        Map<Region, Map<Set<String>, Double>> result = new HashMap<>();

        for(Map.Entry<Region, Map<Set<String>, Long>> regionToTable : regionsToPerformanceTables.entrySet()) {
            Map<Set<String>, Double> configToNewPerformance = new HashMap<>();

            for(Map.Entry<Set<String>, Long> configToPerformance : regionToTable.getValue().entrySet()) {
                double seconds = DefaultPerformanceEntry.toSeconds(configToPerformance.getValue());
                configToNewPerformance.put(configToPerformance.getKey(), DefaultPerformanceEntry.toHumanReadable(seconds));
            }

            result.put(regionToTable.getKey(), configToNewPerformance);
        }

        return result;
    }

    private static Map<Region, Map<Set<String>, Double>> toHumanReadableStd(Map<Region, Map<Set<String>, Double>> regionsToStdTables) {
        Map<Region, Map<Set<String>, Double>> result = new HashMap<>();

        for(Map.Entry<Region, Map<Set<String>, Double>> regionToTable : regionsToStdTables.entrySet()) {
            Map<Set<String>, Double> configToNewStd = new HashMap<>();

            for(Map.Entry<Set<String>, Double> configToStd : regionToTable.getValue().entrySet()) {
                double seconds = PerformanceEntryStatistic.toSeconds(configToStd.getValue());
                configToNewStd.put(configToStd.getKey(), DefaultPerformanceEntry.toHumanReadable(seconds));
            }

            result.put(regionToTable.getKey(), configToNewStd);
        }

        return result;
    }


    public double evaluate(Set<String> configuration) {
        double performance = this.baseTimeHumanReadable;

        for(Map.Entry<Region, Map<Set<String>, Double>> entry : this.regionsToPerformanceTablesHumanReadable.entrySet()) {
            Set<String> optionsInRegion = new HashSet<>();

            for(Set<String> options : entry.getValue().keySet()) {
                optionsInRegion.addAll(options);
            }

            Set<String> configurationValueInRegion = new HashSet<>(configuration);
            configurationValueInRegion.retainAll(optionsInRegion);

            for(Map.Entry<Set<String>, Double> configurationToPerformance : entry.getValue().entrySet()) {
                if(configurationToPerformance.getKey().equals(configurationValueInRegion)) {
                    performance += configurationToPerformance.getValue();
                }
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String perfString = decimalFormat.format(performance);
        performance = Double.valueOf(perfString);

        return performance;
    }

    // TODO fix later
    public double evaluateStd(Set<String> configuration) {
        return 0.0;
//        double std = this.baseStdHumanReadable;
//
//        for(Map.Entry<Region, Map<Set<String>, Double>> entry : this.regionsToStdTablesHumanReadable.entrySet()) {
//            Set<String> optionsInRegion = new HashSet<>();
//
//            for(Set<String> options : entry.getValue().keySet()) {
//                optionsInRegion.addAll(options);
//            }
//
//            Set<String> configurationValueInRegion = new HashSet<>(configuration);
//            configurationValueInRegion.retainAll(optionsInRegion);
//
//            for(Map.Entry<Set<String>, Double> configurationToStd : entry.getValue().entrySet()) {
//                if(configurationToStd.getKey().equals(configurationValueInRegion)) {
//                    std += configurationToStd.getValue();
//                }
//            }
//        }
//
//        DecimalFormat decimalFormat = new DecimalFormat("#.###");
//        String perfString = decimalFormat.format(std);
//        std = Double.valueOf(perfString);
//
//        return std;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        StringBuilder pm = new StringBuilder();
        pm.append("Base -> ");
        pm.append(decimalFormat.format(this.baseTimeHumanReadable));
        pm.append("\n");

        for(Map<Set<String>, Double> performanceTables : this.regionsToPerformanceTablesHumanReadable.values()) {
            for(Map.Entry<Set<String>, Double> configurationToPerformance : performanceTables.entrySet()) {
                pm.append(configurationToPerformance.getKey());
                pm.append(" -> ");
                pm.append(decimalFormat.format(configurationToPerformance.getValue()));
                pm.append("\n");
            }

            pm.append("\n");
        }

        return pm.toString();
    }
//    @Override
//    public String toString() {
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        StringBuilder performanceModel = new StringBuilder("T =");
//        Set<String> allOptions = new HashSet<>();
//
//        for(Set<String> configurations : this.configurationToPerformance.keySet()) {
//            allOptions.addAll(configurations);
//        }
//
//        for(int i = 0; i <= allOptions.size(); i++) {
//            for(Map.Entry<Set<String>, Double> entry : this.configurationToPerformance.entrySet()) {
//                if(entry.getKey().size() != i) {
//                    continue;
//                }
//
//                if(Math.abs(entry.getValue()) < 0.01) {
//                    continue;
//                }
//
//                if(entry.getValue() > 0) {
//                    performanceModel.append(" + ");
//                }
//                else {
//                    performanceModel.append(" - ");
//                }
//
//                performanceModel.append(decimalFormat.format(Math.abs(entry.getValue())));
//
//                for(String option : entry.getKey()) {
//                    performanceModel.append(option).append(" ");
//                }
//
//                if(!entry.getKey().isEmpty()) {
//                    performanceModel.deleteCharAt(performanceModel.length() - 1);
//                }
//            }
//
//        }
//
//        return performanceModel.toString();
//    }

//    @Override
//    public boolean equals(Object o) {
//        if(this == o) {
//            return true;
//        }
//        if(o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        PerformanceModel that = (PerformanceModel) o;
//
//        return configurationToPerformance.equals(that.configurationToPerformance);
//    }
//
//    @Override
//    public int hashCode() {
//        return configurationToPerformance.hashCode();
//    }


    public long getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }

    public double getBaseTimeHumanReadable() {
        return baseTimeHumanReadable;
    }

    public void setBaseTimeHumanReadable(double baseTimeHumanReadable) {
        this.baseTimeHumanReadable = baseTimeHumanReadable;
    }

    public Map<Region, Map<Set<String>, Long>> getRegionsToPerformanceTables() {
        return regionsToPerformanceTables;
    }

    public void setRegionsToPerformanceTables(Map<Region, Map<Set<String>, Long>> regionsToPerformanceTables) {
        this.regionsToPerformanceTables = regionsToPerformanceTables;
    }

    public Map<Region, Map<Set<String>, Double>> getRegionsToPerformanceTablesHumanReadable() {
        return regionsToPerformanceTablesHumanReadable;
    }

    public void setRegionsToPerformanceTablesHumanReadable(Map<Region, Map<Set<String>, Double>> regionsToPerformanceTablesHumanReadable) {
        this.regionsToPerformanceTablesHumanReadable = regionsToPerformanceTablesHumanReadable;
    }

//    public double getBaseStd() {
//        return baseStd;
//    }
//
//    public void setBaseStd(double baseStd) {
//        this.baseStd = baseStd;
//    }
//
//    public double getBaseStdHumanReadable() {
//        return baseStdHumanReadable;
//    }
//
//    public void setBaseStdHumanReadable(double baseStdHumanReadable) {
//        this.baseStdHumanReadable = baseStdHumanReadable;
//    }

//    public Map<Region, Map<Set<String>, Double>> getRegionsToStdTables() {
//        return regionsToStdTables;
//    }
//
//    public void setRegionsToStdTables(Map<Region, Map<Set<String>, Double>> regionsToStdTables) {
//        this.regionsToStdTables = regionsToStdTables;
//    }
//
//    public Map<Region, Map<Set<String>, Double>> getRegionsToStdTablesHumanReadable() {
//        return regionsToStdTablesHumanReadable;
//    }
//
//    public void setRegionsToStdTablesHumanReadable(Map<Region, Map<Set<String>, Double>> regionsToStdTablesHumanReadable) {
//        this.regionsToStdTablesHumanReadable = regionsToStdTablesHumanReadable;
//    }
}
