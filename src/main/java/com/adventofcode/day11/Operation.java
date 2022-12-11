package com.adventofcode.day11;

public class Operation {
    private final String operation;

    public Operation(String operation) {
        this.operation = operation;
    }

    public void perform(Number number) {
        switch (operation) {
            case "new = old * old" -> number.square();

            case "new = old * 3" -> number.multiply(3);
            case "new = old * 7" -> number.multiply(7);
            case "new = old * 19" -> number.multiply(19);

            case "new = old + 2" -> number.add(2);
            case "new = old + 3" -> number.add(3);
            case "new = old + 4" -> number.add(4);
            case "new = old + 6" -> number.add(6);
            case "new = old + 7" -> number.add(7);
            case "new = old + 8" -> number.add(8);

            default -> throw new IllegalStateException();
        }
    }
}








