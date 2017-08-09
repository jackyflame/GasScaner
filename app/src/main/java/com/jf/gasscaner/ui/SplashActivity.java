package com.jf.gasscaner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBActivity;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: Haozi
 * Date: 2017/6/4
 * Time: 16:28
 */

public class SplashActivity extends BaseDBActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定布局值
        bindLayout(R.layout.activity_splash);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        mHandler.sendEmptyMessageDelayed(1000,3000);
    }

}
