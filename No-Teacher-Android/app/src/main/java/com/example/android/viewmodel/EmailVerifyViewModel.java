package com.example.android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.EmailRequest;
import com.example.android.http.request.VerifyEmailRequest;
import com.example.android.http.retrofit.BaseResponse;

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
    public void requestSendVerificationCode() {
        EmailRequest request = new EmailRequest(email.getValue());
        apiService.sendVerificationEmail(request).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isFlag()) {
                    statusMsg.postValue("验证码已发送到您的邮箱。");
                } else {
                    statusMsg.postValue("发送验证码失败：" + response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                statusMsg.postValue("网络请求失败：" + t.getMessage());
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

    //用于存储发送或验证邮箱验证码的结果
//    private MutableLiveData<VerifyEmailResponse> verifyEmailResponse= new MutableLiveData<>();
    //用于存储错误信息
//    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
//    public void setApiService(api_service apiService)
//    {
//        this.apiService = apiService;
//    }

//    /**
//     * 发送邮箱验证码
//     * @param email
//     */
//    public void sendEmailVerificationCode(String email) {
//        // 此处仅设置邮箱地址，验证码待用户输入后验证时再赋值
//        VerifyEmailRequest request = new VerifyEmailRequest(email, "");
//        //待分装调用
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:3000/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiService = retrofit.create(api_service.class);
//        apiService.sendEmailVerificationCode(request).enqueue(new Callback<VerifyEmailResponse>(){
//
//            @Override
//            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
//                // 判断请求是否成功
//                if (response.isSuccessful()){
//                    verifyEmailResponse.postValue(response.body());
//                    // 请求成功，提示验证码发送成功
//
//                }else{
//                    // 请求失败，提示验证码发送失败
//                    errorMessage.setValue("验证码发送失败");
//                }
//            }
//            @Override
//            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
//                errorMessage.setValue("验证码发送失败"+t.getMessage());
//            }
//        });
//    }
//    /**
//     * 验证邮箱验证码
//     * @param email
//     * @param verificationCode
//     */
//    public void verifyEmail(String email, String verificationCode) {
//        VerifyEmailRequest request = new VerifyEmailRequest(email, verificationCode);
//        apiService.verifyEmail(request).enqueue(new Callback<Void>() {
//
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()){
//                    // 验证成功,跳转页面
//                }else{
//                    errorMessage.setValue("验证失败");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                errorMessage.setValue("验证失败"+t.getMessage());
//            }
//        });
//    }
//    public MutableLiveData<VerifyEmailResponse> getVerifyEmailResponse() {
//        return verifyEmailResponse;
//    }
//    public MutableLiveData<String> getErrorMessage() {
//        return errorMessage;
//    }

}
