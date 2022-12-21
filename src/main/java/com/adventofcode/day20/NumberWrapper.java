package com.adventofcode.day20;

public record NumberWrapper(int value) {

    @Override
    public String toString() {
        return String.valueOf(value);

    }
}
