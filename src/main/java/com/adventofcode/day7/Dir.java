package com.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dir implements FilesSystemElement {
    private final Dir parent;
    private final List<FilesSystemElement> children = new ArrayList<>();
    private final String name;

    public Dir(String name) {
        this.parent = null;
        this.name = name;
    }

    public Dir(Dir parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public Optional<Dir> parent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size() {
        return children.stream().mapToLong(FilesSystemElement::size).sum();
    }

    @Override
    public List<FilesSystemElement> children() {
        return List.copyOf(children);
    }

    public void add(FilesSystemElement dir) {
        this.children.add(dir);
    }
}
