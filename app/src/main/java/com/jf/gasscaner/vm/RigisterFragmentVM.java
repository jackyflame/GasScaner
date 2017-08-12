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

import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.baselibrary.utils.ViewUtils;
import com.jf.gasscaner.BR;
import com.jf.gasscaner.R;
import com.jf.gasscaner.base.vm.BaseVM;
import com.jf.gasscaner.net.entity.GasRecordEntity;
import com.jf.gasscaner.ui.RigisterFragment;
import com.speedata.libid2.IDInfor;

/**
 * Created by admin on 2017/8/9.
 */

public class RigisterFragmentVM extends BaseVM implements TextView.OnEditorActionListener {

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

    public RigisterFragmentVM(RigisterFragment fragment) {
        this.activity = fragment.getActivity();
        this.fragment = fragment;

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

        gasRecordEntity = new GasRecordEntity();
        gasRecordEntity.setIdInfo(idInfor);
        gasRecordEntity.setCardNum("T1234");
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
    }

    @Bindable
    public IDInfor getIdInfor() {
        return idInfor;
    }

    public void setIdInfor(IDInfor idInfor) {
        this.idInfor = idInfor;
        notifyPropertyChanged(BR.idInfor);
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
}
