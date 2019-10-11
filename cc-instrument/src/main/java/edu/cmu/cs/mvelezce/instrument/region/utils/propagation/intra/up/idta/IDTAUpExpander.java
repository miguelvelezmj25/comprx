package edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.up.idta;

import de.fosd.typechef.featureexpr.FeatureExpr;
import edu.cmu.cs.mvelezce.analysis.region.java.JavaRegion;
import edu.cmu.cs.mvelezce.instrument.region.utils.blockRegionMatcher.BlockRegionMatcher;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.idta.BaseIDTAExpander;
import edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.up.BaseUpExpander;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class IDTAUpExpander extends BaseUpExpander<Set<FeatureExpr>> {

  private final BaseIDTAExpander baseIDTAExpander;

  public IDTAUpExpander(
      String programName,
      String debugDir,
      Set<String> options,
      BlockRegionMatcher blockRegionMatcher,
      Map<JavaRegion, Set<FeatureExpr>> regionsToData) {
    super(programName, debugDir, options, blockRegionMatcher, regionsToData);

    this.baseIDTAExpander = BaseIDTAExpander.getInstance();
    this.baseIDTAExpander.init(regionsToData.values());
  }

  @Override
  protected boolean containsAll(
      @Nullable Set<FeatureExpr> upConstraints, @Nullable Set<FeatureExpr> thisConstraints) {
    return this.baseIDTAExpander.containsAll(upConstraints, thisConstraints);
  }

  @Override
  protected String getPrettyData(@Nullable JavaRegion region) {
    Set<FeatureExpr> constraints = this.getData(region);
    Set<String> options = this.getOptions();

    return this.baseIDTAExpander.prettyPrintConstraints(constraints, options);
  }

  @Override
  protected boolean canExpandUp(
      @Nullable Set<FeatureExpr> thisConstraints, @Nullable Set<FeatureExpr> thatConstraints) {
    return this.baseIDTAExpander.canExpandConstraints(thisConstraints, thatConstraints);
  }

  @Override
  protected Set<FeatureExpr> mergeData(
      Set<FeatureExpr> thisConstraints, @Nullable Set<FeatureExpr> upConstraints) {
    return this.baseIDTAExpander.mergeData(thisConstraints, upConstraints);
  }
}
