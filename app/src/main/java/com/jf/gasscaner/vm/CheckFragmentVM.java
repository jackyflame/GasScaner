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
import com.haozi.baselibrary.utils.SystemUtil;
import com.haozi.baselibrary.utils.ViewUtils;
import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.net.entity.DicConst;
import com.jf.gasscaner.net.entity.FuelCardEntity;
import com.jf.gasscaner.net.entity.UserEntity;
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
    private FuelCardEntity fuelCard;
    private long lastScanTime;
    private static final long ScanScale = 1000 * 5;

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

    public void scanReslt(IDInfor idInforNew){
//        idInforNew = new IDInfor();
//        idInforNew.setName("张三");
//        idInforNew.setNum("123");
//        idInforNew.setSex("男");
//        idInforNew.setNation("汉族");
//        idInforNew.setAddress("四川省成都市成华区将军路223号");
//        idInforNew.setYear("1988");
//        idInforNew.setMonth("05");
//        idInforNew.setDay("05");
//        Bitmap bmp= BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
//        idInforNew.setBmps(bmp);

        //小于扫描间隔周期
        if((System.currentTimeMillis() - lastScanTime < ScanScale) && idInfor != null
                && idInfor.getNum() != null && idInfor.getNum().equals(idInforNew.getNum())){
            return;
        }

        setIdInfor(idInforNew);
        lastScanTime = System.currentTimeMillis();

        //查询数据
        mPrensent.verify(idInforNew, new ReqCallback<FuelCardEntity>() {
            @Override
            public void onReqStart() {
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(FuelCardEntity response) {
                fragment.dismissProgressDialog();
                setFuelCard(response);
                setMark(R.mipmap.ic_check_ok);
            }
            @Override
            public void onReqError(HttpEvent httpEvent) {
                fragment.dismissProgressDialog();
                setFuelCard(null);
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

    @Bindable
    public FuelCardEntity getFuelCard() {
        return fuelCard;
    }

    public void setFuelCard(FuelCardEntity fuelCard) {
        this.fuelCard = fuelCard;
        notifyPropertyChanged(BR.fuelCard);
    }

    @Bindable
    public String getUserName(){
        UserEntity userEntity = mPrensent.getUser();
        if(userEntity == null || StringUtil.isEmpty(userEntity.getJyz())){
            return "暂无";
        }
        return userEntity.getXm();
    }

    @Bindable
    public String getGasSite(){
        UserEntity userEntity = mPrensent.getUser();
        if(userEntity == null || StringUtil.isEmpty(userEntity.getJyz())){
            return "暂无";
        }
        DicConst.GasSite gasSite = DicConst.GasSite.ValueOf(userEntity.getJyz());
        if(gasSite != null){
            return gasSite.getName();
        }
        return "暂无";
    }
}
