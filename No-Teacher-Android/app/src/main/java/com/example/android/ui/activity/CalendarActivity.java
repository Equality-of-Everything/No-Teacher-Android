package com.example.android.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.android.bean.entity.BarDataEntity;
import com.example.no_teacher_andorid.R;

import java.text.DecimalFormat;
import com.google.android.material.appbar.MaterialToolbar;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private MaterialToolbar topAppBar ;
    private DecimalFormat format = new DecimalFormat("#.##");

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

        tvSelectedDate.setText(selectedDate);

        LinearLayout barChartContainer = dialogView.findViewById(R.id.bar_chart_container);
        bindData(barChartContainer);

        tvSelectedDate.setText(selectedDate);
        dialog.show();
    }

    private void bindData(LinearLayout container) {
        container.removeAllViews();
        BarDataEntity data = new BarDataEntity();
        data.parseData();
        if (data == null || data.getTypeList() == null) {
            return;
        }
        int color1 = Color.parseColor("#c5ecce");
        int color2 = Color.parseColor("#f5e1a7");
        int color3 = Color.parseColor("#ffb5a0");
        double maxScale = 0;
        for (int i = 0; i < data.getTypeList().size(); i++) {
            if (data.getTypeList().get(i).getTypeScale() > maxScale)
                maxScale = data.getTypeList().get(i).getTypeScale();
        }
        for (int i = 0; i < data.getTypeList().size(); i++) {
            final View item = LayoutInflater.from(this).inflate(R.layout.item_calander_bar, container, false);
            final BarDataEntity.Type type = data.getTypeList().get(i);
            ((TextView) item.findViewById(R.id.name)).setText(type.getTypeName());
            final View bar = item.findViewById(R.id.bar);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(40); // 设置圆角半径
            if (i == 0) {
                drawable.setColor(color1);
            } else if (i == 1) {
                drawable.setColor(color2);
            } else {
                drawable.setColor(color3);
            }
            bar.setBackground(drawable); // 设置背景为圆角矩形
            ((TextView) item.findViewById(R.id.percent)).setText(format.format(type.getTypeScale()));
            ((TextView) item.findViewById(R.id.percent)).setTextColor(Color.BLACK);
            final double finalMaxScale = maxScale;
            item.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    item.getViewTreeObserver().removeOnPreDrawListener(this);
                    int barContainerWidth = item.findViewById(R.id.bar_container).getWidth();
                    int percentTxtWidth = item.findViewById(R.id.percent).getWidth();
                    final int initWidth = barContainerWidth - percentTxtWidth;
                    final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bar.getLayoutParams();
                    lp.width = (int) (initWidth * type.getTypeScale()/ finalMaxScale * 100 / 100);
                    bar.setLayoutParams(lp);
                    item.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final int initWidth = bar.getWidth();
                            final ObjectAnimator anim = ObjectAnimator.ofFloat(bar, "alpha", 0.0F, 1.0F).setDuration(1500);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    float cVal = (Float) anim.getAnimatedValue();
                                    lp.width = (int) (initWidth * cVal);
                                    bar.setLayoutParams(lp);
                                }
                            });
                            anim.start();
                        }
                    }, 0);
                    return false;
                }
            });
            container.addView(item);
        }
    }

}