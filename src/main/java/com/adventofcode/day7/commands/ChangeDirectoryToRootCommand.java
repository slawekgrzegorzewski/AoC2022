package com.adventofcode.day7.commands;

import com.adventofcode.day7.Dir;
import org.jetbrains.annotations.Nullable;

public class ChangeDirectoryToRootCommand extends ChangeDirectoryCommand {

    @Override
    public Dir execute(@Nullable Dir dir) {
        if (dir == null) {
            return new Dir("/");
        }
        while (dir.parent().isPresent()) {
            dir = dir.parent().get();
        }
        return dir;
    }


}
