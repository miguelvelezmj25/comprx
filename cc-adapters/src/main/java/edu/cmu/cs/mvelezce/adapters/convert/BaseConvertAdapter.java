package edu.cmu.cs.mvelezce.adapters.convert;

import com.mijecu25.meme.utils.execute.Executor;
import edu.cmu.cs.mvelezce.adapters.BaseAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseConvertAdapter extends BaseAdapter {

  public static final String PROGRAM_NAME = "Convert";
  public static final String MAIN_CLASS = "at.favre.tools.dconvert.Convert";
  public static final String ROOT_PACKAGE = "at.favre.tools.dconvert";
  public static final String ORIGINAL_DIR_PATH =
      "../performance-mapper-evaluation/original/density-converter";
  public static final String ORIGINAL_CLASS_PATH =
      "../performance-mapper-evaluation/original/density-converter/target/classes";
  private static final String OUTPUT_DIR = "output";

  private static final String[] OPTIONS = {
    "SCALE",
    "SCALE_IS_HEIGHT_DP",
    "COMPRESSION_QUALITY",
    "OUT_COMPRESSION",
    "PLATFORM",
    "UPSCALING_ALGO",
    "DOWNSCALING_ALGO",
    "ROUNDING_MODE",
    "SKIP_UPSCALING",
    "SKIP_EXISTING",
    "ANDROID_INCLUDE_LDPI_TVDPI",
    "VERBOSE",
    "ANDROID_MIPMAP_INSTEAD_OF_DRAWABLE",
    "ANTI_ALIASING",
    "POST_PROCESSOR_PNG_CRUSH",
    "POST_PROCESSOR_WEBP",
    "DRY_RUN",
    "POST_PROCESSOR_MOZ_JPEG",
    "KEEP_ORIGINAL_POST_PROCESSED_FILES",
    "IOS_CREATE_IMAGESET_FOLDERS",
    "CLEAN",
    "HALT_ON_ERROR",
  };

  public BaseConvertAdapter() {
    super(
        BaseConvertAdapter.PROGRAM_NAME,
        BaseConvertAdapter.MAIN_CLASS,
        BaseConvertAdapter.getListOfOptions());
  }

  public static List<String> getListOfOptions() {
    return Arrays.asList(BaseConvertAdapter.OPTIONS);
  }

  public void preProcess() {
    try {
      this.removeDir();
      this.makeDir();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Could not create the tmp folder to run " + PROGRAM_NAME, e);
    }
  }

  private void removeDir() throws IOException, InterruptedException {
    List<String> commandList = new ArrayList<>();
    commandList.add("rm");
    commandList.add("-rf");
    commandList.add(OUTPUT_DIR);

    Executor.executeCommand(commandList);
  }

  private void makeDir() throws IOException, InterruptedException {
    List<String> commandList = new ArrayList<>();
    commandList.add("mkdir");
    commandList.add(OUTPUT_DIR);

    Executor.executeCommand(commandList);
  }
}
