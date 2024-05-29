package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.bean.entity.BarEntity;
import com.example.android.bean.entity.SourceEntity;
import com.example.android.view.BarGroup;
import com.example.android.view.BarView;
import com.example.no_teacher_andorid.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColumnActivity extends AppCompatActivity {

    private DecimalFormat mFormat = new DecimalFormat("#.##");
    private HorizontalScrollView root;
    private BarGroup barGroup;
    private float sourceMax;
    private View popView;
    private PopupWindow popupWindow;
    private int initPopHeight = 0;
    private MaterialToolbar topAppBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);
        barGroup = findViewById(R.id.bar_group);
        root = findViewById(R.id.bar_scroll);
        popView = LayoutInflater.from(this).inflate(R.layout.pop_bg, null);
        //返回事件
        topAppBar=findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setBarChart();
    }

    private void setBarChart() {
        barGroup = findViewById(R.id.bar_group);
        root = findViewById(R.id.bar_scroll);
        popView = LayoutInflater.from(this).inflate(R.layout.pop_bg, null);

        final SourceEntity sourceEntity = new SourceEntity();
        sourceEntity.parseData();
        setYAxis(sourceEntity.getList());

        barGroup.removeAllViews();
        List<BarEntity> datas = new ArrayList<>();
        final int size = sourceEntity.getList().size();
        sourceMax = 0;
        for (SourceEntity.Source source : sourceEntity.getList()) {
            if (source.getAllCount() > sourceMax) {
                sourceMax = source.getAllCount();
            }
        }

        // 固定最大值为250
        sourceMax = 250;

        for (int i = 0; i < size; i++) {
            BarEntity barEntity = new BarEntity();
            SourceEntity.Source entity = sourceEntity.getList().get(i);
            String negative = mFormat.format(entity.getBadCount() / sourceMax);
            barEntity.setNegativePer(Float.parseFloat(negative));
            String neutral = mFormat.format(entity.getOtherCount() / sourceMax);
            barEntity.setNeutralPer(Float.parseFloat(neutral));
            String positive = mFormat.format(entity.getGoodCount() / sourceMax);
            barEntity.setPositivePer(Float.parseFloat(positive));
            barEntity.setTitle(entity.getSource());
            barEntity.setScale(entity.getScale());
            barEntity.setAllcount(entity.getAllCount());
            /*计算柱状图透明区域的比例*/
            barEntity.setFillScale(1 - entity.getAllCount() / sourceMax);
            datas.add(barEntity);
        }
        barGroup.setDatas(datas);
        //计算间距
        barGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                barGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = findViewById(R.id.bg).getMeasuredHeight();
                final View baseLineView = findViewById(R.id.left_base_line);
                int baseLineTop = baseLineView.getTop();
                barGroup.setHeight(sourceMax, height - baseLineTop - baseLineView.getHeight() / 2);
                barGroup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BarView barItem = (BarView) barGroup.getChildAt(0).findViewById(R.id.barView);
                        int baseLineHeight = findViewById(R.id.base_line).getTop();
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) root.getLayoutParams();
                        int left = baseLineView.getLeft();
                        lp.leftMargin = (int) (left + getResources().getDisplayMetrics().density * 3);
                        lp.topMargin = Math.abs(baseLineHeight - barItem.getHeight());
                        root.setLayoutParams(lp);
                    }
                }, 0);

                for (int i = 0; i < size; i++) {
                    final BarView barItem = (BarView) barGroup.getChildAt(i).findViewById(R.id.barView);
                    barItem.setAnimTimeCell(1.0f); // 设置动画时间单元
                    barItem.startAnimation(); // 开始动画
                    final int finalI = i;
                    barItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final float top = view.getHeight() - barItem.getFillHeight();
                            SourceEntity.Source ss = sourceEntity.getList().get(finalI);
                            String showText = "优秀：" + (int) ss.getGoodCount() + "个\n"
                                    + "良好：" + (int) ss.getOtherCount() + "个\n"
                                    + "一般：" + (int) ss.getBadCount() + "个\n"
                                    + "总共：" + (int) ss.getAllCount() + "个";
                            ((TextView) popView.findViewById(R.id.txt)).setText(showText);
                            showPop(barItem, top);
                        }
                    });
                }

                // 滚动到最后一个子视图
                root.post(new Runnable() {
                    @Override
                    public void run() {
                        root.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });

                return false;
            }
        });
    }

    private void setYAxis(List<SourceEntity.Source> list) {
        TextView tv_num1 = findViewById(R.id.tv_num1);
        TextView tv_num2 = findViewById(R.id.tv_num2);
        TextView tv_num3 = findViewById(R.id.tv_num3);
        TextView tv_num4 = findViewById(R.id.tv_num4);
        TextView tv_num5 = findViewById(R.id.tv_num5);

        tv_num1.setText(String.valueOf(50));
        tv_num2.setText(String.valueOf(100));
        tv_num3.setText(String.valueOf(150));
        tv_num4.setText(String.valueOf(200));
        tv_num5.setText(String.valueOf(250));
    }

    private void showPop(final View barItem, final float top) {
        if (popupWindow != null)
            popupWindow.dismiss();
        popupWindow = null;
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(barItem, barItem.getWidth() / 2, -((int) top + initPopHeight));
        if (initPopHeight == 0) {
            popView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    popView.getViewTreeObserver().removeOnPreDrawListener(this);
                    initPopHeight = popView.getHeight();
                    popupWindow.update(barItem, barItem.getWidth() / 2, -((int) top + initPopHeight),
                            popupWindow.getWidth(), popupWindow.getHeight());
                    return false;
                }
            });
        }
    }
}