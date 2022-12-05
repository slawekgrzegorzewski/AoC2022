package com.adventofcode.day5;

import java.util.LinkedList;
import java.util.List;

public class CrateMover9001 implements CrateMover {

    private final List<LinkedList<String>> crates;

    public CrateMover9001(List<LinkedList<String>> crates) {
        this.crates = crates;
    }

    @Override
    public List<LinkedList<String>> getCrates() {
        return crates;
    }

    @Override
    public void move(Move move) {
        LinkedList<String> from = crates.get(move.from() - 1);
        LinkedList<String> to = crates.get(move.to() - 1);
        LinkedList<String> stack = new LinkedList<>();
        for (int i = 0; i < move.count(); i++) {
            stack.addLast(from.removeLast());
        }
        while (!stack.isEmpty()) {
            to.addLast(stack.removeLast());
        }
    }
}
