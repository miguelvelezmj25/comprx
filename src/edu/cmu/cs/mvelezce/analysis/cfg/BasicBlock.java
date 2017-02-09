package edu.cmu.cs.mvelezce.analysis.cfg;

import edu.cmu.cs.mvelezce.language.ast.expression.Expression;
import edu.cmu.cs.mvelezce.language.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguelvelez on 2/2/17.
 */
public class BasicBlock {

    private String id;
    private Statement statement;
    private List<Expression> conditions;

    public BasicBlock(String id, Statement statement, List<Expression> conditions) {
        this.id = id;
        this.statement = statement;
        this.conditions = conditions;
    }

    public BasicBlock(String id, Statement statement) {
        this(id, statement, new ArrayList<>());
    }

    public BasicBlock(String id) {
        this(id, null);
    }

    public boolean isSpecial() { return this.statement == null; }

    public boolean isStatement() { return this.statement != null; }

    public String getId() { return this.id; }

    public Statement getStatement() { return this.statement; }

    public List<Expression> getConditions() { return this.conditions; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicBlock that = (BasicBlock) o;

        if (!id.equals(that.id)) return false;
        if (statement != null ? !statement.equals(that.statement) : that.statement != null) return false;
        return conditions.equals(that.conditions);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (statement != null ? statement.hashCode() : 0);
        result = 31 * result + conditions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if(this.statement != null) {
            if(!this.conditions.isEmpty()) {
                return this.statement.toString() + "->[" + this.conditions.toString() + "]";
            }

            return this.statement.toString();
        }

        return this.id;
    }
}
