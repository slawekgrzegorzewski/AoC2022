package com.adventofcode.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Dir implements FilesSystemElement {
    private final Dir parent;
    private final List<FilesSystemElement> children = new ArrayList<>();
    private final String name;
    private final AtomicLong size = new AtomicLong(0L);

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
        return size.updateAndGet(value -> {
            if (value == 0L) {
                value = children.stream().mapToLong(FilesSystemElement::size).sum();
            }
            return value;
        });
    }

    @Override
    public List<FilesSystemElement> children() {
        return List.copyOf(children);
    }

    @Override
    public void contentChanged() {
        size.set(0L);
        parent().ifPresent(FilesSystemElement::contentChanged);
    }

    public void add(FilesSystemElement dir) {
        this.children.add(dir);
        this.contentChanged();
    }
}
