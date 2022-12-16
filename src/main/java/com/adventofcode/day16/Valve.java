package com.adventofcode.day16;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.adventofcode.day16.BFSColor.WHITE;

public final class Valve {
    private final String label;
    private final int flowRate;
    private final Map<Integer, Valve> reachableValves;
    private boolean open = false;

    public BFSColor bfsColor = WHITE;
    public int distance = Integer.MAX_VALUE;
    public Valve parent = null;
    public int id = 0;

    public Valve(String label, int flowRate, Map<Integer, Valve> reachableValves) {
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

    public Map<Integer, Valve> reachableValves() {
        return reachableValves;
    }

    public void open() {
        open = true;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Valve) obj;
        return Objects.equals(this.label, that.label) &&
                this.flowRate == that.flowRate &&
                Objects.equals(this.reachableValves, that.reachableValves);
    }


    @Override
    public String toString() {
        return "Valve{" +
                "label='" + label + '\'' +
                ", flowRate=" + flowRate +
                ", reachableValves=" + reachableValves.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue().label()).collect(Collectors.joining(",")) +
                ", open=" + open +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, flowRate, reachableValves);
    }

}
