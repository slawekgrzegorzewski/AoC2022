package com.adventofcode.day7.printer;

import com.adventofcode.day7.Dir;
import com.adventofcode.day7.File;

public class PrinterImpl implements Printer {

    @Override
    public void print(Dir dir, int level) {
        System.out.printf("%s- %s (dir, size=%d)\n", "\t".repeat(level), dir.name(), dir.size());
        dir.children().forEach(c -> this.print(c, level + 1));
    }

    @Override
    public void print(File file, int level) {
        System.out.printf("%s- %s (file, size=%d)\n", "\t".repeat(level), file.name(), file.size());
    }
}
