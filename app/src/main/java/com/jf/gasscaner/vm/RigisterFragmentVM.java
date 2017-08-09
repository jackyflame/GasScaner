package com.jf.gasscaner.vm;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.Bindable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;

/**
 * Created by admin on 2017/8/9.
 */

public class RigisterFragmentVM extends BaseVM{

    private Activity activity;
    /**当前序列号字母列表*/
    private String[] provinceArray;

    private String carProvinceMark;

    public RigisterFragmentVM(FragmentActivity activity) {
        this.activity = activity;
    }

    public void onCarBandClick(View view){
        if(provinceArray == null){
            provinceArray = activity.getResources().getStringArray(R.array.car_mark_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(provinceArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        carProvinceMark = provinceArray[position];
                        //刷新编码（选择字母的时候裁剪5位超长号码）

                        //刷新数据

                    }
                })
                .create()
                .show();
    }

    @Bindable
    public String getCarProvinceMark() {
        return carProvinceMark;
    }

    public void setCarProvinceMark(String carProvinceMark) {
        this.carProvinceMark = carProvinceMark;
        notifyPropertyChanged(BR.carProvinceMark);
    }
}
