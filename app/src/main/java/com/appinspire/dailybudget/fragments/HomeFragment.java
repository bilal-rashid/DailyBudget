package com.appinspire.dailybudget.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appinspire.dailybudget.FrameActivity;
import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.ActivityUtils;

/**
 * Created by Bilal Rashid on 10/10/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener,View.OnTouchListener{
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
        mHolder.card_dummy.setOnTouchListener(this);
        mHolder.card_settings.setOnTouchListener(this);
//        mHolder.card_settings.setVisibility(View.INVISIBLE);
//        mHolder.button.setOnClickListener(this);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ActivityUtils.startActivity(getActivity(), FrameActivity.class,HomeFragment.class.getName(),null);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.card_income:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_income_color),
                        ContextCompat.getColor(getActivity(), R.color.card_income_color_pressed));
                break;
            case R.id.card_expense:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_expense_color),
                        ContextCompat.getColor(getActivity(), R.color.card_expense_color_pressed));
                break;
            case R.id.card_saving:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_saving_color),
                        ContextCompat.getColor(getActivity(), R.color.card_saving_color_pressed));
                break;
            case R.id.card_purchase:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_purchase_color),
                        ContextCompat.getColor(getActivity(), R.color.card_purchase_color_pressed));
                break;
            case R.id.card_dummy:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_dummy_color),
                        ContextCompat.getColor(getActivity(), R.color.card_dummy_color_pressed));
                break;
            case R.id.card_setting:
                animate(view,motionEvent, ContextCompat.getColor(getActivity(), R.color.card_setting_color),
                        ContextCompat.getColor(getActivity(), R.color.card_setting_color_pressed));
                break;
        }
        return false;
    }

    private void animate(View view, MotionEvent motionEvent,int color,int colorPressed) {
        CardView cardView= (CardView)view;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                cardView.setCardBackgroundColor(color);
//                view.setBackgroundResource(R.color.bright_overlay);
//                mOnItemClickListener.onItemClick(view, item, position);
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
        CardView card_dummy;
        CardView card_settings;
        public ViewHolder(View view) {
            card_income = (CardView) view.findViewById(R.id.card_income);
            card_expense= (CardView) view.findViewById(R.id.card_expense);
            card_savings= (CardView) view.findViewById(R.id.card_saving);
            card_purchase= (CardView) view.findViewById(R.id.card_purchase);
            card_dummy= (CardView) view.findViewById(R.id.card_dummy);
            card_settings= (CardView) view.findViewById(R.id.card_setting);
        }

    }
}
