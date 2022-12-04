package com.adventofcode.input;

public class Pair<V1, V2> {
    private final V1 first;
    private final V2 second;

    public Pair(V1 first, V2 second) {
        this.first = first;
        this.second = second;
    }

    public V1 first() {
        return first;
    }

    public V2 second() {
        return second;
    }

    public boolean contains(Object value) {
        return first.equals(value) || second.equals(value);
    }
}
