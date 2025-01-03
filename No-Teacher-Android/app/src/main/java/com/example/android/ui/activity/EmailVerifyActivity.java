package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.api.ApiService;
import com.example.android.constants.BuildConfig;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.util.ToastManager;
import com.example.android.viewmodel.EmailVerifyViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityEmailVerifyBinding;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class EmailVerifyActivity extends AppCompatActivity {

    private EmailVerifyViewModel viewModel;
    private ActivityEmailVerifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_email_verify);

        viewModel = new ViewModelProvider(this).get(EmailVerifyViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        ApiService apiService = RetrofitManager.getInstance(this, BuildConfig.USER_SERVICE).getApi(ApiService.class);
        viewModel.setApiService(apiService);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=" + "5e62dc3d");
        // 设置按钮的点击事件
        binding.edtTxtVerifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailOnclick(view);
            }
        });

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeInput = binding.edtTxtVerifyNumber.getText().toString();
                if (!TextUtils.isEmpty(codeInput)) {
                    viewModel.setVerifyCode(codeInput);
                }
                viewModel.verifyVerificationCode();
            }
        });

        viewModel.getNavigateToMain().observe(this, shouldNavigate -> {
           if(shouldNavigate) {
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);
               finish();
           } else {
               ToastManager.showCustomToast(this, "Verification failed.");
//               Toast.makeText(this, "Verification failed.", Toast.LENGTH_SHORT).show();
           }
        });
    }
    public void emailOnclick(View view) {
        String emailInput = binding.edTxtVerifyEmail.getText().toString();
        Log.e("EmailVerifyActivity", "Email input: " + emailInput);
        if (!TextUtils.isEmpty(emailInput)) {
            ToastManager.showCustomToast(this, "请接收验证码" + emailInput);
//            Toast.makeText(this, "请接收验证码", Toast.LENGTH_LONG).show();
            viewModel.setEmail(emailInput);
            viewModel.requestSendVerificationCode(this);

            binding.TxtVerifyNumber.setEnabled(false);  // 禁用按钮

            // 开始一个1分钟的倒计时
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    binding.TxtVerifyNumber.setText(millisUntilFinished / 1000 + " 秒");
                }
                public void onFinish() {
                    binding.TxtVerifyNumber.setEnabled(true);  // 启用按钮
                    binding.TxtVerifyNumber.setText(R.string.Txt_verify_number);  // 重置文本
                }
            }.start();
        } else {
            ToastManager.showCustomToast(this, "请输入邮箱");
//            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_LONG).show();
        }
    }


}