package com.adventofcode;

import com.adventofcode.day16.Valve;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;

public class Day16 {

    private final Map<String, Valve> valvesSystem;

    public Day16() throws IOException {
        valvesSystem = Input.day16();
        compactGraph();
    }

    long part1() throws IOException {
        List<String> optimumPath = List.of("AA", "MA", "II", "AS", "RU", "PM", "KQ", "RU", "ED", "AS'", "II'");
        return calculatePathRelease(new LinkedList<>(optimumPath), 30, false);
    }

    long part2() throws IOException {
        List<String> optimumPathForMe = List.of("AA", "MA", "II", "AS", "RU", "PM", "KQ", "RU", "ED", "AS'");
        List<String> optimumPathForElephant = List.of("AA", "HR", "DW", "XO", "VI", "XO", "DW", "MW", "FQ", "LF");
        return this.calculatePathRelease(new LinkedList<>(optimumPathForMe), 26, false)
                + this.calculatePathRelease(new LinkedList<>(optimumPathForElephant), 26, false);
    }

    public int calculatePathRelease(LinkedList<String> path, int numberOfMinutes, boolean debug) {
        valvesSystem.values().forEach(v -> {
            v.close();
            v.skipOpening = false;
        });
        int currentFlow = 0;
        int steamReleased = 0;
        Valve valve = getNode(path.removeFirst());
        int distance = 0;
        for (int i = 1; i <= numberOfMinutes; i++) {
            steamReleased += currentFlow;
            if (debug)
                System.out.println("minute: " + i + ", released: " + currentFlow + ", steamReleased: " + steamReleased + " ");
            if (distance == 0) {
                Valve nextValve = getNode(path.removeFirst());
                distance = valve.reachableValves().get(nextValve);
                if (!valve.isOpen() && valve.flowRate() > 0 && !valve.skipOpening) {
                    valve.open();
                    currentFlow += valve.flowRate();
                    if (debug)
                        System.out.println("\tOpening valve " + valve.label());
                } else {
                    distance--;
                    if (debug)
                        System.out.println("\tGoing to next valve: " + valve.label() + " distance left: " + distance);
                }
                valve = nextValve;
            } else {
                distance--;
                if (debug)
                    System.out.println("\tGoing to next valve: " + valve.label() + " distance left: " + distance);
            }
        }
        return steamReleased;
    }

    private Valve getNode(String label) {
        boolean skipOpening = false;
        if (label.endsWith("'")) {
            label = label.substring(0, label.length() - 1);
            skipOpening = true;
        }
        Valve valve = valvesSystem.get(label);
        valve.skipOpening = skipOpening;
        return valve;
    }

    private void compactGraph() {
        Iterator<Map.Entry<String, Valve>> iterator = valvesSystem.entrySet().iterator();
        iterator.forEachRemaining(entry -> {
            Valve node = entry.getValue();
            if (node.flowRate() == 0 && !node.label().equals("AA")) {
                if (node.reachableValves().size() != 2) throw new RuntimeException();
                ArrayList<Map.Entry<Valve, Integer>> nodes = new ArrayList<>(node.reachableValves().entrySet());
                Valve from = nodes.get(0).getKey();
                Valve to = nodes.get(1).getKey();
                from.reachableValves().put(to, from.reachableValves().get(node) + node.reachableValves().get(to));
                from.reachableValves().remove(node);
                to.reachableValves().put(from, to.reachableValves().get(node) + node.reachableValves().get(from));
                to.reachableValves().remove(node);
                iterator.remove();
            }
        });
    }

}