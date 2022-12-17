package com.adventofcode.day17;

import com.adventofcode.day14.XY;

import java.util.*;

import static com.adventofcode.Day17.WIDTH;

public enum Tile {
    LINE(new XY(0, 0), new XY(0, 1), new XY(0, 2), new XY(0, 3)),
    PLUS(new XY(0, 1), new XY(1, 0), new XY(1, 1), new XY(1, 2), new XY(2, 1)),
    L(new XY(0, 0), new XY(0, 1), new XY(0, 2), new XY(1, 2), new XY(2, 2)),
    I(new XY(0, 0), new XY(1, 0), new XY(2, 0), new XY(3, 0)),
    SQUARE(new XY(0, 0), new XY(0, 1), new XY(1, 0), new XY(1, 1));

    private final List<XY> elements;
    private final long width;

    Tile(XY... elements) {
        this.elements = List.of(elements);
        LongSummaryStatistics yStatistics = Arrays.stream(elements).mapToLong(XY::y).summaryStatistics();
        this.width = yStatistics.getMax() - yStatistics.getMin() + 1;
    }

    public boolean canMoveToPositionOfLeftBottomCorner(Map<Long, Map<Long, Character>> chamber, XY leftBottomPosition) {
        return elements.stream()
                .map(point -> new XY(point.x() + leftBottomPosition.x(), point.y() + leftBottomPosition.y()))
                .noneMatch(newPoint -> chamber.getOrDefault(newPoint.x(), Map.of()).containsKey(newPoint.y()));
    }

    public long placeInChamber(Map<Long, Map<Long, Character>> chamber, XY leftBottomPosition) {
        Set<Long> xs = new HashSet<>();
        for (XY point : elements) {
            XY newPoint = new XY(point.x() + leftBottomPosition.x(), point.y() + leftBottomPosition.y());
            if (chamber.getOrDefault(newPoint.x(), Map.of()).containsKey(newPoint.y()))
                throw new RuntimeException();
            chamber.computeIfAbsent(newPoint.x(), k -> new HashMap<>()).put(newPoint.y(), '#');
            xs.add(newPoint.x());
        }
        return xs.stream()
                .mapToLong(x -> x)
                .filter(x -> chamber.get(x).size() == WIDTH)
                .findFirst().orElse(-1L);
    }

    public long width() {
        return width;
    }

}
