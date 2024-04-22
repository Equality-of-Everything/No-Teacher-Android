package com.example.no_teacher_andorid.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityLoginEmailBinding;
import com.example.no_teacher_andorid.viewmodel.LoginEmailViewModel;

public class LoginEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_email);

        ActivityLoginEmailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login_email);
        LoginEmailViewModel loginViewModel = new LoginEmailViewModel(this);
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);//让LiveData响应生命周期变化

    }
}