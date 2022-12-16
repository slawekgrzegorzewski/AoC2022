package com.adventofcode.input;

import com.adventofcode.day11.MonkeyBehaviour;
import com.adventofcode.day13.ListValue;
import com.adventofcode.day14.XY;
import com.adventofcode.day16.Valve;
import com.adventofcode.day4.Range;
import com.adventofcode.day9.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public static List<String> day12(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static List<ListValue[]> day13(String resourceName) throws IOException {
        List<ListValue[]> packetsPairs = new ArrayList<>();
        List<String> lines = getInputFromFile(resourceName);
        for (int i = 0; i < lines.size(); i++) {
            packetsPairs.add(new ListValue[]{
                    ListValue.parse(lines.get(i++)),
                    ListValue.parse(lines.get(i++))
            });
        }
        return packetsPairs;
    }

    public static List<XY> day14(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(" -> "))
                .map(parts -> Arrays.stream(parts).map(XY::parse).toList())
                .map(Input::generateStonePaths)
                .flatMap(List::stream)
                .toList();
    }

    private static List<XY> generateStonePaths(List<XY> paths) {
        List<XY> rocks = new ArrayList<>();
        for (int i = 1; i < paths.size(); i++) {
            XY from = paths.get(i - 1);
            XY to = paths.get(i);
            rocks.addAll(generatePath(from, to));
        }
        return rocks;
    }

    private static List<XY> generatePath(XY from, XY to) {
        if (from.x() == to.x()) {
            return IntStream.range(Math.min(from.y(), to.y()), Math.max(from.y(), to.y()) + 1)
                    .mapToObj(y -> new XY(from.x(), y))
                    .toList();
        } else if (from.y() == to.y()) {
            return IntStream.range(Math.min(from.x(), to.x()), Math.max(from.x(), to.x()) + 1)
                    .mapToObj(x -> new XY(x, from.y()))
                    .toList();
        }
        throw new RuntimeException();
    }

    public static List<XY[]> day15(String resourceName) throws IOException {
        Pattern pattern = Pattern.compile("=(-?[0-9]*)");
        return getInputFromFile(resourceName).stream()
                .map(pattern::matcher)
                .map(matcher -> {
                    XY sensor = XY.parse(findAndGetNextGroup(matcher) + "," + findAndGetNextGroup(matcher));
                    XY beacon = XY.parse(findAndGetNextGroup(matcher) + "," + findAndGetNextGroup(matcher));
                    return new XY[]{sensor, beacon};
                }).collect(Collectors.toList());
    }

    private static String findAndGetNextGroup(Matcher matcher) {
        if (!matcher.find()) throw new RuntimeException();
        return matcher.group(1);
    }

    public static Map<String, Valve> day16(String resourceName) throws IOException {
        List<String> lines = getInputFromFile(resourceName);
        Map<String, String[]> reachableValves = new HashMap<>();
        Map<String, Valve> valves = new HashMap<>();
        Pattern pattern = Pattern.compile("Valve ([A-Z][A-Z]) has flow rate=([0-9]+); tunnels lead to valves (.*)");
        Pattern pattern2 = Pattern.compile("Valve ([A-Z][A-Z]) has flow rate=([0-9]+); tunnel leads to valve (.*)");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                matcher = pattern2.matcher(line);
                matcher.find();
            }
            valves.put(matcher.group(1), new Valve(matcher.group(1), Integer.parseInt(matcher.group(2)), new ArrayList<>()));
            reachableValves.put(matcher.group(1), matcher.group(3).split(", "));
        }
        for (Map.Entry<String, Valve> entry : valves.entrySet()) {
            String label = entry.getKey();
            Valve valve = entry.getValue();
            List<Valve> valves1
                    = valve.reachableValves();
            for (String s : reachableValves.get(label)) {
                Valve valve1 = valves.get(s);
                valves1.add(valve1);
            }
        }
        return valves;
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName)));
             BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
