package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import java.util.Set;

public class ConfigConstraint extends PartialConfig {

  public static ConfigConstraint fromConfig(Set<String> config, Set<String> options) {
    ConfigConstraint configConstraint = new ConfigConstraint();

    for (String option : options) {
      configConstraint.addEntry(option, config.contains(option));
    }

    return configConstraint;
  }

  public boolean isSubConstraintOf(ConfigConstraint configConstraint) {
    return this.isSubPartialConfigOf(configConstraint);
  }

}
