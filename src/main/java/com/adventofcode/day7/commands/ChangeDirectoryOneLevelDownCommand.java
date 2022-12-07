package com.adventofcode.day7.commands;

import com.adventofcode.day7.Dir;
import org.jetbrains.annotations.Nullable;

public class ChangeDirectoryOneLevelDownCommand extends ChangeDirectoryCommand {

    private final String dirName;

    public ChangeDirectoryOneLevelDownCommand(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public Dir execute(@Nullable Dir dir) {
        if (dir == null) {
            dir = new Dir(dirName);
        } else {
            final Dir current = dir;
            dir = dir.children().stream()
                    .filter(Dir.class::isInstance)
                    .map(Dir.class::cast)
                    .filter(c -> c.name().equals(dirName))
                    .findFirst().orElseGet(() -> new Dir(current, dirName));
        }
        return dir;
    }
}
