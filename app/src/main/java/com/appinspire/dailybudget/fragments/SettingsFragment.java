package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.adapters.SpinnerAdapter;
import com.appinspire.dailybudget.enumerations.SpinnerTypeEnum;
import com.appinspire.dailybudget.toolbox.ToolbarListener;

/**
 * Created by Bilal Rashid on 10/16/2017.
 */

public class SettingsFragment  extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private ViewHolder mHolder;
    private SpinnerAdapter mCurrencyAdapter;
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
//        if (context instanceof ToolbarListener) {
//            ((ToolbarListener) context).setTitle("Income");
//        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mCurrencyAdapter = new SpinnerAdapter(getActivity(), SpinnerTypeEnum.CURRENCY.getValue());
        mHolder.spinner.setAdapter(mCurrencyAdapter);
        mHolder.spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static class ViewHolder {

        AppCompatSpinner spinner;
        Button save,reset;
        public ViewHolder(View view) {
            spinner = (AppCompatSpinner)view.findViewById(R.id.spinner_currency);
            save = (Button)view.findViewById(R.id.button_save);
            reset = (Button)view.findViewById(R.id.button_reset);

        }

    }
}
