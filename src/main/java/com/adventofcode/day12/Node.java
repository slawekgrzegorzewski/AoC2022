package com.adventofcode.day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final int x;
    private final int y;
    private final char elevation;
    private final List<Node> nodes = new ArrayList<>();
    private final boolean isStart;
    private final boolean isEnd;

    public Node(int x, int y, char elevation) {
        this.x = x;
        this.y = y;
        switch (elevation) {
            case 'S' -> {
                isStart = true;
                isEnd = false;
                elevation = 'a';
            }
            case 'E' -> {
                isStart = false;
                isEnd = true;
                elevation = 'z';
            }
            default -> {
                isStart = false;
                isEnd = false;
            }
        }
        this.elevation = elevation;
    }

    public void addChild(Node node) {
        this.nodes.add(node);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public char elevation() {
        return elevation;
    }

    public List<Node> nodes() {
        return List.copyOf(nodes);
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
