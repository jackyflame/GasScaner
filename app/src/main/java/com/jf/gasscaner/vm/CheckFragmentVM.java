package com.jf.gasscaner.vm;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.jf.gasscaner.base.vm.BaseVM;

/**
 * Created by admin on 2017/8/9.
 */

public class CheckFragmentVM extends BaseVM{

    private Activity activity;

    public CheckFragmentVM(FragmentActivity activity) {
        this.activity = activity;
    }
}
