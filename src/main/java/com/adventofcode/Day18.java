package com.adventofcode;

import com.adventofcode.day18.ExternalAir;
import com.adventofcode.day18.XYZ;
import com.adventofcode.input.Input;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.adventofcode.day18.XYZ.allDirectionsStream;

public class Day18 {

    private final Map<Long, Map<Long, Set<Long>>> droplets = new HashMap<>();
    private final LongSummaryStatistics dropletsXStats;
    private final LongSummaryStatistics dropletsYStats;
    private final LongSummaryStatistics dropletsZStats;
    private final Map<Long, Map<Long, Set<Long>>> outerAirMap;

    public Day18() throws IOException {
        Input.day18().forEach(xyz -> put(droplets, xyz));
        dropletsXStats = droplets.keySet().stream().mapToLong(l -> l).summaryStatistics();
        dropletsYStats = droplets.values().stream().flatMap(yz -> yz.keySet().stream()).mapToLong(l -> l).summaryStatistics();
        dropletsZStats = droplets.values().stream().flatMap(yz -> yz.values().stream()).flatMap(Set::stream).mapToLong(l -> l).summaryStatistics();
        outerAirMap = createOuterAirMap();
    }

    @NotNull
    private Map<Long, Map<Long, Set<Long>>> createOuterAirMap() {

        Map<Long, Map<Long, Set<Long>>> airMap = new HashMap<>();
        for (long x = dropletsXStats.getMin() - 1; x <= dropletsXStats.getMax() + 1; x++) {
            for (long y = dropletsYStats.getMin() - 1; y <= dropletsYStats.getMax() + 1; y++) {
                for (long z = dropletsZStats.getMin() - 1; z <= dropletsXStats.getMax() + 1; z++) {
                    XYZ xyz = new XYZ(x, y, z);
                    if (!exists(droplets, xyz)) {
                        put(airMap, xyz);
                    }
                }
            }
        }

        Map<XYZ, ExternalAir> airConnections = new HashMap<>();
        for (XYZ xyz : flatten(airMap)) {
            ExternalAir node = airConnections.computeIfAbsent(xyz, ExternalAir::new);
            allDirectionsStream().map(m -> m.apply(xyz))
                    .filter(c -> exists(airMap, c))
                    .forEach(node::add);
        }
        bfs(airConnections,
                airConnections.get(new XYZ(dropletsXStats.getMin() - 1, dropletsYStats.getMin() - 1, dropletsZStats.getMin() - 1))
        );
        Map<Long, Map<Long, Set<Long>>> outerAirMap = new HashMap<>();
        airConnections.values().stream()
                .filter(externalAir -> externalAir.distance() < Long.MAX_VALUE)
                .map(ExternalAir::coordinates)
                .forEach(xyz -> put(outerAirMap, xyz));
        return outerAirMap;
    }

    private void bfs(Map<XYZ, ExternalAir> graph, ExternalAir head) {
        LinkedList<ExternalAir> Q = new LinkedList<>();
        ExternalAir u = head;
        u.setColor(ExternalAir.Color.GREY);
        u.setDistance(0);
        Q.add(u);
        while (!Q.isEmpty()) {
            u = Q.removeFirst();
            for (XYZ xyz : u.adjacent()) {
                ExternalAir v = graph.get(xyz);
                if (v.color() == ExternalAir.Color.WHITE) {
                    v.setColor(ExternalAir.Color.GREY);
                    v.setDistance(v.distance() + 1);
                    Q.add(v);
                }
            }
            u.setColor(ExternalAir.Color.BLACk);
        }
    }

    long part1() throws IOException {
        return flatten(droplets).stream()
                .mapToLong(this::numberOfFacesNotAdjacentToOtherDroplet)
                .sum();
    }

    long part2() throws IOException {
        return flatten(droplets).stream()
                .mapToLong(this::numberOfFacesAdjacentToOuterAir)
                .sum();
    }

    private long numberOfFacesNotAdjacentToOtherDroplet(XYZ xyz) {
        return allDirectionsStream().map(m -> m.apply(xyz))
                .filter(c -> !exists(droplets, c))
                .count();
    }

    private long numberOfFacesAdjacentToOuterAir(XYZ xyz) {
        return allDirectionsStream().map(m -> m.apply(xyz))
                .filter(c -> exists(outerAirMap, c))
                .count();
    }

    private List<XYZ> flatten(Map<Long, Map<Long, Set<Long>>> scan) {
        return scan.entrySet().stream()
                .flatMap(x -> x.getValue().entrySet().stream().flatMap(y -> y.getValue().stream().map(z -> new XYZ(x.getKey(), y.getKey(), z))))
                .collect(Collectors.toList());
    }

    private void put(Map<Long, Map<Long, Set<Long>>> map, XYZ xyz) {
        map.computeIfAbsent(xyz.x(), x -> new HashMap<>())
                .computeIfAbsent(xyz.y(), y -> new HashSet<>())
                .add(xyz.z());
    }

    private boolean exists(Map<Long, Map<Long, Set<Long>>> map, XYZ xyz) {
        return map.getOrDefault(xyz.x(), Map.of())
                .getOrDefault(xyz.y(), Set.of())
                .contains(xyz.z());
    }

}