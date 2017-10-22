package com.appinspire.dailybudget.enumerations;

/**
 * Created by Bilal Rashid on 10/18/2017.
 */

public enum ExpenseEnum {
    HOME_RENT("RENT",1),
    FUEL("FUEL",2),
    FOOD("FOOD",3),
    TRANSPORT("TRANSPORT",4),
    ENTERTAINMENT("ENTERTAINMENT",5),
    SPORTS("SPORTS",6),
    HEALTH("HEALTH",7),
    TAXI("TAXI",8),
    CLOTHES("CLOTHES",9),
    GIFTS("GIFTS",10),
    GROCERY("GROCERY",11),
    RESTAURANT("RESTAURANT",12),
    SHOPPING("SHOPPING",12),
    ELECTRIC_BILL("Electricity BILL",14),
    GAS_BILL("Gas BILL",15),
    PHONE_BILL("Phone BILL",16),
    TV_BILL("TV BILL",17),
    CREDIT_CARD("CREDIT CARD",18),
    INTERNET_BILL("INTERNET BILL",19),
    VEHICLE_MAINTENANCE ("VEHICLE MAINTENANCE",20);

    private int id;
    private String name;

    ExpenseEnum(String name,int id) {
        this.id = id;
        this.name = name;
    }

    public int getIconId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
