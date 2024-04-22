package com.example.no_teacher_andorid.http.response;


/*
封装从服务器端返回的验证邮箱结果的数据
 */
public class VerifyEmailResponse {
    private String msg;//服务器返回的信息
    private int code;//服务器返回的状态码

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
