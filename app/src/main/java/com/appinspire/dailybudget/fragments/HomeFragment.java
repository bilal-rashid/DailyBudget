package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.FrameActivity;
import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.SimpleFrameActivity;
import com.appinspire.dailybudget.enumerations.AnimationEnum;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.ActivityUtils;
import com.appinspire.dailybudget.utils.AppUtils;

/**
 * Created by Bilal Rashid on 10/10/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private ViewHolder mHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Home");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mHolder.card_income.setOnTouchListener(this);
        mHolder.card_expense.setOnTouchListener(this);
        mHolder.card_savings.setOnTouchListener(this);
        mHolder.card_purchase.setOnTouchListener(this);
        mHolder.card_reports.setOnTouchListener(this);
        mHolder.card_settings.setOnTouchListener(this);
        mHolder.card_reports.setOnClickListener(this);
        mHolder.card_settings.setOnClickListener(this);
        mHolder.card_income.setOnClickListener(this);
        mHolder.card_expense.setOnClickListener(this);
        mHolder.card_purchase.setOnClickListener(this);
        mHolder.card_savings.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_income:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        IncomeFragment.class.getName(), null);
                break;
            case R.id.card_expense:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        ExpensesFragment.class.getName(), null);
                break;
            case R.id.card_saving:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        SavingsFragment.class.getName(), null);
                break;
            case R.id.card_purchase:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        BigPurchasesFragment.class.getName(), null);
//                AppUtils.showSnackBar(getView(),"Coming Soon");
                break;
            case R.id.card_reports:
//                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
//                        ReportsFragment.class.getName(), null);
                AppUtils.showSnackBar(getView(),"Coming Soon");
                break;
            case R.id.card_setting:
                ActivityUtils.startActivity(getActivity(), SimpleFrameActivity.class,
                        SettingsFragment.class.getName(), null, AnimationEnum.VERTICAL);
                break;
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.card_income:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_income_color),
                        ContextCompat.getColor(getActivity(), R.color.card_income_color_pressed));
                break;
            case R.id.card_expense:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_expense_color),
                        ContextCompat.getColor(getActivity(), R.color.card_expense_color_pressed));
                break;
            case R.id.card_saving:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_saving_color),
                        ContextCompat.getColor(getActivity(), R.color.card_saving_color_pressed));
                break;
            case R.id.card_purchase:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_purchase_color),
                        ContextCompat.getColor(getActivity(), R.color.card_purchase_color_pressed));
                break;
            case R.id.card_reports:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_reports_color),
                        ContextCompat.getColor(getActivity(), R.color.card_dummy_color_pressed));
                break;
            case R.id.card_setting:
                animate(view, motionEvent, ContextCompat.getColor(getActivity(), R.color.card_setting_color),
                        ContextCompat.getColor(getActivity(), R.color.card_setting_color_pressed));
                break;
        }
        return false;
    }

    private void animate(View view, MotionEvent motionEvent, int color, int colorPressed) {
        CardView cardView = (CardView) view;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                cardView.setCardBackgroundColor(color);
                break;
            case MotionEvent.ACTION_CANCEL:
                cardView.setCardBackgroundColor(color);
                break;
            case MotionEvent.ACTION_DOWN:
                cardView.setCardBackgroundColor(colorPressed);
                break;
            case MotionEvent.ACTION_MOVE:
                cardView.setCardBackgroundColor(colorPressed);
                break;

        }
    }


    public static class ViewHolder {


        CardView card_income;
        CardView card_savings;
        CardView card_expense;
        CardView card_purchase;
        CardView card_reports;
        CardView card_settings;

        public ViewHolder(View view) {
            card_income = (CardView) view.findViewById(R.id.card_income);
            card_expense = (CardView) view.findViewById(R.id.card_expense);
            card_savings = (CardView) view.findViewById(R.id.card_saving);
            card_purchase = (CardView) view.findViewById(R.id.card_purchase);
            card_reports = (CardView) view.findViewById(R.id.card_reports);
            card_settings = (CardView) view.findViewById(R.id.card_setting);
        }

    }
}
