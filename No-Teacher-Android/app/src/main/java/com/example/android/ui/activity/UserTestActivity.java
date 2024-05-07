package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.android.util.TokenManager;
import com.example.android.viewmodel.EmailVerifyViewModel;
import com.example.android.viewmodel.UserTestViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserTestBinding;

import java.util.List;

public class UserTestActivity extends AppCompatActivity {

    private UserTestViewModel viewModel;
    private ActivityUserTestBinding binding;
    View.OnClickListener toggleButtonSelectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_test);

        viewModel = new ViewModelProvider(this).get(UserTestViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // 创建和设置点击监听器
//        View.OnClickListener toggleButtonSelectionListener = v -> performCommonAction(v);
        toggleButtonSelectionListener = v -> performCommonAction(v);

        setWords();
        addBtnListener();
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 将获取到的8个单词显示在对应的按钮
     * @date 2024/5/7 10:08
     */
    private void setWords() {
        List<String> words = TokenManager.loadWordsFromSharedPreferences(this);

        binding.firstBtn.setText(words.get(0));
        binding.secondBtn.setText(words.get(1));
        binding.threeBtn.setText(words.get(2));
        binding.fourBtn.setText(words.get(3));
        binding.fiveBtn.setText(words.get(4));
        binding.sixBtn.setText(words.get(5));
        binding.sevenBtn.setText(words.get(6));
        binding.eightBtn.setText(words.get(7));
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 为8个按钮添加监听器
     * @date 2024/5/7 10:08
     */
    private void addBtnListener() {
        binding.firstBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.secondBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.threeBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.fourBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.fiveBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.sixBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.sevenBtn.setOnClickListener(toggleButtonSelectionListener);
        binding.eightBtn.setOnClickListener(toggleButtonSelectionListener);
    }

    private void performCommonAction(View v) {
        v.setSelected(!v.isSelected());  // 切换选中状态


    }


}






















