package com.appinspire.dailybudget.enumerations;

/**
 * Created by Bilal Rashid on 10/29/2017.
 */

public enum NumberComparison {
    GREATER_THAN(1),
    LESS_THAN(-1),
    EQUAL(0);

    private final int id;

    NumberComparison(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
