package com.adventofcode.day7;

import java.util.List;

public class File implements FilesSystemElement {
    private final long size;
    private final String name;

    public File(long size, String name) {
        this.size = size;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public List<FilesSystemElement> children() {
        return List.of();
    }
}
