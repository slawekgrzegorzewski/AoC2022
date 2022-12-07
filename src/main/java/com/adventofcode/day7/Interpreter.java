package com.adventofcode.day7;

import com.adventofcode.day7.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Interpreter {
    private final LinkedList<String> input;

    public Interpreter(List<String> input) {
        this.input = new LinkedList<>(input);
    }

    public List<Command> commands() {
        List<Command> commands = new ArrayList<>();
        while (!input.isEmpty()) {
            String line = input.removeFirst();
            commands.add(switch (line) {
                case "$ cd .." -> new ChangeDirectoryOneLevelUpCommand();
                case "$ cd /" -> new ChangeDirectoryToRootCommand();
                case "$ ls" -> new ListDirectoryCommand(getLsResult());
                default -> new ChangeDirectoryOneLevelDownCommand(line.substring(line.lastIndexOf(' ') + 1));
            });
        }
        return commands;
    }

    private List<String> getLsResult() {
        List<String> result = new ArrayList<>();
        while (!input.isEmpty() && !input.getFirst().startsWith("$")) {
            result.add(input.removeFirst());
        }
        return result;
    }
}
