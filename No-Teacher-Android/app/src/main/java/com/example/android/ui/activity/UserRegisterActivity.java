package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.android.api.ApiService;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.viewmodel.UserEditViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserEditBinding;

public class UserRegisterActivity extends AppCompatActivity {
    private UserEditViewModel userEditViewModel;
    private ActivityUserEditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_user_edit);

        userEditViewModel = new ViewModelProvider(this).get(UserEditViewModel.class);
        binding.setViewModel(userEditViewModel);
        binding.setLifecycleOwner(this);
        ApiService apiService = RetrofitManager.getInstance(this).getApi(ApiService.class);
        userEditViewModel.setApiService(apiService);

    }
}