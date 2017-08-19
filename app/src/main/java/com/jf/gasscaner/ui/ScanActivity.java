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
import com.speedata.libutils.ConfigUtils;
import com.speedata.libutils.ReadBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by jackyflame on 2017/8/11.
 */

public abstract class ScanActivity<X extends ViewDataBinding,T extends BaseVM> extends BaseDBActivity<X,T> {

    private long startTime;
    protected IID2Service iid2Service;
    protected boolean isAutoScan;
    protected boolean isScanEnable;
    protected Handler handler;

    private static final int MSG_SCAN_RESULT = 1000;
    private static final int MSG_SCAN_RESTART = 2000;

    protected void initID() {
        initID(false);
    }

    protected void initID(boolean isReinit) {
        PlaySoundUtils.initSoundPool(this);
        iid2Service = IDManager.getInstance();
        getHandler();
        try {
            boolean result = iid2Service.initDev(this, new IDReadCallBack() {
                @Override
                public void callBack(IDInfor infor) {
                    Message message = new Message();
                    message.obj = infor;
                    message.what = MSG_SCAN_RESULT;
                    getHandler().sendMessage(message);
                }
            }, SerialPort.SERIAL_TTYMT1,115200, DeviceControl.PowerType.MAIN,64);
            if (!result) {
                new AlertDialog.Builder(this).setCancelable(false).setMessage("二代证模块初始化失败")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isScanEnable = false;
                            }
                        }).show();
            } else {
                isScanEnable = true;
                if(isReinit){
                    getHandler().sendEmptyMessageDelayed(MSG_SCAN_RESTART,1000);
                }
                showToast("初始化成功");
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
                    if(msg.what == MSG_SCAN_RESULT){
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
                    }else if(msg.what == MSG_SCAN_RESTART){
                        runScan(isAutoScan());
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
            if (iid2Service != null){
                iid2Service.getIDInfor(false,false);
                iid2Service.releaseDev();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
        System.exit(0);
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

    private void showDeviceInfo(){
        boolean isExit = ConfigUtils.isConfigFileExists();
        StringBuffer info = new StringBuffer();
        if (isExit)
            info.append("定制配置：");
        else
            info.append("标准配置：");
        ReadBean.Id2Bean pasm = ConfigUtils.readConfig(this).getId2();
        String gpio = "";
        List<Integer> gpio1 = pasm.getGpio();
        for (Integer s : gpio1) {
            gpio += s + ",";
        }
        info.append("串口:" + pasm.getSerialPort())
                .append( "  波特率：" + pasm.getBraut() )
                .append( " 上电类型:" + pasm.getPowerType() )
                .append( " GPIO:" + gpio);

        Toast.makeText(this,info.toString(),Toast.LENGTH_LONG).show();
    }
}
