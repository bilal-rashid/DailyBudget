package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.FrameActivity;
import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.ActivityUtils;

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
        mHolder.fab_Add.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,
                        AddExpenseFragment.class.getName(), null);
                break;
        }

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
