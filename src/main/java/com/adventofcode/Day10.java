package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day10 {

    private final Map<Integer, Integer> xValuesPerCycles;

    public Day10() throws IOException {
        xValuesPerCycles = processProgram(Input.day10("/day10"));
    }

    long part1() {
        return Stream.of(20, 60, 100, 140, 180, 220).mapToInt(c -> c * xValuesPerCycles.get(c)).sum();
    }

    String part2() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 1; i <= 240; i++) {
            int positionInRow = (i - 1) % 40;
            if (positionInRow == 0 && i > 1) buffer.append("\n");
            if (positionInRow >= xValuesPerCycles.get(i) - 1 && positionInRow <= xValuesPerCycles.get(i) + 1) {
                buffer.append('#');
            } else {
                buffer.append('.');
            }
        }
        return buffer.toString();
    }

    private Map<Integer, Integer> processProgram(List<String> program) {
        Map<Integer, Integer> xValuesAtEndOfCycle = new HashMap<>();
        int cycle = 0;
        int X = 1;
        for (String command : program) {
            xValuesAtEndOfCycle.put(++cycle, X);
            if (!command.equals("noop")) {
                xValuesAtEndOfCycle.put(++cycle, X);
                X += Integer.parseInt(command.replace("addx ", ""));
            }
        }
        return xValuesAtEndOfCycle;
    }

}


