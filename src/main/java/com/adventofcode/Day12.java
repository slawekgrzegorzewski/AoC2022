package com.adventofcode;

import com.adventofcode.day12.Node;
import com.adventofcode.day12.TraversingDirection;
import com.adventofcode.input.Input;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.adventofcode.day12.TraversingDirection.DOWN;
import static com.adventofcode.day12.TraversingDirection.UP;

public class Day12 {

    private final Map<Integer, Map<Integer, Node>> nodes = new HashMap<>();
    private final List<String> input;

    public Day12() throws IOException {
        input = Input.day12("/day12");
    }

    long part1() throws IOException {
        buildGraph(UP);
        Node start = nodes.values().stream().flatMap(m -> m.values().stream()).filter(Node::isStart).findFirst().orElseThrow();
        Node end = nodes.values().stream().flatMap(m -> m.values().stream()).filter(Node::isEnd).findFirst().orElseThrow();
        Integer[][] distancesFromStart = calculateDistancesFromStart(start);
        return getValueForNode(end, distancesFromStart);
    }

    long part2() throws IOException {
        buildGraph(DOWN);
        Node start = nodes.values().stream().flatMap(m -> m.values().stream()).filter(Node::isEnd).findFirst().orElseThrow();
        List<Node> endsToConsider = nodes.values().stream()
                .flatMap(m -> m.values().stream())
                .filter(n -> 'a' == n.elevation())
                .toList();
        Integer[][] distancesFromStart = calculateDistancesFromStart(start);
        return endsToConsider.stream()
                .mapToInt(n -> getValueForNode(n, distancesFromStart))
                .min()
                .orElseThrow();
    }

    private void buildGraph(TraversingDirection up) {
        for (int i = 0; i < input.size(); i++) {
            char[] chars = input.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                Node newNode = new Node(i, j, chars[j]);
                nodes.computeIfAbsent(i, HashMap::new).put(j, newNode);
                if (i > 0) {
                    mutuallyAddNodes(newNode, nodes.get(i - 1).get(j), up);
                }
                if (j > 0) {
                    mutuallyAddNodes(newNode, nodes.get(i).get(j - 1), up);
                }
            }
        }
    }

    private static void mutuallyAddNodes(Node newNode, Node previousNode, TraversingDirection up) {
        if (isReachable(newNode, previousNode, up)) {
            newNode.addChild(previousNode);
        }
        if (isReachable(previousNode, newNode, up)) {
            previousNode.addChild(newNode);
        }
    }

    private static boolean isReachable(Node fromNode, Node toNode, TraversingDirection traversingDirection) {
        return switch (traversingDirection) {
            case UP -> (int) toNode.elevation() - (int) fromNode.elevation() <= 1;
            case DOWN -> (int) toNode.elevation() - (int) fromNode.elevation() >= -1;
        };
    }

    private Integer[][] calculateDistancesFromStart(Node start) {
        List<Node> remainingNodes = new ArrayList<>(nodes.values().stream().flatMap(m -> m.values().stream()).toList());
        Integer[][] distances = initDistancesMatrix();
        Node[][] previousNodes = new Node[nodes.size()][nodes.get(0).size()];
        setValueForNode(start, distances, 0);
        while (!remainingNodes.isEmpty()) {
            Node closestNode = findNodeWithLowestDist(remainingNodes, distances);
            remainingNodes.remove(closestNode);
            for (Node nodeNextToClosestOne : closestNode.nodes()) {
                if (remainingNodes.contains(nodeNextToClosestOne) && getValueForNode(closestNode, distances) != Integer.MAX_VALUE) {
                    int alt = getValueForNode(closestNode, distances) + 1;
                    if (alt < getValueForNode(nodeNextToClosestOne, distances)) {
                        setValueForNode(nodeNextToClosestOne, distances, alt);
                        setValueForNode(nodeNextToClosestOne, previousNodes, closestNode);
                        setValueForNode(nodeNextToClosestOne, previousNodes, closestNode);
                    }
                }
            }
        }
        return distances;
    }

    @NotNull
    private Integer[][] initDistancesMatrix() {
        Integer[][] dist = new Integer[nodes.size()][nodes.get(0).size()];
        for (Integer[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        return dist;
    }

    private Node findNodeWithLowestDist(List<Node> nodes, Integer[][] dist) {
        int min = Integer.MAX_VALUE;
        Node minDistNode = null;
        for (Node node : nodes) {
            int i = getValueForNode(node, dist);
            if (i < min) {
                min = i;
                minDistNode = node;
            }
        }
        if (minDistNode == null) {
            return nodes.get(0);
        }
        return minDistNode;
    }

    private static <T> T getValueForNode(Node node, T[][] array) {
        return array[node.x()][node.y()];
    }

    private static <T> void setValueForNode(Node node, T[][] array, T value) {
        array[node.x()][node.y()] = value;
    }
}


