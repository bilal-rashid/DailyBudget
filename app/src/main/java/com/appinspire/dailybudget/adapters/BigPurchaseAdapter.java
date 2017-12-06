package com.appinspire.dailybudget.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.models.BigPurchase;
import com.appinspire.dailybudget.toolbox.OnItemClickListener;
import com.appinspire.dailybudget.utils.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Rashid on 11/19/2017.
 */

public class BigPurchaseAdapter extends RecyclerView.Adapter<BigPurchaseAdapter.ViewHolder>{

    private List<BigPurchase> mItems = new ArrayList<>();
    OnItemClickListener mItemclickListener;
    double savings;

    public BigPurchaseAdapter(OnItemClickListener onItemClickListener,double money) {
        this.mItemclickListener = onItemClickListener;
        if(money>1) {
            this.savings = money;
        }else {
            this.savings = 0;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_big_purchase, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BigPurchase item = mItems.get(position);
        holder.iconImage.setImageDrawable(AppUtils.getColorDrawable(item.icon, holder.itemView.getContext(), true));
        holder.typeText.setText(item.type);
        if (item.amount % 1 == 0) {
            ///income is without decimals
            DecimalFormat formatter = new DecimalFormat("#,###");
            holder.amountText.setText(formatter.format(item.amount));
        } else {
            ///income is with decimals
            DecimalFormat formatter = new DecimalFormat("#,###.0");
            holder.amountText.setText(formatter.format(item.amount));
        }
        holder.currencyText.setText(AppUtils.getCurrency(holder.itemView.getContext()));
        double total_amount = (item.percent/100)*savings;
        double completed = (total_amount/item.amount)*100;
        if(completed > 100)
            completed = 100;
        holder.percentText.setText((new DecimalFormat("0.0").format(completed))+" % completed");
        holder.progressBar.setProgress((float) completed);


        if (item.tag.length() < 1)
            holder.tagLayout.setVisibility(View.GONE);
        else {
            holder.tagLayout.setVisibility(View.VISIBLE);
            holder.tagText.setText(item.tag);
        }
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemclickListener.onItemClick(view,item,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mItems != null ? mItems.size() : 0);
    }

    public void addAll(List<BigPurchase> collection) {
        mItems.clear();
        if (collection != null)
            mItems.addAll(collection);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView typeText;
        TextView tagText;
        TextView percentText;
        LinearLayout tagLayout;
        TextView amountText;
        TextView currencyText;
        RoundCornerProgressBar progressBar;
        Button delButton;

        public ViewHolder(View view) {
            super(view);
            iconImage = (ImageView) view.findViewById(R.id.image_icon);
            typeText = (TextView) view.findViewById(R.id.text_type);
            percentText = (TextView) view.findViewById(R.id.text_percent);
            tagText = (TextView) view.findViewById(R.id.text_tag);
            tagLayout = (LinearLayout) view.findViewById(R.id.layout_tag);
            amountText = (TextView) view.findViewById(R.id.text_amount);
            currencyText = (TextView) view.findViewById(R.id.text_currency);
            delButton = (Button) view.findViewById(R.id.button_del);
            progressBar = (RoundCornerProgressBar) view.findViewById(R.id.progress);
            Typeface regular = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoRegular.ttf");
            Typeface bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "RobotoBold.ttf");
            typeText.setTypeface(regular);
            percentText.setTypeface(regular);
            tagText.setTypeface(regular);
            amountText.setTypeface(bold);
            currencyText.setTypeface(regular);


        }

    }
}
