package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.android.api.ApiService;
import com.example.android.constants.BuildConfig;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.viewmodel.UserEditViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserEditBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class UserEditActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private UserEditViewModel userEditViewModel;
    private ActivityUserEditBinding binding;

    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("yyyy年M月d日", Locale.CHINA);
    DateTimeFormatter serverFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    private String userName;
    private String userBirthday;
    private String userSex;
    private String userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_user_edit);

        userEditViewModel = new ViewModelProvider(this).get(UserEditViewModel.class);
        binding.setViewModel(userEditViewModel);
        binding.setLifecycleOwner(this);
//        ApiService apiService = RetrofitManager.getInstance(this, BuildConfig.USER_SERVICE).getApi(ApiService.class);
//        userEditViewModel.setApiService(apiService);


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
                        binding.showSelectedDate.setText(materialDatePicker.getHeaderText());
                        String displayedDate = materialDatePicker.getHeaderText();

                        try {
                            LocalDate date = LocalDate.parse(displayedDate, displayFormatter);
                            String formattedDate = date.format(serverFormatter);
                            userEditViewModel.setUserBirthday(formattedDate);
                        } catch (DateTimeParseException e) {
                            Log.e("DateConversion", "Error parsing the date: " + displayedDate, e);
                        }
                    }
                });
        
        binding.ivIndividualAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAvatar();
            }
        });
        
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEditViewModel.updateUserInfo(UserEditActivity.this);
            }
        });

        setData();
    }

    private void setData() {
        userName = binding.etUsername.getText().toString();
        userEditViewModel.setUsername(userName);

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_1:
                        userSex = "男";
                        userEditViewModel.setUserSex(userSex);
                        break;
                    case R.id.radio_button_2:
                        userSex = "女";
                        userEditViewModel.setUserSex(userSex);
                        break;
                    case R.id.radio_button_3:
                        userSex = "保密";
                        userEditViewModel.setUserSex(userSex);
                }
            }
        });
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 从本地相机挑选一张作为头像
     * @date 2024/5/11 14:34
     */
    private void selectAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // 使用Uri加载图片并设置为头像
            binding.ivIndividualAvatar.setImageURI(imageUri);
            userAvatar = imageUri.toString();
            userEditViewModel.setUserAvatar(userAvatar);
        }
    }


}