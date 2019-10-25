package edu.cmu.cs.mvelezce.instrument.region.utils.propagation.intra.idta;

import de.fosd.typechef.featureexpr.FeatureExpr;
import de.fosd.typechef.featureexpr.sat.SATFeatureExprFactory;
import edu.cmu.cs.mvelezce.explorer.utils.ConstraintUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class BaseIDTAExpander {

  private static final FeatureExpr FALSE = SATFeatureExprFactory.False();
  private static final BaseIDTAExpander INSTANCE = new BaseIDTAExpander();
  private final Set<FeatureExpr> globalConstraints = new HashSet<>();

  private BaseIDTAExpander() {}

  public static BaseIDTAExpander getInstance() {
    return INSTANCE;
  }

  public String prettyPrintConstraints(
      @Nullable Set<FeatureExpr> constraints, Set<String> options) {
    Set<String> prettyConstraints = new HashSet<>();

    if (constraints == null) {
      throw new RuntimeException("The constraints cannot be null");
    }

    for (FeatureExpr constraint : constraints) {
      String prettyConstraint = ConstraintUtils.prettyPrintFeatureExpr(constraint, options);
      prettyConstraints.add(prettyConstraint);
    }

    return prettyConstraints.toString();
  }

  public void init(Collection<Set<FeatureExpr>> setOfConstraints) {
    if (!this.globalConstraints.isEmpty()) {
      return;
    }

    for (Set<FeatureExpr> constraints : setOfConstraints) {
      this.globalConstraints.addAll(constraints);
    }
  }

  /** ∃ c ∈ GlobalConstraints . c ⟹ newConstraint */
  public boolean canMergeConstraints(
      @Nullable Set<FeatureExpr> expandingConstraints,
      @Nullable Set<FeatureExpr> currentConstraints) {
    if (expandingConstraints == null) {
      throw new RuntimeException("This case should never happen");
    }

    if (expandingConstraints.isEmpty()) {
      throw new RuntimeException("How can this data be empty, but not null?");
    }

    if (currentConstraints == null) {
      return true;
    }

    if (currentConstraints.isEmpty()) {
      throw new RuntimeException("How can that data be empty, but not null?");
    }

    Set<FeatureExpr> newConstraints = new HashSet<>();

    for (FeatureExpr expandingConstraint : expandingConstraints) {
      for (FeatureExpr currentConstraint : currentConstraints) {
        FeatureExpr newConstraint = expandingConstraint.and(currentConstraint);

        if (newConstraint.isContradiction()) {
          continue;
        }

        newConstraints.add(newConstraint);
      }
    }

    //    for (FeatureExpr newConstraint : newConstraints) {
    //      boolean existsGlobalConstraint = false;
    //
    //      for (FeatureExpr globalConstraint : this.globalConstraints) {
    //        if (globalConstraint.implies(newConstraint).isTautology()) {
    //          existsGlobalConstraint = true;
    //          break;
    //        }
    //      }
    //
    //      if (!existsGlobalConstraint) {
    //        return false;
    //      }
    //    }
    //
    //    if (!this.globalConstraints.containsAll(newConstraints)) {
    //      throw new RuntimeException(
    //          "The global set of constraints does not include all of the new constraints that we
    // derived, but all of the new constraints are implied by at least one global constraints");
    //    }
    //
    //    return true;

    return this.impliesAll(this.globalConstraints, newConstraints);
  }

  /** ∀ dc ∈ ImpliedConstraints . ∃ gc ∈ ImplyingConstraints . gc ⟹ dc */
  public boolean impliesAll(
      Set<FeatureExpr> implyingConstraints, Set<FeatureExpr> impliedConstraints) {
    boolean containsAll = this.containsAll(implyingConstraints, impliedConstraints);

    if (implyingConstraints == null || impliedConstraints == null) {
      return false;
    }

    for (FeatureExpr impliedConstraint : impliedConstraints) {
      boolean exists = false;

      for (FeatureExpr implyingConstraint : implyingConstraints) {
        if (implyingConstraint.implies(impliedConstraint).isTautology()) {
          exists = true;
          break;
        }
      }

      if (!exists) {
        if (containsAll) {
          throw new RuntimeException(
              "The implying constraints contain all of the implied constraints, but the implication check failed");
        }
        return false;
      }
    }

    if (!containsAll) {
      throw new RuntimeException(
          "The implying constraints do not contain all of the implied constraints, but the implication check passed");
    }
    return true;
  }

  private boolean containsAll(
      Set<FeatureExpr> currentConstraints, Set<FeatureExpr> expandingConstraints) {
    if (currentConstraints == null || expandingConstraints == null) {
      return false;
    }

    return currentConstraints.containsAll(expandingConstraints);
  }

  public Set<FeatureExpr> mergeData(
      Set<FeatureExpr> thisConstraints, @Nullable Set<FeatureExpr> thatConstraints) {
    Set<FeatureExpr> newConstraints = new HashSet<>(thisConstraints);

    if (thatConstraints == null) {
      return newConstraints;
    }

    newConstraints.addAll(thatConstraints);

    return newConstraints;
  }
}
