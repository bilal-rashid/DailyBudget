package com.appinspire.dailybudget.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.models.Saving;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;
import com.appinspire.dailybudget.utils.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 11/7/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<Saving> mItems = new ArrayList<>();
    OnItemClickListener mItemclickListener;

    public HistoryAdapter(OnItemClickListener onItemClickListener) {
        this.mItemclickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Saving item = mItems.get(position);
        if (item.savings % 1 == 0) {
            ///income is without decimals
            DecimalFormat formatter = new DecimalFormat("#,###");
            holder.total_savings_text.setText(formatter.format(item.savings));
        } else {
            ///income is with decimals
            DecimalFormat formatter = new DecimalFormat("#,###.0");
            holder.total_savings_text.setText(formatter.format(item.savings));
        }
        holder.start_year_text.setText(item.year+"");
        holder.start_day_text.setText(item.day+"");
        holder.start_month_text.setText(AppUtils.getMonthShortName(item.month)+"");
        holder.currency.setText(AppUtils.getCurrency(holder.itemView.getContext()));

    }
    public Saving getItem(int position) {
        return mItems.get(position);
    }

    public List<Saving> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return (mItems != null ? mItems.size() : 0);
    }
    public void addAll(List<Saving> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView start_day_text;
        TextView start_month_text;
        TextView start_year_text;
        TextView currency;
        TextView total_savings_text;

        public ViewHolder(View view) {
            super(view);
            Typeface regular = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoRegular.ttf");
            Typeface bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoBold.ttf");
            start_day_text = (TextView) view.findViewById(R.id.start_day);
            start_month_text= (TextView) view.findViewById(R.id.start_month);
            start_year_text= (TextView) view.findViewById(R.id.start_year);
            currency= (TextView) view.findViewById(R.id.savings_currency);
            total_savings_text= (TextView) view.findViewById(R.id.textview_total_savings);
            total_savings_text.setTypeface(regular);
            start_day_text.setTypeface(bold);
            start_month_text.setTypeface(bold);
            start_year_text.setTypeface(bold);
            currency.setTypeface(regular);

        }

    }
}
