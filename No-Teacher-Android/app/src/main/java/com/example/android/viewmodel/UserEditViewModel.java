package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.USER_SERVICE;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.UserInfoRequest;
import com.example.android.bean.entity.ErrorInfo;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.util.TokenManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditViewModel extends ViewModel {

    private MutableLiveData<String> userId = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> userBirthday = new MutableLiveData<>();
    private MutableLiveData<String> userSex = new MutableLiveData<>();
    private MutableLiveData<String> userAvatar = new MutableLiveData<>();

    private MutableLiveData<String> statusMsg = new MutableLiveData<>();
    private MutableLiveData<String> uploadStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> uploadProgress = new MutableLiveData<>();

    private MutableLiveData<Boolean> navigateToMain = new MutableLiveData<>();
    private ApiService apiService;

    public MutableLiveData<String> getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId.setValue(userId);
    }

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

    public LiveData<String> getStatusMsg() {
        return statusMsg;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar.setValue(userAvatar);
    }

    public LiveData<String> getUserAvatar() {
        return userAvatar;
    }

    public void updateStatus(String message) {
        statusMsg.setValue(message);
    }

    public LiveData<String> getUploadStatus() {
        return uploadStatus;
    }
    public LiveData<Integer> getUploadProgress() {
        return uploadProgress;
    }

    public LiveData<Boolean> getNavigateToMain() {
        return navigateToMain;
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 更新用户个人资料
     * @date 2024/5/22 9:00
     */
    public void updateUserInfo(Context context) {
        UserInfoRequest request = new UserInfoRequest(userId.getValue(), username.getValue(), userBirthday.getValue(), userSex.getValue(), userAvatar.getValue());
        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("userId", userId.getValue());
        params.put("userName", username.getValue());
        params.put("birthdate", userBirthday.getValue());
        params.put("sex", userSex.getValue());
        params.put("avatar", userAvatar.getValue());
        RetrofitManager.getInstance(context,USER_SERVICE)
                .getApi(ApiService.class)
                .sendNewUserInfo(params)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        Log.e("TAG", "onResponse: " + response.toString());
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("TAG", "onResponse: " + response.body().toString());
                            navigateToMain.setValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        Log.e("Error", "NetWOrk-Error");
                        t.printStackTrace();
                    }
                });
    }

    /**
     * @param context:
     * @param userId:
     * @param file:
     * @return void
     * @author Lee
     * @description 更新头像
     * @date 2024/5/21 9:23
     */
    public void uploadAvatar(Context context, String userId, MultipartBody.Part file) {
        ApiService service = RetrofitManager.getInstance(context, USER_SERVICE)
                        .getApi(ApiService.class);
        Call<BaseResponse<String>> call = service.uploadAvatar(userId, file);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful()) {
                    uploadStatus.setValue("File uploaded successfully!");
                    Log.e("AAAAAAAAAAAAAAA", response.body().getMsg() + "");
                    Log.e("Success", "Response Code: " + response.code() + " Message: " + response.message());
                    userAvatar.setValue(response.body().getData());
                    Log.e("Avatar", response.body().getData());
                    TokenManager.saveUserAvatar(context, response.body().getData());
                } else {
                    uploadStatus.setValue("Upload failed: " + response.message());
                    Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                }
                uploadProgress.setValue(100);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                uploadStatus.setValue("Upload failed: " + t.getMessage());
                Log.e("UploadAvatar-Error", "Network-Error"+t.getMessage());
                uploadProgress.setValue(0);
                t.printStackTrace();
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




