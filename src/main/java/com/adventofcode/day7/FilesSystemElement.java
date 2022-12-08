package com.adventofcode.day7;

import java.util.List;

public interface FilesSystemElement {
    String name();

    long size();

    List<FilesSystemElement> children();

    void contentChanged();

}
