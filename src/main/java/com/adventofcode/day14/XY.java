package com.adventofcode.day14;

public record XY(long x, long y) {
    public static XY parse(String value) {
        String[] parts = value.split(",");
        return new XY(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }

    public long manhattanDistance(XY other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public long[] findXsOfAllPointsInGivenDistance(long manhattanDistance, long y) {
        long range = manhattanDistance - Math.abs(this.y - y);
        if (range > 0) {
            return new long[]{this.x() - range, this.x() + range};
        }
        if (range == 0) {
            return new long[]{this.x()};
        }
        return new long[0];
    }

    public XY moveLeft() {
        return new XY(x(), y() - 1);
    }

    public XY moveRight() {
        return new XY(x(), y() + 1);
    }

    public XY moveDown() {
        return new XY(x() - 1, y());
    }
}