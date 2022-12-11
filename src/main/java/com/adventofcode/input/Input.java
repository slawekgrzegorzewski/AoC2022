package com.adventofcode.input;

import com.adventofcode.day11.MonkeyBehaviour;
import com.adventofcode.day4.Range;
import com.adventofcode.day9.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Input {

    public static List<List<Integer>> day1(String resourceName) throws IOException {
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> group = new ArrayList<>();
        for (String line : getInputFromFile(resourceName)) {
            if (line.trim().equals("")) {
                group = new ArrayList<>();
                groups.add(group);
            } else {
                group.add(Integer.parseInt(line));
            }
        }
        return groups;
    }

    public static List<String[]> day2(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(" "))
                .collect(Collectors.toList());
    }

    public static List<String> day3(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static List<Pair<Range, Range>> day4(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(","))
                .map(ranges -> new Pair<>(Range.parse(ranges[0]), Range.parse(ranges[1])))
                .collect(Collectors.toList());
    }

    public static List<String> day5(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static String day6(String resourceName) throws IOException {
        return String.join("", getInputFromFile(resourceName));
    }

    public static List<String> day7(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static int[][] day8(String resourceName) throws IOException {
        return getInputFromFile(resourceName).stream()
                .map(line -> line.chars().map(c -> Integer.parseInt(String.valueOf((char) c))).toArray())
                .toArray(int[][]::new);
    }

    public static List<Move> day9(String resourceName) throws IOException {
        return getInputFromFile(resourceName).stream()
                .map(line -> line.split(" "))
                .map(parts -> new Move(Move.Direction.valueOf(parts[0]), Integer.parseInt(parts[1])))
                .collect(Collectors.toList());
    }

    public static List<String> day10(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static Map<Integer, MonkeyBehaviour> day11(String resourceName, int worryLevelManagingFactor) throws IOException {
        List<String> lines = getInputFromFile(resourceName);
        return IntStream.iterate(0, i -> i < lines.size(), i -> i + 7)
                .mapToObj(i -> lines.subList(i, i + 6))
                .collect(Collectors.toMap(
                        list -> monkeyIndex(list.get(0)),
                        list -> MonkeyBehaviour.parse(list, worryLevelManagingFactor, monkeyIndex(list.get(0)))
                ));
    }

    private static int monkeyIndex(String line) {
        return Integer.parseInt(line.replace("Monkey ", "").replace(":", ""));
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName)));
             BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
