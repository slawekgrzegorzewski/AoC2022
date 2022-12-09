package com.adventofcode;

import com.adventofcode.day9.Coordinates;
import com.adventofcode.day9.Move;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day9 {

    private final List<Move> input;

    public Day9() throws IOException {
        input = Input.day9("/day9");
    }

    long part1() {
        return simulateRope(1);
    }

    long part2() {
        return simulateRope(9);
    }

    private int simulateRope(int numberOfKnots) {
        Set<Coordinates> tailPositions = new HashSet<>();
        Coordinates H = new Coordinates(0, 0);
        Coordinates[] knots = new Coordinates[numberOfKnots];
        Arrays.fill(knots, new Coordinates(0, 0));
        tailPositions.add(knots[knots.length - 1]);
        for (Move move : input) {
            Function<Coordinates, Coordinates> moveFunction = getMoveFunction(move.direction());
            for (int i = 0; i < move.count(); i++) {
                H = moveFunction.apply(H);
                knots[knots.length - 1] = follow(knots[knots.length - 1], H);
                for (int j = numberOfKnots - 2; j >= 0; j--) {
                    knots[j] = follow(knots[j], knots[j + 1]);
                }
                tailPositions.add(knots[0]);
            }
        }
        return tailPositions.size();
    }

    private Coordinates follow(Coordinates T, Coordinates H) {
        if (T.doesntTouch(H)) {
            T = T.moveToward(H);
            if (T.doesntTouch(H)) {
                throw new RuntimeException("T doesn't touch H");
            }
        }
        return T;
    }

    private static Function<Coordinates, Coordinates> getMoveFunction(Move.Direction direction) {
        return switch (direction) {
            case U -> Coordinates::up;
            case D -> Coordinates::down;
            case L -> Coordinates::left;
            case R -> Coordinates::right;
        };
    }

}


