package edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.down;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.BaseIntraExpander;
import edu.cmu.cs.mvelezce.instrumenter.graph.MethodGraph;
import edu.cmu.cs.mvelezce.instrumenter.graph.block.MethodBlock;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BaseDownIntraExpander<T> extends BaseIntraExpander<T> {
  public BaseDownIntraExpander(
      String programName,
      String debugDir,
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, T> regionsToData) {
    super(programName, debugDir, options, blockRegionMatcher, regionsToData);
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

    while (!ipd.equals(exit) && this.canExpandDataDown(regionData, ipdData)) {
      ipd = graph.getImmediatePostDominator(ipd);
      ipdRegion = blocksToRegions.get(ipd);
      ipdData = this.getData(ipdRegion);
    }

    Set<MethodBlock> reachables = graph.getReachableBlocks(block, ipd);
    reachables.remove(block);
    reachables.remove(exit);
    reachables.remove(ipd);
    List<MethodBlock> orderReachables = this.orderReachables(reachables, blocksToRegions);

    return this.expandDataDown(region, regionData, orderReachables, blocksToRegions, graph, ipd);
  }

  private List<MethodBlock> orderReachables(
      Set<MethodBlock> reachables, LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    List<MethodBlock> orderedReachables = new ArrayList<>();

    for (Map.Entry<MethodBlock, JavaRegion> entry : blocksToRegions.entrySet()) {
      MethodBlock block = entry.getKey();

      if (reachables.contains(block)) {
        orderedReachables.add(block);
      }
    }

    return orderedReachables;
  }

  private Set<MethodBlock> expandDataDown(
      JavaRegion region,
      T regionData,
      List<MethodBlock> reachables,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions,
      MethodGraph graph,
      MethodBlock ipd) {
    Set<MethodBlock> updatedBlocks = new HashSet<>();

    Set<MethodBlock> blocksToSkip = new HashSet<>();

    for (MethodBlock reachable : reachables) {
      if (blocksToSkip.contains(reachable)) {
        continue;
      }

      JavaRegion reachableRegion = blocksToRegions.get(reachable);
      T reachableData = this.getData(reachableRegion);

      if (regionData.equals(reachableData) || this.containsAll(reachableData, regionData)) {
        continue;
      }

      if (!this.canExpandDataDown(regionData, reachableData)) {
        MethodBlock reachableIPD = graph.getImmediatePostDominator(reachable);
        Set<MethodBlock> reachablesToSkip = graph.getReachableBlocks(reachable, reachableIPD);
        reachablesToSkip.remove(reachableIPD);
        blocksToSkip.addAll(reachablesToSkip);

        continue;
      }

      if (reachableRegion == null) {
        reachableRegion =
            new JavaRegion.Builder(
                    region.getRegionPackage(),
                    region.getRegionClass(),
                    region.getRegionMethodSignature(),
                    region.getStartIndex())
                .build();
        blocksToRegions.put(reachable, reachableRegion);
      }

      T newData = this.mergeData(regionData, reachableData);
      this.addRegionToData(reachableRegion, newData);
      updatedBlocks.add(reachable);
    }

    return updatedBlocks;
  }

  protected abstract boolean containsAll(T downData, T thisData);

  protected abstract T mergeData(T thisData, @Nullable T downData);

  protected abstract boolean canExpandDataDown(@Nullable T thisData, @Nullable T downData);
}
