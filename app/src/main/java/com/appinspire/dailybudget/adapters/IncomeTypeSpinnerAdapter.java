package com.appinspire.dailybudget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.enumerations.IncomeEnum;
import com.appinspire.dailybudget.utils.AppUtils;

/**
 * Created by Bilal Rashid on 10/22/2017.
 */

public class IncomeTypeSpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    public IncomeTypeSpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return IncomeEnum.values().length;
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
        icon.setImageDrawable(AppUtils.getColorDrawable(IncomeEnum.values()[i].getIconId(),context));
        //icon.setImageResource(IncomeEnum.values()[i].getIconId());
        names.setText(IncomeEnum.values()[i].getName());
        return view;
    }
}