package com.adventofcode.day18;

import java.util.LinkedHashSet;

public class ExternalAir {
    private final XYZ coordinates;
    private final LinkedHashSet<XYZ> adjacent = new LinkedHashSet<>();
    private Color color = Color.WHITE;
    private long distance = Long.MAX_VALUE;

    public ExternalAir(XYZ coordinates) {
        this.coordinates = coordinates;
    }

    public XYZ coordinates() {
        return coordinates;
    }

    public LinkedHashSet<XYZ> adjacent() {
        return new LinkedHashSet<>(adjacent);
    }

    public void add(XYZ xyz) {
        adjacent.add(xyz);
    }

    public Color color() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public long distance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public enum Color {
        WHITE, GREY, BLACk
    }
}
