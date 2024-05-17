package com.example.android.bean;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/17 14:37
 * @Decription :
 */

public class LexileRequest {
    private String userId;
    private String lexile;

    public LexileRequest() {}

    public LexileRequest(String userId, String lexile) {
        this.userId = userId;
        this.lexile = lexile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLexile() {
        return lexile;
    }

    public void setLexile(String lexile) {
        this.lexile = lexile;
    }

    @Override
    public String toString() {
        return "LexileRequest{" +
                "userId='" + userId + '\'' +
                ", lexile='" + lexile + '\'' +
                '}';
    }
}
