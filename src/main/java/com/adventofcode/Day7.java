package com.adventofcode;

import com.adventofcode.day7.Dir;
import com.adventofcode.day7.Interpreter;
import com.adventofcode.day7.commands.ChangeDirectoryToRootCommand;
import com.adventofcode.day7.commands.Command;
import com.adventofcode.day7.filter.FilterImpl;
import com.adventofcode.day7.printer.PrinterImpl;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;

public class Day7 {

    private final Dir root;

    public Day7() throws IOException {
        List<String> input = Input.day7("/day7");
        Dir currentDir = null;
        for (Command command : new Interpreter(input).commands()) {
            currentDir = command.execute(currentDir);
        }
        root = new ChangeDirectoryToRootCommand().execute(currentDir);
    }

    long part1() {
        new PrinterImpl().print(root, 0);
        return new FilterImpl().filter(
                        root,
                        d -> d.size() <= 100000L)
                .stream().mapToLong(Dir::size).sum();
    }

    long part2() {
        long totalCapacity = 70000000L;
        long updateSize = 30000000L;
        long currentTakenSpace = root.size();
        return new FilterImpl().filter(
                        root,
                        d -> d.size() >= updateSize - totalCapacity + currentTakenSpace)
                .stream()
                .mapToLong(Dir::size)
                .min()
                .orElseThrow();
    }

}


