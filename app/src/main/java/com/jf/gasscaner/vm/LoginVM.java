package com.jf.gasscaner.vm;

import android.content.Intent;
import android.databinding.Bindable;
import android.view.View;

import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.net.retrofit.BaseReqCallback;
import com.haozi.baselibrary.utils.StringUtil;
import com.jf.gasscaner.BR;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.gasscaner.ui.LoginActivity;
import com.jf.gasscaner.ui.MainActivity;

/**
 * Created by Haozi on 2017/5/23.
 */
public class LoginVM extends BaseVM {

    private boolean isloginMark;
    private boolean isloginging;
    private LoginActivity loginActivity;

    public LoginVM(LoginActivity loginActivity, boolean isLogin) {
        this.loginActivity = loginActivity;
        isloginMark = isLogin;
    }

    @Bindable
    public boolean getIsloginging() {
        return isloginging;
    }

    public void setIsloginging(boolean isloginging) {
        this.isloginging = isloginging;
        notifyPropertyChanged(BR.isloginging);
    }

    /**
     * 确定按钮click
     * */
    public void onConfirmClick(View view){
        String userName = loginActivity.getUserName();
        if(StringUtil.isEmpty(userName)){
            loginActivity.showToast("请输入账号");
            return;
        }
        String password = loginActivity.getPassword();
        if(StringUtil.isEmpty(password)){
            loginActivity.showToast("请输入密码");
            return;
        }
        UserPresent.getInstance().registerOrLogin(userName, password, new BaseReqCallback<UserEntity>() {
            @Override
            public void onReqStart() {
                loginActivity.showProgressDialog();
            }
            @Override
            public void onNetResp(UserEntity response) {
                UserPresent.getInstance().saveUser(response);
                loginActivity.hideProgressDialog();
                loginActivity.showToast("登录成功");
                loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
            }

            @Override
            public void onReqError(HttpEvent httpEvent) {
                super.onReqError(httpEvent);
                loginActivity.hideProgressDialog();
                loginActivity.showToast("登录失败");
            }
        });
    }

    @Bindable
    public boolean getIsloginMark() {
        return isloginMark;
    }

    public void setIsloginMark(boolean isloginMark) {
        this.isloginMark = isloginMark;
        notifyPropertyChanged(BR.isloginMark);
    }
}
