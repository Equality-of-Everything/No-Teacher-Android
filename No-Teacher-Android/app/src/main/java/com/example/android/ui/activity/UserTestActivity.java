package com.example.android.ui.activity;

import static com.example.android.util.TokenManager.loadALLWordIds;
import static com.example.android.util.TokenManager.printAllSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.util.TokenManager;
import com.example.android.viewmodel.UserTestViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserTestBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTestActivity extends AppCompatActivity {

    private UserTestViewModel viewModel;
    private ActivityUserTestBinding binding;

    private ArrayList<Integer> knowWordsList = new ArrayList<Integer>();
    private ArrayList<Integer> unknowWordsList = new ArrayList<>();
    private Map<Button, String> buttonWordMap = new HashMap<>(); // 类级变量
    private Map<String, Integer> wordAndId = new HashMap<>(); // 全局变量

    private AlertDialog completionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_test);

        viewModel = new ViewModelProvider(this).get(UserTestViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.requestTestWordNum(this);  // 请求总页数
        binding.setLifecycleOwner(this);

        unknowWordsList.clear();
        knowWordsList.clear();

        viewModel.getWordsLiveData().observe(this, this::setWords);
        viewModel.getTestCompleteLiveData().observe(this, complete -> {
            if(complete) {
                showCompletionDialog();
            }
        });

        setupButtonListeners();
        viewModel.fetchWords(this); // 获取第一页单词
        viewModel.getCurrentPageLiveData().observe(this, page -> {
            binding.wordPageText.setText("第"+(page*8+1)+"~"+(page+1)*8+"单词");
        });
    }

    private void setupButtonListeners() {
        View.OnClickListener listener = v -> {
            Button btn = (Button) v;
            boolean wasSelected = btn.isSelected(); // 获取当前按钮的选中状态
            //根据按钮状态处理单词的掌握情况
            Log.e("Button Status Before", "Button was selected: " + wasSelected);
            btn.setSelected(!wasSelected);
            Log.e("Button Status After", "Button is now selected: " + btn.isSelected());

            printAllSharedPreferences(this);
            String word = btn.getText().toString();
            Log.e("UserTestActivity", "word: " + word);

            Map<String, Integer> wordAndId = TokenManager.loadWordsAndIds(this);
            Log.e("Button Status", "Button was selected: " + wasSelected);
            if (btn.isSelected()) {
                if (!knowWordsList.contains(word)) {
                    knowWordsList.add(wordAndId.get(word));
                    Log.e("knowWordList", "Added wordId: " + word);
                }
            } else {
                knowWordsList.remove(wordAndId.get(word));
                Log.e("knowWordList", "Removed wordId: " + word);
            }
        };

        binding.nextTest.setOnClickListener(v -> viewModel.fetchWords(this));
        binding.firstBtn.setOnClickListener(listener);
        binding.secondBtn.setOnClickListener(listener);
        binding.threeBtn.setOnClickListener(listener);
        binding.fourBtn.setOnClickListener(listener);
        binding.fiveBtn.setOnClickListener(listener);
        binding.sixBtn.setOnClickListener(listener);
        binding.sevenBtn.setOnClickListener(listener);
        binding.eightBtn.setOnClickListener(listener);
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
        int cnt = knowWordsList.size();
        sendWordTestResultToServer(unknowWordsList, knowWordsList);

        completionDialog = new AlertDialog.Builder(this)
                .setTitle("测试完成")
                .setMessage("你已完成所有测试。已经掌握" + cnt + "个单词")
                .setPositiveButton("确定", (dialog, which) -> {
                    returnToMainActivity();
                })
                .setCancelable(false)
                .create();

        if (!isFinishing()) {
            completionDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        if(completionDialog != null && completionDialog.isShowing()){
            completionDialog.dismiss();
        }
        super.onDestroy();
    }

    private void sendWordTestResultToServer(List<Integer> unknowWordsList, List<Integer> knowWordsList) {

        for(Integer wordId : knowWordsList) {
            Log.e("print", "knowWord : " + wordId);
        }

        List<Integer> allIds = loadALLWordIds(this);
        for(Integer wordId : knowWordsList) {
            allIds.remove(wordId);
        }

        unknowWordsList = allIds;
        Log.e("print", "unknowWordSize : " + unknowWordsList.size());
        for(Integer wordId : unknowWordsList) {
            Log.e("print", "unknowWord : " + wordId);
        }
        viewModel.sendTestResultToServer(this, unknowWordsList, knowWordsList);
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






















