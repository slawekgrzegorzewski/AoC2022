package com.adventofcode.day5;

import java.util.LinkedList;
import java.util.List;

public class CrateMover9000 implements CrateMover {

    private final List<LinkedList<String>> crates;

    public CrateMover9000(List<LinkedList<String>> crates) {
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
        for (int i = 0; i < move.count(); i++) {
            to.addLast(from.removeLast());
        }
    }
}
