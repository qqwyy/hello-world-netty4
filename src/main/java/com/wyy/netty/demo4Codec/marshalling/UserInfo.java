package com.wyy.netty.demo4Codec.marshalling;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String name;
    private long userId;
    private String email;
    private String mobile;
    private String remark;

    public UserInfo() {
    }

    public UserInfo(String name, long userId, String email, String mobile, String remark) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.mobile = mobile;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
