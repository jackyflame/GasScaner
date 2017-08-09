package com.jf.gasscaner.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.haozi.baselibrary.utils.StringUtil;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.BaseDBActivity;

public class MainActivity extends BaseDBActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;

    private TabHost.TabSpec tab_check;
    private TabHost.TabSpec tab_register;
    private TabHost.TabSpec tab_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initTabHost();
        initData();
    }

    private void initData() {}

    private void initTabHost() {
        //获取Tabhost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //为每一个Tab按钮设置图标、文字和内容
        tab_check = getNewIndicator(R.string.tab_check_menue, R.drawable.main_tab_check_selector);
        tab_register = getNewIndicator(R.string.tab_regiter_menue, R.drawable.main_tab_register_selector);
        tab_user = getNewIndicator(R.string.tab_user_menue, R.drawable.main_tab_user_selector);

        //将Tab按钮添加进Tab选项卡中
        mTabHost.addTab(tab_check, CheckFragment.class, null);
        mTabHost.addTab(tab_register, RigisterFragment.class, null);
        mTabHost.addTab(tab_user, UserFragment.class, null);

        //设置tabs之间的分隔线不显示
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //设置切换监听
        mTabHost.setOnTabChangedListener(this);
        //初始化选择首页
        mTabHost.setCurrentTab(0);
        setTitle(R.string.tab_check_menue);
    }

    /**
     * 获取一个新的TabSpec
     * @param titleResid
     * @param iconResid
     * */
    @SuppressLint("InflateParams")
    private TabHost.TabSpec getNewIndicator(int titleResid, int iconResid) {
        //获取标题
        String title = "";
        if(titleResid > 0){
            title = getResources().getString(titleResid);
        }
        //实例化新的TabSpec
        TabHost.TabSpec spec = mTabHost.newTabSpec(titleResid+"");
        //实例化TabSpec的内容VIEW
        View v = LayoutInflater.from(this).inflate(R.layout.layout_home_tab_item, null);
        //设置图标
        ImageView img_tab_icon = (ImageView) v.findViewById(R.id.img_tab_icon);
        if(iconResid <= 0){
            img_tab_icon.setVisibility(View.GONE);
        }else{
            img_tab_icon.setImageResource(iconResid);
        }
        //设置标题
        TextView txv_tab_title = (TextView) v.findViewById(R.id.txv_tab_title);
        if(StringUtil.isEmpty(title)){
            txv_tab_title.setVisibility(View.GONE);
        }else{
            txv_tab_title.setText(title);
        }
        return spec.setIndicator(v);
    }

    @Override
    public void onTabChanged(String tabId) {
        if(StringUtil.isInteger(tabId)){
            setTitle(Integer.valueOf(tabId));
        }
    }
}
