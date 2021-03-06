package com.appinspire.dailybudget.enumerations;

import com.appinspire.dailybudget.R;

/**
 * Created by Bilal Rashid on 12/5/2017.
 */

public enum WishListEnum {
//    HOME_RENT("RENT",R.drawable.icon_home_rent),
//    FUEL("FUEL",R.drawable.icon_fuel),
//    FOOD("FOOD",R.drawable.icon_food),
//    TRANSPORT("TRANSPORT",R.drawable.icon_transport),
    ENTERTAINMENT("ENTERTAINMENT",R.drawable.icon_entertainment),
    CONSOLE("Gaming Console",R.drawable.console),
    SPORTS("SPORTS",R.drawable.icon_sports),
    MUSIC("MUSICAL INSTRUMENT",R.drawable.guitar),
    DRONE("QUADCOPTER",R.drawable.drone),
//    HEALTH("HEALTH",R.drawable.icon_health),
//    TAXI("TAXI",R.drawable.icon_taxi),
    CLOTHES("CLOTHES",R.drawable.icon_clothing),
//    GIFTS("GIFTS",R.drawable.icon_gift),
//    GROCERY("GROCERY",R.drawable.icon_grocery),
//    RESTAURANT("RESTAURANT",R.drawable.icon_restaurant),
//    SHOPPING("SHOPPING",R.drawable.icon_shopping),
//    ELECTRIC_BILL("Electricity BILL",R.drawable.icon_electric_bill),
//    GAS_BILL("Gas BILL",R.drawable.icon_gas_bill),
//    PHONE_BILL("Phone BILL",R.drawable.icon_phone_bill),
//    TV_BILL("TV BILL",R.drawable.icon_tv_bill),
//    CREDIT_CARD("CREDIT CARD",R.drawable.icon_credit_card_bill),
//    INTERNET_BILL("INTERNET BILL",R.drawable.icon_internet_bill),
    BICYCLE ("BICYCLE",R.drawable.bicycle),
    BIKE("MOTOR BIKE",R.drawable.motor_bike),
    CAR ("CAR",R.drawable.car),
    HOUSE("House",R.drawable.own_house),
    Land("Land",R.drawable.land),
    REAL_ESTATE("Real Estate",R.drawable.real_estate),
//    VEHICLE_MAINTENANCE ("VEHICLE MAINTENANCE",R.drawable.icon_maintenance),
    OTHER ("OTHER",R.drawable.icon_other_income);

    private int id;
    private String name;

    WishListEnum(String name,int id) {
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
