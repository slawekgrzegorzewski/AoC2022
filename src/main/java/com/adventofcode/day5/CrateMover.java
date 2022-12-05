package com.adventofcode.day5;

import java.util.LinkedList;
import java.util.List;

public interface CrateMover {
    List<LinkedList<String>> getCrates();

    void move(Move move);
}
