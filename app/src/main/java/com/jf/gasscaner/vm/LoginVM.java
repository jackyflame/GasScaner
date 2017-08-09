package com.jf.gasscaner.vm;

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
            loginActivity.showToast("请输入正确的昵称");
            return;
        }
        String password = loginActivity.getPassword();
        if(StringUtil.isEmpty(password)){
            loginActivity.showToast("请输入密码");
            return;
        }else if(password.length() < 6){
            loginActivity.showToast("密码长度必须大于6位");
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
                if(isloginMark){
                    loginActivity.showToast("登录成功");
                }else{
                    loginActivity.showToast("注册成功");
                }
                loginActivity.finish();
            }

            @Override
            public void onReqError(HttpEvent httpEvent) {
                super.onReqError(httpEvent);
                loginActivity.hideProgressDialog();
                if(isloginMark) {
                    loginActivity.showToast("登录失败");
                }else{
                    loginActivity.showToast("注册成功");
                }
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
