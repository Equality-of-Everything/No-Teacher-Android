package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.USER_SERVICE;

import android.content.Context;
import android.content.Intent;
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
import com.example.android.ui.activity.MainActivity;
import com.example.android.util.GsonUtils;
import com.example.android.util.GsonWrapper;
import com.example.android.util.TokenManager;
import com.google.gson.Gson;

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
    private MutableLiveData<Boolean> navigateToMain = new MutableLiveData<>();

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

    public LiveData<Boolean> getNavigateToMain() {
        return navigateToMain;
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 请求发送验证码
     * @date 2024/4/24 15:45
     */
    public void requestSendVerificationCode(Context context) {
        EmailRequest request = new EmailRequest(email.getValue());
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email.getValue());
        RetrofitManager.getInstance(context,USER_SERVICE).
                getApi(ApiService.class)
                .sendVerificationEmail(params)
                .enqueue(new Callback<BaseResponse<User>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            BaseResponse<User> body = response.body();
                            if (body.isSuccess()) {

                                User user = body.getData();
                                Log.e("aaaaaaaa", user.toString());
                                String userId = user.getUserId();
                                TokenManager.saveUserId(context.getApplicationContext(), userId);
                                TokenManager.saveUserAvatar(context.getApplicationContext(), user.getAvatar());
                                String avatar = TokenManager.getUserAvatar(context.getApplicationContext());
                                Log.e("avatar", avatar);

                            } else {
                                Log.e("Request Error", "Error from server: " + body.getMessage());
                            }
                        } else {
                            // HTTP错误处理
                            Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                        Log.e("LoginActivity-Error", "NetWOrk-Error");
                        t.printStackTrace();
                    }
                });

    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 验证验证码是否正确以登录
     * @date 2024/4/24 15:45
     */
    public void verifyVerificationCode() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email.getValue());
        params.put("code", verifyCode.getValue());
        RetrofitManager.getInstance(null,USER_SERVICE).
                getApi(ApiService.class)
                .verifyEmail(params)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            boolean flag = response.body().isFlag();

                            navigateToMain.postValue(true);
                        } else {
                            navigateToMain.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        Log.e("LoginActivity-Error", "NetWOrk-Error");
                        t.printStackTrace();
                        navigateToMain.postValue(false);
                    }
                });
    }
}












