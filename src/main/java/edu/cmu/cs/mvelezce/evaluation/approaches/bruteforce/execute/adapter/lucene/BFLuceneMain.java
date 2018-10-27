package edu.cmu.cs.mvelezce.evaluation.approaches.bruteforce.execute.adapter.lucene;

import edu.cmu.cs.mvelezce.evaluation.approaches.bruteforce.execute.BruteForceExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Main;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.lucene.LuceneAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.lucene.LuceneMain;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class BFLuceneMain extends LuceneMain {

    public static final String BF_BERKELEY_MAIN = BFLuceneMain.class.getCanonicalName();

    public BFLuceneMain(String programName, String iteration, String[] args) {
        super(programName, iteration, args);
    }

    public static void main(String[] args) throws IOException {
        String programName = args[0];
        String mainClass = args[1];
        String iteration = args[2];
        String[] sleepArgs = Arrays.copyOfRange(args, 3, args.length);

        Main main = new BFLuceneMain(programName, iteration, sleepArgs);
        main.execute(mainClass, sleepArgs);
        main.logExecution();
    }

    @Override
    public void logExecution() throws IOException {
        Adapter adapter = new LuceneAdapter();
        Set<String> configuration = adapter.configurationAsSet(this.getArgs());

        BruteForceExecutor executor = new BruteForceExecutor(this.getProgramName());
        Map<String, Long> results = executor.getResults();
        executor.writeToFile(this.getIteration(), configuration, results);
    }

}
