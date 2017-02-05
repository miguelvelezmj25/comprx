package edu.cmu.cs.mvelezce.analysis.visitor;

import edu.cmu.cs.mvelezce.analysis.cfg.BasicBlock;
import edu.cmu.cs.mvelezce.analysis.cfg.CFG;
import edu.cmu.cs.mvelezce.language.ast.expression.*;
import edu.cmu.cs.mvelezce.language.ast.statement.*;
import edu.cmu.cs.mvelezce.language.ast.value.ValueInt;
import edu.cmu.cs.mvelezce.language.parser.Parser;
import edu.cmu.cs.mvelezce.analysis.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by miguelvelez on 2/2/17.
 */
// TODO what if I leave <> empty
public class CFGVisitor implements Visitor {
    private int steps;
    private CFG cfg;
    private BasicBlock currentBasicBlock;
    private Stack<BasicBlock> expressionStack;

    public CFGVisitor() {
        this.steps = 0;
        this.cfg = new CFG();
        this.currentBasicBlock = this.cfg.getEntry();
        this.expressionStack = new Stack<>();
    }

    public CFG buildCFG(Statement ast) {
        ast.accept(this);
        this.cfg.addEdge(this.currentBasicBlock, this.cfg.getExit());

        if(!this.expressionStack.isEmpty()) {
            this.cfg.addEdge(this.expressionStack.pop(), this.cfg.getExit());
        }
        return this.cfg;
    }

    @Override
    public Object visitExpressionBinary(ExpressionBinary expressionBinary) {
        return null;
    }

    @Override
    public Object visitExpressionConfiguration(ExpressionConfiguration expressionConfiguration) {
        return null;
    }

    @Override
    public Object visitExpressionConstantInt(ExpressionConstantInt expressionConstantInt) {
        return null;
    }

    @Override
    public Object visitExpressionUnary(ExpressionUnary expressionUnary) {
        return null;
    }

    @Override
    public Object visitExpressionVariable(ExpressionVariable varExpr) {
        return null;
    }

    @Override
    public void visitStatementBlock(StatementBlock statementBlock) {
        List<Statement> statements = statementBlock.getStatements();

        for(Statement statement : statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visitStatementAssignment(StatementAssignment statementAssignment) {
        BasicBlock statement = new BasicBlock(this.steps++ + "| " + statementAssignment, statementAssignment);
//        this.cfg.addBasicBlock(statement);
        this.cfg.addEdge(this.currentBasicBlock, statement);
        this.checkBranching(statement);
        this.currentBasicBlock = statement;
    }

    @Override
    public void visitStatementIf(StatementIf statementIf) {
        BasicBlock expression = new BasicBlock(this.steps++ + "| " + statementIf.getCondition(),
                statementIf.getCondition());
//        this.cfg.addBasicBlock(expression);
        this.cfg.addEdge(this.currentBasicBlock, expression);
        this.checkBranching(expression);

        this.currentBasicBlock = expression;

        statementIf.getStatementThen().accept(this);

        this.expressionStack.push(expression);
    }

    @Override
    public void visitStatementSleep(StatementSleep statementSleep) {
        BasicBlock statement = new BasicBlock(this.steps++ + "| " + statementSleep, statementSleep);
//        this.cfg.addBasicBlock(statement);
        this.cfg.addEdge(this.currentBasicBlock, statement);
        this.checkBranching(statement);

        this.currentBasicBlock = statement;
    }

    @Override
    public void visitStatementWhile(StatementWhile statementAssignment) {
        BasicBlock expression = new BasicBlock(this.steps++ + "| " + statementAssignment.getCondition(),
            statementAssignment.getCondition());
//        this.cfg.addBasicBlock(expression);
        this.cfg.addEdge(this.currentBasicBlock, expression);
        this.checkBranching(expression);

        this.currentBasicBlock = expression;
    }

    private void checkBranching(BasicBlock basicBlock) {
        if(!this.expressionStack.isEmpty()) {
            this.cfg.addEdge(this.expressionStack.pop(), basicBlock);
        }
    }
}
