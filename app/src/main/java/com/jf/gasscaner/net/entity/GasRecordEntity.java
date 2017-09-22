package com.jf.gasscaner.net.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cvr.device.IDCardInfo;
import com.haozi.baselibrary.net.entity.BaseNetEntity;
import com.haozi.baselibrary.utils.StringUtil;
import com.jf.gasscaner.R;
import com.speedata.libid2.IDInfor;

/**
 * Created by Haozi on 2017/8/12.
 */

public class GasRecordEntity extends BaseNetEntity {

    private String id;
    private String cardNum;
    private String image;
    private String carType;
    private String carTypeName;
    private String plateType;
    private String plateTypeName;
    private String plateNum;
    private String gasType;
    private String gasTypeName;
    private int gasMount;

    private String plateFirstNum;
    private String plateOtherNum;

    private String oporator;
    private String gasStation;

    //身份证信息
    private String name;
    private String sex;
    private String nation;//民族
    private String year;
    private String month;
    private String day;
    private String address;
    private String idNum;
    private String headerImg;

    private String birthday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getPlateTypeName() {
        return plateTypeName;
    }

    public void setPlateTypeName(String plateTypeName) {
        this.plateTypeName = plateTypeName;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public String getGasTypeName() {
        return gasTypeName;
    }

    public void setGasTypeName(String gasTypeName) {
        this.gasTypeName = gasTypeName;
    }

    public int getGasMount() {
        return gasMount;
    }

    public String getGasMountStr(){
        if(gasMount <= 0){
            return "";
        }
        return String.valueOf(gasMount);
    }

    public void setGasMount(int gasMount) {
        this.gasMount = gasMount;
    }

    public void setGasMountStr(String gasMount){
        if(StringUtil.isInteger(gasMount)){
            setGasMount(Integer.valueOf(gasMount));
        }else{
            setGasMount(0);
        }
    }

    public String getOporator() {
        return oporator;
    }

    public void setOporator(String oporator) {
        this.oporator = oporator;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public void setIdInfo(IDInfor idInfo){
        if(idInfo == null){
            setName(null);
            setIdNum(null);
            setSex(null);
            setNation(null);
            setAddress(null);
            setYear(null);
            setMonth(null);
            setDay(null);
        }else{
            setName(idInfo.getName());
            setIdNum(idInfo.getNum());
            setSex(idInfo.getSex());
            setNation(idInfo.getNation());
            setAddress(idInfo.getAddress());
            setYear(idInfo.getYear());
            setMonth(idInfo.getMonth());
            setDay(idInfo.getDay());
        }
        setBirthday(null);
    }

    public String getPlateFirstNum(){
        if(StringUtil.isEmpty(plateFirstNum)){
            if(StringUtil.isEmpty(plateNum)){
                plateFirstNum = "川";
            }else if(!StringUtil.isChinese(plateNum.substring(0,1))){
                plateFirstNum = "";
            }else{
                plateFirstNum = plateNum.substring(0,1);
            }
        }
        return plateFirstNum;
    }

    public void setPlateFirstNum(String plateFirstNum) {
        this.plateFirstNum = plateFirstNum;
    }

    public String getPlateOtherNum(){
        if(StringUtil.isEmpty(plateOtherNum)) {
            if (StringUtil.isEmpty(plateNum)) {
                plateOtherNum = "";
            //头字是汉字
            }else if (StringUtil.isChinese(plateNum.substring(0, 1))) {
                plateOtherNum = plateNum.substring(1);
            }else{
                plateOtherNum = plateNum;
            }
        }
        return plateOtherNum;
    }

    public void setPlateOtherNum(String plateOtherNum) {
        this.plateOtherNum = plateOtherNum;
    }

    public String getBirthday(){
        if(StringUtil.isEmpty(birthday)){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getYear()).append("年");
            stringBuffer.append(getMonth()).append("月");
            stringBuffer.append(getDay()).append("日");
            birthday = stringBuffer.toString();
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
