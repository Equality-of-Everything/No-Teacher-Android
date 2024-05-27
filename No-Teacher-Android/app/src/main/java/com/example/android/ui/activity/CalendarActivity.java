package com.example.android.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import com.example.no_teacher_andorid.R;
import com.google.android.material.appbar.MaterialToolbar;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private MaterialToolbar topAppBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        topAppBar =findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 处理日期选择事件
                String selectedDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                showCustomDialog(selectedDate);
            }
        });
    }

    private void showCustomDialog(String selectedDate) {
        // 创建自定义对话框
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_calender, null);
        dialog.setContentView(dialogView);
        TextView tvSelectedDate = dialogView.findViewById(R.id.tvSelectedDate);
        tvSelectedDate.setText("You selected: " + selectedDate);
        dialog.show();
    }
}