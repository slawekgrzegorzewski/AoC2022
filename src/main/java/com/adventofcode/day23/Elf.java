package com.adventofcode.day23;

import com.adventofcode.input.XY;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.adventofcode.day23.Direction.*;

public class Elf {
    private final LinkedList<List<Direction>> directionsToConsider = new LinkedList<>();

    public Elf() {
        directionsToConsider.addLast(List.of(N, NE, NW));
        directionsToConsider.addLast(List.of(S, SE, SW));
        directionsToConsider.addLast(List.of(W, NW, SW));
        directionsToConsider.addLast(List.of(E, NE, SE));
    }

    public Optional<XY> nextPosition(XY currentPosition, Map<XY, Elf> mapOfOtherElves) {
        if (noNeighbours(currentPosition, mapOfOtherElves)) {
            rotateDirections();
            return Optional.empty();
        }
        Optional<Direction> directionToGo = directionsToConsider.stream()
                .filter(directions -> noNeighboursInDirection(currentPosition, mapOfOtherElves, directions))
                .findFirst()
                .map(list -> list.get(0));
        rotateDirections();
        return directionToGo.map(d -> d.getPointNextToInMyDirection(currentPosition));
    }

    private static boolean noNeighbours(XY currentPosition, Map<XY, Elf> mapOfOtherElves) {
        return Stream.of(Direction.values()).noneMatch(d -> mapOfOtherElves.containsKey(d.getPointNextToInMyDirection(currentPosition)));
    }

    private static boolean noNeighboursInDirection(XY currentPosition, Map<XY, Elf> mapOfOtherElves, List<Direction> directions) {
        return directions.stream().noneMatch(d -> mapOfOtherElves.containsKey(d.getPointNextToInMyDirection(currentPosition)));
    }

    public void rotateDirections() {
        directionsToConsider.addLast(directionsToConsider.removeFirst());
    }
}
