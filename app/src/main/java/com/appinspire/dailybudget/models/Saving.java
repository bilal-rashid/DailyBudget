package com.appinspire.dailybudget.models;

import com.appinspire.dailybudget.enumerations.NumberComparison;

/**
 * Created by Bilal Rashid on 10/29/2017.
 */

public class Saving {
    public double savings;
    public int year;
    public int month;
    public int day;

    public Saving(double savings,int year,int month,int day){
        this.savings=savings;
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public int compare(Saving item){
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

    @Override
    public boolean equals(Object obj) {
        Saving saving = (Saving) obj;
        if(((Saving) obj).year==this.year &&
                ((Saving) obj).day==this.day &&
                ((Saving) obj).month==(this.month) ){
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.year+(this.month*8)+(this.day*13);
    }

}
