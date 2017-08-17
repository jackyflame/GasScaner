package com.jf.gasscaner.vm;

import com.haozi.baselibrary.net.retrofit.ReqCallback;
import com.haozi.baselibrary.utils.StringUtil;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.gasscaner.ui.SplashActivity;

/**
 * Created by admin on 2017/8/9.
 */

public class SplashVM extends BaseVM<UserPresent>{

    private SplashActivity mContext;

    public SplashVM(SplashActivity splashActivity) {
        super(new UserPresent());
        mContext = splashActivity;
    }

    public boolean isLogin() {
        UserEntity userEntity = mPrensent.getUser();
        if(userEntity == null || StringUtil.isEmpty(userEntity.getUserName()) || StringUtil.isEmpty(userEntity.getMm())){
            return false;
        }
        return true;
    }

    public void requestLoginToken(ReqCallback<UserEntity> callback) {
        UserEntity userEntity = mPrensent.getUser();
        mPrensent.login(userEntity.getId(),userEntity.getMm(),callback);
    }

    public void cleanUserData() {
        mPrensent.cleanUserData();
    }

    public void saveUserData(UserEntity userEntity) {
        mPrensent.saveUser(userEntity);
    }
}
