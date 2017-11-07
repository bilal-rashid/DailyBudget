package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.adapters.HistoryAdapter;
import com.appinspire.dailybudget.models.Saving;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.AppUtils;
import com.appinspire.dailybudget.utils.Database;
import com.appinspire.dailybudget.utils.GsonUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Bilal Rashid on 10/16/2017.
 */

public class SavingsFragment extends Fragment implements View.OnClickListener,OnItemClickListener{
    private ViewHolder mHolder;
    Saving mSavings;
    private HistoryAdapter mHistoryAdapter;
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
            ((ToolbarListener) context).setTitle("Savings");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_savings, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        List<Saving> list = Database.getSavingList(getContext());
        if(list.size()<1) {
            mHolder.savingsLayout.setVisibility(View.GONE);
            mHolder.errorLayout.setVisibility(View.VISIBLE);
        }else {
            mHolder.savingsLayout.setVisibility(View.VISIBLE);
            mHolder.errorLayout.setVisibility(View.GONE);
            mSavings = list.get(list.size() - 1);
            mHolder.date.setText("" + AppUtils.getMonthShortName(mSavings.month) + " " + mSavings.day + "," + mSavings.year);
            if (mSavings.savings % 1 == 0) {
                DecimalFormat formatter = new DecimalFormat("#,###");
                mHolder.savings.setText("Rs " + formatter.format(mSavings.savings));
            } else {
                DecimalFormat formatter = new DecimalFormat("#,###.0");
                mHolder.savings.setText("Rs " + formatter.format(mSavings.savings));
            }
            if(mSavings.savings < 0){
                mHolder.savings.setTextColor(ContextCompat.getColor(getContext(),R.color.card_expense_color));
            }
        }
        setupRecyclerView();
    }
    private void setupRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mHolder.historyRecycler.setLayoutManager(mLayoutManager);
        mHistoryAdapter = new HistoryAdapter(this);
        mHolder.historyRecycler.setAdapter(mHistoryAdapter);
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    public static class ViewHolder {
        TextView date,savings;
        RecyclerView historyRecycler;
        LinearLayout errorLayout,savingsLayout;

        public ViewHolder(View view) {
            Typeface regular = Typeface.createFromAsset(view.getContext().getAssets(), "RobotoRegular.ttf");
            Typeface bold = Typeface.createFromAsset(view.getContext().getAssets(), "RobotoBold.ttf");
            date = (TextView) view.findViewById(R.id.textview_date);
            savings = (TextView) view.findViewById(R.id.textview_total_savings);
            historyRecycler = (RecyclerView) view.findViewById(R.id.recycler_history);
            savings.setTypeface(regular);
            date.setTypeface(regular);
            errorLayout = view.findViewById(R.id.error_savings);
            savingsLayout= view.findViewById(R.id.savings_layout);


        }

    }
}
