package com.example.android.bean;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/11 14:14
 * @Decription :
 */

public class UserInfoRequest {
    private String userId;
    private String userName;
    private String avatar;
    private String birthdate;
    private String sex;

    public UserInfoRequest(String value) {}
    public UserInfoRequest(String userId, String userName,
                           String avatar, String birthdate, String sex) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.birthdate = birthdate;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
