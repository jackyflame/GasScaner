package com.jf.gasscaner.base.vm;

import android.content.DialogInterface;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.haozi.baselibrary.base.ActivityManager;
import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.constants.SPKeys;
import com.haozi.baselibrary.db.MyShareDbHelper;
import com.haozi.baselibrary.interfaces.listeners.OnItemActionListener;
import com.haozi.baselibrary.interfaces.listeners.OnItemClickListener;
import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.cqhl.R;
import com.haozi.cqhl.net.entity.CommodityEntity;

/**
 * Created by Haozi on 2017/5/29.
 */

public abstract class BaseCommondityCellVM<T> extends BaseCellVM<T> {

    public BaseCommondityCellVM(T item){
        super(item);
    }

    public BaseCommondityCellVM(T item, OnItemClickListener<T> listener) {
        super(item,listener);
    }

    public BaseCommondityCellVM(T item, OnItemActionListener<T> listener) {
        super(item,listener);
    }

    public BaseCommondityCellVM(T item, OnItemClickListener<T> itemClickListener, OnItemActionListener<T> actionListener) {
        super(item,itemClickListener,actionListener);
    }

    public CommodityEntity getCommondity(){
        if(getItem() instanceof CommodityEntity){
            return (CommodityEntity) getItem();
        }
        return null;
    }

    @Bindable
    public String getCommodityName(){
        if(getCommondity() == null || StringUtil.isEmpty(getCommondity().getPrice())){
            return "";
        }
        return getCommondity().getCommodityName();
    }

    @Bindable
    public String getImageUrl(){
        if(getCommondity() == null || getCommondity().getAdvertiseImage() == null
                || StringUtil.isEmpty(getCommondity().getAdvertiseImage().getZipFileUrl())){
            return "";
        }
        return getCommondity().getAdvertiseImage().getZipFileUrl();
    }

    @Bindable
    public String getPirceStr(){
        if(getCommondity() == null || StringUtil.isEmpty(getCommondity().getPrice())
                || Double.valueOf(getCommondity().getPrice()) == 0){
            return "暂无";
        }
        return BaseApplication.getInstance().getString(R.string.commodity_price_format,getCommondity().getPrice());
    }

    public void onValidateClick(View view){
        if(ActivityManager.getInstance().getCurrentActivity() != null){
            // 创建构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityManager.getInstance().getCurrentActivity());
            String vilidateMsg = MyShareDbHelper.getString(SPKeys.SPKEY_VALIDATEINFO,"请到官方进行认证");
            // 设置参数
            builder.setTitle("提示")
                    .setMessage(vilidateMsg)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }
}
