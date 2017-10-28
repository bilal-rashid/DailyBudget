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
import com.appinspire.dailybudget.adapters.SpinnerAdapter;
import com.appinspire.dailybudget.enumerations.ExpenseEnum;
import com.appinspire.dailybudget.enumerations.SpinnerTypeEnum;
import com.appinspire.dailybudget.models.Expense;
import com.appinspire.dailybudget.toolbox.ToolbarListener;
import com.appinspire.dailybudget.utils.AppUtils;
import com.appinspire.dailybudget.utils.Database;

import java.util.Calendar;

/**
 * Created by Bilal Rashid on 10/23/2017.
 */

public class AddExpenseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ViewHolder mHolder;
    private SpinnerAdapter mExpenseTypeAdapter;
    private Expense mExpense;

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
            ((ToolbarListener) context).setTitle("Add Expense");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
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
        mExpenseTypeAdapter = new SpinnerAdapter(getActivity(), SpinnerTypeEnum.EXPENSE.getValue());
        mHolder.incomeTypeSpinner.setAdapter(mExpenseTypeAdapter);
        mHolder.incomeTypeSpinner.setOnItemSelectedListener(this);
        mHolder.saveButton.setOnClickListener(this);
        mExpense = new Expense();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        mHolder.fab_Add.setOnClickListener(this);

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mHolder.dateEditText.setText("" + AppUtils.getMonthShortName(monthOfYear) + " " + dayOfMonth + "," + year);
            mExpense.year=year;
            mExpense.day=dayOfMonth;
            mExpense.month=monthOfYear;

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
            mHolder.inputLayoutIncome.setError("Expense Amount Required");
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
            mExpense.expense = Double.parseDouble(mHolder.incomeEditText.getText().toString());
            mHolder.inputLayoutIncome.setError(null);
            mHolder.inputLayoutIncome.setErrorEnabled(false);
        } catch (Exception e) {
            mHolder.inputLayoutIncome.setErrorEnabled(true);
            mHolder.inputLayoutIncome.setError("Invalid Amount");
            return;
        }
        mHolder.inputLayoutDate.setError(null);
        mHolder.inputLayoutDate.setErrorEnabled(false);
        mExpense.tag = mHolder.tagEditText.getText().toString();
        Database.saveExpense(getContext(),mExpense);
        getActivity().onBackPressed();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mExpense.type = ExpenseEnum.values()[i].getName();
        mExpense.icon = ExpenseEnum.values()[i].getIconId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mExpense.type = ExpenseEnum.values()[0].getName();
        mExpense.icon = ExpenseEnum.values()[0].getIconId();

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