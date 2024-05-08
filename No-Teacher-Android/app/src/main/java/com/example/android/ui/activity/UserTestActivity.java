package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.util.TokenManager;
import com.example.android.viewmodel.EmailVerifyViewModel;
import com.example.android.viewmodel.UserTestViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserTestBinding;

import java.util.List;

public class UserTestActivity extends AppCompatActivity {

    private UserTestViewModel viewModel;
    private ActivityUserTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_test);

        viewModel = new ViewModelProvider(this).get(UserTestViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.requestTestWordNum(this);  // 请求总页数
        binding.setLifecycleOwner(this);

        viewModel.getWordsLiveData().observe(this, this::setWords);
        viewModel.getTestCompleteLiveData().observe(this, complete -> {
            if(complete) {
                showCompletionDialog();
            }
        });

        setupButtonListeners();
        viewModel.fetchWords(this); // 获取第一页单词
    }

    private void setupButtonListeners() {
        View.OnClickListener listener = v -> {
            Button btn = (Button) v;
            btn.setSelected(!btn.isSelected());
            //根据按钮状态处理单词的掌握情况
        };

        binding.firstBtn.setOnClickListener(listener);
        binding.secondBtn.setOnClickListener(listener);
        binding.threeBtn.setOnClickListener(listener);
        binding.fourBtn.setOnClickListener(listener);
        binding.fiveBtn.setOnClickListener(listener);
        binding.sixBtn.setOnClickListener(listener);
        binding.sevenBtn.setOnClickListener(listener);
        binding.eightBtn.setOnClickListener(listener);

        binding.nextTest.setOnClickListener(v -> viewModel.fetchWords(this));
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 将获取到的8个单词显示在对应的按钮
     * @date 2024/5/7 10:08
     */
    private void setWords(List<String> words) {
        Button[] buttons = {binding.firstBtn, binding.secondBtn,
                binding.threeBtn, binding.fourBtn, binding.fiveBtn,
                binding.sixBtn, binding.sevenBtn, binding.eightBtn};
        for (int i = 0; i < words.size(); i++) {
            buttons[i].setText(words.get(i));
            buttons[i].setSelected(false); // 重置选中状态
        }
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 完成单词测试后显示对话框
     * @date 2024/5/8 9:05
     */
    private void showCompletionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("测试完成")  // 设置对话框标题
                .setMessage("你已完成所有测试。已经掌握88个单词")  // 设置对话框显示的文本
                .setPositiveButton("确定", (dialog, which) -> {
                    // 用户点击确定按钮的处理逻辑
                    returnToMainActivity();
                })
                .setCancelable(false)  // 设置对话框不可取消，防止用户点击外部取消对话框
                .show();  // 显示对话框
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 跳转主界面
     * @date 2024/5/8 9:06
     */
    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // 清除之前的活动栈
        startActivity(intent);
        finish();  // 结束当前活动
    }

}






















