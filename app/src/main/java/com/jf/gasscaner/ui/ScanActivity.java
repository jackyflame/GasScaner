package com.jf.gasscaner.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jf.gasscaner.base.BaseDBActivity;
import com.jf.gasscaner.base.vm.BaseVM;

import java.io.IOException;

/**
 * Created by jackyflame on 2017/8/11.
 */

public class ScanActivity<X extends ViewDataBinding,T extends BaseVM> extends BaseDBActivity<X,T> {

    private long startTime;
//    private IID2Service iid2Service;
//
//    private void initID() {
//        iid2Service = IDManager.getInstance();
//        try {
//            boolean result = iid2Service.initDev(this, new IDReadCallBack() {
//                @Override
//                public void callBack(IDInfor infor) {
//                    Message message = new Message();
//                    message.obj = infor;
//                    handler.sendMessage(message);
//                }
//            });
//
////            tvInfor.setText(String.format("s:%s b:115200 p:%s",
////                    DeviceType.getSerialPort().substring(DeviceType.getSerialPort().length() - 6,
////                            DeviceType.getSerialPort().length()),
////                    Arrays.toString(DeviceType.getGpio()).replace("[", "").replace("]", "")));
//            if (!result) {
//                new AlertDialog.Builder(this).setCancelable(false).setMessage("二代证模块初始化失败")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                btnGet.setEnabled(false);
//                            }
//                        }).show();
//            } else {
//                showToast("初始化成功");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SuppressLint("HandlerLeak")
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            long left_time = System.currentTimeMillis() - startTime;
//            Log.d("Reginer", "time is: " + left_time);
//            startTime = System.currentTimeMillis();
//
//            iid2Service.getIDInfor(false, btnGet.isChecked());
////            clearUI();
//            IDInfor idInfor1 = (IDInfor) msg.obj;
//
////            showToast("ok");
//            if (idInfor1.isSuccess()) {
//                Log.d("Reginer", "read success time is: " + left_time);
//                PlaySoundUtils.play(1,1);
//                tvTime.setText("耗时："+left_time+"ms");
//                tvIDInfor.setText("姓名:" + idInfor1.getName() + "\n身份证号：" + idInfor1.getNum()
//                        + "\n性别：" + idInfor1.getSex()
//                        + "\n民族：" + idInfor1.getNation() + "\n住址:"
//                        + idInfor1.getAddress() + "\n出生：" + idInfor1.getYear() + "年" + idInfor1
//                        .getMonth() + "月" + idInfor1.getDay() + "日" + "\n有效期限：" + idInfor1
//                        .getDeadLine());
//                Bitmap bmps = idInfor1.getBmps();
//                imgPic.setImageBitmap(bmps);
//                tvMsg.setText("");
//            } else {
//                tvMsg.setText(String.format("ERROR:%s", idInfor1.getErrorMsg()));
//            }
//        }
//    };
}
