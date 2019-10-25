package edu.cmu.cs.mvelezce.instrument.region.utils.startEndBlocksSetter.idta;

import de.fosd.typechef.featureexpr.FeatureExpr;
import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.idta.BaseIDTAExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.sootAsmMethodMatcher.SootAsmMethodMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.startEndBlocksSetter.BaseStartEndRegionBlocksSetter;
import soot.jimple.toolkits.callgraph.CallGraph;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class IDTAStartEndRegionBlocksSetter
    extends BaseStartEndRegionBlocksSetter<Set<FeatureExpr>> {

  private final BaseIDTAExpander baseIDTAExpander;

  public IDTAStartEndRegionBlocksSetter(
      String programName,
      String debugDir,
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, Set<FeatureExpr>> regionsToData,
      SootAsmMethodMatcher sootAsmMethodMatcher,
      CallGraph callGraph,
      BaseIDTAExpander baseIDTAExpander) {
    super(
        programName,
        debugDir,
        options,
        blockRegionMatcher,
        regionsToData,
        sootAsmMethodMatcher,
        callGraph);

    this.baseIDTAExpander = baseIDTAExpander;
  }

  @Override
  protected String getPrettyData(@Nullable JavaRegion region) {
    Set<FeatureExpr> constraints = this.getData(region);
    Set<String> options = this.getOptions();

    return this.baseIDTAExpander.prettyPrintConstraints(constraints, options);
  }

  @Override
  protected boolean completelyContainsAll(
      Set<FeatureExpr> callerConstraints, @Nullable Set<FeatureExpr> calleeConstraints) {
    return this.baseIDTAExpander.completelyImplies(callerConstraints, calleeConstraints);
  }
}
