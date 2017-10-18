package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.toolbox.ToolbarListener;

/**
 * Created by Bilal Rashid on 10/16/2017.
 */

public class ExpensesFragment extends Fragment implements View.OnClickListener{
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
            ((ToolbarListener) context).setTitle("Expenses");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
    }
    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {

        public ViewHolder(View view) {
        }

    }
}
