package com.jf.gasscaner.net.entity;

import com.haozi.baselibrary.net.entity.BaseNetEntity;

import java.util.Date;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: Haozi
 * Date: 2017/6/10
 * Time: 13:37
 */

public class UserEntity extends BaseNetEntity{

    /**主键*/
    private String id;
    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    private long createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
    private java.util.Date updateDate;
    /**姓名*/
    private String xm;
    /**身份证号*/
    private String sfzh;
    /**联系电话*/
    private String lxdh;
    /**密码*/
    private String mm;
    /**加油站*/
    private String jyz;

    /**登录名*/
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getJyz() {
        return jyz;
    }

    public void setJyz(String jyz) {
        this.jyz = jyz;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}



