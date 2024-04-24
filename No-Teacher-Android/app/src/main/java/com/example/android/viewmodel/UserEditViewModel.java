package com.example.android.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.RegisterRequest;
import com.example.android.bean.entity.ErrorInfo;
import com.example.android.http.retrofit.BaseResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditViewModel extends ViewModel {
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> userBirthday = new MutableLiveData<>();
    private MutableLiveData<String> userSex = new MutableLiveData<>();
    private MutableLiveData<String> userImage = new MutableLiveData<>();

    private MutableLiveData<String> statusMsg = new MutableLiveData<>();
    private ApiService apiService;

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }
    public LiveData<String> getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username.setValue(username);
    }
    public LiveData<String> getUserBirthday() {
        return userBirthday;
    }
    public void setUserBirthday(String userBirthday) {
        this.userBirthday.setValue(userBirthday);
    }
    public LiveData<String> getUserSex() {
        return userSex;
    }
    public void setUserSex(String userSex) {
        this.userSex.setValue(userSex);
    }
    public LiveData<String> getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage.setValue(userImage);
    }
    public LiveData<String> getStatusMsg() {
        return statusMsg;
    }
    public void updateStatus(String message) {
        statusMsg.setValue(message);
    }

    /**
     * 发起用户注册请求。收集已输入的用户信息
     * 通过 updateStatus 方法更新状态消息
     */
    public void perFormRegistration() {
        String enteredUsername = username.getValue();
        String enteredBirthday = userBirthday.getValue();
        String enteredSex = userSex.getValue();
        String enteredImage = userImage.getValue();
        // 用于存放错误对应处理信息
        LinkedHashMap<Boolean, ErrorInfo> validationRules = new LinkedHashMap<>();
        getErrorInfo(enteredUsername, enteredBirthday, enteredSex, enteredImage, validationRules);
        // 创建 RegisterRequest 对象，封装用户注册信息
        RegisterRequest registerRequest = new RegisterRequest(
                enteredUsername,
                enteredBirthday,
                enteredSex,
                enteredImage
        );
        apiService.registerUser(registerRequest).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isFlag()) {
                    statusMsg.postValue("信息已保存");

                } else {
                    statusMsg.postValue("保存失败：" + response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                statusMsg.postValue("网络请求失败：" + t.getMessage());
            }
        });
    }

    /**
     *
     * @param enteredUsername
     * @param enteredBirthday
     * @param enteredSex
     * @param enteredImage
     * @param validationRules
     */
    private void getErrorInfo(String enteredUsername, String enteredBirthday, String enteredSex, String enteredImage, Map<Boolean, ErrorInfo> validationRules) {
        validationRules.put(!isValidUsername(enteredUsername),
                new ErrorInfo("温馨提示", "用户名不符合规则，请输入合法用户名"));
        validationRules.put(TextUtils.isEmpty(enteredUsername)||TextUtils.isEmpty(enteredBirthday)||TextUtils.isEmpty(enteredSex)||TextUtils.isEmpty(enteredImage),new ErrorInfo("温馨提示","请输入完整信息后再进行注册"));
    }

    /**
     * 非法用户名判断
     * @param username
     * @return
     */
    public boolean isValidUsername(String username) {
        String pattern = "^[a-zA-Z0-9_]+$";
        return username.matches(pattern);
    }

}
