package com.jf.gasscaner.vm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.jf.gasscaner.R;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/5/19
 * Time: 17:26
 */

public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        //主题样式最好是指定的样式,比如这样的样式:DatePickerDialog.THEME_HOLO_LIGHT，因为在小米手机中会显示不同的弹窗日期样式甚至有的会报错崩溃
        super(context, R.style.Dialog_hololight,callBack, year, monthOfYear, dayOfMonth);
        //((ViewGroup) ((ViewGroup) (getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        //super(context, callBack, year, monthOfYear, dayOfMonth);
        super(context, R.style.Dialog_hololight,callBack, year, monthOfYear, dayOfMonth);
        //((ViewGroup) ((ViewGroup) (getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
    }
}
