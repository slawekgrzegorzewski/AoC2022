package com.adventofcode.day14;

public record XY(int x, int y) {
    public static XY parse(String value) {
        String[] parts = value.split(",");
        return new XY(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }
}
