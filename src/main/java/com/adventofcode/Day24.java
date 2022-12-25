package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.XY;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Day24 {
    private static final List<Blizzard> EMPTY_SPACE = List.of(new Blizzard('.'));
    public static final List<Character> BLIZZARDS = List.of('>', '<', '^', 'v');
    private final Map<XY, List<Blizzard>> input;
    private final LongSummaryStatistics xStats;
    private final LongSummaryStatistics yStats;

    public Day24() throws IOException {
        input = Input.day24();
        xStats = input.keySet().stream().mapToLong(XY::x).summaryStatistics();
        yStats = input.keySet().stream().mapToLong(XY::y).summaryStatistics();
    }

    long part1() throws IOException {
        List<Map<XY, List<Blizzard>>> steps = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            moveBlizzards();
            steps.add(copyOf(input));
            //print();
        }
        return 0L;
    }

    private Map<XY, List<Blizzard>> copyOf(Map<XY, List<Blizzard>> input) {
        Map<XY, List<Blizzard>> result = new HashMap<>();
        for (Map.Entry<XY, List<Blizzard>> e : input.entrySet()) {
            result.computeIfAbsent(e.getKey(), k -> new ArrayList<>()).addAll(e.getValue());
        }
        return result;
    }

    long part2() throws IOException {
        return 0L;
    }

    private void moveBlizzards() {
        List<XY> allBlizzards = input.entrySet().stream().filter(e -> containsBlizzard(e.getValue())).map(Map.Entry::getKey).toList();
        Map<XY, Map<XY, Blizzard>> moves = new HashMap<>();
        for (XY xy : allBlizzards) {
            List<Blizzard> blizzards = input.get(xy);
            blizzards.forEach(c -> {
                XY newPosition = switch (c.direction()) {
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
                input.computeIfPresent(from, (k, v) -> {
                    v.remove(blizzard);
                    return v.isEmpty() ? null : v;
                });
                input.computeIfAbsent(to, k -> new ArrayList<>()).add(blizzard);
            });
        });
    }

    private boolean containsBlizzard(List<Blizzard> value) {
        return value.stream().map(Blizzard::direction).anyMatch(BLIZZARDS::contains);
    }

    private XY nextPosition(XY xy, Function<XY, XY> movingFunction, Function<XY, XY> wrappingFunction) {
        if (input.getOrDefault(movingFunction.apply(xy), EMPTY_SPACE).stream().map(Blizzard::direction).anyMatch(c -> c == '#')) {
            XY newPosition = wrappingFunction.apply(xy);
            while (input.getOrDefault(newPosition, EMPTY_SPACE).stream().map(Blizzard::direction).anyMatch(c -> c == '#')) {
                newPosition = movingFunction.apply(newPosition);
            }
            return newPosition;
        }
        return movingFunction.apply(xy);
    }

    void print() {
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                List<Blizzard> elementsInPosition = input.getOrDefault(new XY(x, y), EMPTY_SPACE);
                System.out.print(elementsInPosition.size() <= 1 ? elementsInPosition.get(0) : "" + elementsInPosition.size());
            }
            System.out.println();
        }
    }
}