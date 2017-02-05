package edu.cmu.cs.mvelezce.interpreter.cfg;

import edu.cmu.cs.mvelezce.interpreter.SimpleInterpreter;
import edu.cmu.cs.mvelezce.interpreter.ast.expression.*;
import edu.cmu.cs.mvelezce.interpreter.ast.statement.*;
import edu.cmu.cs.mvelezce.interpreter.lexer.Lexer;
import edu.cmu.cs.mvelezce.interpreter.parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mvelezce on 2/3/17.
 */
public class CFGVisitorTest {
    private static final String PATH = "src/edu/cmu/cs/mvelezce/interpreter/programs/";

    @Test
    public void test1() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program1");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(1));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("C"), "=", new ExpressionConstantInt(0));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        StatementIf statementIf = new StatementIf(new ExpressionVariable("a"),
                new StatementAssignment(new ExpressionConfiguration("C"), "=", new ExpressionConstantInt(1)));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getStatementThen(),
                statementIf.getStatementThen());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(currentBasicBlock, cfg.getExit());
        cfg.addEdge(basicBlockIf, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }

    @Test
    public void test2() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program2");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(2));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementSleep(new ExpressionVariable("a"));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(1));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementSleep(new ExpressionConstantInt(1));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(currentBasicBlock, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }

    @Test
    public void test3() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program3");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(0));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("C"), "=", new ExpressionVariable("a"));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        StatementIf statementIf = new StatementIf(new ExpressionConfiguration("C"),
                new StatementSleep(new ExpressionConstantInt(2)));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getStatementThen(),
                statementIf.getStatementThen());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementSleep(new ExpressionConstantInt(3));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(basicBlockIf, currentBasicBlock);

        cfg.addEdge(currentBasicBlock, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }

    @Test
    public void test4() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program4");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(0));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionVariable("b"), "=", new ExpressionVariable("a"));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("C"), "=",
                new ExpressionBinary(new ExpressionVariable("a"), "+", new ExpressionConstantInt(1)));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        List<Statement> statements = new LinkedList<>();
        statements.add(new StatementSleep(new ExpressionConstantInt(2)));
        statements.add(new StatementAssignment(
                new ExpressionConfiguration("C"), "=", new ExpressionVariable("b")));

        StatementIf statementIf = new StatementIf(new ExpressionConfiguration("C"),
                new StatementBlock(statements));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statements.get(0),
                statements.get(0));
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statements.get(1),
                statements.get(1));
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementSleep(new ExpressionConstantInt(2));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(basicBlockIf, currentBasicBlock);

        cfg.addEdge(currentBasicBlock, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }

    @Test
    public void test5() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program5");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(1));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("C"), "=", new ExpressionVariable("a"));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        List<Statement> statements = new LinkedList<>();
        statements.add(new StatementSleep(new ExpressionConstantInt(2)));
        statements.add(new StatementAssignment(
                new ExpressionConfiguration("C"), "=", new ExpressionConstantInt(0)));

        StatementIf statementIf = new StatementIf(new ExpressionConfiguration("C"),
                new StatementBlock(statements));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statements.get(0),
                statements.get(0));
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statements.get(1),
                statements.get(1));
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statementIf = new StatementIf(new ExpressionUnary("!", new ExpressionConfiguration("C")),
                new StatementSleep(new ExpressionConstantInt(2)));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        cfg.addEdge(basicBlockIf, basicBlock);
        currentBasicBlock = basicBlock;
        basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getStatementThen(), statementIf.getStatementThen());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(currentBasicBlock, cfg.getExit());
        cfg.addEdge(basicBlockIf, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }

    @Test
    public void test6() throws Exception {
        String program = SimpleInterpreter.loadFile(PATH + "program6");
        Lexer lexer = new Lexer(program);
        Parser parser = new Parser(lexer);
        Statement ast = parser.parse();
        CFGVisitor builder = new CFGVisitor(parser);

        int steps = 0;
        CFG cfg = new CFG();

        Statement statement = new StatementAssignment(new ExpressionVariable("a"), "=", new ExpressionConstantInt(1));
        BasicBlock basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(cfg.getEntry(), basicBlock);
        BasicBlock currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("C"), "=", new ExpressionVariable("a"));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementAssignment(new ExpressionConfiguration("D"), "=", new ExpressionConstantInt(0));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        StatementIf statementIf = new StatementIf(new ExpressionConfiguration("C"), new StatementBlock(new LinkedList<>()));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf1 = currentBasicBlock;

        statement = new StatementSleep(new ExpressionConstantInt(1));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statementIf = new StatementIf(new ExpressionUnary("!", new ExpressionConfiguration("D")), new StatementSleep(new ExpressionConstantInt(2)));
        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getCondition(), statementIf.getCondition());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;
        BasicBlock basicBlockIf = currentBasicBlock;

        basicBlock = new BasicBlock(steps++ + "| " + statementIf.getStatementThen(), statementIf.getStatementThen());
        cfg.addEdge(currentBasicBlock, basicBlock);
        currentBasicBlock = basicBlock;

        statement = new StatementSleep(new ExpressionConstantInt(3));
        basicBlock = new BasicBlock(steps++ + "| " + statement, statement);
        cfg.addEdge(currentBasicBlock, basicBlock);
        cfg.addEdge(basicBlockIf, basicBlock);
        currentBasicBlock = basicBlock;

        cfg.addEdge(currentBasicBlock, cfg.getExit());
        cfg.addEdge(basicBlockIf1, cfg.getExit());

        Assert.assertEquals(cfg, builder.buildCFG(ast));
    }
}