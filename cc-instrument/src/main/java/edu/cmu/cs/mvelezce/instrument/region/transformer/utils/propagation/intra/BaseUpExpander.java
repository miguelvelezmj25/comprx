package edu.cmu.cs.mvelezce.instrument.region.transformer.utils.propagation.intra;

import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.region.transformer.utils.blockRegionAnalyzer.BlockRegionAnalyzer;
import edu.cmu.cs.mvelezce.instrument.region.transformer.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrumenter.graph.MethodGraph;
import edu.cmu.cs.mvelezce.instrumenter.graph.block.MethodBlock;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseUpExpander<T> extends BlockRegionAnalyzer<T> {

  public BaseUpExpander(
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, T> regionsToData) {
    super(options, blockRegionMatcher, regionsToData);
  }

  @Override
  protected void processBlock(
      MethodBlock block,
      JavaRegion region,
      MethodGraph graph,
      LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    System.err.println("Have to continue expanding regions until a fix point");
    MethodBlock id = graph.getImmediateDominator(block);

    if (id == null || id == graph.getEntryBlock()) {
      return;
    }

    System.out.println(graph.toDotString("test"));

    JavaRegion idRegion = blocksToRegions.get(id);
    T idData = this.getData(idRegion);
    T regionData = this.getData(region);

    if (regionData == null) {
      throw new RuntimeException("The data at this region cannot be null");
    }

    // If the data are the same, we do not want to process anything
    if (regionData.equals(idData) || !this.canExpandUp(regionData, idData)) {
      return;
    }

    this.expandUp(block, regionData, blocksToRegions);
  }

  private void expandUp(
      MethodBlock block, T regionData, LinkedHashMap<MethodBlock, JavaRegion> blocksToRegions) {
    Set<MethodBlock> predBlocks = block.getPredecessors();

    if (block.getPredecessors().isEmpty()) {
      throw new RuntimeException("The predecessors cannot be empty " + block.getID());
    }

    for (MethodBlock predBlock : predBlocks) {
      // A block might jump to itself
      if (block == predBlock) {
        continue;
      }

      @Nullable JavaRegion predRegion = blocksToRegions.get(predBlock);
      T predData = this.getData(predRegion);

      if (!this.canExpandUp(regionData, predData)) {
        System.err.println(
            "Might not be able to merge all constraints (e.g., up = {A}, {!A}; down = {A}, {B}; can only merge up {A}, not {B})");
        if (predBlock.isCatchWithImplicitThrow()) {
          throw new RuntimeException("Why do we check this?");
          //                  continue;
        }

        //        this.debugBlockDecisions(methodNode);
        throw new RuntimeException(
            "Cannot merge data from " + block.getID() + " to " + predBlock.getID());
        //
        //////                    System.out.println("Cannot push up to predecessor in " +
        // methodNode.name + " " + bDecision + " -> " + aDecision);
        ////                    continue;
      }

      if (predRegion == null) {
        throw new UnsupportedOperationException("Implement");
      }

      T newData = this.mergeData(regionData, predData);
      this.addRegionToData(predRegion, newData);
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

    //    return updatedBlocks;
  }

  protected abstract T mergeData(T thisData, @Nullable T thatData);

  protected abstract boolean canExpandUp(@Nullable T thisData, @Nullable T upData);
}
