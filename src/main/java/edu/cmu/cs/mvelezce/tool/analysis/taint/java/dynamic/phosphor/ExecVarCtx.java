package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor;

import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Literal;
import com.bpodgursky.jbool_expressions.Not;
import com.bpodgursky.jbool_expressions.Variable;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Context under which taints reached sinks.
 */
public class ExecVarCtx extends PartialConfig {

  public static final String LLBRACKET = "\u27E6";
  public static final String RRBRACKET = "\u27E7";

  public Expression<String> toCNF() {
    List<Expression<String>> vars = new ArrayList<>();

    for (Map.Entry<String, Boolean> entry : this.getPartialConfig().entrySet()) {
      Expression<String> var = Variable.of(entry.getKey());

      if (!entry.getValue()) {
        var = Not.of(var);
      }

      vars.add(var);
    }

    if(vars.isEmpty()) {
      vars.add(Literal.getTrue());
    }

    And<String> expr = And.of(vars);
    return RuleSet.toCNF(expr);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(LLBRACKET);

    Map<String, Boolean> partialConfig = this.getPartialConfig();

    if (partialConfig.isEmpty()) {
      stringBuilder.append("true");
    }
    else {
      Iterator<Entry<String, Boolean>> partialConfigIter = partialConfig.entrySet().iterator();

      while (partialConfigIter.hasNext()) {
        Map.Entry<String, Boolean> entry = partialConfigIter.next();

        if (!entry.getValue()) {
          stringBuilder.append("!");
        }

        stringBuilder.append(entry.getKey());

        if(partialConfigIter.hasNext()) {
          stringBuilder.append(" ");
          stringBuilder.append("^");
          stringBuilder.append(" ");
        }
      }
    }

    stringBuilder.append(RRBRACKET);

    return stringBuilder.toString();
  }

}
