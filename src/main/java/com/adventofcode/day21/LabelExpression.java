package com.adventofcode.day21;

import java.util.Map;

public class LabelExpression implements Expression {

    private final String label;

    public LabelExpression(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    @Override
    public long evaluate(Map<String, Expression> context) {
        return context.get(label).evaluate(context);
    }
}
