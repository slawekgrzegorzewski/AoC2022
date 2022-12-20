package com.adventofcode.day19;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Blueprint(long id,
                        ResourceCost oreRobotCost,
                        ResourceCost clayRobotCost,
                        ResourceCost[] obsidianRobotCost,
                        ResourceCost[] geodeRobotCost) {
    public static Blueprint parse(String line) {
        Pattern pattern = Pattern.compile("Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore. " +
                "Each clay robot costs ([0-9]+) ore. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay. " +
                "Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian.");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) throw new RuntimeException();
        return new Blueprint(
                Long.parseLong(matcher.group(1)),
                new ResourceCost(Resource.ORE, Long.parseLong(matcher.group(2))),
                new ResourceCost(Resource.ORE, Long.parseLong(matcher.group(3))),
                new ResourceCost[]{
                        new ResourceCost(Resource.ORE, Long.parseLong(matcher.group(4))),
                        new ResourceCost(Resource.CLAY, Long.parseLong(matcher.group(5)))
                },
                new ResourceCost[]{
                        new ResourceCost(Resource.ORE, Long.parseLong(matcher.group(6))),
                        new ResourceCost(Resource.OBSIDIAN, Long.parseLong(matcher.group(7)))
                }
        );


    }

    public ResourceCost[] getCostOfResourceRobot(Resource resource) {
        return switch (resource) {
            case ORE -> new ResourceCost[]{oreRobotCost};
            case CLAY -> new ResourceCost[]{clayRobotCost};
            case OBSIDIAN -> obsidianRobotCost;
            case GEODE -> geodeRobotCost;
        };
    }

    @Override
    public String toString() {
        return "Blueprint{" +
                "id=" + id +
                ", oreRobotCost=" + oreRobotCost +
                ", clayRobotCost=" + clayRobotCost +
                ", obsidianRobotCost=" + Arrays.toString(obsidianRobotCost) +
                ", geodeRobotCost=" + Arrays.toString(geodeRobotCost) +
                '}';
    }
}
