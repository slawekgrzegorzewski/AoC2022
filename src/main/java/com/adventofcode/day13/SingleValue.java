package com.adventofcode.day13;

import org.jetbrains.annotations.NotNull;

public class SingleValue implements Value {
    private final int value;

    public static Value parse(char[] chars) {
        return new SingleValue(Integer.parseInt(new String(chars)));
    }

    public SingleValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull Value other) {
        if (other instanceof SingleValue) return value - ((SingleValue) other).value;
        else return -other.compareTo(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
