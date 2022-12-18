package com.adventofcode;

import com.adventofcode.day14.XY;
import com.adventofcode.day4.Range;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day15 {

    private final List<XY[]> input;

    public Day15() throws IOException {
        input = Input.day15();
    }

    long part1() throws IOException {
        int considerRow = 2000000;
        //for an example input
        //int considerRow = 10;
        return getExcludesAndBeacons(considerRow, Integer.MIN_VALUE, Integer.MAX_VALUE).excludes()
                .stream()
                .mapToLong(Range::size)
                .sum();
    }

    long part2() throws IOException {
        final int minX = 0, minY = 0, maxX = 4000000, maxY = 4000000;
        //for an example input
        //final int minX = 0, minY = 0, maxX = 20, maxY = 20;
        for (int considerRow = minY; considerRow <= maxY; considerRow++) {
            ExcludesAndBeacons excludesAndBeacons = getExcludesAndBeacons(considerRow, minX, maxX);
            long excludesLength = excludesAndBeacons.excludes().stream().mapToLong(Range::size).sum();
            if (excludesLength + excludesAndBeacons.beacons().size() < maxX - minX + 1) {
                for (long i = minX; i <= maxX; i++) {
                    if (Range.rangesDontContain(excludesAndBeacons.excludes(), i) && !excludesAndBeacons.beacons().contains(i)) {
                        return 4000000L * i + considerRow;
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    private ExcludesAndBeacons getExcludesAndBeacons(int considerRow, int minX, int maxX) {
        Set<Long> beaconsAtGivenRow = new HashSet<>();
        Set<Range> excludes = new HashSet<>();
        for (XY[] xies : input) {
            if (xies[0].y() == considerRow || xies[1].y() == considerRow) {
                beaconsAtGivenRow.add(xies[1].x());
            }
            long noBeaconsInDistance = xies[0].manhattanDistance(xies[1]);
            long[] excludeAllXsInRange = xies[0].findXsOfAllPointsInGivenDistance(noBeaconsInDistance, considerRow);
            if (excludeAllXsInRange.length == 1) {
                if (excludeAllXsInRange[0] >= minX && excludeAllXsInRange[0] <= maxX) {
                    excludes = new Range(excludeAllXsInRange[0], excludeAllXsInRange[0]).addToSetOfRangesAndCompact(excludes);
                }
            }
            if (excludeAllXsInRange.length == 2) {
                long from = Math.max(excludeAllXsInRange[0], minX);
                long to = Math.min(excludeAllXsInRange[1], maxX);
                if (to >= from)
                    excludes = new Range(from, to).addToSetOfRangesAndCompact(excludes);
            }
        }
        Set<Range> excludesWithoutBeacons = removeBeacons(excludes, beaconsAtGivenRow);
        return new ExcludesAndBeacons(excludesWithoutBeacons, beaconsAtGivenRow);
    }

    private Set<Range> removeBeacons(Set<Range> excludes, Set<Long> beaconsAtGivenRow) {
        LinkedList<Long> copyOfBeacons = beaconsAtGivenRow.stream().sorted().collect(Collectors.toCollection(LinkedList::new));
        return excludes.stream().sorted()
                .flatMap(range -> {
                    List<Range> ranges = new ArrayList<>();
                    while (range != null && !copyOfBeacons.isEmpty() && range.containsValue(copyOfBeacons.getFirst())) {
                        Long beaconPosition = copyOfBeacons.removeFirst();
                        if (beaconPosition == range.fromInclusive()) {
                            Range r = new Range(range.fromInclusive() + 1, range.toInclusive());
                            range = r.isCorrect() ? r : null;
                        } else if (beaconPosition == range.toInclusive()) {
                            Range r = new Range(range.fromInclusive(), range.toInclusive());
                            if (r.isCorrect())
                                ranges.add(r);
                            range = null;
                        } else {
                            ranges.add(new Range(range.fromInclusive(), beaconPosition - 1));
                            range = new Range(beaconPosition + 1, range.toInclusive());
                        }
                    }
                    if (range != null) {
                        ranges.add(range);
                    }
                    return ranges.stream();
                })
                .collect(Collectors.toSet());

    }

    private record ExcludesAndBeacons(Set<Range> excludes, Set<Long> beacons) {
    }
}