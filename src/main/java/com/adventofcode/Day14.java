package com.adventofcode;

import com.adventofcode.input.XY;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day14 {

    public static final long NO_FLOOR = -1;
    private final List<XY> rocks;
    private final Map<XY, Character> map = new HashMap<>();
    private final long maxY;
    private long floor = NO_FLOOR;

    public Day14() throws IOException {
        rocks = Input.day14();
        long minY = Integer.MAX_VALUE;
        long maxY = 0;
        long minX = Integer.MAX_VALUE;
        long maxX = 0;
        for (XY r : rocks) {
            if (minX > r.x()) minX = r.x();
            if (maxX < r.x()) maxX = r.x();
            if (minY > r.y()) minY = r.y();
            if (maxY < r.y()) maxY = r.y();
        }
        this.maxY = maxY;
    }

    private void initMap() {
        map.clear();
        for (XY xy : rocks) {
            map.put(xy, '#');
        }
    }

    long part1() throws IOException {
        initMap();
        int i = 0;
        while (simulateDrop(500, 0)) {
            i++;
        }
        print();
        return i;
    }

    long part2() throws IOException {
        initMap();
        floor = maxY + 1;
        int i = 0;
        while (simulateDrop(500, 0)) {
            i++;
        }
        print();
        return i;
    }

    private boolean simulateDrop(int x, int y) {
        long max = Math.max(maxY, floor);
        if (y > max) {
            return false;
        }
        while (isAir(x, y)) {
            if (y > max) break;
            y++;
        }
        if (isAir(x - 1, y)) {
            return simulateDrop(x - 1, y + 1);
        }
        if (isAir(x + 1, y)) {
            return simulateDrop(x + 1, y + 1);
        }
        if (map.containsKey(new XY(x, y))) return false;
        map.put(new XY(x, y), 'O');
        return true;
    }

    private boolean isAir(int x, int y) {
        return y != floor && !map.containsKey(new XY(x, y + 1));
    }

    private void print() {
        LongSummaryStatistics xStats = map.keySet().stream().mapToLong(XY::x).summaryStatistics();
        LongSummaryStatistics yStats = map.keySet().stream().mapToLong(XY::y).summaryStatistics();

        String toPrint = LongStream.range(Math.min(0, yStats.getMin()), yStats.getMax() + 1)
                .mapToObj(y -> LongStream.range(xStats.getMin(), xStats.getMax() + 1).mapToObj(x -> map.getOrDefault(new XY(x, y), '.')).collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString
                )))
                .collect(Collectors.joining("\n"));
        System.out.println(toPrint);
    }
}