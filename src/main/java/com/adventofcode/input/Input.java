package com.adventofcode.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName)));
             BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
