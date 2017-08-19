package com.jf.gasscaner.db;

import com.alibaba.fastjson.JSON;
import com.haozi.baselibrary.constants.SPKeys;
import com.haozi.baselibrary.db.BasePresent;
import com.haozi.baselibrary.db.MyShareDbHelper;
import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.net.config.ErrorType;
import com.haozi.baselibrary.net.retrofit.ReqCallback;
import com.haozi.baselibrary.utils.StringUtil;
import com.jf.gasscaner.net.entity.FuelCardEntity;
import com.jf.gasscaner.net.entity.GasRecordEntity;
import com.jf.gasscaner.net.entity.ImageEntity;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.gasscaner.net.worker.UserWorker;
import com.speedata.libid2.IDInfor;

import java.io.File;

/**
 * Created by Haozi on 2017/5/27.
 */
public class UserPresent extends BasePresent {

    /**静态单例初始化*/
    private static class SingletonHolder{
        /** 静态初始化器，由JVM来保证线程安全*/
        private static UserPresent instance = new UserPresent();
    }
    /**单例静态引用*/
    public static UserPresent getInstance(){
        return SingletonHolder.instance;
    }

    private final UserWorker userWorker;

    public UserPresent() {
        this.userWorker = new UserWorker();
    }

    public UserEntity getUser(){
        String userInfoStr = MyShareDbHelper.getString(SPKeys.SPKEY_USERINFO,"");
        if(StringUtil.isEmpty(userInfoStr)){
            return null;
        }
        UserEntity user = JSON.parseObject(userInfoStr,UserEntity.class);
        return user;
    }

    public void saveUser(UserEntity user){
        if(user == null){
            MyShareDbHelper.putString(SPKeys.SPKEY_USERINFO,"");
        }else{
            MyShareDbHelper.putString(SPKeys.SPKEY_USERINFO,JSON.toJSONString(user));
        }
    }


    public void cleanUserData() {
        MyShareDbHelper.putString(SPKeys.SPKEY_USERINFO,"");
    }

    public String getNowUserId(){
        UserEntity user = getUser();
        if(user == null){
            return null;
        }
        return user.getId();
    }

    public void login(final String username, String password, final ReqCallback<UserEntity> callback){
        userWorker.login(username, password, new ReqCallback<UserEntity>() {
            @Override
            public void onReqStart() {
                callback.onReqStart();
            }

            @Override
            public void onNetResp(UserEntity response) {
                if(response != null){
                    response.setUserName(username);
                }
                callback.onNetResp(response);
            }

            @Override
            public void onReqError(HttpEvent httpEvent) {
                callback.onReqError(httpEvent);
            }
        });
    }

    public void uploadPhoto(String filePath,ReqCallback<ImageEntity> callback){
        String nowUserId = getNowUserId();
        if(StringUtil.isEmpty(nowUserId)){
            callback.onReqError(new HttpEvent(ErrorType.ERROR_INVALID_USER,"用户未登录"));
        }else{
            userWorker.uploadPhoto(filePath,callback);
        }
    }

    public void uploadPhoto(File file, ReqCallback<ImageEntity> callback){
        String nowUserId = getNowUserId();
        if(StringUtil.isEmpty(nowUserId)){
            callback.onReqError(new HttpEvent(ErrorType.ERROR_INVALID_USER,"用户未登录"));
        }else{
            userWorker.uploadPhoto(file,callback);
        }
    }

    public void verify(IDInfor idInfor, ReqCallback<FuelCardEntity> callback){
        userWorker.verify(idInfor,callback);
    }

    public void saveGasRecord(GasRecordEntity gasRecordEntity, ReqCallback<String> callback){
        if(gasRecordEntity != null){
            UserEntity userEntity = getUser();
            if(userEntity != null){
                gasRecordEntity.setOporator(userEntity.getXm());
                gasRecordEntity.setGasStation(userEntity.getJyz());
            }
        }
        if(gasRecordEntity.getPlateOtherNum() != null){
            String platNum = gasRecordEntity.getPlateOtherNum();
            if(!StringUtil.isEmpty(gasRecordEntity.getPlateFirstNum())){
                platNum = gasRecordEntity.getPlateFirstNum() + platNum;
            }
            gasRecordEntity.setPlateNum(platNum);
        }
        userWorker.saveGasRecord(gasRecordEntity,callback);
    }
}
