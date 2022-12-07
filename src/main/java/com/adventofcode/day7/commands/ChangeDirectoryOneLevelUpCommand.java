package com.adventofcode.day7.commands;

import com.adventofcode.day7.Dir;

public class ChangeDirectoryOneLevelUpCommand extends ChangeDirectoryCommand {

    @Override
    public Dir execute(Dir dir) {
        return dir.parent().orElseThrow();
    }

}
