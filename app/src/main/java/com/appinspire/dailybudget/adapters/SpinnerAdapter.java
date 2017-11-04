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
import com.appinspire.dailybudget.models.Expense;
import com.appinspire.dailybudget.utils.AppUtils;

/**
 * Created by Bilal Rashid on 10/22/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    int spinnerType;

    public SpinnerAdapter(Context applicationContext, int spinnerType) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        this.spinnerType = spinnerType;
    }

    @Override
    public int getCount() {
        if(spinnerType == SpinnerTypeEnum.INCOME.getValue())
            return IncomeEnum.values().length;
        else if(spinnerType == SpinnerTypeEnum.EXPENSE.getValue())
            return ExpenseEnum.values().length;
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
        view = inflter.inflate(R.layout.item_income_type_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.image_type_icon);
        TextView names = (TextView) view.findViewById(R.id.text_income_type);
        if(spinnerType == SpinnerTypeEnum.EXPENSE.getValue()){
            icon.setImageDrawable(AppUtils.getColorDrawable(ExpenseEnum.values()[i].getIconId(), context,false));
            names.setText(ExpenseEnum.values()[i].getName());
        }else if(spinnerType == SpinnerTypeEnum.INCOME.getValue()){
            icon.setImageDrawable(AppUtils.getColorDrawable(IncomeEnum.values()[i].getIconId(), context,false));
            names.setText(IncomeEnum.values()[i].getName());
        }
        return view;
    }
}