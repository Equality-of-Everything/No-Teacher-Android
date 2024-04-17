package com.example.no_teacher_andorid.http.respone;

/**
 * @Author wpt
 * @Date 2023/2/20-15:25
 * @desc
 */
public class BaseResponse<T> {

    private int code;

    private String msg;

    private T data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
