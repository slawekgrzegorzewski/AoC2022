package com.adventofcode.day16;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Valve {
    private final String label;
    private final int flowRate;
    private final Map<Valve, Integer> reachableValves;
    public boolean skipOpening = false;
    private boolean open = false;

    public Valve(String label, int flowRate, Map<Valve, Integer> reachableValves) {
        this.label = label;
        this.flowRate = flowRate;
        this.reachableValves = reachableValves;
    }

    public String label() {
        return label;
    }

    public int flowRate() {
        return flowRate;
    }

    public Map<Valve, Integer> reachableValves() {
        return reachableValves;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return Objects.equals(label, valve.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "Valve{" +
                "label='" + label + '\'' +
                ", flowRate=" + flowRate +
                ", reachableValves=" + reachableValves.entrySet().stream().map(e -> e.getValue() + ":" + e.getKey().label()).collect(Collectors.joining(",")) +
                ", open=" + open +
                '}';
    }
}
