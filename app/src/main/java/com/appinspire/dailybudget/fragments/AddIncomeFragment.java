package com.appinspire.dailybudget.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.appinspire.dailybudget.adapters.IncomeTypeSpinnerAdapter;
import com.appinspire.dailybudget.enumerations.IncomeEnum;
import com.appinspire.dailybudget.models.Income;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.AppUtils;

import java.util.Calendar;

/**
 * Created by Bilal Rashid on 10/19/2017.
 */

public class AddIncomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ViewHolder mHolder;
    private IncomeTypeSpinnerAdapter mIncomeTypeAdapter;
    private Income mIncome;

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
        mIncomeTypeAdapter = new IncomeTypeSpinnerAdapter(getActivity());
        mHolder.incomeTypeSpinner.setAdapter(mIncomeTypeAdapter);
        mHolder.incomeTypeSpinner.setOnItemSelectedListener(this);
        mHolder.saveButton.setOnClickListener(this);
        mIncome = new Income();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        mHolder.fab_Add.setOnClickListener(this);

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mHolder.dateEditText.setText("" + AppUtils.getMonthShortName(monthOfYear) + " " + dayOfMonth + "," + year);

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
        mIncome.date = mHolder.dateEditText.getText().toString();
        mIncome.tag = mHolder.tagEditText.getText().toString();
        AppUtils.showSnackBar(view,""+mIncome.income+","+mIncome.icon+","+mIncome.date
        +","+mIncome.type);
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

        public ViewHolder(View view) {
            incomeEditText = (TextInputEditText) view.findViewById(R.id.edit_text_income);
            tagEditText = (TextInputEditText) view.findViewById(R.id.edit_text_tag);
            dateEditText = (TextInputEditText) view.findViewById(R.id.edit_text_date);
            inputLayoutIncome = (TextInputLayout) view.findViewById(R.id.input_layout_income);
            inputLayoutTag = (TextInputLayout) view.findViewById(R.id.input_layout_tag);
            inputLayoutDate = (TextInputLayout) view.findViewById(R.id.input_layout_date);
            incomeTypeSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_incometype);
            saveButton = (Button) view.findViewById(R.id.button_save);
        }

    }
}
