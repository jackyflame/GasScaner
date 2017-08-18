package com.jf.gasscaner.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.serialport.DeviceControl;
import android.serialport.SerialPort;
import android.util.Log;
import android.widget.Toast;

import com.jf.gasscaner.base.BaseDBActivity;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.scanerlib.PlaySoundUtils;
import com.speedata.libid2.IDInfor;
import com.speedata.libid2.IDManager;
import com.speedata.libid2.IDReadCallBack;
import com.speedata.libid2.IID2Service;

import java.io.IOException;

/**
 * Created by jackyflame on 2017/8/11.
 */

public abstract class ScanActivity<X extends ViewDataBinding,T extends BaseVM> extends BaseDBActivity<X,T> {

    private long startTime;
    protected IID2Service iid2Service;
    protected boolean isAutoScan;
    protected boolean isScanEnable;
    protected Handler handler;

    protected void initID() {
        iid2Service = IDManager.getInstance();
        getHandler();
        try {
            boolean result = iid2Service.initDev(this, new IDReadCallBack() {
                @Override
                public void callBack(IDInfor infor) {
                    Message message = new Message();
                    message.obj = infor;
                    getHandler().sendMessage(message);
                }
            }, SerialPort.SERIAL_TTYMT1,115200, DeviceControl.PowerType.MAIN,94);
            if (!result) {
                new AlertDialog.Builder(this).setCancelable(false).setMessage("二代证模块初始化失败")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isScanEnable = false;
                            }
                        }).show();
            } else {
                showToast("初始化成功");
                isScanEnable = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    protected Handler getHandler(){
        if(handler == null){
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    long left_time = System.currentTimeMillis() - startTime;
                    Log.d("Reginer", "耗时：: " + left_time+"ms");
                    startTime = System.currentTimeMillis();
                    iid2Service.getIDInfor(false,isAutoScan());
                    IDInfor idInfor = (IDInfor) msg.obj;
                    if (idInfor.isSuccess()) {
                        Log.d("Reginer", "read success time is: " + left_time);
                        PlaySoundUtils.play(1,1);
                        handleIDInfo(idInfor);
                    } else {
                        Toast.makeText(ScanActivity.this,String.format("ERROR:%s", idInfor.getErrorMsg()),Toast.LENGTH_SHORT);
                    }
                }
            };
        }
        return handler;
    }

    protected  abstract void handleIDInfo(IDInfor idInfor);

    @Override
    protected void onDestroy() {
        try {
            if (iid2Service != null)
                iid2Service.releaseDev();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public boolean isAutoScan() {
        return isAutoScan;
    }

    public void setAutoScan(boolean autoScan) {
        isAutoScan = autoScan;
    }

    public boolean isScanEnable() {
        return isScanEnable;
    }

    public void runScan(boolean isAutoScan){
        setAutoScan(isAutoScan);
        iid2Service.getIDInfor(false, isAutoScan());
    }
}
