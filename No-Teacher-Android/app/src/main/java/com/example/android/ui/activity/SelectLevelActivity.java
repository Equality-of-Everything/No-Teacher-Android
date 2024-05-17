package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.util.DataManager;
import com.example.android.util.TokenManager;
import com.example.android.viewmodel.SelectLevelViewModel;
import com.example.no_teacher_andorid.R;
import com.example.android.adapter.SelectLevelAdapter;
import com.example.no_teacher_andorid.databinding.ActivityLevelSelectBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class SelectLevelActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private SelectLevelViewModel viewModel;
    private ActivityLevelSelectBinding binding;
    private String userId;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_level_select);
        viewModel = new ViewModelProvider(this).get(SelectLevelViewModel.class);
        binding.setViewModel(viewModel);

//        viewModel.setIsSelectLiveData(false);

        // 初始化 Toolbar
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
        if (topAppBar != null) {
            setSupportActionBar(topAppBar);
            topAppBar.setNavigationOnClickListener(v -> finish()); // 设置导航按钮的点击事件为结束当前活动
        } else {
            Log.e("SelectLevelActivity", "Toolbar not found.");
        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.list_level);
        ArrayList<String> data = new ArrayList<>();
        int sum = 0;
        for (int i = 0;i <240;i++){
            sum += 5;
            if (sum == 110){
                data.add(sum + " (容易)");
            }else if (sum == 150){
                data.add(sum + " (适中)");
            }else if (sum == 200){
                data.add(sum + " (困难)");
            }
        }
        SelectLevelAdapter adapter = new SelectLevelAdapter(this, data);
        listView.setAdapter(adapter);
        userId = TokenManager.getUserId(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position); // 设置被选中的项

                showAlertDialog(position);
            }
        });

    }


    /**
     * @param position:
     * @return void
     * @author Lee
     * @description 选择阅读难度
     * @date 2024/5/8 14:22
     */
    private void showAlertDialog(int position) {
        num = 0;
        if(position == 0) {
            num = 110;
        } else if(position == 1) {
            num = 150;
        } else {
            num = 200;
        }
        int finalNum = num;
        new AlertDialog.Builder(this)
                .setTitle("阅读难度选择")  // 设置对话框标题
                .setMessage("选择阅读难度为" + num)  // 设置对话框显示的文本
                .setPositiveButton("确定", (dialog, which) -> {
                    // 用户点击确定按钮的处理逻辑
                    viewModel.updateLexile(this, userId, num);
                    DataManager.getInstance().setIsSelectLiveData(true);

                    Log.e("userId", userId);
                    Intent intent = new Intent();
                    intent.setClass(SelectLevelActivity.this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("取消", (dialog, which) -> {

                })
                .show();  // 显示对话框
    }


}