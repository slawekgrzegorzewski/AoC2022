package com.adventofcode;

import com.adventofcode.day21.Expression;
import com.adventofcode.day21.LabelExpression;
import com.adventofcode.day21.OperationExpression;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Map;

public class Day21 {
    private final Map<String, Expression> monkeyOperations;

    public Day21() throws IOException {
        monkeyOperations = Input.day21();
    }

    long part1() throws IOException {
        return monkeyOperations.get("root").evaluate(monkeyOperations);
    }

    long part2() throws IOException {
        monkeyOperations.remove("humn");
        OperationExpression root = (OperationExpression) monkeyOperations.get("root");
        Long value = null;
        Expression expressionBasedOnHumn = null;
        try {
            value = root.firstPart().evaluate(monkeyOperations);
        } catch (NullPointerException ex) {
            expressionBasedOnHumn = root.firstPart();
        }
        try {
            value = root.secondPart().evaluate(monkeyOperations);
        } catch (NullPointerException ex) {
            expressionBasedOnHumn = root.secondPart();
        }
        return findHumn(expressionBasedOnHumn, value);
    }

    private long findHumn(Expression expressionBasedOnHumn, long value) {
        if (expressionBasedOnHumn instanceof LabelExpression && ((LabelExpression) expressionBasedOnHumn).label().equals("humn")) {
            return value;
        }
        if (expressionBasedOnHumn instanceof LabelExpression) {
            expressionBasedOnHumn = monkeyOperations.get(((LabelExpression) expressionBasedOnHumn).label());
        }
        if (expressionBasedOnHumn instanceof OperationExpression operationExpression) {
            Long nextValue = null;
            Expression nextExpressionBasedOnHumn = null;
            try {
                nextValue = operationExpression.findSecondValue(value, operationExpression.firstPart().evaluate(monkeyOperations));
            } catch (NullPointerException ex) {
                nextExpressionBasedOnHumn = operationExpression.firstPart();
            }
            try {
                nextValue = operationExpression.findFirstValue(value, operationExpression.secondPart().evaluate(monkeyOperations));
            } catch (NullPointerException ex) {
                nextExpressionBasedOnHumn = operationExpression.secondPart();
            }
            return findHumn(nextExpressionBasedOnHumn, nextValue);
        }
        throw new RuntimeException();
    }

}