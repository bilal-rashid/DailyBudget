package com.appinspire.dailybudget.models;

import com.appinspire.dailybudget.enumerations.NumberComparison;

/**
 * Created by Bilal Rashid on 10/23/2017.
 */

public class Expense {
    public String type;
    public int icon;
    public String tag;
    public double expense;
    public int year;
    public int month;
    public int day;

    public int compare(Expense item){
        if(item.year>this.year)
            return NumberComparison.GREATER_THAN.getValue();
        else if(item.year<this.year)
            return NumberComparison.LESS_THAN.getValue();
        else {
            if(item.month>this.month)
                return NumberComparison.GREATER_THAN.getValue();
            else if(item.month<this.month)
                return NumberComparison.LESS_THAN.getValue();
            else {
                if(item.day>this.day)
                    return NumberComparison.GREATER_THAN.getValue();
                else if(item.day<this.day)
                    return NumberComparison.LESS_THAN.getValue();
                else
                    return NumberComparison.EQUAL.getValue();
            }
        }
    }
}