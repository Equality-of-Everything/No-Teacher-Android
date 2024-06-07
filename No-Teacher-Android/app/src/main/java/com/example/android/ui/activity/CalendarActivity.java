package com.example.android.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.android.bean.entity.BarDataEntity;
import com.example.android.util.TokenManager;
import com.example.android.viewmodel.CalenderViewModel;
import com.example.android.viewmodel.HomeViewModel;
import com.example.no_teacher_andorid.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.material.appbar.MaterialToolbar;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private MaterialToolbar topAppBar ;
    private DecimalFormat format = new DecimalFormat("#.##");
    private CalenderViewModel calenderViewModel;
    private HomeViewModel homeViewModel;
    private List<BarDataEntity.Type> types = new ArrayList<>();

    AtomicInteger excellent = new AtomicInteger();
    AtomicInteger good = new AtomicInteger();
    AtomicInteger normal = new AtomicInteger();
    BarDataEntity.Type type1 = new BarDataEntity.Type();
    final BarDataEntity.Type type2 = new BarDataEntity.Type();
    final BarDataEntity.Type type3 = new BarDataEntity.Type();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        type1.setTypeName("优秀");
        type2.setTypeName("良好");
        type3.setTypeName("一般");

        calendarView = findViewById(R.id.calendarView);
        topAppBar =findViewById(R.id.topAppBar);
        calenderViewModel = new CalenderViewModel();
        homeViewModel = new HomeViewModel();
        calenderViewModel.getReadLongDataCount(this, TokenManager.getUserId(this));
        homeViewModel.getTotalWordNum(this,TokenManager.getUserId(this));
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
                String selectedDate = year + "年"  +(month + 1) + "月" + dayOfMonth+"日";
                showCustomDialog(selectedDate,month+1+"",dayOfMonth);
            }
        });
    }


    private void showCustomDialog(String selectedDate,String month,int dayOfMonth) {
        // 创建自定义对话框
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_calender, null);
        dialog.setContentView(dialogView);
        TextView tvSelectedDate = dialogView.findViewById(R.id.tvSelectedDate);
        TextView tvReadTime = dialogView.findViewById(R.id.text_min);
        TextView tvReadCount = dialogView.findViewById(R.id.text_read_count);
        TextView tvReadWordCount = dialogView.findViewById(R.id.text_count);
        calenderViewModel.getReadlogDataCountLiveData().observe(this, readLogDataCounts -> {
            readLogDataCounts.forEach(readLogDataCount -> {
                if (readLogDataCount.getToday().toLocaleString().contains(selectedDate)) {
                    tvReadTime.setText(readLogDataCount.getTotalReadDuration() / 1000 / 60 + "");
                    if (readLogDataCount.getTotalReadWordNum() == null) {
                        tvReadCount.setText("0");
                    } else {
                        tvReadCount.setText(readLogDataCount.getTotalReadWordNum() + "");
                    }
                    homeViewModel.getTotalWordNumLiveData().observe(this, totalWordNum -> {
                        tvReadWordCount.setText(totalWordNum + "");
                    });
                }

                });

            });


        calenderViewModel.getRecordingCountData(this,TokenManager.getUserId(this),month);

        calenderViewModel.getWordDetailRecordingLiveData().observe(this, wordDetailRecordings -> {

            wordDetailRecordings.forEach(wordDetailRecording -> {
                if (wordDetailRecording.getTime().getDate()==dayOfMonth) {
                    if (0 <= wordDetailRecording.getScore() && wordDetailRecording.getScore() <= 60) {
                        normal.getAndIncrement();
                    } else if (60 < wordDetailRecording.getScore() && wordDetailRecording.getScore() <= 80) {
                        good.getAndIncrement();
                    }else{
                        excellent.getAndIncrement();
                    }
                }

            });
        });

        type1.setTypeScale(excellent.get());
        type2.setTypeScale(good.get());
        type3.setTypeScale(normal.get());
        types.add(type1);
        types.add(type2);
        types.add(type3);

        tvSelectedDate.setText(selectedDate);

        LinearLayout barChartContainer = dialogView.findViewById(R.id.bar_chart_container);
        bindData(barChartContainer);
        tvSelectedDate.setText(selectedDate);
        dialog.show();
        dialog.setOnDismissListener(dialog1 -> {
            normal.set(0);
            good.set(0);
            excellent.set(0);
            types.clear();
        });

    }


    private void bindData(LinearLayout container) {
        container.removeAllViews();
        BarDataEntity data = new BarDataEntity();
        data.parseData(types);
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