package com.example.no_teacher_andorid.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;

import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.http.response.VerifyEmailResponse;
import com.example.no_teacher_andorid.viewmodel.EmailVerificationViewModel;


public class LoginEmailActivity extends AppCompatActivity {
    private EmailVerificationViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        //初始化ViewModel
        viewModel = new ViewModelProvider(this).get(EmailVerificationViewModel.class);
        viewModel.getVerifyEmailResponse().observe(this, new Observer<VerifyEmailResponse>() {
            public void onChanged(VerifyEmailResponse verifyEmailResponse) {
                // 更新UI，显示验证码发送状态或结果
            }
        });
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            public void onChanged(String errorMessage) {
                // 更新UI，显示错误信息
            }
        });
        //发送验证码点击事件
        findViewById(R.id.Txt_verify_number).setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.edTxt_verify_email)).getText().toString();
            viewModel.sendEmailVerificationCode(email);
        });
        //验证事件
        findViewById(R.id.btn_verify).setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.edTxt_verify_email)).getText().toString();
            String verificationCode = ((EditText) findViewById(R.id.edtTxt_verify_number)).getText().toString();
            viewModel.verifyEmail(email, verificationCode);
        });
    }
}