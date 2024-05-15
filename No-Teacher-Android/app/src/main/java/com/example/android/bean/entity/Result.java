package com.example.android.bean.entity;

import java.io.Serializable;

/**
 * @Author : zhang
 * @Date : Created in 2024/4/24 14:43
 * @Decription :
 */


public class Result<T> implements Serializable {

    private boolean flag; //编码：true成功，false为失败
    private String msg; //错误信息
    private T data; //数据
    private int code; //状态码

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }

    public Result(boolean flag, String msg, T data){
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result(boolean flag, String msg, T data, int code) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }
}