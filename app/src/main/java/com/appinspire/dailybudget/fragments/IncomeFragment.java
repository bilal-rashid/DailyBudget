package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.FrameActivity;
import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.adapters.IncomeAdapter;
import com.appinspire.dailybudget.enumerations.IncomeEnum;
import com.appinspire.dailybudget.models.Income;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.ActivityUtils;
import com.appinspire.dailybudget.utils.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 10/16/2017.
 */

public class IncomeFragment extends Fragment implements View.OnClickListener ,OnItemClickListener{
    private ViewHolder mHolder;
    private IncomeAdapter mIncomeAdapter;
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
            ((ToolbarListener) context).setTitle("Income");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mHolder.fab_Add.setOnClickListener(this);
        setupRecyclerView();
        mHolder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mHolder.fab_Add.getVisibility() == View.VISIBLE) {
                    mHolder.fab_Add.hide();
                } else if (dy < 0 && mHolder.fab_Add.getVisibility() != View.VISIBLE) {
                    mHolder.fab_Add.show();
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        populateData(Database.getIncomeList(getContext()));
    }

    private void setupRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mHolder.recyclerView.setLayoutManager(mLayoutManager);
        mIncomeAdapter = new IncomeAdapter(this);
        mHolder.recyclerView.setAdapter(mIncomeAdapter);
    }

    private void populateData(List<Income> objects) {
        mIncomeAdapter.addAll(objects);
        if (objects == null || objects.size() <= 0) {
//            mHolder.sErrorText.setText("No Stores");
//            mHolder.sErrorContainer.setVisibility(View.VISIBLE);
        } else {
//            mHolder.sErrorContainer.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        AddIncomeFragment.class.getName(), null);
                break;
        }

    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    public static class ViewHolder {
        FloatingActionButton fab_Add;
        RecyclerView recyclerView;

        public ViewHolder(View view) {
            fab_Add = (FloatingActionButton) view.findViewById(R.id.add_button);
            recyclerView = (RecyclerView) view.findViewById(R.id.income_recycler);
        }

    }
}
