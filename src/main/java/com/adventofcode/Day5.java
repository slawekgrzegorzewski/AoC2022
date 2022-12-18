package com.adventofcode;

import com.adventofcode.day5.CrateMover;
import com.adventofcode.day5.CrateMover9000;
import com.adventofcode.day5.CrateMover9001;
import com.adventofcode.day5.Move;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    private final List<LinkedList<String>> crates = new ArrayList<>();
    private final List<Move> moves;

    public Day5() throws IOException {
        List<String> input = Input.day5();

        final int splitIndex = input.indexOf("");
        final int numberOfCrates = Integer.parseInt(input.get(splitIndex - 1).substring(input.get(splitIndex - 1).lastIndexOf(' ') + 1));
        for (int i = 0; i < numberOfCrates; i++) {
            crates.add(new LinkedList<>());
        }

        input.subList(0, splitIndex - 1)
                .stream()
                .map(String::toCharArray)
                .forEach(line -> {
                    int slot = -1;
                    for (int i = 0; i < line.length; i += 4) {
                        slot++;
                        if (line[i + 1] == ' ') continue;
                        crates.get(slot).push(String.valueOf(line[i + 1]));
                    }
                });
        moves = input.subList(splitIndex + 1, input.size())
                .stream()
                .map(Move::parse)
                .collect(Collectors.toList());

    }

    String part1() {
        return processProcedure(new CrateMover9000(deepCopy(crates)));
    }

    String part2() {
        return processProcedure(new CrateMover9001(deepCopy(crates)));
    }

    private List<LinkedList<String>> deepCopy(List<LinkedList<String>> crates) {
        return crates.stream()
                .map(LinkedList::new)
                .collect(Collectors.toList());
    }

    private String processProcedure(CrateMover crateMover) {
        moves.forEach(crateMover::move);
        return crateMover.getCrates().stream().map(LinkedList::getLast).collect(Collectors.joining());
    }
}


