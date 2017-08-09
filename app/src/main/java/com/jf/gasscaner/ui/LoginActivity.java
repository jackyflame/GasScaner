package com.jf.gasscaner.ui;

import android.os.Bundle;
import android.text.Editable;

import com.haozi.baselibrary.constants.IntentKeys;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBActivity;
import com.jf.gasscaner.databinding.ActivityLoginBinding;
import com.jf.gasscaner.vm.LoginVM;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseDBActivity<ActivityLoginBinding,LoginVM> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取是否登录标记
        boolean isLogin = getIntent().getBooleanExtra(IntentKeys.EXTRA_BOOLEAN_ISLOGIN,true);
        //绑定布局值
        bindLayout(R.layout.activity_login,new LoginVM(this,isLogin));
        initView();
    }

    private void initView() {
        setTitle("");
    }

    public String getUserName(){
        Editable text = mBinding.txvNickname.getText();
        if(text == null){
            return "";
        }else{
            return text.toString();
        }
    }

    public String getPassword(){
        Editable text = mBinding.txvPassword.getText();
        if(text == null){
            return "";
        }else{
            return text.toString();
        }
    }
}

