package com.appinspire.dailybudget.enumerations;

/**
 * Created by Bilal Rashid on 10/23/2017.
 */

public enum SpinnerTypeEnum {
    INCOME(0),
    EXPENSE(1),
    CURRENCY(2),
    WISHLIST(3);

    private final int id;

    SpinnerTypeEnum(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
