package com.adventofcode.day21;

import java.util.Map;

public class ValueExpression implements Expression {
    private final long value;

    public ValueExpression(long value) {
        this.value = value;
    }


    @Override
    public long evaluate(Map<String, Expression> context) {
        return value;
    }
}
