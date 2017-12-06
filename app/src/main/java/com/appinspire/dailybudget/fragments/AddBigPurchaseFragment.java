package com.appinspire.dailybudget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.adapters.SpinnerAdapter;
import com.appinspire.dailybudget.dialog.MyNumberPickerDialog;
import com.appinspire.dailybudget.enumerations.SpinnerTypeEnum;
import com.appinspire.dailybudget.enumerations.WishListEnum;
import com.appinspire.dailybudget.models.BigPurchase;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.AppUtils;
import com.appinspire.dailybudget.utils.Constants;
import com.appinspire.dailybudget.utils.Database;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;

/**
 * Created by Bilal Rashid on 12/5/2017.
 */

public class AddBigPurchaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ViewHolder mHolder;
    private SpinnerAdapter mExpenseTypeAdapter;
    private BigPurchase mBigPurchase;
    private boolean mShowAd;
    private InterstitialAd mInterstitialAd;
    private final int REFRESH_TIME_SECONDS = 2 * 1000;
    private Handler mHandler;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler.removeCallbacks(mRunnable);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    if(AppUtils.isInternetAvailable(getContext()))
                        mHandler.postDelayed(mRunnable, REFRESH_TIME_SECONDS);
                }
            }catch (Exception e){}

        }
    };
    MyNumberPickerDialog mPercentageDialog;
    public static int Percentage = 0;

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
            ((ToolbarListener) context).setTitle("Add Big Purchases");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_big_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mHandler = new Handler();
        mHolder.percentEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == (MotionEvent.ACTION_UP)) {
                    //show dialog here
                    mPercentageDialog.show();
                }
                return true;
            }
        });
        mShowAd = getArguments().getBoolean(Constants.SHOWAD,false);
        mExpenseTypeAdapter = new SpinnerAdapter(getActivity(), SpinnerTypeEnum.WISHLIST.getValue());
        mHolder.incomeTypeSpinner.setAdapter(mExpenseTypeAdapter);
        mHolder.incomeTypeSpinner.setOnItemSelectedListener(this);
        mHolder.saveButton.setOnClickListener(this);
        mHolder.inputLayoutAmount.setHint("Amount (in "+AppUtils.getCurrency(getContext())+")");
        mBigPurchase = new BigPurchase();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AdRequest adRequest = new AdRequest.Builder().build();
        mHolder.mAdView.loadAd(adRequest);
        mHolder.mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mHolder.mAdView.setVisibility(View.VISIBLE);
            }
        });
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        mPercentageDialog = new MyNumberPickerDialog(getContext(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                submitForm(view);
                break;
            case R.id.button_confirm:
                if (mPercentageDialog.isShowing()) {
                    mHolder.percentEditText.setText(String.valueOf(mPercentageDialog.numberPicker.getValue()*5)+" %");
                    Percentage = mPercentageDialog.numberPicker.getValue()*5;
                    mPercentageDialog.dismiss();
                }
                break;
            case R.id.button_cancel:
                mPercentageDialog.dismiss();
                break;
        }

    }

    private void submitForm(View view) {
        if (mHolder.amountEditText.getText().toString().length() < 1) {
            mHolder.inputLayoutAmount.setErrorEnabled(true);
            mHolder.inputLayoutAmount.setError("Amount Required");
            return;
        }
        mHolder.inputLayoutAmount.setError(null);
        mHolder.inputLayoutAmount.setErrorEnabled(false);
        if (mHolder.percentEditText.getText().toString().length() < 1) {
            mHolder.inputLayoutPercent.setErrorEnabled(true);
            mHolder.inputLayoutPercent.setError("Set percentage of total savings");
            return;
        }
        try {
            mBigPurchase.amount= Double.parseDouble(mHolder.amountEditText.getText().toString());
            mHolder.inputLayoutAmount.setError(null);
            mHolder.inputLayoutAmount.setErrorEnabled(false);
        } catch (Exception e) {
            mHolder.inputLayoutAmount.setErrorEnabled(true);
            mHolder.inputLayoutAmount.setError("Invalid Amount");
            return;
        }
        mHolder.inputLayoutPercent.setError(null);
        mHolder.inputLayoutPercent.setErrorEnabled(false);
        mBigPurchase.tag = mHolder.tagEditText.getText().toString();
        mBigPurchase.percent = Percentage;
        mBigPurchase.completed = false;
        Database.saveBigPurchase(getContext(),mBigPurchase);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Big Purchase");
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, " " + mBigPurchase.type);
        bundle.putString(FirebaseAnalytics.Param.PRICE, " " + mBigPurchase.amount);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        if(mShowAd)
            mHandler.postDelayed(mRunnable, 500);
        getActivity().onBackPressed();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mBigPurchase.type = WishListEnum.values()[i].getName();
        mBigPurchase.icon = WishListEnum.values()[i].getIconId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mBigPurchase.type = WishListEnum.values()[0].getName();
        mBigPurchase.icon = WishListEnum.values()[0].getIconId();

    }


    public static class ViewHolder {
        TextInputEditText amountEditText;
        TextInputEditText tagEditText;
        TextInputEditText percentEditText;
        Button saveButton;
        TextInputLayout inputLayoutAmount;
        TextInputLayout inputLayoutTag;
        TextInputLayout inputLayoutPercent;
        AppCompatSpinner incomeTypeSpinner;
        AdView mAdView;

        public ViewHolder(View view) {
            amountEditText = (TextInputEditText) view.findViewById(R.id.edit_text_amount);
            tagEditText = (TextInputEditText) view.findViewById(R.id.edit_text_tag);
            percentEditText = (TextInputEditText) view.findViewById(R.id.edit_text_percent);
            inputLayoutAmount = (TextInputLayout) view.findViewById(R.id.input_layout_amount);
            inputLayoutTag = (TextInputLayout) view.findViewById(R.id.input_layout_tag);
            inputLayoutPercent = (TextInputLayout) view.findViewById(R.id.input_layout_percent);
            incomeTypeSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_item_type);
            saveButton = (Button) view.findViewById(R.id.button_save);
            mAdView = (AdView)view.findViewById(R.id.adView);
            mAdView.setVisibility(View.GONE);
        }

    }
}
