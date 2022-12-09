package com.adventofcode.day9;

public record Move(Direction direction, int count) {

    public enum Direction {
        L, R, U, D
    }
}
