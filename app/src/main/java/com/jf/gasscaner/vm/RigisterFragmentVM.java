package com.jf.gasscaner.vm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
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
import com.jf.gasscaner.net.entity.DicConst;
import com.jf.gasscaner.net.entity.FuelCardEntity;
import com.jf.gasscaner.net.entity.GasRecordEntity;
import com.jf.gasscaner.net.entity.ImageEntity;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.gasscaner.ui.RigisterFragment;
import com.speedata.libid2.IDInfor;

import java.io.File;
import java.util.Calendar;

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
    private String[] platetypeNameArray;
    /**油类型列表*/
    private String[] gastypeArray;
    private String[] gastypeNameArray;

    private String[] nationArray;

    private IDInfor idInfor;
    private GasRecordEntity gasRecordEntity;
    private int requestTime = 0;

    private long lastScanTime;
    private static final long ScanScale = 1000 * 10;

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
                        if(gasRecordEntity != null){
                            //保存选择字母编号
                            gasRecordEntity.setPlateFirstNum(provinceArray[position]);
                            notifyPropertyChanged(BR.gasRecordEntity);
                        }
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
                        if(itemArray.length >= 2 && gasRecordEntity != null){
                            gasRecordEntity.setCarTypeName(itemArray[0]);
                            gasRecordEntity.setCarType(itemArray[1]);
                            //散装油
                            if(gasRecordEntity.getCarType().equals("5")){
                                gasRecordEntity.setPlateType(null);
                                gasRecordEntity.setPlateTypeName(null);
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
            platetypeNameArray = new String[platetypeArray.length];
            for (int i=0;i<platetypeArray.length;i++) {
                String[] itemArray = platetypeArray[i].split(",");
                if(itemArray.length > 0){
                    platetypeNameArray[i] = itemArray[0];
                }else{
                    platetypeNameArray[i] = "--";
                }
            }
        }
        new AlertDialog.Builder(activity)
                .setItems(platetypeNameArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        String[] itemArray = platetypeArray[position].split(",");
                        //刷新数据
                        if(itemArray.length >= 2 && gasRecordEntity != null) {
                            gasRecordEntity.setPlateTypeName(itemArray[0]);
                            gasRecordEntity.setPlateType(itemArray[1]);
                        }
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
            gastypeNameArray = new String[gastypeArray.length];
            for (int i=0;i<gastypeArray.length;i++) {
                String[] itemArray = gastypeArray[i].split(",");
                if(itemArray.length > 0){
                    gastypeNameArray[i] = itemArray[0];
                }else{
                    gastypeNameArray[i] = "--";
                }
            }
        }
        new AlertDialog.Builder(activity)
                .setItems(gastypeNameArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        String[] itemArray = gastypeArray[position].split(",");
                        //刷新数据
                        if(itemArray.length >= 2 && gasRecordEntity != null) {
                            gasRecordEntity.setGasTypeName(itemArray[0]);
                            gasRecordEntity.setGasType(itemArray[1]);
                        }
                        notifyPropertyChanged(BR.gasRecordEntity);
                    }
                })
                .create()
                .show();
    }

    public void onUploadRecordClick(View view){
        if(checkIdinfoEmpty()){
            Toast.makeText(activity,"请扫描或者填写完整的被登记人信息",Toast.LENGTH_SHORT).show();
            return;
        }
        if(gasRecordEntity == null){
            Toast.makeText(activity,"获取登记信息失败",Toast.LENGTH_SHORT).show();
            return;
        }else{
            gasRecordEntity.setIdInfo(getIdInfor());
        }
        if(StringUtil.isEmpty(gasRecordEntity.getGasType())){
            Toast.makeText(activity,"请选择油品种类",Toast.LENGTH_SHORT).show();
            return;
        }
        if(gasRecordEntity.getGasMount()<=0){
            Toast.makeText(activity,"请填写加油量",Toast.LENGTH_SHORT).show();
            return;
        }
        if(gasRecordEntity.getGasMount()>=10000){
            Toast.makeText(activity,"加油量超限，请重新输入",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtil.isEmpty(gasRecordEntity.getImage())){
            Toast.makeText(activity,"请采集照片",Toast.LENGTH_SHORT).show();
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
                fragment.cleanPic();
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

    private boolean checkIdinfoEmpty(){
        if(idInfor == null){
            return true;
        }
        if(StringUtil.isEmpty(idInfor.getNum()) || StringUtil.isEmpty(idInfor.getName())
                || StringUtil.isEmpty(idInfor.getSex())|| StringUtil.isEmpty(idInfor.getNation())){
            return true;
        }
        return false;
    }

    public void onImageTakeClick(View view){
        SystemUtil.takePicture(fragment,fragment.INPUT_CONTENT_TACKPIC);
        //SystemUtil.addImage(fragment,fragment.INPUT_CONTENT_ADDPIC);
    }

    public void onSexClick(View view){
        new AlertDialog.Builder(activity)
                .setItems(new String[]{"男","女"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                       if(position == 0){
                            getIdInfor().setSex("男");
                       }else{
                           getIdInfor().setSex("女");
                       }
                       notifyPropertyChanged(BR.idInfor);
                    }
                })
                .create()
                .show();
    }

    public void onNationClick(View view){
        if(nationArray == null){
            nationArray = activity.getResources().getStringArray(R.array.nation_items);
        }
        new AlertDialog.Builder(activity)
                .setItems(nationArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        //保存选择字母编号
                        String nation = nationArray[position];
                        getIdInfor().setNation(nation);
                        //刷新数据
                        notifyPropertyChanged(BR.idInfor);
                        //关联加油数据
                        if(getGasRecordEntity() == null){
                            GasRecordEntity newRecord = new GasRecordEntity();
                            newRecord.setIdInfo(getIdInfor());
                            setGasRecordEntity(newRecord);
                        }
                    }
                })
                .create()
                .show();
    }

    public void onBirthdayClick(View view){
        int year = 2017;
        int month = 1;
        int day = 1;
        if(idInfor != null && !StringUtil.isEmpty(idInfor.getYear())){
            year = Integer.valueOf(idInfor.getYear());
            month = Integer.valueOf(idInfor.getMonth());
            day = Integer.valueOf(idInfor.getDay());
        }
        MyDatePickerDialog dialog = new MyDatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                idInfor.setYear(String.valueOf(year));
                idInfor.setMonth(String.valueOf(monthOfYear+1));
                idInfor.setDay(String.valueOf(dayOfMonth));
                notifyPropertyChanged(BR.birthday);
            }
        },year,month-1,day);
        dialog.show();
    }

    @Bindable
    public IDInfor getIdInfor() {
        if(idInfor == null){
            idInfor = new IDInfor();
        }
        return idInfor;
    }

    public void setIdInfor(IDInfor idInfor) {
        if(idInfor == null){
            this.idInfor = new IDInfor();
        }else{
            this.idInfor = idInfor;
        }
        //刷新加油信息
        GasRecordEntity newRecord = new GasRecordEntity();
        newRecord.setIdInfo(idInfor);
        setGasRecordEntity(newRecord);
        //刷新页面
        notifyPropertyChanged(BR.idInfor);
        notifyPropertyChanged(BR.birthday);
        notifyPropertyChanged(BR.address);
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
        mPrensent.uploadPhoto(pic, new ReqCallback<ImageEntity>() {
            @Override
            public void onReqStart() {
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(ImageEntity response) {
                fragment.dismissProgressDialog();
                if(gasRecordEntity != null && response != null){
                    gasRecordEntity.setImage(response.getId());
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

    public void scanResult(IDInfor idInforNew) {
        if((System.currentTimeMillis() - lastScanTime < ScanScale) && idInfor != null
                && idInfor.getNum() != null && idInfor.getNum().equals(idInforNew.getNum())){
            ViewUtils.Toast(activity,"请不要重复刷卡");
            return;
        }

        setIdInfor(idInforNew);
        lastScanTime = System.currentTimeMillis();

        //上传身份证头像
        uploadIdHeader();

        //查询数据库登记记录
        refreshRegisterInfo();
    }

    public void refreshCardInfo(FuelCardEntity response){
        if(gasRecordEntity == null){
            gasRecordEntity = new GasRecordEntity();
        }
        if(response != null){
            //加油卡号
            gasRecordEntity.setCardNum(response.getJykh());
            //燃油类型
            gasRecordEntity.setGasType(response.getGyzl());
            DicConst.GasType gasType = DicConst.GasType.ValueOf(response.getGyzl());
            if(gasType != null){
                gasRecordEntity.setGasTypeName(gasType.getName());
            }
            //车牌号
            gasRecordEntity.setPlateNum(response.getHm());
            //车型
            DicConst.CarType carType = DicConst.CarType.ValueOf(response.getJyklx());
            if(carType != null){
                gasRecordEntity.setCarTypeName(carType.getName());
                gasRecordEntity.setCarType(String.valueOf(carType.getValue()));
            }
        }
        gasRecordEntity.setIdInfo(idInfor);
        setGasRecordEntity(gasRecordEntity);
    }

    public void uploadIdHeader(){
        if(idInfor == null){
            ViewUtils.Toast(activity,"请重新扫描身份证");
            return;
        }
        boolean isSuccess = BitmapUtils.writeBmpToSDCard(idInfor.getBmps(),FileUtil.PROJECT_IMAGE_HEADER_CACHE,100);
        if(isSuccess){
            mPrensent.uploadPhoto(FileUtil.PROJECT_IMAGE_HEADER_CACHE, new ReqCallback<ImageEntity>() {
                @Override
                public void onReqStart() {
                    requestTime++;
                    fragment.showProgressDialog();
                }
                @Override
                public void onNetResp(ImageEntity response) {
                    requestTime--;
                    if(requestTime <= 0){
                        fragment.dismissProgressDialog();
                    }
                    if(response != null && !StringUtil.isEmpty(response.getId())){
                        gasRecordEntity.setHeaderImg(response.getId());
                    }
                }
                @Override
                public void onReqError(HttpEvent httpEvent) {
                    requestTime--;
                    if(requestTime <= 0){
                        fragment.dismissProgressDialog();
                    }
                    ViewUtils.Toast(activity,"上传身份证头像失败，请重新扫描身份证上传");
                }
            });
        }
    }

    public void refreshRegisterInfo(){
        if(idInfor == null){
            ViewUtils.Toast(activity,"请重新扫描身份证");
            return;
        }
        mPrensent.verify(idInfor, new ReqCallback<FuelCardEntity>() {
            @Override
            public void onReqStart() {
                requestTime++;
                fragment.showProgressDialog();
            }
            @Override
            public void onNetResp(FuelCardEntity response) {
                requestTime--;
                if(requestTime <= 0){
                    fragment.dismissProgressDialog();
                }
                //刷新加油信息
                refreshCardInfo(response);
            }
            @Override
            public void onReqError(HttpEvent httpEvent) {
                requestTime--;
                if(requestTime <= 0){
                    fragment.dismissProgressDialog();
                }
                if(httpEvent.getCode() != ErrorType.ERROR_CHECK_BLACKLIST && httpEvent.getCode() != ErrorType.ERROR_CHECK_UNREGISTER){
                    ViewUtils.Toast(activity,"查询失败，请重新扫描身份证查询");
                }
            }
        });
    }

    @Bindable
    public String getAddress(){
        if(idInfor == null){
            return "";
        }
        return idInfor.getAddress();
    }

    public void setAddress(String address){
        if(idInfor == null){
            idInfor = new IDInfor();
        }
        idInfor.setAddress(address);
    }
}
