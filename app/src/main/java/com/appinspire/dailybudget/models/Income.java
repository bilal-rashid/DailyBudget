package com.appinspire.dailybudget.models;

import com.appinspire.dailybudget.enumerations.NumberComparison;

/**
 * Created by Bilal Rashid on 10/19/2017.
 */

public class Income {
    public String type;
    public int icon;
    public String tag;
    public double income;
    public int year;
    public int month;
    public int day;

    @Override
    public boolean equals(Object obj) {
        Income income = (Income) obj;
        if(((Income) obj).year==this.year &&
                ((Income) obj).day==this.day &&
                ((Income) obj).type.equals(this.type) &&
                ((Income) obj).tag.equals(this.tag) &&
                ((Income) obj).income==this.income &&
                ((Income) obj).month==this.month&&
                ((Income) obj).icon==this.icon){
            return true;
        }
        return false;
    }
    public int compare(Income item){
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
