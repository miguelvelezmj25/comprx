package edu.cmu.cs.mvelezce.instrument.region.transformer.utils.propagation.intra.up;

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

public abstract class BaseUpExpander<T> extends BlockRegionAnalyzer<T> {

  public BaseUpExpander(
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
    MethodBlock id = graph.getImmediateDominator(block);

    if (id == null || id == graph.getEntryBlock()) {
      return new HashSet<>();
    }

    //    System.out.println(graph.toDotString("test"));

    JavaRegion idRegion = blocksToRegions.get(id);
    T idData = this.getData(idRegion);
    T regionData = this.getData(region);

    if (regionData == null) {
      throw new RuntimeException("The data at this region cannot be null");
    }

    // If the data are the same, we do not want to process anything
    if (regionData.equals(idData) || !this.canExpandUp(regionData, idData)) {
      return new HashSet<>();
    }

    return this.expandDataUp(region, block, regionData, blocksToRegions);
  }

  private Set<MethodBlock> expandDataUp(
      JavaRegion region,
      MethodBlock block,
      T regionData,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    Set<MethodBlock> predBlocks = block.getPredecessors();

    if (block.getPredecessors().isEmpty()) {
      throw new RuntimeException("The predecessors cannot be empty " + block.getID());
    }

    Set<MethodBlock> updatedBlocks = new HashSet<>();

    for (MethodBlock predBlock : predBlocks) {
      // A block might jump to itself
      if (block == predBlock) {
        continue;
      }

      JavaRegion predRegion = blocksToRegions.get(predBlock);
      T predData = this.getData(predRegion);

      if (regionData.equals(predData) || this.containsAll(predData, regionData)) {
        continue;
      }

      if (!this.canExpandUp(regionData, predData)) {
        System.err.println(
            "Might not be able to merge all constraints (e.g., up = {A}, {!A}; down = {A}, {B}; can only merge up {A}, not {B})");
        //        if (predBlock.isCatchWithImplicitThrow()) {
        //          throw new RuntimeException("Why do we check this?");
        //        }

        throw new RuntimeException(
            "Cannot merge data from " + block.getID() + " to " + predBlock.getID());
      }

      if (predRegion == null) {
        predRegion =
            new JavaRegion.Builder(
                    region.getRegionPackage(),
                    region.getRegionClass(),
                    region.getRegionMethod(),
                    region.getStartIndex())
                .build();
        blocksToRegions.put(predBlock, predRegion);
      }

      T newData = this.mergeData(regionData, predData);
      this.addRegionToData(predRegion, newData);
      updatedBlocks.add(predBlock);
      //
      //      JavaRegion newRegion = new JavaRegion.Builder(blockRegion.getRegionPackage(),
      //              blockRegion.getRegionClass(), blockRegion.getRegionMethod()).build();
      //      int index;
      //
      //
      //      if (predRegion == null) {
      //        index = methodNode.instructions.indexOf(id.getInstructions().get(0));
      //      }
      //      else {
      //        index = predRegion.getStartRegionIndex();
      //        this.getRegionsToData().remove(predRegion);
      //      }
      //
      //      newRegion.setStartRegionIndex(index);
      //      blocksToRegions.put(pred, newRegion);
      //
      //      Set<Set<String>> newOptionSet = new HashSet<>();
      //      newOptionSet.add(blockDecision);
      //      this.getRegionsToData().put(newRegion, newOptionSet);
      //
      //      updatedBlocks.add(0, pred);
    }

    return updatedBlocks;
  }

  protected abstract boolean containsAll(@Nullable T upData, @Nullable T thisData);

  protected abstract T mergeData(T thisData, @Nullable T upData);

  protected abstract boolean canExpandUp(@Nullable T thisData, @Nullable T upData);
}
