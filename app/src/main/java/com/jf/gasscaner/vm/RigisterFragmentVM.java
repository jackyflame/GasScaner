package com.jf.gasscaner.vm;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.speedata.libid2.IDInfor;

/**
 * Created by admin on 2017/8/9.
 */

public class RigisterFragmentVM extends BaseVM{

    private Activity activity;
    /**当前序列号字母列表*/
    private String[] provinceArray;
    private String carProvinceMark;

    private IDInfor idInfor;

    public RigisterFragmentVM(FragmentActivity activity) {
        this.activity = activity;

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
        //tvIDInfor.setText("姓名:" + idInfor1.getName() + "\n身份证号：" + idInfor1.getNum()
        //        + "\n性别：" + idInfor1.getSex()
        //        + "\n民族：" + idInfor1.getNation() + "\n住址:"
        //        + idInfor1.getAddress() + "\n出生：" + idInfor1.getYear() + "年" + idInfor1
        //        .getMonth() + "月" + idInfor1.getDay() + "日" + "\n有效期限：" + idInfor1
        //        .getDeadLine());
    }

    public void onCarBandClick(View view){
        if(provinceArray == null){
            provinceArray = activity.getResources().getStringArray(R.array.car_mark_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(provinceArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        carProvinceMark = provinceArray[position];
                        //刷新编码（选择字母的时候裁剪5位超长号码）

                        //刷新数据

                    }
                })
                .create()
                .show();
    }

    @Bindable
    public String getCarProvinceMark() {
        return carProvinceMark;
    }

    public void setCarProvinceMark(String carProvinceMark) {
        this.carProvinceMark = carProvinceMark;
        notifyPropertyChanged(BR.carProvinceMark);
    }

    @Bindable
    public IDInfor getIdInfor() {
        return idInfor;
    }

    public void setIdInfor(IDInfor idInfor) {
        this.idInfor = idInfor;
        notifyPropertyChanged(BR.idInfor);
    }
}
