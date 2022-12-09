package com.adventofcode.day9;

import org.jetbrains.annotations.NotNull;

public record Coordinates(int x, int y) {
    public Coordinates up() {
        return new Coordinates(this.x, this.y + 1);
    }

    public Coordinates down() {
        return new Coordinates(this.x, this.y - 1);
    }

    public Coordinates left() {
        return new Coordinates(this.x - 1, this.y);
    }

    public Coordinates right() {
        return new Coordinates(this.x + 1, this.y);
    }

    public boolean doesntTouch(Coordinates H) {
        return Math.abs(this.x - H.x) > 1 || Math.abs(this.y - H.y) > 1;
    }

    public @NotNull Coordinates moveToward(Coordinates H) {
        return new Coordinates(
                this.x + Integer.compare(H.x, this.x),
                this.y + Integer.compare(H.y, this.y)
        );
    }

}
