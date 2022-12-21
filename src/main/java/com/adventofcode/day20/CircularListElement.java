package com.adventofcode.day20;

public class CircularListElement {
    private final long value;
    private CircularListElement next;
    private CircularListElement previous;

    public CircularListElement(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public CircularListElement next() {
        return next;
    }

    public CircularListElement setNext(CircularListElement next) {
        this.next = next;
        return this;
    }

    public CircularListElement previous() {
        return previous;
    }

    public CircularListElement setPrevious(CircularListElement previous) {
        this.previous = previous;
        return this;
    }

    public void moveNext() {
        CircularListElement previous = previous();
        CircularListElement next = next();
        CircularListElement nextNext = next().next();

        previous.setNext(next);
        next.setPrevious(previous);
        next.setNext(this);
        this.setPrevious(next);
        this.setNext(nextNext);
        nextNext.setPrevious(this);
    }

    public void moveBack() {
        CircularListElement previousPrevious = previous().previous();
        CircularListElement previous = previous();
        CircularListElement next = next();

        previousPrevious.setNext(this);
        this.setPrevious(previousPrevious);
        this.setNext(previous);
        previous.setPrevious(this);
        previous.setNext(next);
        next.setPrevious(previous);
    }
}
