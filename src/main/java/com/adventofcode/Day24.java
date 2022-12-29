package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.XY;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.LongStream;

public class Day24 {
    private static final List<MapElement> EMPTY_SPACE = List.of(new MapElement('.'));
    public static final List<Character> BLIZZARDS = List.of('>', '<', '^', 'v');
    private final Map<XY, List<MapElement>> input;
    private final LongSummaryStatistics xStats;
    private final LongSummaryStatistics yStats;
    private final static long MAX_EXPECTED_LENGTH = 300L;
    private static long CURRENT_MIN = MAX_EXPECTED_LENGTH;


    public Day24() throws IOException {
        input = Input.day24();
        xStats = input.keySet().stream().mapToLong(XY::x).summaryStatistics();
        yStats = input.keySet().stream().mapToLong(XY::y).summaryStatistics();
    }

    long part1() throws IOException {
        LinkedList<XY> currentPath = new LinkedList<>();
        currentPath.add(new XY(1, 0));
        return moveExpedition(currentPath, new XY(xStats.getMax() - 1, yStats.getMax()), copyOf(input));
    }

    private Map<XY, List<MapElement>> copyOf(Map<XY, List<MapElement>> input) {
        Map<XY, List<MapElement>> result = new HashMap<>();
        for (Map.Entry<XY, List<MapElement>> e : input.entrySet()) {
            result.computeIfAbsent(e.getKey(), k -> new ArrayList<>()).addAll(e.getValue());
        }
        return result;
    }

    long part2() throws IOException {
        return 0L;
    }

    private long moveExpedition(LinkedList<XY> currentPath, XY expeditionDestination, Map<XY, List<MapElement>> currentMap) {
        if (currentPath.size() >= CURRENT_MIN) return Long.MAX_VALUE;
        moveBlizzards(currentMap);
        Map<XY, List<MapElement>> nextStepMap = copyOf(currentMap);
        return LongStream.of(
                        exploreOption(currentPath, expeditionDestination, nextStepMap, XY::moveRight2),
                        exploreOption(currentPath, expeditionDestination, nextStepMap, XY::moveLeft2),
                        exploreOption(currentPath, expeditionDestination, nextStepMap, XY::moveDown2),
                        exploreOption(currentPath, expeditionDestination, nextStepMap, XY::moveUp2),
                        exploreOption(currentPath, expeditionDestination, nextStepMap, Function.identity())
                )
                .filter(l -> l != Long.MAX_VALUE)
                .min().orElse(Long.MAX_VALUE);
    }

    private long exploreOption(LinkedList<XY> currentPath, XY destination, Map<XY, List<MapElement>> map, Function<XY, XY> nextPositionTransformer) {
        XY expeditionPosition = currentPath.getLast();
        XY nextPosition = nextPositionTransformer.apply(expeditionPosition);
        if (currentPath.subList(0, currentPath.size() - 1).contains(nextPosition)) return Long.MAX_VALUE;
        if (nextPosition.x() > xStats.getMax() || nextPosition.x() < xStats.getMin() || nextPosition.y() > yStats.getMax() || nextPosition.y() < yStats.getMin())
            return Long.MAX_VALUE;
        if (map.getOrDefault(nextPosition, EMPTY_SPACE).stream().map(MapElement::element).anyMatch(c -> c == EMPTY_SPACE.get(0).element())) {
            if (nextPosition.equals(destination)) {
                long length = currentPath.size() + 1;
                if (length <= CURRENT_MIN) {
                    CURRENT_MIN = length;
                    System.out.println(length);
                }
                return length;
            } else {
                currentPath.addLast(nextPosition);
                long result = moveExpedition(currentPath, destination, map);
                currentPath.removeLast();
                return result;
            }
        }
        return Long.MAX_VALUE;
    }

    private void moveBlizzards(Map<XY, List<MapElement>> map) {
        List<XY> allBlizzards = map.entrySet().stream().filter(e -> containsBlizzard(e.getValue())).map(Map.Entry::getKey).toList();
        Map<XY, Map<XY, MapElement>> moves = new HashMap<>();
        for (XY xy : allBlizzards) {
            List<MapElement> mapElements = map.get(xy);
            mapElements.forEach(c -> {
                XY newPosition = switch (c.element()) {
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
            destination.forEach((to, mapElement) -> {
                map.computeIfPresent(from, (k, v) -> {
                    v.remove(mapElement);
                    return v.isEmpty() ? null : v;
                });
                map.computeIfAbsent(to, k -> new ArrayList<>()).add(mapElement);
            });
        });
    }

    private boolean containsBlizzard(List<MapElement> value) {
        return value.stream().map(MapElement::element).anyMatch(BLIZZARDS::contains);
    }

    private XY nextPosition(XY xy, Function<XY, XY> movingFunction, Function<XY, XY> wrappingFunction) {
        if (input.getOrDefault(movingFunction.apply(xy), EMPTY_SPACE).stream().map(MapElement::element).anyMatch(c -> c == '#')) {
            XY newPosition = wrappingFunction.apply(xy);
            while (input.getOrDefault(newPosition, EMPTY_SPACE).stream().map(MapElement::element).anyMatch(c -> c == '#')) {
                newPosition = movingFunction.apply(newPosition);
            }
            return newPosition;
        }
        return movingFunction.apply(xy);
    }

    void print() {
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                List<MapElement> elementsInPosition = input.getOrDefault(new XY(x, y), EMPTY_SPACE);
                System.out.print(elementsInPosition.size() <= 1 ? elementsInPosition.get(0) : "" + elementsInPosition.size());
            }
            System.out.println();
        }
    }
}