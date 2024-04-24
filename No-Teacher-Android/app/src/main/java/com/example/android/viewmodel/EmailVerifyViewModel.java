package com.example.android.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.EmailRequest;
import com.example.android.bean.entity.Result;
import com.example.android.bean.entity.User;
import com.example.android.http.request.VerifyEmailRequest;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
 * ViewModel负责处理与 UI 相关的业务逻辑，
 * 并在数据发生变化时通知 View 进行更新
 */
public class EmailVerifyViewModel extends ViewModel {
    //与服务器进行通信，执行发送邮箱验证码和验证邮箱验证码的操作

    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> verifyCode = new MutableLiveData<>();
    private MutableLiveData<String> statusMsg = new MutableLiveData<>();

    private ApiService apiService;
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }
//    public MutableLiveData<String> getEmail() {
//        return this.email;
//    }

    public LiveData<String> getVerifyCode() {
        return verifyCode;
    }

    public LiveData<String> getStatusMsg() {
        return statusMsg;
    }

    public void setVerifyCode(String code) {
        this.verifyCode.setValue(code);
    }

    public void updateStatus(String message) {
        statusMsg.setValue(message);
    }


    // 请求发送验证码
    public void requestSendVerificationCode(Context context) {
        EmailRequest request = new EmailRequest(email.getValue());
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email.getValue());
        RetrofitManager.getInstance(context).
                getApi(ApiService.class)
                .sendVerificationEmail(params)
                .enqueue(new Callback<BaseResponse<User>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        Log.e("Reuqest Success", response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                        Log.e("LoginActivity-Error", "NetWOrk-Error");
                        t.printStackTrace();
                    }
                });

    }

    // 验证输入的验证码
    public void verifyVerificationCode() {
        VerifyEmailRequest request = new VerifyEmailRequest(email.getValue(), verifyCode.getValue());
        apiService.verifyEmail(request).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isFlag()) {
                    statusMsg.postValue("验证码验证成功。");
                } else {
                    statusMsg.postValue("验证码验证失败：" + response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                statusMsg.postValue("网络请求失败：" + t.getMessage());
            }
        });
    }
}
