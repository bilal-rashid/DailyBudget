package com.appinspire.dailybudget.utils;

import android.content.Context;
import android.text.TextUtils;

import com.appinspire.dailybudget.models.Income;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 10/28/2017.
 */

public class Database {
    public static void saveIncome(Context context, Income item) {
        List<Income> list = getIncomeList(context);
        list.add(item);
        saveIncomeList(context,list);

    }
    public static void RemoveIncome(Context context, Income item) {
        List<Income> list = getIncomeList(context);
        list.remove(item);
        saveIncomeList(context,list);

    }

    public static void saveIncomeList(Context context, List<Income> items) {
        if (items != null) {
            PrefUtils.persistString(context, Constants.INCOME_LIST, GsonUtils.toJson(items));
        }
    }

    public static List<Income> getIncomeList(Context context) {
        String json = PrefUtils.getString(context, Constants.INCOME_LIST);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(PrefUtils.getString(context, Constants.INCOME_LIST), new TypeToken<List<Income>>() {
        }.getType());
    }

    public static void removeIncomeList(Context context) {
        PrefUtils.remove(context, Constants.INCOME_LIST);
    }
}
