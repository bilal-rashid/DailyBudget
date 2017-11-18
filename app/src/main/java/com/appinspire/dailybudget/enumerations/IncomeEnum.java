package com.appinspire.dailybudget.enumerations;

import com.appinspire.dailybudget.R;

/**
 * Created by Bilal Rashid on 10/19/2017.
 */

public enum IncomeEnum {
    BUSINESS("BUSINESS", R.drawable.icon_business),
    JOB("JOB",R.drawable.icon_job),
    FREELANCER("FREE LANCE",R.drawable.icon_freelance),
    PROPERTY_RENT("PROPERTY RENT",R.drawable.icon_own_rent),
    OTHER("OTHER",R.drawable.icon_other_income);

    private int id;
    private String name;

    IncomeEnum(String name,int id) {
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