package com.jf.gasscaner.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
    protected Handler handler;

    protected void initID() {
        iid2Service = IDManager.getInstance();
        try {
            boolean result = iid2Service.initDev(this, new IDReadCallBack() {
                @Override
                public void callBack(IDInfor infor) {
                    Message message = new Message();
                    message.obj = infor;
                    handler.sendMessage(message);
                }
            });
            if (!result) {
                new AlertDialog.Builder(this).setCancelable(false).setMessage("二代证模块初始化失败")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isAutoScan = false;
                            }
                        }).show();
            } else {
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
                    long left_time = System.currentTimeMillis() - startTime;
                    Log.d("Reginer", "耗时：: " + left_time+"ms");
                    startTime = System.currentTimeMillis();
                    iid2Service.getIDInfor(false, isAutoScan);
                    IDInfor idInfor = (IDInfor) msg.obj;
                    if (idInfor.isSuccess()) {
                        Log.d("Reginer", "read success time is: " + left_time);
                        PlaySoundUtils.play(1,1);
                        handleIDInfo(idInfor);
                        //tvIDInfor.setText("姓名:" + idInfor1.getName() + "\n身份证号：" + idInfor1.getNum()
                        //        + "\n性别：" + idInfor1.getSex()
                        //        + "\n民族：" + idInfor1.getNation() + "\n住址:"
                        //        + idInfor1.getAddress() + "\n出生：" + idInfor1.getYear() + "年" + idInfor1
                        //        .getMonth() + "月" + idInfor1.getDay() + "日" + "\n有效期限：" + idInfor1
                        //        .getDeadLine());
                        //Bitmap bmps = idInfor1.getBmps();
                        //imgPic.setImageBitmap(bmps);
                        //tvMsg.setText("");
                    } else {
                        Toast.makeText(ScanActivity.this,String.format("ERROR:%s", idInfor.getErrorMsg()),Toast.LENGTH_SHORT);
                    }
                }
            };
        }
        return handler;
    }

    protected  abstract void handleIDInfo(IDInfor idInfor);
}
