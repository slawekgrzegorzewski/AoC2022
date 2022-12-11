package com.adventofcode.day11;

public class NumberForDebugging implements Number {
    private final RegularNumber regularNumber;
    private final DividersNumber dividersNumber;

    public NumberForDebugging(int input) {
        this.regularNumber = new RegularNumber(input);
        this.dividersNumber = new DividersNumber(input);
    }

    public void add(int add) {
        this.regularNumber.add(add);
        this.dividersNumber.add(add);
    }

    public void multiply(int multiply) {
        this.regularNumber.multiply(multiply);
        this.dividersNumber.multiply(multiply);
    }

    public void divide(int divideBy) {
        this.regularNumber.divide(divideBy);
        this.dividersNumber.divide(divideBy);
    }

    public void square() {
        this.regularNumber.square();
        this.dividersNumber.square();
    }

    public boolean isDivisibleBy(int divisior) {
        boolean divisibleBy1 = this.regularNumber.isDivisibleBy(divisior);
        boolean divisibleBy = this.dividersNumber.isDivisibleBy(divisior);
        if(divisibleBy != divisibleBy1){
            System.out.println("a");
        }
        return divisibleBy;
    }
}
