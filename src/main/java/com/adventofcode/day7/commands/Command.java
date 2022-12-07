package com.adventofcode.day7.commands;

import com.adventofcode.day7.Dir;

public interface Command {
    Dir execute(Dir dir);
}
