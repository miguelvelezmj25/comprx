package edu.cmu.cs.mvelezce.instrument.region.transformer.utils.propagation.intra.down;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.region.transformer.utils.blockRegionAnalyzer.BlockRegionAnalyzer;
import edu.cmu.cs.mvelezce.instrument.region.transformer.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrumenter.graph.MethodGraph;
import edu.cmu.cs.mvelezce.instrumenter.graph.block.MethodBlock;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseDownExpander<T> extends BlockRegionAnalyzer<T> {
  public BaseDownExpander(
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, T> regionsToData) {
    super(options, blockRegionMatcher, regionsToData);
  }

  @Override
  protected Set<MethodBlock> processBlock(
      MethodBlock block,
      JavaRegion region,
      MethodGraph graph,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    Set<MethodBlock> succBlocks = block.getSuccessors();
    MethodBlock exit = graph.getExitBlock();

    if (succBlocks.size() == 1 && succBlocks.iterator().next().equals(exit)) {
      return new HashSet<>();
    }

    //    System.out.println(graph.toDotString("test"));

    MethodBlock ipd = graph.getImmediatePostDominator(block);
    JavaRegion ipdRegion = blocksToRegions.get(ipd);
    T ipdData = this.getData(ipdRegion);
    T regionData = this.getData(region);

    if (regionData == null) {
      throw new RuntimeException("The data at this region cannot be null");
    }

    while (!ipd.equals(exit) && this.canExpandDown(regionData, ipdData)) {
      ipd = graph.getImmediatePostDominator(ipd);
      ipdRegion = blocksToRegions.get(ipd);
      ipdData = this.getData(ipdRegion);
    }

    Set<MethodBlock> reachables = graph.getReachableBlocks(block, ipd);
    reachables.remove(block);
    reachables.remove(exit);
    reachables.remove(ipd);

    return this.copyRegionDataDown(region, regionData, reachables, blocksToRegions);
  }

  private Set<MethodBlock> copyRegionDataDown(
      JavaRegion region,
      T regionData,
      Set<MethodBlock> reachables,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    Set<MethodBlock> updatedBlocks = new HashSet<>();

    for (MethodBlock reachable : reachables) {
      JavaRegion reachableRegion = blocksToRegions.get(reachable);
      T reachableData = this.getData(reachableRegion);

      if (regionData.equals(reachableData)) {
        continue;
      }

      if (reachableRegion == null) {
        reachableRegion =
            new JavaRegion.Builder(
                    region.getRegionPackage(),
                    region.getRegionClass(),
                    region.getRegionMethod(),
                    region.getStartIndex())
                .build();
        blocksToRegions.put(reachable, reachableRegion);
      }

      this.addRegionToData(reachableRegion, regionData);
      updatedBlocks.add(reachable);
    }

    return updatedBlocks;
  }

  protected abstract boolean canExpandDown(@Nullable T thisData, @Nullable T downData);
}
