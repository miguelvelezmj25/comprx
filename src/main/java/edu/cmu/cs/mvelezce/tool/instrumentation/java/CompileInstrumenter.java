package edu.cmu.cs.mvelezce.tool.instrumentation.java;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Arrays;

public class CompileInstrumenter extends BaseInstrumenter {

    public CompileInstrumenter(String srcDir, String classDir) {
        super(srcDir, classDir);
    }

    @Override
    public void instrument() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void compileFromSource() {
        this.writeFilesToCompile();

        try {
            String[] command = new String[]{"javac", //"-cp",
//                    "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/elevator/lib/*",
                    "-d", this.getClassDir(), "@" + this.getSrcDir() + "sources.txt"};
            System.out.println(Arrays.toString(command));
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String string;

            while (inputReader.readLine() != null) {
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((string = errorReader.readLine()) != null) {
                System.out.println(string);
            }

            process.waitFor();
        } catch (IOException | InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    private void writeFilesToCompile() {
        try {
            String[] command = {"find", this.getSrcDir(), "-name", "*.java"};
            System.out.println(Arrays.toString(command));
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getSrcDir() + "sources.txt"));
            String string;

            while ((string = inputReader.readLine()) != null) {
                writer.write(string);
                writer.write("\n");
            }

            writer.close();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((string = errorReader.readLine()) != null) {
                System.out.println(string);
            }

            process.waitFor();
        } catch (IOException | InterruptedException ie) {
            ie.printStackTrace();
        }
    }

}