package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.constraint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.Options;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.BaseDynamicAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.ConfigConstraint;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PhosphorConstraintAnalysis extends BaseDynamicAnalysis<Set<ConfigConstraint>> {

  // TODO how do we handle unsoundness of taint analysis?
  private final Set<ConfigConstraint> constraints = new HashSet<>();

  public PhosphorConstraintAnalysis(String programName) {
    super(programName, new HashSet<>(), new HashSet<>());
  }

  @Override
  public Set<ConfigConstraint> analyze() throws IOException, InterruptedException {
    return this.getSimplifiedConstraints(this.constraints);
  }

  public void addConstraints(Set<ConfigConstraint> constraints) {
    this.constraints.addAll(constraints);
  }

  private Set<ConfigConstraint> getSimplifiedConstraints(Set<ConfigConstraint> constraints) {
    Set<ConfigConstraint> simplifiedConstraints = new HashSet<>();

    for (ConfigConstraint candidateConstraintToAdd : constraints) {
      boolean add = true;

      for (ConfigConstraint constraint : constraints) {
        if (candidateConstraintToAdd.equals(constraint)) {
          continue;
        }

        if (candidateConstraintToAdd.isSubConstraintOf(constraint)) {
          add = false;
          break;
        }
      }

      if (add) {
        simplifiedConstraints.add(candidateConstraintToAdd);
      }
    }

    return simplifiedConstraints;
  }

  @Override
  public void writeToFile(Set<ConfigConstraint> constraints) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String outputFile = this.outputDir() + "/" + this.getProgramName() + Options.DOT_JSON;
    File file = new File(outputFile);
    file.getParentFile().mkdirs();
    mapper.writeValue(file, constraints);
  }

  @Override
  public Set<ConfigConstraint> readFromFile(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    return mapper
        .readValue(file, new TypeReference<Set<ConfigConstraint>>() {
        });
  }

  @Override
  public String outputDir() {
    return DIRECTORY + "/" + this.getProgramName() + "/cc/constraints";
  }

}
