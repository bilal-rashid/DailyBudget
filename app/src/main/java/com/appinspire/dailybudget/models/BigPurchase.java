package com.appinspire.dailybudget.models;

/**
 * Created by Bilal Rashid on 11/9/2017.
 */

public class BigPurchase {
    public double amount;
    public double progress;
    public double percent;
    public String type;
    public int icon;
    public boolean completed;
    public String tag;

    @Override
    public boolean equals(Object obj) {
        BigPurchase purchase = (BigPurchase) obj;
        if (((BigPurchase) obj).amount == this.amount &&
                ((BigPurchase) obj).percent == this.percent &&
                ((BigPurchase) obj).progress== this.progress &&
                ((BigPurchase) obj).type.equals(this.type) &&
                ((BigPurchase) obj).tag.equals(this.tag) &&
                ((BigPurchase) obj).completed == this.completed &&
                ((BigPurchase) obj).icon == this.icon) {
            return true;
        }
        return false;
    }
}
