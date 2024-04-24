package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.no_teacher_andorid.R;
import com.example.android.adapter.SelectLevelAdapter;

import java.util.ArrayList;

public class SelectLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.list_level);
        ArrayList<String> data = new ArrayList<>();
        int sum = 0;
        for (int i = 0;i <240;i++){
            sum += 5;
            if (sum == 5){
                data.add(sum + " (相当于一年级及以下水平)");
            }else if (sum == 10){
                data.add(sum + " (相当于一年级水平)");
            }else if (sum == 30){
                data.add(sum + " (相当于二年级水平)");
            }else if (sum == 60){
                data.add(sum + " (相当于三年级水平)");
            }else if (sum == 110){
                data.add(sum + " (相当于四年级水平)");
            }else if (sum == 160){
                data.add(sum + " (相当于五年级水平)");
            }else if (sum == 200){
                data.add(sum + " (相当于六年级水平)");
            }else if (sum == 240){
                data.add(sum + " (相当于七年级水平)");
            }else if (sum == 320){
                data.add(sum + " (相当于八年级水平)");
            }else if (sum == 400){
                data.add(sum + " (相当于九年级水平)");
            }else if (sum == 450){
                data.add(sum + " (相当于高中一年级水平)");
            }else if (sum == 500){
                data.add(sum + " (相当于高中二年级水平)");
            }else if (sum == 550){
                data.add(sum + " (相当于高中三年级水平)");
            }else if (sum == 600) {
                data.add(sum + " (相当于大学及以上水平)");
            }else{
                data.add(sum + " ");
            }
        }
        SelectLevelAdapter adapter = new SelectLevelAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position); // 设置被选中的项
            }
        });
    }
}