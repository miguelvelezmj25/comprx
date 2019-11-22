package edu.cmu.cs.mvelezce.model;

import java.util.*;

public class LocalPerformanceModel<T> {

  private final UUID region;
  private final Map<T, Long> model;
  private final Map<T, Long> modelToMin;
  private final Map<T, Long> modelToMax;
  private final Map<T, Long> modelToDiff;
  private final Map<T, String> modelToPerfHumanReadable;
  private final Map<T, String> modelToMinHumanReadable;
  private final Map<T, String> modelToMaxHumanReadable;
  private final Map<T, String> modelToDiffHumanReadable;

  // Private constructor for jackson xml
  private LocalPerformanceModel() {
    this.region = UUID.randomUUID();
    this.model = new HashMap<>();
    this.modelToMin = new HashMap<>();
    this.modelToMax = new HashMap<>();
    this.modelToDiff = new HashMap<>();
    this.modelToPerfHumanReadable = new HashMap<>();
    this.modelToMinHumanReadable = new HashMap<>();
    this.modelToMaxHumanReadable = new HashMap<>();
    this.modelToDiffHumanReadable = new HashMap<>();
  }

  public LocalPerformanceModel(
      UUID region,
      Map<T, Long> model,
      Map<T, Long> modelToMin,
      Map<T, Long> modelToMax,
      Map<T, Long> modelToDiff,
      Map<T, String> modelToPerfHumanReadable,
      Map<T, String> modelToMinHumanReadable,
      Map<T, String> modelToMaxHumanReadable,
      Map<T, String> modelToDiffHumanReadable) {
    this.region = region;
    this.model = model;
    this.modelToMin = modelToMin;
    this.modelToMax = modelToMax;
    this.modelToDiff = modelToDiff;
    this.modelToPerfHumanReadable = modelToPerfHumanReadable;
    this.modelToMinHumanReadable = modelToMinHumanReadable;
    this.modelToMaxHumanReadable = modelToMaxHumanReadable;
    this.modelToDiffHumanReadable = modelToDiffHumanReadable;
  }

  public long evaluate(Set<String> config, List<String> options) {
    throw new UnsupportedOperationException(
        "Cannot call evaluate on this object since we do not know the type used for the regions");
  }

  public UUID getRegion() {
    return region;
  }

  public Map<T, Long> getModel() {
    return model;
  }

  public Map<T, Long> getModelToMin() {
    return modelToMin;
  }

  public Map<T, Long> getModelToMax() {
    return modelToMax;
  }

  public Map<T, Long> getModelToDiff() {
    return modelToDiff;
  }

  public Map<T, String> getModelToPerfHumanReadable() {
    return modelToPerfHumanReadable;
  }

  public Map<T, String> getModelToMinHumanReadable() {
    return modelToMinHumanReadable;
  }

  public Map<T, String> getModelToMaxHumanReadable() {
    return modelToMaxHumanReadable;
  }

  public Map<T, String> getModelToDiffHumanReadable() {
    return modelToDiffHumanReadable;
  }
}
