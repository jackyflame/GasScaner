package com.jf.gasscaner.net.service;

import com.haozi.baselibrary.net.entity.RespEntity;
import com.jf.gasscaner.net.entity.ImageEntity;
import com.jf.gasscaner.net.entity.UserEntity;
import com.jf.greendaolib.User;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: Haozi
 * Date: 2017/6/10
 * Time: 18:21
 */

public interface UserService {

    /**
     * 注册登录
     * @param username 用户名
     * @param password 密码
     * @return 603：用户不存在 604：密码错误 200：登录成功
     * */
    @GET("fuelingRecordController.do?login")
    Observable<Response<RespEntity<UserEntity>>> login(@Query(value = "username") String username, @Query(value = "password") String password);

    /**
     * 上传头像
     * @param file 文件信息
     * */
    @Multipart
    @POST("systemController.do?filedeal")
    Observable<Response<RespEntity<String>>> uploadPhoto(@Part MultipartBody.Part file, @Query(value = "upFlag") int upFlag);

    /**
     * 数据采集
     * @param name 姓名
     * @param idcard 身份证号
     * @param sex 性别
     * @param birthday 出生日期
     * @param nation 民族
     * @param address 地址
     * @param carType 车辆类型
     * @param cardNo 加油卡号
     * @param carCardType 车牌类型
     * @param carNo 车牌号码
     * @param fuelType 油品种类
     * @param num 加油数量
     * @param station 加油站
     * @param fuelMan 加油员
     * @param idcardPhotoId 现场照片id
     * @param scenePhotoId 身份证照片id
     * */
    @GET("fuelingRecordController.do?record")
    Observable<Response<RespEntity<String>>> saveGasRecord(@Query(value = "name") String name, @Query(value = "idcard") String idcard,
            @Query(value = "sex") String sex,@Query(value = "birthday") String birthday,@Query(value = "nation") String nation
            ,@Query(value = "address") String address,@Query(value = "carType") String carType,@Query(value = "cardNo") String cardNo
            ,@Query(value = "carCardType") String carCardType,@Query(value = "carNo") String carNo,@Query(value = "fuelType") String fuelType
            ,@Query(value = "num") String num,@Query(value = "station") String station,@Query(value = "fuelMan") String fuelMan
            ,@Query(value = "idcardPhotoId") String idcardPhotoId,@Query(value = "scenePhotoId") String scenePhotoId);

    /**
     * 实名验证
     * @param name:姓名
     * @param idcard：身份证号
     * @param sex：性别
     * @param birthday：出生日期
     * @param nation：民族
     * @param address：地址
     * @return  601：黑名单 602：未登记 200：认证通过
     * */
    @GET("fuelCardController.do?verify")
    Observable<Response<RespEntity<String>>> verify(@Query(value = "name") String name, @Query(value = "idcard") String idcard,
                                                        @Query(value = "sex") String sex, @Query(value = "birthday") String birthday,
                                                        @Query(value = "nation") String nation, @Query(value = "address") String address);

}
