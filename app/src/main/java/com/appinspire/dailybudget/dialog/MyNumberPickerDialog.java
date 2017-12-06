package com.appinspire.dailybudget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.appinspire.dailybudget.R;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by Bilal Rashid on 12/5/2017.
 */

public class MyNumberPickerDialog extends Dialog {
    public MaterialNumberPicker numberPicker;
    private Button cancelButton;
    private Button confirmButton;
    private View.OnClickListener onClickListener;

    public MyNumberPickerDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_number_picker);
        numberPicker = (MaterialNumberPicker) findViewById(R.id.number_picker);
        numberPicker.setValue(20);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        numberPicker.setDisplayedValues( new String[] { "5","10","15","20","25","30","35","40","45","50",
                                                        "55","60","65","70","75","80","85","90","95","100"} );
        cancelButton = (Button) findViewById(R.id.button_cancel);
        confirmButton = (Button) findViewById(R.id.button_confirm);
        cancelButton.setOnClickListener(onClickListener);
        confirmButton.setOnClickListener(onClickListener);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
