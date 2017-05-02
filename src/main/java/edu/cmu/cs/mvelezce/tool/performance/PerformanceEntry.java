package edu.cmu.cs.mvelezce.tool.performance;

import edu.cmu.cs.mvelezce.tool.analysis.Region;

import java.util.*;

/**
 * Created by mvelezce on 4/10/17.
 */
public class PerformanceEntry {
    private static long baseTime = -1; // TODO make this non static since it does not make sense. It makes sense for a single program, but we have to be general.

    private Set<String> configuration;
    private Map<Region, Region> regions; // TODO make set
    private Map<Region, Long> regionsToExecutionTime; // TODO make set
    private Region program;

    public PerformanceEntry(Set<String> configuration, Set<Region> regions, Region program) {
        this.configuration = configuration;
        this.regions = new HashMap<>();

        for(Region region : regions) {
            if(region.getStartTime() > region.getEndTime()) {
                throw new RuntimeException("A region has a negative execution time. This might be caused by incorrect instrumentation");
            }

            Region clonedRegion = region.clone();
            this.regions.put(clonedRegion, clonedRegion);
        }

        this.program = program.clone();
    }

    public PerformanceEntry(Set<String> configuration, List<Region> executedRegions) {
        this.configuration = configuration;
        this.regionsToExecutionTime = new HashMap<>();

        this.calculateRealPerformance(executedRegions);
    }

    /**
     * Recreating execution trace to calculate real performance of each region
     *
     * @param executedRegions
     */
    public void calculateRealPerformance(List<Region> executedRegions) {
        Stack<Region> executingRegions = new Stack<>();
        Stack<Long> innerRegionExecutionTime = new Stack<>();
        Region previousRegionEntry = null;

        for(Region executingRegion : executedRegions) {
            if(executingRegions.isEmpty() || !executingRegions.peek().getRegionID().equals(executingRegion.getRegionID())) {
                executingRegions.add(executingRegion);
                innerRegionExecutionTime.push((long) 0);
            }
            else {
                if(executingRegion.getStartTime() > executingRegion.getEndTime()) {
                    throw new RuntimeException("A region has a negative execution time. This might be caused by incorrect instrumentation");
                }

                executingRegions.pop();
                long regionExecutionTime = executingRegion.getEndTime() - executingRegion.getStartTime();

                if(!previousRegionEntry.getRegionID().equals(executingRegion.getRegionID())) {
                    regionExecutionTime -= innerRegionExecutionTime.peek();
                }

                innerRegionExecutionTime.pop();

                if(!executingRegions.isEmpty()) {
                    Stack<Long> added = new Stack<>();

                    while(!innerRegionExecutionTime.isEmpty()) {
                        long currentInnerRegionExecutionTime = innerRegionExecutionTime.pop();
                        added.push(currentInnerRegionExecutionTime + regionExecutionTime);
                    }

                    while(!added.isEmpty()) {
                        innerRegionExecutionTime.push(added.pop());
                    }
                }

                this.regionsToExecutionTime.put(executingRegion, regionExecutionTime);
            }

            previousRegionEntry = executingRegion;
        }
    }

    public static void reset() {
        PerformanceEntry.baseTime = -1;
    }

    public Region getRegion(Region region) {
        return this.regions.get(region);
    }

    public Set<String> getConfiguration() { return this.configuration; }

    public Set<Region> getRegions() { return this.regions.keySet(); }

    public Region getProgram() { return this.program; }

    public Map<Region, Long> getRegionsToExecutionTime() { return this.regionsToExecutionTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerformanceEntry that = (PerformanceEntry) o;

        if (!configuration.equals(that.configuration)) return false;
        return regions.equals(that.regions);
    }

    @Override
    public int hashCode() {
        int result = configuration.hashCode();
        result = 31 * result + regions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PerformanceEntry{" +
                "configuration=" + configuration +
                ", regions=" + regions +
                '}';
    }
}
