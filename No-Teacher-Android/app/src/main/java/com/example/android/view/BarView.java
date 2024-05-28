package com.example.android.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.bean.entity.BarEntity;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/28 9:14
 * @Decription:分段堆积柱状图View（绘制分段柱状图）
 */
public class BarView extends View {
    private BarEntity data;
    private Paint paint;
    private float animTimeCell = 0;
    private float fillHeight; // 添加 fillHeight 变量


    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data != null) {
            // 高度填充
            fillHeight = data.getFillScale() * getHeight();
            if (fillHeight != 0) {
                paint.setColor(Color.TRANSPARENT);
                canvas.drawRect(0f, 0f, getWidth(), fillHeight, paint);
                canvas.translate(0f, fillHeight);
            }
            // 负面
            paint.setColor(Color.parseColor(data.negativeColor));
            canvas.drawRect(0f, 0f, getWidth(), data.getNegativePer() * getHeight(), paint);
            canvas.translate(0f, data.getNegativePer() * getHeight());
            // 中性
            paint.setColor(Color.parseColor(data.neutralColor));
            canvas.drawRect(0f, 0f, getWidth(), data.getNeutralPer() * getHeight(), paint);
            canvas.translate(0f, data.getNeutralPer() * getHeight());
            // 正面
            paint.setColor(Color.parseColor(data.positiveColor));
            canvas.drawRect(0f, 0f, getWidth(), data.getPositivePer() * getHeight(), paint);
            canvas.translate(0f, data.getPositivePer() * getHeight());
        }
    }

    public void startAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "animTimeCell", 0, 1).setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animTimeCell = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    public float getFillHeight() {
        return getHeight() * (data.getNegativePer() + data.getNeutralPer() + data.getPositivePer());
    }

    public void setData(BarEntity data) {
        this.data = data;
        invalidate(); // 数据改变时重新绘制视图
    }

    public BarEntity getData() {
        return data;
    }

    public float getAnimTimeCell() {
        return animTimeCell;
    }

    public void setAnimTimeCell(float animTimeCell) {
        this.animTimeCell = animTimeCell;
        invalidate(); // 动画时间单元值改变时重新绘制视图
    }
}
