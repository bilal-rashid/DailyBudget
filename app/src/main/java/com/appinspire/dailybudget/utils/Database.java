package com.appinspire.dailybudget.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.appinspire.dailybudget.enumerations.NumberComparison;
import com.appinspire.dailybudget.models.Expense;
import com.appinspire.dailybudget.models.Income;
import com.appinspire.dailybudget.models.Saving;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Bilal Rashid on 10/28/2017.
 */

public class Database {
    public static void saveIncome(Context context, Income item) {
        List<Income> list = getIncomeList(context);
        list.add(item);
        processSaving(context, item);
        Collections.sort(list, new Comparator<Income>() {
            @Override
            public int compare(Income income, Income t1) {
                return t1.compare(income);
            }
        });

        saveIncomeList(context, list);

    }

    private static void processSaving(Context context, Income item) {
        List<Saving> list = getSavingList(context);
        Saving saving;// = new Saving(item.income,item.year,item.month,item.day);
        if (list.size() < 1) {
            Log.d("TAAAG", "Income 1st item");
            saving = new Saving(item.income, item.year, item.month, item.day);
            list.add(saving);
            saveSavingList(context, list);
        } else if (list.size() >= 1) {
            saving = new Saving(item.income + list.get(list.size() - 1).savings, item.year, item.month, item.day);
            if (saving.compare(list.get(list.size() - 1)) == NumberComparison.EQUAL.getValue()) {
                Log.d("TAAAG", "Income equal date");
                list.remove(list.size() - 1);
                list.add(saving);
                saveSavingList(context, list);
            } else if (saving.compare(list.get(list.size() - 1)) == NumberComparison.LESS_THAN.getValue()) {
                Log.d("TAAAG", "Income greater date");
                list.add(saving);
                saveSavingList(context, list);
            } else if (saving.compare(list.get(list.size() - 1)) == NumberComparison.GREATER_THAN.getValue()) {
                Log.d("TAAAG", "Income lesser date");


                if (list.contains(saving)) {
                    int pos = list.indexOf(saving);
                    list.get(pos).savings += item.income;
                    for (int i = pos + 1; i < list.size(); i++) {
                        list.get(i).savings += item.income;
                    }
                    saveSavingList(context, list);
                } else {
                    saving.savings = item.income;
                    list.add(saving);
                    Collections.sort(list, new Comparator<Saving>() {
                        @Override
                        public int compare(Saving saving, Saving t1) {
                            return t1.compare(saving);
                        }
                    });
                    int pos_saving = list.indexOf(saving);
                    if (pos_saving > 0) {
                        int prev_item = pos_saving - 1;
                        double new_saving = list.get(prev_item).savings + item.income;
                        list.get(pos_saving).savings = new_saving;
                        for (int i = pos_saving + 1; i < list.size(); i++) {
                            list.get(i).savings += item.income;
                        }
                        saveSavingList(context, list);

                    } else {
                        int next_pos = pos_saving + 1;
                        double new_saving = item.income;
                        list.get(pos_saving).savings = new_saving;
                        for (int i = pos_saving + 1; i < list.size(); i++) {
                            list.get(i).savings += item.income;
                        }
                        saveSavingList(context, list);

                    }

                }
            }

        }

    }


    public static void removeIncome(Context context, Income item) {
        List<Income> list = getIncomeList(context);
        list.remove(item);
        processRemoveIncome(context, item);
        saveIncomeList(context, list);

    }

    private static void processRemoveIncome(Context context, Income item) {
        List<Saving> list = getSavingList(context);
        Saving saving = new Saving(item.income, item.year, item.month, item.day);
        int pos = list.indexOf(saving);
        for (int i = pos; i < list.size(); i++) {
            list.get(i).savings -= item.income;
        }
        saveSavingList(context, list);
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
    ///////////////////expense//////////////////

    public static void saveExpense(Context context, Expense item) {
        List<Expense> list = getExpenseList(context);
        list.add(item);
        processSaving(context, item);
        Collections.sort(list, new Comparator<Expense>() {
            @Override
            public int compare(Expense expense, Expense t1) {
                return t1.compare(expense);
            }
        });
        saveExpenseList(context, list);

    }

    public static void removeExpense(Context context, Expense item) {
        List<Expense> list = getExpenseList(context);
        list.remove(item);
        processRemoveExpense(context, item);
        saveExpenseList(context, list);

    }

    private static void processRemoveExpense(Context context, Expense item) {
        List<Saving> list = getSavingList(context);
        Saving saving = new Saving(item.expense, item.year, item.month, item.day);
        int pos = list.indexOf(saving);
        for (int i = pos; i < list.size(); i++) {
            list.get(i).savings += item.expense;
        }
        saveSavingList(context, list);
    }

    private static void processSaving(Context context, Expense item) {
        List<Saving> list = getSavingList(context);
        Saving saving;// = new Saving(item.income,item.year,item.month,item.day);
        if (list.size() < 1) {
            Log.d("TAAAG", "1st item");
            saving = new Saving(-1 * item.expense, item.year, item.month, item.day);
            list.add(saving);
            saveSavingList(context, list);
        } else if (list.size() >= 1) {
            saving = new Saving(list.get(list.size() - 1).savings - item.expense, item.year, item.month, item.day);
            if (saving.compare(list.get(list.size() - 1)) == NumberComparison.EQUAL.getValue()) {
                Log.d("TAAAG", "equal date");
                list.remove(list.size() - 1);
                list.add(saving);
                saveSavingList(context, list);
            } else if (saving.compare(list.get(list.size() - 1)) == NumberComparison.LESS_THAN.getValue()) {
                Log.d("TAAAG", "greater date");
                list.add(saving);
                saveSavingList(context, list);
            } else if (saving.compare(list.get(list.size() - 1)) == NumberComparison.GREATER_THAN.getValue()) {
                Log.d("TAAAG", "lesser date " + saving.day + ",,," + list.get(list.size() - 1).day);


                if (list.contains(saving)) {
                    int pos = list.indexOf(saving);
                    list.get(pos).savings -= item.expense;
                    for (int i = pos + 1; i < list.size(); i++) {
                        list.get(i).savings -= item.expense;
                    }
                    saveSavingList(context, list);
                } else {
                    saving.savings = item.expense;
                    list.add(saving);
                    Collections.sort(list, new Comparator<Saving>() {
                        @Override
                        public int compare(Saving saving, Saving t1) {
                            return t1.compare(saving);
                        }
                    });
                    int pos_saving = list.indexOf(saving);
                    if (pos_saving > 0) {
                        int prev_item = pos_saving - 1;
                        double new_saving = list.get(prev_item).savings - item.expense;
                        list.get(pos_saving).savings = new_saving;
                        for (int i = pos_saving + 1; i < list.size(); i++) {
                            list.get(i).savings -= item.expense;
                        }
                        saveSavingList(context, list);

                    } else {
                        int next_pos = pos_saving + 1;
                        double new_saving = item.expense * (-1);
                        list.get(pos_saving).savings = new_saving;
                        for (int i = pos_saving + 1; i < list.size(); i++) {
                            list.get(i).savings -= item.expense;
                        }
                        saveSavingList(context, list);

                    }

                }
            }

        }

    }

    public static void saveExpenseList(Context context, List<Expense> items) {
        if (items != null) {
            PrefUtils.persistString(context, Constants.EXPENSE_LIST, GsonUtils.toJson(items));
        }
    }

    public static List<Expense> getExpenseList(Context context) {
        String json = PrefUtils.getString(context, Constants.EXPENSE_LIST);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(PrefUtils.getString(context, Constants.EXPENSE_LIST), new TypeToken<List<Expense>>() {
        }.getType());
    }

    public static void removeExpenseList(Context context) {
        PrefUtils.remove(context, Constants.EXPENSE_LIST);
    }

    /////////////////saving/////////////
    public static void saveSaving(Context context, Saving item) {
        List<Saving> list = getSavingList(context);
        list.add(item);
        saveSavingList(context, list);

    }

    public static void removeSaving(Context context, Saving item) {
        List<Saving> list = getSavingList(context);
        list.remove(item);
        saveSavingList(context, list);

    }

    public static void saveSavingList(Context context, List<Saving> items) {
        if (items != null) {
            PrefUtils.persistString(context, Constants.SAVING_LIST, GsonUtils.toJson(items));
        }
    }

    public static List<Saving> getSavingList(Context context) {
        String json = PrefUtils.getString(context, Constants.SAVING_LIST);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(PrefUtils.getString(context, Constants.SAVING_LIST), new TypeToken<List<Saving>>() {
        }.getType());
    }
    ////////////currency//////////////////
    public static void setCurrency(Context context,int position){
        PrefUtils.persistInt(context,Constants.CURRENCY,position);
    }
    public static int getCurrency(Context context){
        return PrefUtils.getInt(context,Constants.CURRENCY,0);
    }
}
