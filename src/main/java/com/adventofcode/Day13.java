package com.adventofcode;

import com.adventofcode.day13.ListValue;
import com.adventofcode.day13.SingleValue;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 {

    private final List<ListValue[]> packets;

    public Day13() throws IOException {
        packets = Input.day13();
    }

    long part1() throws IOException {
        return IntStream.range(0, packets.size())
                .filter(i -> packets.get(i)[0].compareTo(packets.get(i)[1]) <= 0)
                .map(i -> i + 1)
                .sum();
    }

    long part2() throws IOException {
        ListValue firstDivider = new ListValue(List.of(new ListValue(List.of(new SingleValue(2)))));
        ListValue secondDivider = new ListValue(List.of(new ListValue(List.of(new SingleValue(6)))));
        List<ListValue> allPackets = packets.stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        allPackets.add(firstDivider);
        allPackets.add(secondDivider);
        allPackets = allPackets.stream().sorted().toList();
        return (long) (allPackets.indexOf(firstDivider) + 1) * (allPackets.indexOf(secondDivider) + 1);
    }
}