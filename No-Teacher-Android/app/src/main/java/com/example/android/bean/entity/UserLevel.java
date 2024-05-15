package com.example.android.bean.entity;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/9 8:31
 * @Decription :
 */


public class UserLevel {
    private String id;;
    private String userId;
    private String unknowWordId;
    private String knowWordId;

    public UserLevel(String id, String userId, String unknowWordId, String knowWordId) {
        this.id = id;
        this.userId = userId;
        this.unknowWordId = unknowWordId;
        this.knowWordId = knowWordId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnknowWordId() {
        return unknowWordId;
    }

    public void setUnknowWordId(String unknowWordId) {
        this.unknowWordId = unknowWordId;
    }

    public String getKnowWordId() {
        return knowWordId;
    }

    public void setKnowWordId(String knowWordId) {
        this.knowWordId = knowWordId;
    }

    @Override
    public String toString() {
        return "UserLevel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", unknowWordId='" + unknowWordId + '\'' +
                ", knowWordId='" + knowWordId + '\'' +
                '}';
    }
}
