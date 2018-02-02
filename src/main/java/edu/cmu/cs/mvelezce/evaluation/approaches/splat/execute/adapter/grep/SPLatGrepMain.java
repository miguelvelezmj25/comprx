package edu.cmu.cs.mvelezce.evaluation.approaches.splat.execute.adapter.grep;

import edu.cmu.cs.mvelezce.evaluation.approaches.splat.execute.adapter.SPLatMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.grep.GrepAdapter;
import org.unix4j.grep.Main;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class SPLatGrepMain implements SPLatMain {

    @Override
    public Set<Set<String>> getSPLatConfigurations(String programName) {
        if(!programName.contains("grep")) {
            throw new RuntimeException("Could not find the main class " + programName);
        }

        Set<Set<String>> splatConfigurations = new HashSet<>();
        Adapter adapter = new GrepAdapter();
        Stack<String> stack = new Stack<>();

        Set<String> configuration = new HashSet<>();
        splatConfigurations.add(configuration);
        String[] args = adapter.configurationAsMainArguments(configuration);

//        Main.splat(args, stack);
//
//        while(!stack.isEmpty()) {
//            String option = stack.peek();
//
//            if(configuration.contains(option)) {
//                configuration = new HashSet<>(configuration);
//                configuration.remove(option);
//                stack.pop();
//            }
//            else {
//                configuration = new HashSet<>(configuration);
//                configuration.add(option);
//                splatConfigurations.add(configuration);
//                args = adapter.configurationAsMainArguments(configuration);
//                Main.splat(args, stack);
//            }
//        }

        return splatConfigurations;
    }
}