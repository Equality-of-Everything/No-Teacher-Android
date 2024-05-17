package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.no_teacher_andorid.R;
import com.example.android.adapter.SelectLevelAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class SelectLevelActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private Button btnToTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

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
                data.add(sum + " (相当于一年级及以下水平)");
            }else if (sum == 150){
                data.add(sum + " (相当于一年级水平)");
            }else if (sum == 200){
                data.add(sum + " (相当于二年级水平)");
            }
//            }else if (sum == 60){
//                data.add(sum + " (相当于三年级水平)");
//            }
//            }else if (sum == 110){
//                data.add(sum + " (相当于四年级水平)");
//            }else if (sum == 160){
//                data.add(sum + " (相当于五年级水平)");
//            }else if (sum == 200){
//                data.add(sum + " (相当于六年级水平)");
//            }else if (sum == 240){
//                data.add(sum + " (相当于七年级水平)");
//            }else if (sum == 320){
//                data.add(sum + " (相当于八年级水平)");
//            }else if (sum == 400){
//                data.add(sum + " (相当于九年级水平)");
//            }else if (sum == 450){
//                data.add(sum + " (相当于高中一年级水平)");
//            }else if (sum == 500){
//                data.add(sum + " (相当于高中二年级水平)");
//            }else if (sum == 550){
//                data.add(sum + " (相当于高中三年级水平)");
//            }else if (sum == 600) {
//                data.add(sum + " (相当于大学及以上水平)");
//            }else{
//                data.add(sum + " ");
//            }
        }
        SelectLevelAdapter adapter = new SelectLevelAdapter(this, data);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position); // 设置被选中的项

                showAlertDialog(position);
            }
        });

        //初始化按钮
        btnToTest = (Button) findViewById(R.id.btn_to_test);
        if (btnToTest != null) {
            btnToTest.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(SelectLevelActivity.this, UserTestActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("SelectLevelActivity", "Button to test not found.");
        }
    }


    /**
     * @param position:
     * @return void
     * @author Lee
     * @description 选择阅读难度
     * @date 2024/5/8 14:22
     */
    private void showAlertDialog(int position) {
        int num = position;
        new AlertDialog.Builder(this)
                .setTitle("阅读难度选择")  // 设置对话框标题
                .setMessage("选择阅读难度为" + num)  // 设置对话框显示的文本
                .setPositiveButton("确定", (dialog, which) -> {
                    // 用户点击确定按钮的处理逻辑
                    finish();
                })
                .setNegativeButton("取消", (dialog, which) -> {

                })
                .show();  // 显示对话框
    }


}