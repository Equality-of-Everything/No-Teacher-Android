package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.android.api.ApiService;
import com.example.android.constants.BuildConfig;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.viewmodel.UserEditViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserEditBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

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
        ApiService apiService = RetrofitManager.getInstance(this, BuildConfig.USER_SERVICE).getApi(ApiService.class);
        userEditViewModel.setApiService(apiService);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("选择日期");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        binding.pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        binding.showSelectedDate.setText(materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });

    }
}