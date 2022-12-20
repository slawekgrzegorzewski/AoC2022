package com.adventofcode.day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class Factory {
    private final Map<Resource, Long> resources = new HashMap<>();
    private final Map<Resource, Long> robotsPerResource = new HashMap<>();
    private final Blueprint blueprint;

    public Factory copy() {
        return new Factory(blueprint, resources, robotsPerResource);
    }

    public Factory(Blueprint blueprint, Map<Resource, Long> initialRobots) {
        this(blueprint, Map.of(), initialRobots);
    }

    private Factory(Blueprint blueprint, Map<Resource, Long> initialResources, Map<Resource, Long> initialRobots) {
        this.blueprint = blueprint;
        resources.putAll(initialResources);
        robotsPerResource.putAll(initialRobots);
    }

    public Blueprint blueprint() {
        return blueprint;
    }

    public long getResourcesAmount(Resource resource) {
        return resources.getOrDefault(resource, 0L);
    }

    public boolean isPossibleToBuildResourceRobot(Resource resource) {
        ResourceCost[] costOfResourceRobot = blueprint.getCostOfResourceRobot(resource);
        boolean hasEnoughResources = true;
        for (ResourceCost cost : costOfResourceRobot) {
            hasEnoughResources = hasEnoughResources && cost.cost() <= resources.getOrDefault(cost.resource(), 0L);
        }
        return hasEnoughResources;
    }

    public List<Resource> robotsPossibleToBuild() {
        return Arrays.stream(Resource.values()).filter(this::isPossibleToBuildResourceRobot).collect(Collectors.toList());
    }

    public void buildResourceRobot(Resource resource) {
        if (!isPossibleToBuildResourceRobot(resource)) throw new RuntimeException();
        ResourceCost[] costOfResourceRobot = blueprint.getCostOfResourceRobot(resource);
        for (ResourceCost cost : costOfResourceRobot) {
            resources.computeIfPresent(cost.resource(), (r, a) -> a - cost.cost());
        }
        robotsPerResource.compute(resource, (r, a) -> a == null ? 1 : a + 1);
    }

    public void collectResources() {
        robotsPerResource.forEach((key, value) -> resources.compute(key, (r, a) -> ofNullable(a).orElse(0L) + value));
    }
}
