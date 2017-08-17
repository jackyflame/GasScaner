package com.jf.gasscaner.net.worker;

import com.haozi.baselibrary.net.retrofit.BaseWorker;
import com.haozi.baselibrary.net.retrofit.ReqCallback;
import com.haozi.baselibrary.net.retrofit.RetrofitHelper;
import com.jf.gasscaner.net.entity.GasRecordEntity;
import com.jf.gasscaner.net.entity.ImageEntity;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.gasscaner.net.service.UserService;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: Haozi
 * Date: 2017/6/10
 * Time: 18:24
 */

public class UserWorker extends BaseWorker {

    private final UserService userService;

    public UserWorker() {
        userService = RetrofitHelper.getInstance().callAPI(UserService.class);
    }

    public void login(String username, String password,ReqCallback<UserEntity> callback){
        defaultCall(userService.login(username,password),callback);
    }

    public void uploadPhoto(String filePath,ReqCallback<String> callback){
        File file = new File(filePath);
        uploadPhoto(file,callback);
    }

    /**
     * 上传成功返回附件id
     * */
    public void uploadPhoto(File file,ReqCallback<String> callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile",file.getName(),requestBody);
        defaultCall(userService.uploadPhoto(body,1),callback);
    }

    /**
     * 保存加油记录
     * */
    public void saveGasRecord(GasRecordEntity recordEntity,ReqCallback<String> callback){
        saveGasRecord(recordEntity.getName(),recordEntity.getIdNum(),recordEntity.getSex(),recordEntity.getBirthday(),recordEntity.getNation(),
                recordEntity.getAddress(),recordEntity.getCarType(),recordEntity.getCardNum(),recordEntity.getPlateType(),recordEntity.getPlateNum(),recordEntity.getGasType(),
                recordEntity.getGasMountStr(),recordEntity.getGasStation(),recordEntity.getOporator(),recordEntity.getHeaderImg(),recordEntity.getImage(),
                callback);
    }

    /**
     * 保存加油记录
     * */
    public void saveGasRecord(String name, String idcard,String sex, String birthday, String nation,
                              String address,String carType, String cardNo,  String carCardType,String carNo,String fuelType,
                              String num, String station, String fuelMan, String idcardPhotoId, String scenePhotoId,
                              ReqCallback<String> callback){
        defaultCall(userService.saveGasRecord(name,idcard,sex,birthday,nation,address,carType,cardNo,carCardType,carNo,fuelType,
                num,station,fuelMan,idcardPhotoId,scenePhotoId),callback);
    }

    public void verify(String name,String idcard, String sex, String birthday,
                       String nation, String address,ReqCallback<String> callback){
        defaultCall(userService.verify(name,idcard,sex,birthday,nation,address),callback);
    }
}
