package com.appinspire.dailybudget.fragments;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;

import com.appinspire.dailybudget.R;
import com.appinspire.dailybudget.adapters.SpinnerAdapter;
import com.appinspire.dailybudget.enumerations.IncomeEnum;
import com.appinspire.dailybudget.enumerations.SpinnerTypeEnum;
import com.appinspire.dailybudget.models.Income;
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
 * Created by Bilal Rashid on 10/19/2017.
 */

public class AddIncomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ViewHolder mHolder;
    private SpinnerAdapter mIncomeTypeAdapter;
    private Income mIncome;
    private boolean mShowAd;
    private FirebaseAnalytics mFirebaseAnalytics;
    private InterstitialAd mInterstitialAd;
    private final int REFRESH_TIME_SECONDS = 2 * 1000;
    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler.removeCallbacks(mRunnable);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    if (AppUtils.isInternetAvailable(getContext()))
                        mHandler.postDelayed(mRunnable, REFRESH_TIME_SECONDS);
                }
            } catch (Exception e) {
            }

        }
    };

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
            ((ToolbarListener) context).setTitle("Add Income");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_income, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mHandler = new Handler();
        mHolder = new ViewHolder(view);
        mHolder.dateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == (MotionEvent.ACTION_UP)) {
                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog d = new DatePickerDialog(getActivity(),
                            mDateSetListener, yy, mm, dd);
                    d.show();
                }


                return true;
            }
        });
        mShowAd = getArguments().getBoolean(Constants.SHOWAD, false);
        mIncomeTypeAdapter = new SpinnerAdapter(getActivity(), SpinnerTypeEnum.INCOME.getValue());
        mHolder.incomeTypeSpinner.setAdapter(mIncomeTypeAdapter);
        mHolder.incomeTypeSpinner.setOnItemSelectedListener(this);
        mHolder.saveButton.setOnClickListener(this);
        mHolder.inputLayoutIncome.setHint("Income (in " + AppUtils.getCurrency(getContext()) + ")");
        mIncome = new Income();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AdRequest adRequest = new AdRequest.Builder().build();
        mHolder.mAdView.loadAd(adRequest);
        mHolder.mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mHolder.mAdView.setVisibility(View.VISIBLE);
            }
        });
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mHolder.dateEditText.setText("" + AppUtils.getMonthShortName(monthOfYear) + " " + dayOfMonth + "," + year);
            mIncome.year = year;
            mIncome.day = dayOfMonth;
            mIncome.month = monthOfYear;

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                submitForm(view);
                break;
        }

    }

    private void submitForm(View view) {
        if (mHolder.incomeEditText.getText().toString().length() < 1) {
            mHolder.inputLayoutIncome.setErrorEnabled(true);
            mHolder.inputLayoutIncome.setError("Income Required ");
            return;
        }
        mHolder.inputLayoutIncome.setError(null);
        mHolder.inputLayoutIncome.setErrorEnabled(false);
        if (mHolder.dateEditText.getText().toString().length() < 1) {
            mHolder.inputLayoutDate.setErrorEnabled(true);
            mHolder.inputLayoutDate.setError("Select date");
            return;
        }
        try {
            mIncome.income = Double.parseDouble(mHolder.incomeEditText.getText().toString());
            mHolder.inputLayoutIncome.setError(null);
            mHolder.inputLayoutIncome.setErrorEnabled(false);
        } catch (Exception e) {
            mHolder.inputLayoutIncome.setErrorEnabled(true);
            mHolder.inputLayoutIncome.setError("Invalid Income");
            return;
        }
        mHolder.inputLayoutDate.setError(null);
        mHolder.inputLayoutDate.setErrorEnabled(false);
        mIncome.tag = mHolder.tagEditText.getText().toString();
        Database.saveIncome(getContext(), mIncome);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Income");
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, " " + mIncome.type);
        bundle.putString(FirebaseAnalytics.Param.PRICE, " " + mIncome.income);
        bundle.putString(FirebaseAnalytics.Param.START_DATE, mIncome.day + "/" + mIncome.month + "/" + mIncome.year);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        if (mShowAd)
            mHandler.postDelayed(mRunnable, 500);
        getActivity().onBackPressed();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mIncome.type = IncomeEnum.values()[i].getName();
        mIncome.icon = IncomeEnum.values()[i].getIconId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mIncome.type = IncomeEnum.values()[0].getName();
        mIncome.icon = IncomeEnum.values()[0].getIconId();

    }


    public static class ViewHolder {
        TextInputEditText incomeEditText;
        TextInputEditText tagEditText;
        TextInputEditText dateEditText;
        Button saveButton;
        TextInputLayout inputLayoutIncome;
        TextInputLayout inputLayoutTag;
        TextInputLayout inputLayoutDate;
        AppCompatSpinner incomeTypeSpinner;
        AdView mAdView;


        public ViewHolder(View view) {
            incomeEditText = (TextInputEditText) view.findViewById(R.id.edit_text_income);
            tagEditText = (TextInputEditText) view.findViewById(R.id.edit_text_tag);
            dateEditText = (TextInputEditText) view.findViewById(R.id.edit_text_date);
            inputLayoutIncome = (TextInputLayout) view.findViewById(R.id.input_layout_income);
            inputLayoutTag = (TextInputLayout) view.findViewById(R.id.input_layout_tag);
            inputLayoutDate = (TextInputLayout) view.findViewById(R.id.input_layout_date);
            incomeTypeSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_incometype);
            saveButton = (Button) view.findViewById(R.id.button_save);
            mAdView = (AdView) view.findViewById(R.id.adView);
            mAdView.setVisibility(View.GONE);
        }

    }
}
