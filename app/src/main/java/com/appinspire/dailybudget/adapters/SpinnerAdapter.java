package com.appinspire.dailybudget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.enumerations.ExpenseEnum;
import com.appinspire.dailybudget.enumerations.IncomeEnum;
import com.appinspire.dailybudget.enumerations.SpinnerTypeEnum;
import com.appinspire.dailybudget.enumerations.WishListEnum;
import com.appinspire.dailybudget.models.Expense;
import com.appinspire.dailybudget.utils.AppUtils;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by Bilal Rashid on 10/22/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    int spinnerType;
    ArrayList<Currency> currencies;

    public SpinnerAdapter(Context applicationContext, int spinnerType) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        this.spinnerType = spinnerType;
        if(spinnerType == SpinnerTypeEnum.CURRENCY.getValue()){
            currencies = new ArrayList<Currency>(Currency.getAvailableCurrencies());

        }
    }

    @Override
    public int getCount() {
        if(spinnerType == SpinnerTypeEnum.INCOME.getValue())
            return IncomeEnum.values().length;
        else if(spinnerType == SpinnerTypeEnum.EXPENSE.getValue())
            return ExpenseEnum.values().length;
        else if(spinnerType == SpinnerTypeEnum.CURRENCY.getValue())
            return Currency.getAvailableCurrencies().size();
        else if(spinnerType == SpinnerTypeEnum.WISHLIST.getValue())
            return WishListEnum.values().length;
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(spinnerType == SpinnerTypeEnum.CURRENCY.getValue()){
            view = inflter.inflate(R.layout.item_currency, null);
            TextView symbol = (TextView) view.findViewById(R.id.text_symbol);
            TextView name = (TextView) view.findViewById(R.id.text_currency);
            name.setText(currencies.get(i).getDisplayName());
            symbol.setText(currencies.get(i).getSymbol());
            return view;
        }else {
            view = inflter.inflate(R.layout.item_income_type_spinner, null);
            ImageView icon = (ImageView) view.findViewById(R.id.image_type_icon);
            TextView names = (TextView) view.findViewById(R.id.text_income_type);
            if (spinnerType == SpinnerTypeEnum.EXPENSE.getValue()) {
                icon.setImageDrawable(AppUtils.getColorDrawable(ExpenseEnum.values()[i].getIconId(), context, false));
                names.setText(ExpenseEnum.values()[i].getName());
            } else if (spinnerType == SpinnerTypeEnum.INCOME.getValue()) {
                icon.setImageDrawable(AppUtils.getColorDrawable(IncomeEnum.values()[i].getIconId(), context, false));
                names.setText(IncomeEnum.values()[i].getName());
            } else if (spinnerType == SpinnerTypeEnum.WISHLIST.getValue()){
                icon.setImageDrawable(AppUtils.getColorDrawable(WishListEnum.values()[i].getIconId(), context, false));
                names.setText(WishListEnum.values()[i].getName());
            }
            return view;
        }
    }
}