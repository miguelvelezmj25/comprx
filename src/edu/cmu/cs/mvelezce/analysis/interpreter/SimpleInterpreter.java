package edu.cmu.cs.mvelezce.analysis.interpreter;

import edu.cmu.cs.mvelezce.language.lexer.Lexer;
import edu.cmu.cs.mvelezce.language.parser.Parser;

import java.io.File;
import java.util.Scanner;

/**
 * Created by miguelvelez on 1/31/17.
 */
public class SimpleInterpreter {

    public static void main(String[] args) throws Exception {
        String program = SimpleInterpreter.loadFile("src/edu/cmu/cs/mvelezce/interpreter/program1");
        System.out.println(program);
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());


    }

    public static String loadFile(String name) throws Exception {
        Scanner s = new Scanner(new File(name));
        String file = "";
        while (s.hasNext()) {
            file += s.nextLine() + "\n";
        }
        s.close();
        return file;
    }

}
