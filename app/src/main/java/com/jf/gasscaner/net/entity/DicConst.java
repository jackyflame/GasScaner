package com.jf.gasscaner.net.entity;

import com.haozi.baselibrary.utils.StringUtil;

/**
 * Created by Haozi on 2017/8/18.
 */
public class DicConst {

    /**加油站点*/
    public enum GasSite{
        /**农机加油站*/
        NJ(1,"农机加油站"),
        /**中信加油站*/
        ZX(2,"中信加油站"),
        /**川藏路中石油加油站*/
        CZLZSY(3,"川藏路中石油加油站"),
        /**雅桥中石油加油站*/
        YQZSY(4,"雅桥中石油加油站"),
        /**雅桥鸿运加油站*/
        YQHY(5,"雅桥鸿运加油站");

        private int mValue;
        private String mName;

        private GasSite(int value,String name){
            mValue = value;
            mName = name;
        }

        public int getValue(){
            return mValue;
        }

        public String getName(){
            return mName;
        }

        public static GasSite ValueOf(int typecode) {
            //获取所有枚举
            GasSite[] arr = GasSite.values();
            if(arr == null || arr.length == 0){
                return null;
            }
            //遍历获取数据
            for(GasSite type : arr){
                if(type.mValue == typecode){
                    return type;
                }
            }
            return null;
        }

        public static GasSite ValueOf(String typecode) {
            if(StringUtil.isEmpty(typecode) || !StringUtil.isInteger(typecode)){
                return null;
            }
            return ValueOf(Integer.valueOf(typecode));
        }
    }

    /**加油种类*/
    public enum GasType{
        QY92(1,"汽油92#"),
        QY95(2,"汽油95#"),
        QY98(3,"汽油98#"),
        CY0(4,"柴油0#"),
        CY10(5,"柴油10#"),
        CY20(6,"柴油20#"),
        QT(9,"其他");

        private int mValue;
        private String mName;

        private GasType(int value,String name){
            mValue = value;
            mName = name;
        }

        public int getValue(){
            return mValue;
        }

        public String getName(){
            return mName;
        }

        public static GasType ValueOf(int typecode) {
            //获取所有枚举
            GasType[] arr = GasType.values();
            if(arr == null || arr.length == 0){
                return null;
            }
            //遍历获取数据
            for(GasType type : arr){
                if(type.mValue == typecode){
                    return type;
                }
            }
            return null;
        }

        public static GasType ValueOf(String typecode) {
            if(StringUtil.isEmpty(typecode) || !StringUtil.isInteger(typecode)){
                return null;
            }
            return ValueOf(Integer.valueOf(typecode));
        }
    }

    /**加油种类*/
    public enum CarType{
        PTQC(1,"普通汽车"),
        GWC(2,"公务车"),
        GCC(3,"工程车"),
        MTC(4,"摩托车"),
        SZY(5,"散装油");

        private int mValue;
        private String mName;

        private CarType(int value,String name){
            mValue = value;
            mName = name;
        }

        public int getValue(){
            return mValue;
        }

        public String getName(){
            return mName;
        }

        public static CarType ValueOf(int typecode) {
            //获取所有枚举
            CarType[] arr = CarType.values();
            if(arr == null || arr.length == 0){
                return null;
            }
            //遍历获取数据
            for(CarType type : arr){
                if(type.mValue == typecode){
                    return type;
                }
            }
            return null;
        }

        public static CarType NameOf(String name) {
            if(StringUtil.isEmpty(name)){
                return null;
            }
            //获取所有枚举
            CarType[] arr = CarType.values();
            if(arr == null || arr.length == 0){
                return null;
            }
            //遍历获取数据
            for(CarType type : arr){
                if(type.mName.equals(name)){
                    return type;
                }
            }
            return null;
        }
    }
}
