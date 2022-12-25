package com.adventofcode;

public class Blizzard {
    private final char direction;

    public char direction() {
        return direction;
    }

    public Blizzard(char direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "" + direction;
    }
}
