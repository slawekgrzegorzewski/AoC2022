package com.adventofcode.day7.commands;

import com.adventofcode.day7.Dir;
import com.adventofcode.day7.File;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListDirectoryCommand implements Command {

    private final List<String> listResult;

    public ListDirectoryCommand(List<String> listResult) {
        this.listResult = listResult;
    }

    @Override
    public Dir execute(@NotNull Dir dir) {
        listResult.forEach(
                line -> {
                    if (line.startsWith("dir")) {
                        dir.add(new Dir(dir, line.substring(line.indexOf(' ') + 1)));
                    } else {
                        String[] parts = line.split(" ");
                        dir.add(new File(Long.parseLong(parts[0]), parts[1]));
                    }
                });
        return dir;
    }
}
