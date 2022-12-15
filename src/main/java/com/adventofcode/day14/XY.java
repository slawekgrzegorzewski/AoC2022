package com.adventofcode.day14;

public record XY(int x, int y) {
    public static XY parse(String value) {
        String[] parts = value.split(",");
        return new XY(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }

    public int manhattanDistance(XY other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public int[] findXsOfAllPointsInGivenDistance(int manhattanDistance, int y) {
        int range = manhattanDistance - Math.abs(this.y - y);
        if (range > 0) {
            return new int[]{this.x() - range, this.x() + range};
        }
        if (range == 0) {
            return new int[]{this.x()};
        }
        return new int[0];
    }
}