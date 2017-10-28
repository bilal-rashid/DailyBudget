package com.appinspire.dailybudget.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.models.Income;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;
import com.appinspire.dailybudget.utils.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 10/26/2017.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder>{
    private List<Income> mItems = new ArrayList<>();
    OnItemClickListener mItemclickListener;

    public IncomeAdapter(OnItemClickListener onItemClickListener){
        this.mItemclickListener = onItemClickListener;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Income item = mItems.get(position);
        holder.iconImage.setImageDrawable(AppUtils.getColorDrawable(item.icon, holder.itemView.getContext()));
        holder.incomeTypeText.setText(item.type);
        if(item.income%1==0){
            ///income is without decimals
            DecimalFormat formatter = new DecimalFormat("#,###");
            holder.amountText.setText(formatter.format(item.income));
        }else {
            ///income is with decimals
            DecimalFormat formatter = new DecimalFormat("#,###.0");
            holder.amountText.setText(formatter.format(item.income));
        }
        holder.currencyText.setText("RS");
        holder.dateText.setText("" + AppUtils.getMonthShortName(item.month) + " " + item.day+ "," + item.year);
        if(item.tag.length()<1)
            holder.tagLayout.setVisibility(View.INVISIBLE);
        else {
            holder.tagLayout.setVisibility(View.VISIBLE);
            holder.tagText.setText(item.tag);
        }

    }

    public Income getItem(int position) {
        return mItems.get(position);
    }

    public List<Income> getItems() {
        return mItems;
    }
    @Override
    public int getItemCount() {
        return (mItems != null ? mItems.size() : 0);
    }
    public void addAll(List<Income> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView incomeTypeText;
        TextView dateText;
        TextView tagText;
        LinearLayout tagLayout;
        TextView amountText;
        TextView currencyText;

        public ViewHolder(View view){
            super(view);
            iconImage = (ImageView) view.findViewById(R.id.image_icon);
            incomeTypeText= (TextView)view.findViewById(R.id.text_type);
            dateText = (TextView)view.findViewById(R.id.text_date);
            tagText = (TextView)view.findViewById(R.id.text_tag);
            tagLayout= (LinearLayout) view.findViewById(R.id.layout_tag);
            amountText = (TextView)view.findViewById(R.id.text_amount);
            currencyText = (TextView)view.findViewById(R.id.text_currency);
            Typeface regular = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoRegular.ttf");
            Typeface bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoBold.ttf");
            incomeTypeText.setTypeface(regular);
            dateText.setTypeface(regular);
            tagText.setTypeface(regular);
            amountText.setTypeface(bold);
            currencyText.setTypeface(regular);

        }

    }
}
