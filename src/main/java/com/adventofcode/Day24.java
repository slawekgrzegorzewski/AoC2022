package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.XY;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Day24 {
    private static final List<Character> EMPTY_SPACE = List.of('.');
    public static final List<Character> BLIZZARDS = List.of('>', '<', '^', 'v');
    private final Map<XY, List<Character>> input;
    private final LongSummaryStatistics xStats;
    private final LongSummaryStatistics yStats;

    public Day24() throws IOException {
        input = Input.day24();
        xStats = input.keySet().stream().mapToLong(XY::x).summaryStatistics();
        yStats = input.keySet().stream().mapToLong(XY::y).summaryStatistics();
    }

    long part1() throws IOException {
        for (int i = 0; i < 100; i++) {
            moveBlizzards();
            print();
        }
        return 0L;
    }

    long part2() throws IOException {
        return 0L;
    }

    private void moveBlizzards() {
        List<XY> allBlizzards = input.entrySet().stream().filter(e -> containsBlizzard(e.getValue())).map(Map.Entry::getKey).toList();
        Map<XY, Map<XY, Character>> moves = new HashMap<>();
        for (XY xy : allBlizzards) {
            List<Character> characters = input.get(xy);
            characters.forEach(c -> {
                XY newPosition = switch (c) {
                    case '>' -> nextPosition(xy, XY::moveRight2, from -> new XY(xStats.getMin(), from.y()));
                    case '<' -> nextPosition(xy, XY::moveLeft2, from -> new XY(xStats.getMax(), from.y()));
                    case '^' -> nextPosition(xy, XY::moveUp2, from -> new XY(from.x(), yStats.getMax()));
                    case 'v' -> nextPosition(xy, XY::moveDown2, from -> new XY(from.x(), yStats.getMin()));
                    default -> null;
                };
                if (newPosition != null) {
                    moves.computeIfAbsent(xy, k -> new HashMap<>()).put(newPosition, c);
                }
            });
        }
        moves.forEach((from, destination) -> {
            destination.forEach((to, blizzard) -> {
                input.get(from).remove(blizzard);
                input.computeIfPresent(from, (k, v) -> {
                    v.remove(blizzard);
                    return v.isEmpty() ? null : v;
                });
                input.computeIfAbsent(to, k -> new ArrayList<>()).add(blizzard);
            });
        });
    }

    private boolean containsBlizzard(List<Character> value) {
        return value.stream().anyMatch(BLIZZARDS::contains);
    }

    private XY nextPosition(XY xy, Function<XY, XY> movingFunction, Function<XY, XY> wrappingFunction) {
        if (input.getOrDefault(movingFunction.apply(xy), EMPTY_SPACE).contains('#')) {
            XY newPosition = wrappingFunction.apply(xy);
            while (input.getOrDefault(newPosition, EMPTY_SPACE).contains('#')) {
                newPosition = movingFunction.apply(newPosition);
            }
            return newPosition;
        }
        return movingFunction.apply(xy);
    }

    void print() {
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                List<Character> elementsInPosition = input.getOrDefault(new XY(x, y), EMPTY_SPACE);
                System.out.print(elementsInPosition.size() <= 1 ? elementsInPosition.get(0) : "" + elementsInPosition.size());
            }
            System.out.println();
        }
    }
}