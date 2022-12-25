package com.adventofcode.input;

import com.adventofcode.day11.MonkeyBehaviour;
import com.adventofcode.day13.ListValue;
import com.adventofcode.day16.Valve;
import com.adventofcode.day18.XYZ;
import com.adventofcode.day19.Blueprint;
import com.adventofcode.day20.NumberWrapper;
import com.adventofcode.day21.Expression;
import com.adventofcode.day22.Instruction;
import com.adventofcode.day23.Elf;
import com.adventofcode.day4.Range;
import com.adventofcode.day9.Move;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Input {

    public static List<List<Integer>> day1() throws IOException {
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> group = new ArrayList<>();
        for (String line : getInputFromFile("/day1")) {
            if (line.trim().equals("")) {
                group = new ArrayList<>();
                groups.add(group);
            } else {
                group.add(Integer.parseInt(line));
            }
        }
        return groups;
    }

    public static List<String[]> day2() throws IOException {
        return getInputFromFile("/day2").stream().map(line -> line.split(" ")).collect(Collectors.toList());
    }

    public static List<String> day3() throws IOException {
        return getInputFromFile("/day3");
    }

    public static List<Pair<Range, Range>> day4() throws IOException {
        return getInputFromFile("/day4").stream().map(line -> line.split(",")).map(ranges -> new Pair<>(Range.parse(ranges[0]), Range.parse(ranges[1]))).collect(Collectors.toList());
    }

    public static List<String> day5() throws IOException {
        return getInputFromFile("/day5");
    }

    public static String day6() throws IOException {
        return String.join("", getInputFromFile("/day6"));
    }

    public static List<String> day7() throws IOException {
        return getInputFromFile("/day7");
    }

    public static int[][] day8() throws IOException {
        return getInputFromFile("/day8").stream().map(line -> line.chars().map(c -> Integer.parseInt(String.valueOf((char) c))).toArray()).toArray(int[][]::new);
    }

    public static List<Move> day9() throws IOException {
        return getInputFromFile("/day9").stream().map(line -> line.split(" ")).map(parts -> new Move(Move.Direction.valueOf(parts[0]), Integer.parseInt(parts[1]))).collect(Collectors.toList());
    }

    public static List<String> day10(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static Map<Integer, MonkeyBehaviour> day11(int worryLevelManagingFactor) throws IOException {
        List<String> lines = getInputFromFile("/day11");
        return IntStream.iterate(0, i -> i < lines.size(), i -> i + 7).mapToObj(i -> lines.subList(i, i + 6)).collect(Collectors.toMap(list -> monkeyIndex(list.get(0)), list -> MonkeyBehaviour.parse(list, worryLevelManagingFactor, monkeyIndex(list.get(0)))));
    }

    private static int monkeyIndex(String line) {
        return Integer.parseInt(line.replace("Monkey ", "").replace(":", ""));
    }

    public static List<String> day12() throws IOException {
        return getInputFromFile("/day12");
    }

    public static List<ListValue[]> day13() throws IOException {
        List<ListValue[]> packetsPairs = new ArrayList<>();
        List<String> lines = getInputFromFile("/day13");
        for (int i = 0; i < lines.size(); i++) {
            packetsPairs.add(new ListValue[]{ListValue.parse(lines.get(i++)), ListValue.parse(lines.get(i++))});
        }
        return packetsPairs;
    }

    public static List<XY> day14() throws IOException {
        return getInputFromFile("/day14").stream().map(line -> line.split(" -> ")).map(parts -> Arrays.stream(parts).map(XY::parse).toList()).map(Input::generateStonePaths).flatMap(List::stream).toList();
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
            return LongStream.range(Math.min(from.y(), to.y()), Math.max(from.y(), to.y()) + 1).mapToObj(y -> new XY(from.x(), y)).toList();
        } else if (from.y() == to.y()) {
            return LongStream.range(Math.min(from.x(), to.x()), Math.max(from.x(), to.x()) + 1).mapToObj(x -> new XY(x, from.y())).toList();
        }
        throw new RuntimeException();
    }

    public static List<XY[]> day15() throws IOException {
        Pattern pattern = Pattern.compile("=(-?[0-9]*)");
        return getInputFromFile("/day15").stream().map(pattern::matcher).map(matcher -> {
            XY sensor = XY.parse(findAndGetNextGroup(matcher) + "," + findAndGetNextGroup(matcher));
            XY beacon = XY.parse(findAndGetNextGroup(matcher) + "," + findAndGetNextGroup(matcher));
            return new XY[]{sensor, beacon};
        }).collect(Collectors.toList());
    }

    private static String findAndGetNextGroup(Matcher matcher) {
        if (!matcher.find()) throw new RuntimeException();
        return matcher.group(1);
    }

    public static Map<String, Valve> day16() throws IOException {
        List<String> lines = getInputFromFile("/day16");
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
            valves.put(matcher.group(1), new Valve(matcher.group(1), Integer.parseInt(matcher.group(2)), new HashMap<>()));
            reachableValves.put(matcher.group(1), matcher.group(3).split(", "));
        }
        for (Map.Entry<String, Valve> entry : valves.entrySet()) {
            String label = entry.getKey();
            Valve valve = entry.getValue();
            for (String s : reachableValves.get(label)) {
                Valve valve1 = valves.get(s);
                valve.reachableValves().put(valve1, 1);
            }
        }
        return valves;
    }

    public static List<Character> day17() throws IOException {
        return getInputFromFile("/day17").stream().map(String::toCharArray).map(chars -> {
            ArrayList<Character> asList = new ArrayList<>();
            for (char aChar : chars) {
                asList.add(aChar);
            }
            return asList;
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    public static List<XYZ> day18() throws IOException {
        return getInputFromFile("/day18").stream().map(XYZ::parse).collect(Collectors.toList());
    }

    public static List<Blueprint> day19() throws IOException {
        return getInputFromFile("/day19").stream().map(Blueprint::parse).collect(Collectors.toList());
    }

    public static List<NumberWrapper> day20() throws IOException {
        return getInputFromFile("/day20").stream().map(Long::parseLong).map(NumberWrapper::new).collect(Collectors.toList());
    }

    public static Map<String, Expression> day21() throws IOException {
        Pattern pattern = Pattern.compile("^([a-z]+): (.*)$");
        return getInputFromFile("/day21").stream()
                .map(pattern::matcher)
                .peek(Matcher::find)
                .collect(Collectors.toMap(
                        m -> m.group(1),
                        m -> Expression.parse(m.group(2))
                ));
    }

    public static Instruction day22() throws IOException {
        List<String> inputFromFile = getInputFromFile("/day22");
        Map<XY, Character> map = new HashMap<>();
        for (int y = 0; y < inputFromFile.size() - 2; y++) {
            String line = inputFromFile.get(y);
            char[] chars = line.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                char aChar = chars[x];
                if (aChar == ' ') continue;
                map.put(new XY(x + 1, y + 1), aChar);
            }
        }
        List<String> commands = new ArrayList<>();
        Pattern pattern = Pattern.compile("([0-9]+)(R|L*)");
        Matcher matcher = pattern.matcher(inputFromFile.get(inputFromFile.size() - 1));
        while (matcher.find()) {
            commands.add(matcher.group(1));
            if (matcher.groupCount() > 1) {
                if (!Strings.isNullOrEmpty(matcher.group(2)))
                    commands.add(matcher.group(2));
            }
        }
        return new Instruction(map, commands);
    }

    public static Map<XY, Elf> day23() throws IOException {
        Map<XY, Elf> elfPositions = new HashMap<>();
        List<String> lines = getInputFromFile("/day23");
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            char[] lineChars = line.toCharArray();
            for (int x = 0; x < lineChars.length; x++) {
                if (lineChars[x] == '#') {
                    elfPositions.put(new XY(x, y), new Elf());
                }
            }
        }
        return elfPositions;
    }

    public static Map<XY, List<Character>> day24() throws IOException {
        Map<XY, List<Character>> result = new HashMap<>();
        List<String> lines = getInputFromFile("/day24");
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                ArrayList<Character> characters = new ArrayList<>();
                characters.add(chars[x]);
                if (chars[x] != '.') result.put(new XY(x, y), characters);
            }
        }
        return result;
    }

    public static List<String> day25() throws IOException {
        return getInputFromFile("/day25");
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName))); BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
