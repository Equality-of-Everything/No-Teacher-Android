package com.example.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/6/5 15:00
 * @Decription:
 */
public class WaveView extends View {
    private Paint wavePaint;
    private int waveColor;
    private float waveWidth;
    private float waveHeight;
    private double decibel;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        waveColor = Color.BLUE; // 波形图颜色
        waveWidth = 5; // 波形图宽度
        waveHeight = 100; // 默认波形图高度
        decibel = 0; // 默认声音分贝
        wavePaint = new Paint();
        wavePaint.setColor(waveColor);
        wavePaint.setStrokeWidth(waveWidth);
        wavePaint.setStyle(Paint.Style.STROKE);
    }

    public void updateWaveHeight(double decibel) {
        // 根据声音分贝更新波形图高度
        this.decibel = decibel;
        invalidate(); // 刷新视图
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 在此处绘制波形图
        float centerY = getHeight() / 2;
        float startX = 0;
        float endX = getWidth();
        float offsetY = (float) (decibel * 0.5); // 将声音分贝映射到波形图的高度范围内
        canvas.drawLine(startX, centerY - offsetY, endX, centerY - offsetY, wavePaint);
    }
}