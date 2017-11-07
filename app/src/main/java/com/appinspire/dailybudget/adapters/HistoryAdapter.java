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

    }
    public Saving getItem(int position) {
        return mItems.get(position);
    }

    public List<Saving> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    public void addAll(List<Saving> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView incomeTypeText;
        TextView dateText;
        TextView tagText;
        TextView amountText;
        TextView currencyText;

        public ViewHolder(View view) {
            super(view);
//            iconImage = (ImageView) view.findViewById(R.id.image_icon);
//            incomeTypeText = (TextView) view.findViewById(R.id.text_type);
//            dateText = (TextView) view.findViewById(R.id.text_date);
//            tagText = (TextView) view.findViewById(R.id.text_tag);
//            tagLayout = (LinearLayout) view.findViewById(R.id.layout_tag);
//            amountText = (TextView) view.findViewById(R.id.text_amount);
//            currencyText = (TextView) view.findViewById(R.id.text_currency);
//            Typeface regular = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoRegular.ttf");
//            Typeface bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoBold.ttf");
//            incomeTypeText.setTypeface(regular);
//            dateText.setTypeface(regular);
//            tagText.setTypeface(regular);
//            amountText.setTypeface(bold);
//            currencyText.setTypeface(regular);

        }

    }
}
