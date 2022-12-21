package com.adventofcode.day21;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.adventofcode.day21.OperationExpression.Operation.*;

public interface Expression {
    Pattern expressionPattern = Pattern.compile("^([a-z]+) ([+\\-*/]{1}) ([a-z]+)$");

    static Expression parse(String value) {
        if (value.matches("^[0-9]+$"))
            return new ValueExpression(Long.parseLong(value));
        Matcher matcher = expressionPattern.matcher(value);
        matcher.find();
        OperationExpression.Operation operation = switch (matcher.group(2)) {
            case "+" -> SUM;
            case "-" -> SUBTRACTION;
            case "*" -> MULTIPLICATION;
            case "/" -> DIVISION;
            default -> throw new IllegalArgumentException();
        };
        return new OperationExpression(
                new LabelExpression(matcher.group(1)),
                new LabelExpression(matcher.group(3)),
                operation
        );
    }

    long evaluate(Map<String, Expression> context);
}
