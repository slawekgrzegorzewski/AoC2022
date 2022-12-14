package com.adventofcode;

import com.adventofcode.day14.XY;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {

    private final List<XY> rocks;
    private final Map<XY, Character> map = new HashMap<>();
    private final int minY;
    private int maxY;
    private int minX;
    private int maxX;

    public Day14() throws IOException {
        rocks = Input.day14("/day14");
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (XY r : rocks) {
            if (minX > r.x()) minX = r.x();
            if (maxX < r.x()) maxX = r.x();
            if (minY > r.y()) minY = r.y();
            if (maxY < r.y()) maxY = r.y();
        }
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
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
        minX -= 1000;
        maxX += 1000;
        maxY += 2;
        for(int x = minX; x <= maxX; x++){
            map.put(new XY(x, maxY), '#');
        }
        int i = 0;
        while (simulateDrop(500, 0)) {
            i++;
        }
        print();
        return i;
    }

    private boolean simulateDrop(int x, int y) {
        if (y > maxY) {
            return false;
        }
        while (!map.containsKey(new XY(x, y + 1)) && y <= maxY) y++;
        if (!map.containsKey(new XY(x - 1, y + 1))) {
            return simulateDrop(x - 1, y + 1);
        }
        if (!map.containsKey(new XY(x + 1, y + 1))) {
            return simulateDrop(x + 1, y + 1);
        }
        if (map.containsKey(new XY(x, y))) return false;
        map.put(new XY(x, y), 'O');
        return true;
    }

    private String print() {
        String toPrint = IntStream.range(Math.min(0, minY), maxY + 1)
                .mapToObj(y -> IntStream.range(minX, maxX + 1).mapToObj(x -> map.getOrDefault(new XY(x, y), '.')).collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString
                )))
                .collect(Collectors.joining("\n"));
        System.out.println(toPrint);
        return toPrint;
    }
}