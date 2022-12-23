package com.adventofcode.day23;

import com.adventofcode.input.XY;

public enum Direction {
    N, NE, NW, S, SE, SW, W, E;

    public XY getPointNextToInMyDirection(XY from) {
        return switch (this) {
            case N -> from.moveUp2();
            case S -> from.moveDown2();
            case E -> from.moveRight2();
            case W -> from.moveLeft2();
            case NE -> from.moveUp2().moveRight2();
            case NW -> from.moveUp2().moveLeft2();
            case SE -> from.moveDown2().moveRight2();
            case SW -> from.moveDown2().moveLeft2();
        };
    }
}
