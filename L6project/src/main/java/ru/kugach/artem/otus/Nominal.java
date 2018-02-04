package ru.kugach.artem.otus;

public enum Nominal {
    FIVE_THOUSANDS(5000),
    TWO_THOUSANDS(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100);

    private int value;

    Nominal(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
