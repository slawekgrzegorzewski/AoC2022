package com.adventofcode;

import com.adventofcode.day23.Elf;
import com.adventofcode.input.Input;
import com.adventofcode.input.XY;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

public class Day23 {
    private final long numberOfStepWithNoMoves;
    private long numberOfEmptySpacesAfter10Steps;

    public Day23() throws IOException {
        Map<XY, Elf> elvesMap = Input.day23();
        int i = 1;
        while (singleRound(elvesMap) != 0) {
            if (i == 10) {
                LongSummaryStatistics xStats = elvesMap.keySet().stream().mapToLong(XY::x).summaryStatistics();
                LongSummaryStatistics yStats = elvesMap.keySet().stream().mapToLong(XY::y).summaryStatistics();
                numberOfEmptySpacesAfter10Steps = (xStats.getMax() - xStats.getMin() + 1) * (yStats.getMax() - yStats.getMin() + 1) - Input.day23().size();
            }
            i++;
        }
        numberOfStepWithNoMoves = i;
    }

    long part1() throws IOException {
        return numberOfEmptySpacesAfter10Steps;
    }

    long part2() throws IOException {
        return numberOfStepWithNoMoves;
    }

    private static int singleRound(Map<XY, Elf> elvesMap) {
        Map<XY, Map<XY, Elf>> propositionsToMoveToFrom = new HashMap<>();
        for (Map.Entry<XY, Elf> entry : elvesMap.entrySet()) {
            XY xy = entry.getKey();
            Elf elf = entry.getValue();
            elf.nextPosition(xy, elvesMap).ifPresent(nextXY ->
                    propositionsToMoveToFrom.computeIfAbsent(nextXY, key -> new HashMap<>()).put(xy, elf));
        }
        List<Map.Entry<XY, Map<XY, Elf>>> elvesToMove = propositionsToMoveToFrom.entrySet().stream()
                .filter(e -> e.getValue().size() == 1)
                .toList();
        elvesToMove.forEach(e -> {
            Map.Entry<XY, Elf> currentElfPosition = e.getValue().entrySet().iterator().next();
            elvesMap.remove(currentElfPosition.getKey());
            elvesMap.put(e.getKey(), currentElfPosition.getValue());
        });
        return elvesToMove.size();
    }

    private void print(Map<XY, Elf> elvesMap) {
        LongSummaryStatistics xStats = elvesMap.keySet().stream().mapToLong(XY::x).summaryStatistics();
        LongSummaryStatistics yStats = elvesMap.keySet().stream().mapToLong(XY::y).summaryStatistics();
        for (long y = yStats.getMin(); y <= yStats.getMax(); y++) {
            for (long x = xStats.getMin(); x <= xStats.getMax(); x++) {
                if (elvesMap.containsKey(new XY(x, y))) System.out.print("#");
                else System.out.print(".");
            }
            System.out.println();
        }
    }
}