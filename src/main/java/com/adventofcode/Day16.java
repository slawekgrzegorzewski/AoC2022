package com.adventofcode;

import com.adventofcode.day16.BFSColor;
import com.adventofcode.day16.Valve;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day16 {

    private final Map<String, Valve> valvesSystem;

    public Day16() throws IOException {
        valvesSystem = Input.day16("/day16");
    }

    long part1() throws IOException {
        List<String> labels = new ArrayList<>();
        for (Valve value : valvesSystem.values()) {
            for (Valve reachableValve : value.reachableValves()) {
                String firstLabel = value.label() + "." + value.flowRate();
                String secondLabel = reachableValve.label() + "." + reachableValve.flowRate();
                if (labels.contains(firstLabel + "-" + secondLabel) || labels.contains(secondLabel + "-" + firstLabel))
                    continue;
                labels.add(firstLabel + "-" + secondLabel);
                System.out.println(firstLabel + "-" + secondLabel);
            }
        }
        bfs(valvesSystem.get("AA"));
//        dijkstra(valvesSystem.get("AA"), new ArrayList<>(valvesSystem.values()));
        return 0L;
    }

    long part2() throws IOException {
        return 0L;
    }

    private void dijkstra(Valve start, List<Valve> all) {
        LinkedList<Valve> Q = new LinkedList<>(all);
        start.distance = 0;
        while (!Q.isEmpty()) {
            Valve u = removeLowestDistance(Q);
            if (u == null) break;
            for (Valve v : u.reachableValves()) {
                if (v.distance > u.distance + 1) {
                    v.distance = u.distance + 1;
                    v.parent = u;
                }
            }
        }
    }

    private Valve removeLowestDistance(LinkedList<Valve> Q) {
        Valve result = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Valve valve : Q) {
            if (valve.distance < lowestDistance) {
                lowestDistance = valve.distance;
                result = valve;
            }
        }
        if (result != null) {
            Q.remove(result);
        }
        return result;
    }

    private void bfs(Valve start) {
        LinkedList<Valve> Q = new LinkedList<>();
        start.bfsColor = BFSColor.GREY;
        start.distance = 0;
        Q.add(start);
        while (!Q.isEmpty()) {
            Valve u = Q.removeFirst();
            for (Valve v : u.reachableValves()) {
                if (v.bfsColor == BFSColor.WHITE) {
                    v.bfsColor = BFSColor.GREY;
                    v.distance = u.distance + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.bfsColor = BFSColor.BLACK;
        }
    }
}