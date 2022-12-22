package com.adventofcode;

import com.adventofcode.day14.XY;
import com.adventofcode.day22.Instruction;
import com.adventofcode.input.Input;
import com.adventofcode.input.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.regex.Pattern;

public class Day22 {
    private final static Pattern NUMBER = Pattern.compile("[0-9]+");
    private final Instruction input;
    private final LongSummaryStatistics xStats;
    private final LongSummaryStatistics yStats;

    public Day22() throws IOException {
        input = Input.day22();
        xStats = input.map().keySet().stream().mapToLong(XY::x).summaryStatistics();
        yStats = input.map().keySet().stream().mapToLong(XY::y).summaryStatistics();
    }

    long part1() throws IOException {
        //0 = >, 1 = v, 2 = <, 3 = ^
        int facing = 0;
        long x = input.map().entrySet().stream()
                .filter(e -> e.getValue().equals('.'))
                .map(Map.Entry::getKey)
                .filter(e -> e.y() == 1)
                .mapToLong(XY::x)
                .min().orElseThrow();
        XY currentPosition = new XY(x, 1);
        input.map().put(currentPosition, getFacingChar(facing));
        for (String instruction : input.instructions()) {
            if (NUMBER.matcher(instruction).matches()) {
                currentPosition = move(currentPosition, Long.parseLong(instruction), facing);
            } else {
                if (instruction.equals("L")) facing--;
                if (instruction.equals("R")) facing++;
                if (facing == -1) facing = 3;
                if (facing == 4) facing = 0;
                input.map().put(currentPosition, getFacingChar(facing));
            }
        }
        return 1000L * currentPosition.y() + 4 * currentPosition.x() + facing;
    }

    private XY move(XY currentPosition, long steps, int facing) {
        XY next = currentPosition;
        while (steps-- > 0) {
            XY previous = next;
            next = getNextTile(next, facing);
            if (input.map().getOrDefault(next, ' ').equals('#')) return previous;
            input.map().put(next, getFacingChar(facing));
        }
        return next;
    }

    private XY getNextTile(XY currentPosition, int facing) {
        if (facing == 0) {
            if (input.map().containsKey(currentPosition.moveRight2()))
                return currentPosition.moveRight2();
            long minX = input.map().keySet().stream().filter(xy -> xy.y() == currentPosition.y()).mapToLong(XY::x).min().orElseThrow();
            return new XY(minX, currentPosition.y());
        }
        if (facing == 2) {
            if (input.map().containsKey(currentPosition.moveLeft2()))
                return currentPosition.moveLeft2();
            long maxX = input.map().keySet().stream().filter(xy -> xy.y() == currentPosition.y()).mapToLong(XY::x).max().orElseThrow();
            return new XY(maxX, currentPosition.y());
        }
        if (facing == 1) {
            if (input.map().containsKey(currentPosition.moveDown2()))
                return currentPosition.moveDown2();
            long minY = input.map().keySet().stream().filter(xy -> xy.x() == currentPosition.x()).mapToLong(XY::y).min().orElseThrow();
            return new XY(currentPosition.x(), minY);
        }
        if (facing == 3) {
            if (input.map().containsKey(currentPosition.moveUp2()))
                return currentPosition.moveUp2();
            long maxY = input.map().keySet().stream().filter(xy -> xy.x() == currentPosition.x()).mapToLong(XY::y).max().orElseThrow();
            return new XY(currentPosition.x(), maxY);
        }
        throw new RuntimeException();
    }

    private Character getFacingChar(int facing) {
        return switch (facing) {
            case 0 -> '>';
            case 1 -> 'v';
            case 2 -> '<';
            case 3 -> '^';
            default -> throw new IllegalStateException("Unexpected value: " + facing);
        };
    }

    long part2() throws IOException {
        //0 = >, 1 = v, 2 = <, 3 = ^
        int facing = 0;
        long x = input.map().entrySet().stream()
                .filter(e -> e.getValue().equals('.'))
                .map(Map.Entry::getKey)
                .filter(e -> e.y() == 1)
                .mapToLong(XY::x)
                .min().orElseThrow();
        XY currentPosition = new XY(x, 1);
        input.map().put(currentPosition, getFacingChar(facing));
        for (String instruction : input.instructions()) {
            if (NUMBER.matcher(instruction).matches()) {
                currentPosition = move2(currentPosition, Long.parseLong(instruction), facing);
            } else {
                if (instruction.equals("L")) facing--;
                if (instruction.equals("R")) facing++;
                if (facing == -1) facing = 3;
                if (facing == 4) facing = 0;
                input.map().put(currentPosition, getFacingChar(facing));
            }
        }
        return 1000L * currentPosition.y() + 4 * currentPosition.x() + facing;
    }

    private XY move2(XY currentPosition, long steps, int facing) {
        XY next = currentPosition;
        while (steps-- > 0) {
            XY previous = next;
            Pair<XY, Integer> move = getNextTile2(next, facing);
            next = move.first();
            facing = move.second();
            if (input.map().getOrDefault(next, ' ').equals('#')) return previous;
            input.map().put(next, getFacingChar(facing));
        }
        return next;
    }

    private Pair<XY, Integer> getNextTile2(XY currentPosition, int facing) {
        if (facing == 0) {
            if (input.map().containsKey(currentPosition.moveRight2()))
                return new Pair<>(currentPosition.moveRight2(), facing);
            if (getWall(currentPosition).equals('B')) {
                return rightToLeft(currentPosition, 'B', 'E');
            }
            if (getWall(currentPosition).equals('C')) {
                return rightToBottom(currentPosition, 'C', 'B');
            }
            if (getWall(currentPosition).equals('E')) {
                return rightToLeft(currentPosition, 'E', 'B');
            }
            if (getWall(currentPosition).equals('F')) {
                return rightToBottom(currentPosition, 'F', 'E');
            }
            throw new RuntimeException();
        }
        if (facing == 2) {
            if (input.map().containsKey(currentPosition.moveLeft2()))
                return new Pair<>(currentPosition.moveLeft2(), facing);
            if (getWall(currentPosition).equals('A')) {
                return leftToRight(currentPosition, 'A', 'D');
            }
            if (getWall(currentPosition).equals('C')) {
                return leftToUp(currentPosition, 'C', 'D');
            }
            if (getWall(currentPosition).equals('D')) {
                return leftToRight(currentPosition, 'D', 'A');
            }
            if (getWall(currentPosition).equals('F')) {
                return leftToUp(currentPosition, 'F', 'A');
            }
            throw new RuntimeException();
        }
        if (facing == 1) {
            if (input.map().containsKey(currentPosition.moveDown2()))
                return new Pair<>(currentPosition.moveDown2(), facing);
            if (getWall(currentPosition).equals('B')) {
                return bottomToLeft(currentPosition, 'B', 'C');
            }
            if (getWall(currentPosition).equals('E')) {
                return bottomToLeft(currentPosition, 'E', 'F');
            }
            if (getWall(currentPosition).equals('F')) {
                return bottomToUp(currentPosition, 'F', 'B');
            }
            throw new RuntimeException();
        }
        if (facing == 3) {
            if (input.map().containsKey(currentPosition.moveUp2()))
                return new Pair<>(currentPosition.moveUp2(), facing);
            if (getWall(currentPosition).equals('A')) {
                return upToLeft(currentPosition, 'A', 'F');
            }
            if (getWall(currentPosition).equals('B')) {
                return upToBottom(currentPosition, 'B', 'F');
            }
            if (getWall(currentPosition).equals('D')) {
                return upToLeft(currentPosition, 'D', 'C');
            }
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }

    @NotNull
    private Pair<XY, Integer> upToBottom(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall) + getXRelative(currentPosition, fromWall),
                        maxY(toWall)),
                directions.get('^'));
    }

    @NotNull
    private Pair<XY, Integer> upToLeft(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall),
                        minY(toWall) + getXRelative(currentPosition, fromWall)),
                directions.get('>'));
    }

    @NotNull
    private Pair<XY, Integer> bottomToUp(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall) + getXRelative(currentPosition, fromWall),
                        minY(toWall)),
                directions.get('v'));
    }

    @NotNull
    private Pair<XY, Integer> bottomToLeft(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        maxX(toWall),
                        minY(toWall) + getXRelative(currentPosition, fromWall)),
                directions.get('<'));
    }

    @NotNull
    private Pair<XY, Integer> leftToUp(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall) + getYRelative(currentPosition, fromWall),
                        minY(toWall)),
                directions.get('v'));
    }

    @NotNull
    private Pair<XY, Integer> leftToRight(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall),
                        maxY(toWall) - getYRelative(currentPosition, fromWall)),
                directions.get('>'));
    }

    @NotNull
    private Pair<XY, Integer> rightToLeft(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        maxX(toWall),
                        maxY(toWall) - getYRelative(currentPosition, fromWall)),
                directions.get('<'));
    }

    @NotNull
    private Pair<XY, Integer> rightToBottom(XY currentPosition, char fromWall, char toWall) {
        return new Pair<>(
                new XY(
                        minX(toWall) + getYRelative(currentPosition, fromWall),
                        maxY(toWall)),
                directions.get('^'));
    }

    private long minY(char wall) {
        return walls.get(wall).get(0).y();
    }

    private long minX(char wall) {
        return walls.get(wall).get(0).x();
    }

    private long maxY(char wall) {
        return walls.get(wall).get(1).y();
    }

    private long maxX(char wall) {
        return walls.get(wall).get(1).x();
    }

    private long getXRelative(XY currentPosition, char wall) {
        return currentPosition.x() - minX(wall);
    }

    private long getYRelative(XY currentPosition, char wall) {
        return currentPosition.y() - minY(wall);
    }


    Map<Character, List<XY>> walls = Map.of(
            'A', List.of(new XY(51, 1), new XY(100, 50)),
            'B', List.of(new XY(101, 1), new XY(150, 50)),
            'C', List.of(new XY(51, 51), new XY(100, 100)),
            'D', List.of(new XY(1, 101), new XY(50, 150)),
            'E', List.of(new XY(51, 101), new XY(100, 150)),
            'F', List.of(new XY(1, 151), new XY(50, 200))
    );

    Map<Character, Integer> directions = Map.of(
            '>', 0,
            'v', 1,
            '<', 2,
            '^', 3
    );

    Character getWall(XY xy) {
        return walls.entrySet().stream().filter(e -> e.getValue().get(0).x() <= xy.x() && e.getValue().get(1).x() >= xy.x()
                        && e.getValue().get(0).y() <= xy.y() && e.getValue().get(1).y() >= xy.y())
                .findFirst()
                .orElseThrow()
                .getKey();
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