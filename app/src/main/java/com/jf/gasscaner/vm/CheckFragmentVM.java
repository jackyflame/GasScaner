package com.jf.gasscaner.vm;

import android.app.Activity;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;

import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.net.config.ErrorType;
import com.haozi.baselibrary.net.retrofit.ReqCallback;
import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.baselibrary.utils.ViewUtils;
import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.ui.CheckFragment;
import com.speedata.libid2.IDInfor;

/**
 * Created by admin on 2017/8/9.
 */

public class CheckFragmentVM extends BaseVM<UserPresent>{

    private Activity activity;
    private CheckFragment fragment;
    private IDInfor idInfor;
    private int mark;

    public CheckFragmentVM(CheckFragment fragment) {
        super(new UserPresent());
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }

    @Bindable
    public IDInfor getIdInfor() {
        return idInfor;
    }

    public void setIdInfor(IDInfor idInfor) {
        this.idInfor = idInfor;
        notifyPropertyChanged(BR.idInfor);
        notifyPropertyChanged(BR.birthday);
    }

    public void scanReslt(){
        idInfor = new IDInfor();
        idInfor.setName("张三");
        idInfor.setNum("510622198805052211");
        idInfor.setSex("男");
        idInfor.setNation("汉族");
        idInfor.setAddress("四川省成都市成华区将军路223号");
        idInfor.setYear("1988");
        idInfor.setMonth("05");
        idInfor.setDay("05");
        Bitmap bmp= BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        idInfor.setBmps(bmp);

        setIdInfor(idInfor);

        mPrensent.verify(idInfor, new ReqCallback<String>() {
            @Override
            public void onReqStart() {
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(String response) {
                fragment.dismissProgressDialog();
                setMark(R.mipmap.ic_check_ok);
            }
            @Override
            public void onReqError(HttpEvent httpEvent) {
                fragment.dismissProgressDialog();
                if(httpEvent.getCode() == ErrorType.ERROR_CHECK_UNREGISTER){
                    setMark(R.mipmap.ic_check_unregiter);
                }else if(httpEvent.getCode() == ErrorType.ERROR_CHECK_BLACKLIST){
                    setMark(R.mipmap.ic_check_blacklist);
                }else{
                    ViewUtils.Toast(activity,httpEvent.getMessage());
                }
            }
        });
    }

    @Bindable
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
        notifyPropertyChanged(BR.mark);
    }

    @Bindable
    public String getBirthday(){
        if(idInfor == null || StringUtil.isEmpty(idInfor.getYear())){
            return "";
        }
        return activity.getResources().getString(R.string.id_birthday_format,idInfor.getYear(),idInfor.getMonth(),idInfor.getDay());
    }
}
