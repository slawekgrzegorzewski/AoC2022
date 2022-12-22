package com.adventofcode;

import com.adventofcode.day14.XY;
import com.adventofcode.day22.Direction;
import com.adventofcode.day22.Instruction;
import com.adventofcode.day22.Position;
import com.adventofcode.input.Input;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.adventofcode.day22.Direction.*;

public class Day22 {
    private final static Pattern NUMBER = Pattern.compile("[0-9]+");
    private final Instruction input;
    private final LongSummaryStatistics xStats;
    private final LongSummaryStatistics yStats;

    private static final Map<Character, List<XY>> WALLS = Map.of(
            'A', List.of(new XY(51, 1), new XY(100, 50)),
            'B', List.of(new XY(101, 1), new XY(150, 50)),
            'C', List.of(new XY(51, 51), new XY(100, 100)),
            'D', List.of(new XY(1, 101), new XY(50, 150)),
            'E', List.of(new XY(51, 101), new XY(100, 150)),
            'F', List.of(new XY(1, 151), new XY(50, 200))
    );

    public Day22() throws IOException {
        input = Input.day22();
        xStats = input.map().keySet().stream().mapToLong(XY::x).summaryStatistics();
        yStats = input.map().keySet().stream().mapToLong(XY::y).summaryStatistics();
    }

    long part1() throws IOException {
        return tracePath(this::wrapAround);
    }

    long part2() throws IOException {
        return tracePath(this::asCube);
    }

    private long tracePath(Function<Position, Position> wrappingStrategy) {
        long startX = input.map().entrySet().stream()
                .filter(e -> e.getValue().equals('.'))
                .map(Map.Entry::getKey)
                .filter(e -> e.y() == 1)
                .mapToLong(XY::x)
                .min().orElseThrow();
        Position currentPosition = new Position(new XY(startX, 1), RIGHT);

        input.map().put(
                currentPosition.coordinates(),
                currentPosition.direction().asChar());

        for (String instruction : input.instructions()) {
            if (NUMBER.matcher(instruction).matches()) {
                currentPosition = moveIntoDirection(currentPosition, Long.parseLong(instruction), wrappingStrategy);
            } else {
                currentPosition = new Position(
                        currentPosition.coordinates(),
                        instruction.equals("L") ? currentPosition.direction().left() : currentPosition.direction().right());
                input.map().put(currentPosition.coordinates(), currentPosition.direction().asChar());
            }
        }
        return 1000L * currentPosition.coordinates().y() + 4 * currentPosition.coordinates().x() + currentPosition.direction().asInt();
    }

    private Position moveIntoDirection(Position from, long numberOfSteps, Function<Position, Position> wrappingStrategy) {
        Position nextTile = from;
        while (numberOfSteps-- > 0) {
            Position previous = nextTile;
            nextTile = getNextTile(nextTile, wrappingStrategy);
            if (input.map().getOrDefault(nextTile.coordinates(), ' ').equals('#')) return previous;
            input.map().put(nextTile.coordinates(), nextTile.direction().asChar());
        }
        return nextTile;
    }

    private Position getNextTile(Position currentPosition, Function<Position, Position> wrappingStrategy) {
        Function<XY, XY> moveFunction = switch (currentPosition.direction()) {
            case RIGHT -> XY::moveRight2;
            case LEFT -> XY::moveLeft2;
            case DOWN -> XY::moveDown2;
            case UP -> XY::moveUp2;
        };
        XY nextCoordinate = moveFunction.apply(currentPosition.coordinates());
        if (input.map().containsKey(nextCoordinate)) {
            return new Position(nextCoordinate, currentPosition.direction());
        }
        return wrappingStrategy.apply(currentPosition);
    }

    private Position wrapAround(Position position) {
        return switch (position.direction()) {
            case RIGHT -> new Position(
                    new XY(
                            input.map().keySet().stream().filter(xy -> xy.y() == position.coordinates().y()).mapToLong(XY::x).min().orElseThrow(),
                            position.coordinates().y()),
                    position.direction());
            case LEFT -> new Position(
                    new XY(
                            input.map().keySet().stream().filter(xy -> xy.y() == position.coordinates().y()).mapToLong(XY::x).max().orElseThrow(),
                            position.coordinates().y()),
                    position.direction());
            case DOWN -> new Position(
                    new XY(
                            position.coordinates().x(),
                            input.map().keySet().stream().filter(xy -> xy.x() == position.coordinates().x()).mapToLong(XY::y).min().orElseThrow()),
                    position.direction());
            case UP -> new Position(
                    new XY(
                            position.coordinates().x(),
                            input.map().keySet().stream().filter(xy -> xy.x() == position.coordinates().x()).mapToLong(XY::y).max().orElseThrow()),
                    position.direction());
        };
    }

    private Position asCube(Position position) {
        if (position.direction() == RIGHT) {
            if (getWall(position.coordinates()).equals('B')) {
                return rightToRight(position.coordinates(), 'B', 'E');
            }
            if (getWall(position.coordinates()).equals('C')) {
                return rightToBottom(position.coordinates(), 'C', 'B');
            }
            if (getWall(position.coordinates()).equals('E')) {
                return rightToRight(position.coordinates(), 'E', 'B');
            }
            if (getWall(position.coordinates()).equals('F')) {
                return rightToBottom(position.coordinates(), 'F', 'E');
            }
            throw new RuntimeException();
        }
        if (position.direction() == LEFT) {
            if (getWall(position.coordinates()).equals('A')) {
                return leftToLeft(position.coordinates(), 'A', 'D');
            }
            if (getWall(position.coordinates()).equals('C')) {
                return leftToTop(position.coordinates(), 'C', 'D');
            }
            if (getWall(position.coordinates()).equals('D')) {
                return leftToLeft(position.coordinates(), 'D', 'A');
            }
            if (getWall(position.coordinates()).equals('F')) {
                return leftToTop(position.coordinates(), 'F', 'A');
            }
            throw new RuntimeException();
        }
        if (position.direction() == DOWN) {
            if (getWall(position.coordinates()).equals('B')) {
                return bottomToRight(position.coordinates(), 'B', 'C');
            }
            if (getWall(position.coordinates()).equals('E')) {
                return bottomToRight(position.coordinates(), 'E', 'F');
            }
            if (getWall(position.coordinates()).equals('F')) {
                return bottomToTop(position.coordinates(), 'F', 'B');
            }
            throw new RuntimeException();
        }
        if (position.direction() == UP) {
            if (getWall(position.coordinates()).equals('A')) {
                return upToLeft(position.coordinates(), 'A', 'F');
            }
            if (getWall(position.coordinates()).equals('B')) {
                return upToBottom(position.coordinates(), 'B', 'F');
            }
            if (getWall(position.coordinates()).equals('D')) {
                return upToLeft(position.coordinates(), 'D', 'C');
            }
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }

    Character getWall(XY xy) {
        return WALLS.entrySet().stream().filter(e -> e.getValue().get(0).x() <= xy.x() && e.getValue().get(1).x() >= xy.x()
                        && e.getValue().get(0).y() <= xy.y() && e.getValue().get(1).y() >= xy.y())
                .findFirst()
                .orElseThrow()
                .getKey();
    }

    @NotNull
    private Position upToBottom(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall) + getXRelative(currentPosition, fromWall),
                        maxY(toWall)),
                Direction.UP);
    }

    @NotNull
    private Position upToLeft(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall),
                        minY(toWall) + getXRelative(currentPosition, fromWall)),
                RIGHT);
    }

    @NotNull
    private Position bottomToTop(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall) + getXRelative(currentPosition, fromWall),
                        minY(toWall)),
                Direction.DOWN);
    }

    @NotNull
    private Position bottomToRight(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        maxX(toWall),
                        minY(toWall) + getXRelative(currentPosition, fromWall)),
                Direction.LEFT);
    }

    @NotNull
    private Position leftToTop(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall) + getYRelative(currentPosition, fromWall),
                        minY(toWall)),
                Direction.DOWN);
    }

    @NotNull
    private Position leftToLeft(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall),
                        maxY(toWall) - getYRelative(currentPosition, fromWall)),
                RIGHT);
    }

    @NotNull
    private Position rightToRight(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        maxX(toWall),
                        maxY(toWall) - getYRelative(currentPosition, fromWall)),
                Direction.LEFT);
    }

    @NotNull
    private Position rightToBottom(XY currentPosition, char fromWall, char toWall) {
        return new Position(
                new XY(
                        minX(toWall) + getYRelative(currentPosition, fromWall),
                        maxY(toWall)),
                Direction.UP);
    }

    private long minY(char wall) {
        return WALLS.get(wall).get(0).y();
    }

    private long minX(char wall) {
        return WALLS.get(wall).get(0).x();
    }

    private long maxY(char wall) {
        return WALLS.get(wall).get(1).y();
    }

    private long maxX(char wall) {
        return WALLS.get(wall).get(1).x();
    }

    private long getXRelative(XY currentPosition, char wall) {
        return currentPosition.x() - minX(wall);
    }

    private long getYRelative(XY currentPosition, char wall) {
        return currentPosition.y() - minY(wall);
    }

    public void print() {
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                System.out.print(input.map().getOrDefault(new XY(x, y), ' '));
            }
            System.out.println();
        }
    }

    public void printWalls() {
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                XY position = new XY(x, y);
                if (input.map().containsKey(position)) {
                    System.out.print(getWall(position));
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }


}