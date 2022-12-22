package com.adventofcode.day22;

import java.time.Duration;
import java.util.stream.Stream;

public enum Direction {
    RIGHT(0, '>'), DOWN(1, 'v'), LEFT(2, '<'), UP(3, '^');
    private final int asInt;
    private final char asChar;

    Direction(int asInt, char asChar) {
        this.asInt = asInt;
        this.asChar = asChar;
    }

    public int asInt() {
        return asInt;
    }

    public char asChar() {
        return asChar;
    }

    public static Direction fromChar(char c) {
        return Stream.of(Direction.values()).filter(v -> v.asChar() == c).findFirst().orElseThrow();
    }

    public static Direction fromInt(int i) {
        return Stream.of(Direction.values()).filter(v -> v.asInt() == i).findFirst().orElseThrow();
    }

    public Direction left() {
        int next = asInt() - 1;
        if (next < 0) next = 3;
        return fromInt(next);
    }

    public Direction right() {
        int next = asInt() + 1;
        if (next > 3) next = 0;
        return fromInt(next);
    }
}
