package com.adventofcode.day20;

public record NumberWrapper(long value) {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
