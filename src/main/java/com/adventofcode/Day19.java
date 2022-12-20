package com.adventofcode;

import com.adventofcode.day19.Blueprint;
import com.adventofcode.day19.Factory;
import com.adventofcode.day19.Resource;
import com.adventofcode.day19.Snapshot;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;

import static com.adventofcode.day19.Resource.GEODE;

public class Day19 {
    private static final Map<Long, Long> numberOfStepsToProduceFirstGeode = new HashMap<>();
    private final List<Blueprint> blueprints;

    public Day19() throws IOException {
        blueprints = Input.day19();
    }

    long part1() throws IOException {
        blueprints.forEach(b -> numberOfStepsToProduceFirstGeode.put(b.id(), 24L));
        return blueprints.parallelStream()
                .mapToLong(blueprint -> qualityOf(blueprint, 24L))
                .sum();
    }

    long part2() throws IOException {
        List<Blueprint> firstThreeBlueprints = blueprints.subList(0, 3);
        firstThreeBlueprints.forEach(b -> numberOfStepsToProduceFirstGeode.put(b.id(), 32L));
        return firstThreeBlueprints.parallelStream()
                .mapToLong(blueprint -> qualityOf(blueprint, 32L))
                .reduce(1L, (left, right) -> left * right);
    }

    private long qualityOf(Blueprint blueprint, long minutes) {
        System.out.println("Processing blueprint " + blueprint.id());
        Factory factory = new Factory(blueprint, Map.of(Resource.ORE, 1L));
        LinkedList<Resource> robotBuildDecisions = new LinkedList<>();
        numberOfStepsToProduceFirstGeode.put(blueprint.id(), minutes);
        var snapshot = findBestPathOfDecisions(factory, robotBuildDecisions, (int) minutes);
        long geodes = snapshot.factory().getResourcesAmount(GEODE);
        long quality = blueprint.id() * geodes;
        System.out.println("Finished blueprint " + blueprint.id() + " geodes: " + geodes + " quailty " + quality);
        return quality;
    }

    private Snapshot findBestPathOfDecisions(Factory factory, LinkedList<Resource> robotBuildDecisions, int limitOfSteps) {
        long geodesProduced = factory.getResourcesAmount(GEODE);
        if (geodesProduced == 0 && robotBuildDecisions.size() > numberOfStepsToProduceFirstGeode.get(factory.blueprint().id())) {
            return new Snapshot(factory.copy(), robotBuildDecisions);
        }
        if (geodesProduced > 0 && robotBuildDecisions.size() < numberOfStepsToProduceFirstGeode.get(factory.blueprint().id()))
            numberOfStepsToProduceFirstGeode.put(factory.blueprint().id(), (long) robotBuildDecisions.size());

        LinkedList<Resource> resources = new LinkedList<>(factory.robotsPossibleToBuild());
        if (resources.contains(GEODE)) {
            resources.clear();
            resources.add(GEODE);
        }
        resources.addLast(null);

        List<Snapshot> snapshot = new LinkedList<>();
        for (Resource resource : resources) {
            Factory copy = factory.copy();
            copy.collectResources();
            if (resource != null)
                copy.buildResourceRobot(resource);
            robotBuildDecisions.addLast(resource);
            if (robotBuildDecisions.size() == limitOfSteps) {
                snapshot.add(new Snapshot(copy, new LinkedList<>(robotBuildDecisions)));
            } else {
                Snapshot s = findBestPathOfDecisions(copy, robotBuildDecisions, limitOfSteps);
                snapshot.add(s);
            }
            robotBuildDecisions.removeLast();
        }
        return snapshot.stream().max(Comparator.comparing(s -> s.factory().getResourcesAmount(GEODE))).orElseThrow();
    }

}