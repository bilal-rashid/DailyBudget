package com.appinspire.dailybudget.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.models.BigPurchase;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 11/19/2017.
 */

public class BigPurchaseAdapter extends RecyclerView.Adapter<BigPurchaseAdapter.ViewHolder>{

    private List<BigPurchase> mItems = new ArrayList<>();
    OnItemClickListener mItemclickListener;

    public BigPurchaseAdapter(OnItemClickListener onItemClickListener) {
        this.mItemclickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_big_purchase, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        final BigPurchase item = mItems.get(position);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void addAll(List<BigPurchase> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);

        }

    }
}
