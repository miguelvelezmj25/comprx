package edu.cmu.cs.mvelezce.java.execute.sampling.adapters.measureDiskOrderedScan;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.java.execute.adapters.ExecutorAdapter;
import edu.cmu.cs.mvelezce.java.execute.sampling.idta.profiler.jprofiler.IDTAJProfilerSamplingExecutor;

import java.io.IOException;
import java.util.Set;

public class SamplingMeasureDiskOrderedScanAdapter extends BaseMeasureDiskOrderedScanAdapter
    implements ExecutorAdapter {

  private final IDTAJProfilerSamplingExecutor executor;

  public SamplingMeasureDiskOrderedScanAdapter(IDTAJProfilerSamplingExecutor executor) {
    this.executor = executor;
  }

  @Override
  public void execute(Set<String> configuration) throws IOException, InterruptedException {
    throw new UnsupportedOperationException("Implement");
  }

  public IDTAJProfilerSamplingExecutor getExecutor() {
    return executor;
  }
}
