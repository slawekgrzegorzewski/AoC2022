package com.adventofcode.day7.printer;

import com.adventofcode.day7.Dir;
import com.adventofcode.day7.File;
import com.adventofcode.day7.FilesSystemElement;

public interface Printer {
    default void print(FilesSystemElement filesSystemElement, int level) {
        if (filesSystemElement instanceof Dir)
            print((Dir) filesSystemElement, level);
        else
            print((File) filesSystemElement, level);
    }

    void print(Dir filesSystemElement, int level);

    void print(File filesSystemElement, int level);
}
