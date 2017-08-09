package com.jf.gasscaner.vm;

import android.app.Activity;
import android.databinding.Bindable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.haozi.baselibrary.interfaces.listeners.DialogCallBack;
import com.haozi.baselibrary.utils.ViewUtils;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.net.entity.UserEntity;

/**
 * Created by admin on 2017/8/9.
 */

public class UserFragmentVM extends BaseVM<UserPresent>{

    private Activity activity;

    public UserFragmentVM(Activity activity) {
        super(new UserPresent());
        this.activity = activity;
    }

    @Bindable
    public UserEntity getUser(){
        return UserPresent.getInstance().getUser();
    }

    public void onLogoutClick(View view){
        ViewUtils.showMsgDialog(activity, R.string.dailog_sign_out_tips,new DialogCallBack(){
            @Override
            public void neutralButtonCallBack() {}
            @Override
            public void negativeButtonCallBack() {
                mPrensent.cleanUserData();
                activity.finish();
            }
        });
    }
}
