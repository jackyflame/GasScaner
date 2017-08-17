package com.jf.gasscaner.vm;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.net.config.ErrorType;
import com.haozi.baselibrary.net.retrofit.ReqCallback;
import com.haozi.baselibrary.utils.BitmapUtils;
import com.haozi.baselibrary.utils.FileUtil;
import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.baselibrary.utils.SystemUtil;
import com.haozi.baselibrary.utils.ViewUtils;
import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.db.UserPresent;
import com.jf.gasscaner.net.entity.GasRecordEntity;
import com.jf.gasscaner.ui.RigisterFragment;
import com.speedata.libid2.IDInfor;

import java.io.File;

/**
 * Created by admin on 2017/8/9.
 */

public class RigisterFragmentVM extends BaseVM<UserPresent> implements TextView.OnEditorActionListener {

    private Activity activity;
    private RigisterFragment fragment;
    /**车牌汉字列表*/
    private String[] provinceArray;
    /**车辆类型列表*/
    private String[] cartypeArray;
    private String[] cartypeNameArray;
    /**车牌类型列表*/
    private String[] platetypeArray;
    /**油类型列表*/
    private String[] gastypeArray;

    private IDInfor idInfor;
    private GasRecordEntity gasRecordEntity;
    private int requestTime = 0;

    public RigisterFragmentVM(RigisterFragment fragment) {
        super(new UserPresent());
        this.activity = fragment.getActivity();
        this.fragment = fragment;
    }

    public void onPlateFirstClick(View view){
        if(provinceArray == null){
            provinceArray = activity.getResources().getStringArray(R.array.car_mark_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(provinceArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        gasRecordEntity.setPlateFirstNum(provinceArray[position]);
                        notifyPropertyChanged(BR.gasRecordEntity);
                    }
                })
                .create()
                .show();
    }

    public void onCarTypeClick(View view){
        if(cartypeArray == null){
            cartypeArray = activity.getResources().getStringArray(R.array.car_type_items);
            cartypeNameArray = new String[cartypeArray.length];
            for (int i=0;i<cartypeArray.length;i++) {
                String[] itemArray = cartypeArray[i].split(",");
                if(itemArray.length > 0){
                    cartypeNameArray[i] = itemArray[0];
                }else{
                    cartypeNameArray[i] = "--";
                }
            }
        }
        new AlertDialog.Builder(activity).setItems(cartypeNameArray,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        String[] itemArray = cartypeArray[position].split(",");
                        if(itemArray.length >= 2){
                            gasRecordEntity.setCarTypeName(itemArray[0]);
                            gasRecordEntity.setCarType(itemArray[1]);
                            //散装油
                            if(gasRecordEntity.getCarType().equals("5")){
                                gasRecordEntity.setPlateType(null);
                                gasRecordEntity.setPlateTypeName(null);
                                gasRecordEntity.setCardNum(null);
                            }
                            notifyPropertyChanged(BR.gasRecordEntity);
                            notifyPropertyChanged(BR.plateTypeVisible);
                            notifyPropertyChanged(BR.plateVisible);
                        }
                    }
                })
                .create()
                .show();
    }

    public void onPlateTypetClick(View view){
        if(platetypeArray == null){
            platetypeArray = activity.getResources().getStringArray(R.array.plate_type_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(platetypeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //刷新数据
                        gasRecordEntity.setPlateTypeName(platetypeArray[position]);
                        notifyPropertyChanged(BR.gasRecordEntity);
                        notifyPropertyChanged(BR.plateFirstVisible);
                    }
                })
                .create()
                .show();
    }

    public void onGasTypetClick(View view){
        if(gastypeArray == null){
            gastypeArray = activity.getResources().getStringArray(R.array.gas_type_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(gastypeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //刷新数据
                        gasRecordEntity.setGasType(gastypeArray[position]);
                        notifyPropertyChanged(BR.gasRecordEntity);
                    }
                })
                .create()
                .show();
    }

    public void onUploadRecordClick(View view){
        if(gasRecordEntity == null){
            Toast.makeText(activity,"获取登记信息失败",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtil.isEmpty(gasRecordEntity.getGasType())){
            Toast.makeText(activity,"请选择油品种类",Toast.LENGTH_SHORT).show();
            return;
        }
        if(gasRecordEntity.getGasMount()<=0){
            Toast.makeText(activity,"请填写加油量",Toast.LENGTH_SHORT).show();
            return;
        }
        mPrensent.saveGasRecord(gasRecordEntity, new ReqCallback<String>() {
            @Override
            public void onReqStart() {
                fragment.showProgressDialog();
            }

            @Override
            public void onNetResp(String response) {
                fragment.dismissProgressDialog();
                ViewUtils.showMsgDialog(activity,"登记成功");
                //清空记录
                setIdInfor(null);
                setGasRecordEntity(null);
            }

            @Override
            public void onReqError(HttpEvent httpEvent) {
                fragment.dismissProgressDialog();
                String msg = "登记失败";
                if(!StringUtil.isEmpty(httpEvent.getMessage())){
                    msg = msg+","+httpEvent.getMessage();
                }
                ViewUtils.Toast(activity,msg);
            }
        });
    }

    public void onImageTakeClick(View view){
        SystemUtil.takePicture(fragment,fragment.INPUT_CONTENT_TACKPIC);
        //SystemUtil.addImage(fragment,fragment.INPUT_CONTENT_ADDPIC);
    }

    @Bindable
    public IDInfor getIdInfor() {
        return idInfor;
    }

    public void setIdInfor(IDInfor idInfor) {
        this.idInfor = idInfor;
        //刷新加油信息
        GasRecordEntity newRecord = new GasRecordEntity();
        newRecord.setIdInfo(idInfor);
        setGasRecordEntity(newRecord);
        //刷新页面
        notifyPropertyChanged(BR.idInfor);
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getBirthday(){
        if(idInfor == null || StringUtil.isEmpty(idInfor.getYear())){
            return "";
        }
        return activity.getResources().getString(R.string.id_birthday_format,idInfor.getYear(),idInfor.getMonth(),idInfor.getDay());
    }

    @Bindable
    public GasRecordEntity getGasRecordEntity() {
        return gasRecordEntity;
    }

    public void setGasRecordEntity(GasRecordEntity gasRecordEntity) {
        this.gasRecordEntity = gasRecordEntity;
        notifyPropertyChanged(BR.gasRecordEntity);
    }

    /**
     * 车牌类型
     * */
    @Bindable
    public boolean getPlateTypeVisible(){
        //散装油
        if(gasRecordEntity != null && gasRecordEntity.getCarType() != null && gasRecordEntity.getCarType().equals("5")){
            return false;
        }
        return true;
    }

    /**
     * 车牌号首汉字
     * */
    @Bindable
    public boolean getPlateFirstVisible(){
        //散装油
        if(gasRecordEntity != null && gasRecordEntity.getCarType() != null && gasRecordEntity.getCarType().equals("5")){
            return false;
        }else if(gasRecordEntity != null && gasRecordEntity.getPlateTypeName() != null && gasRecordEntity.getPlateTypeName().equals("其他")){
            return false;
        }
        return true;
    }

    /**
     * 车牌号
     * */
    @Bindable
    public boolean getPlateVisible(){
        //散装油
        if(gasRecordEntity != null && gasRecordEntity.getCarType() != null && gasRecordEntity.getCarType().equals("5")){
            return false;
        }
        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        //点击完成，下一步操作
        if (event != null && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT
                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            fragment.hideKeyboard();
        }
        return false;
    }

    public void uploadImage(File pic) {
        mPrensent.uploadPhoto(pic, new ReqCallback<String>() {
            @Override
            public void onReqStart() {
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(String response) {
                fragment.dismissProgressDialog();
                if(gasRecordEntity != null){
                    gasRecordEntity.setImage(response);
                }
            }
            @Override
            public void onReqError(HttpEvent httpEvent) {
                fragment.dismissProgressDialog();
                ViewUtils.Toast(activity,"上传失败："+httpEvent.getMessage());
                fragment.cleanPic();
            }
        });
    }

    public void scanResult() {
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

        //上传身份证头像
        uploadIdHeader();

        //查询数据库登记记录
        refreshRegisterInfo();
    }

    public void refreshCardInfo(){
        gasRecordEntity.setIdInfo(idInfor);
        gasRecordEntity.setCardNum("T1234");
        setGasRecordEntity(gasRecordEntity);
    }

    public void uploadIdHeader(){
        if(idInfor == null){
            ViewUtils.Toast(activity,"请重新扫描身份证");
            return;
        }
        boolean isSuccess = BitmapUtils.writeBmpToSDCard(idInfor.getBmps(),FileUtil.PROJECT_IMAGE_HEADER_CACHE,100);
        if(isSuccess){
            mPrensent.uploadPhoto(FileUtil.PROJECT_IMAGE_HEADER_CACHE, new ReqCallback<String>() {
                @Override
                public void onReqStart() {
                    requestTime++;
                    fragment.showProgressDialog();
                }
                @Override
                public void onNetResp(String response) {
                    requestTime--;
                    if(requestTime <= 0){
                        fragment.dismissProgressDialog();
                    }
                    gasRecordEntity.setHeaderImg(response);
                }
                @Override
                public void onReqError(HttpEvent httpEvent) {
                    requestTime--;
                    if(requestTime <= 0){
                        fragment.dismissProgressDialog();
                    }
                }
            });
        }
    }

    public void refreshRegisterInfo(){
        if(idInfor == null){
            ViewUtils.Toast(activity,"请重新扫描身份证");
            return;
        }
        mPrensent.verify(idInfor, new ReqCallback<String>() {
            @Override
            public void onReqStart() {
                requestTime++;
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(String response) {
                requestTime--;
                if(requestTime <= 0){
                    fragment.dismissProgressDialog();
                }
                //刷新加油信息
                refreshCardInfo();
            }
            @Override
            public void onReqError(HttpEvent httpEvent) {
                requestTime--;
                if(requestTime <= 0){
                    fragment.dismissProgressDialog();
                }
            }
        });
    }
}
