package com.adventofcode.day11;

import java.math.BigDecimal;

public class Operation {
    private final String operation;

    public Operation(String operation) {
        this.operation = operation;
    }

    public BigDecimal perform(BigDecimal input) {
        return switch (operation) {
            case "new = old * old" -> input.pow(2);

            case "new = old * 3" -> input.multiply(BigDecimal.valueOf(3L));
            case "new = old * 7" -> input.multiply(BigDecimal.valueOf(7L));
            case "new = old * 19" -> input.multiply(BigDecimal.valueOf(19L));

            case "new = old + 2" -> input.add(BigDecimal.valueOf(2L));
            case "new = old + 3" -> input.add(BigDecimal.valueOf(3L));
            case "new = old + 4" -> input.add(BigDecimal.valueOf(4L));
            case "new = old + 6" -> input.add(BigDecimal.valueOf(6L));
            case "new = old + 7" -> input.add(BigDecimal.valueOf(7L));
            case "new = old + 8" -> input.add(BigDecimal.valueOf(8L));

            default -> throw new IllegalStateException();
        };
    }
}








