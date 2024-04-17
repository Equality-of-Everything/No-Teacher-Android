package com.example.no_teacher_andorid.http.retrofit;

import android.content.Intent;

import com.zhilehuo.advenglish.base.BaseApplication;
import com.zhilehuo.advenglish.http.respone.BaseResponse;
import com.zhilehuo.advenglish.ui.activity.AccountInvalidDialogActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author wpt
 * @Date 2023/2/28-14:04
 * @desc
 */
public abstract class ResponseCallBack<T> implements Callback<BaseResponse<T>> {



//    public static boolean showVipDialog = false;

    public static boolean showInvalidDialog = false;

    public static final int SUCCESS_CODE = 0;

    //账号无效
    public static final int INVALID_ACCOUNT = -10086;

    //vip到期
    public static final int INVALID_VIP = -10087;

    //未购买vip
    public static final int UN_BUY_VIP = -10088;

    public abstract void success(T t);

    public abstract void failure(T t,int code, String msg);

    public abstract void error(String msg);

    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if (response.isSuccessful() && response.body() != null) {
            int code = response.body().getCode();
            if (SUCCESS_CODE == code) {
                success(response.body().getData());
            } else if (INVALID_ACCOUNT == code){
                showAccountInvalidDialog();
            } /*else if (INVALID_VIP == code || UN_BUY_VIP == code){
                showVipDialog(code);
            } */else {
                failure(response.body().getData(),code,response.body().getMsg());
            }
        } else {
            failure(null,response.code(),response.message());
        }
    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {//超时
            error(t.getMessage());
        } else if (t instanceof ConnectException) {//连接错误
            error(t.getMessage());
        } else if (t instanceof UnknownError) { //未找到主机
            error(t.getMessage());
        } else {//其他错误
            error(t.getMessage());
        }
    }

    private void showAccountInvalidDialog() {
        if (showInvalidDialog) {
            return;
        }
        showInvalidDialog = true;
        Intent intent = new Intent(BaseApplication.mInstance, AccountInvalidDialogActivity.class);
        intent.putExtra("title","温馨提示");
        intent.putExtra("content","账户状态异常");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mInstance.startActivity(intent);
    }

   /* private void showVipDialog(int code) {
        if (showVipDialog) {
            return;
        }
        showVipDialog = true;
        Intent intent = new Intent(BaseApplication.mInstance, BuyVipDialogActivity.class);
        intent.putExtra("isNoVip",code == UN_BUY_VIP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mInstance.startActivity(intent);
    }*/
}
