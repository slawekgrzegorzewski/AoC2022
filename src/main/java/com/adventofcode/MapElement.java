package com.adventofcode;

public class MapElement {
    private final char element;

    public char element() {
        return element;
    }

    public MapElement(char element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "" + element;
    }
}
