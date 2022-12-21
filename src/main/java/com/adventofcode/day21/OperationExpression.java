package com.adventofcode.day21;

import java.util.Map;

public class OperationExpression implements Expression {

    private final Expression firstPart;
    private final Expression secondPart;
    private final Operation operation;

    public OperationExpression(Expression firstPart, Expression secondPart, Operation operation) {
        this.firstPart = firstPart;
        this.secondPart = secondPart;
        this.operation = operation;
    }

    public Expression firstPart() {
        return firstPart;
    }

    public Expression secondPart() {
        return secondPart;
    }

    public long findFirstValue(long expectedResult, long secondValue) {
        return switch (operation) {
            case SUM -> expectedResult - secondValue;
            case SUBTRACTION -> secondValue + expectedResult;
            case MULTIPLICATION -> expectedResult / secondValue;
            case DIVISION -> secondValue * expectedResult;
        };
    }

    public long findSecondValue(long expectedResult, long firstValue) {
        return switch (operation) {
            case SUM -> expectedResult - firstValue;
            case SUBTRACTION -> firstValue - expectedResult;
            case MULTIPLICATION -> expectedResult / firstValue;
            case DIVISION -> firstValue / expectedResult;
        };
    }

    @Override
    public long evaluate(Map<String, Expression> context) {
        long first = firstPart.evaluate(context);
        long second = secondPart.evaluate(context);
        return switch (operation) {
            case SUM -> first + second;
            case SUBTRACTION -> first - second;
            case MULTIPLICATION -> first * second;
            case DIVISION -> first / second;
        };
    }

    public enum Operation {
        SUM, SUBTRACTION, MULTIPLICATION, DIVISION
    }
}
