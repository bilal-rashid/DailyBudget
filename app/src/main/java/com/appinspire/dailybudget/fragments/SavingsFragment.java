package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.models.Saving;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.Database;
import com.appinspire.dailybudget.utils.GsonUtils;

import java.util.List;

/**
 * Created by Bilal Rashid on 10/16/2017.
 */

public class SavingsFragment extends Fragment implements View.OnClickListener{
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
            ((ToolbarListener) context).setTitle("Savings");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        List<Saving> list = Database.getSavingList(getContext());
        for(int i=0;i<list.size();i++){
            Log.d("TAAAG p",i+"");
            Log.d("TAAAG",""+ GsonUtils.toJson(list.get(i)));

        }
    }
    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {

        public ViewHolder(View view) {
        }

    }
}
