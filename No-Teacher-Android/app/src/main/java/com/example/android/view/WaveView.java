package com.example.android.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/6/6 9:23
 * @Decription:
 */
public class WaveView extends View {

    // 成员变量
    private int mProgress;
    private Paint mWavePaint;
    private Bitmap mBallBitmap;
    private ObjectAnimator mProgressAnimator;
    private ObjectAnimator mWaveStopAnimator;
    private boolean isWaveMoving;
    private int mOffsetA;
    private int mOffsetB;
    private int mWaveHeightA;
    private int mWaveHeightB;
    private float mWaveACycle;
    private float mWaveBCycle;
    private int mWaveSpeedA;
    private int mWaveSpeedB;
    private int mWaveColor;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mWavePaint = new Paint();
    }

    /**
     * 波浪的函数，用于求y值
     * 函数为a*sin(b*(x + c))+d
     * @param x           x轴
     * @param offset      偏移
     * @param waveHeight  振幅
     * @param waveCycle   周期
     * @return
     */
    private double getWaveY(int x, int offset, int waveHeight, float waveCycle) {
        return waveHeight * Math.sin(waveCycle * (x + offset)) + (1 - mProgress / 100.0) * getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getHeight() > 0 && getWidth() > 0) {
            // 绘制边缘
            Paint paint = new Paint();
            paint.setColor(mWaveColor);
            paint.setStyle(Paint.Style.STROKE);
            RectF edge = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawArc(edge, 0, 360, false, paint);

            canvas.drawColor(Color.TRANSPARENT);
            int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            if (isWaveMoving) {
                /**
                 * 如果有波浪，则绘制两条波浪
                 * 波浪实际上是一条条一像素的直线组成的，线的顶端是根据正弦函数得到的
                 */
                for (int i = 0; i < getWidth(); i++) {
                    canvas.drawLine(i, (int) getWaveY(i, mOffsetA, mWaveHeightA, mWaveACycle), i, getHeight(), mWavePaint);
                    canvas.drawLine(i, (int) getWaveY(i, mOffsetB, mWaveHeightB, mWaveBCycle), i, getHeight(), mWavePaint);
                }
            } else {
                /**
                 * 如果没有波浪，则绘制两次矩形
                 * 之所以绘制两次，是因为波浪有两条，所以除了浪尖的部分，其他部分都是重合的，颜色较重
                 */
                float height = (1 - mProgress / 100.0f) * getHeight();
                canvas.drawRect(0, height, getWidth(), getHeight(), mWavePaint);
                canvas.drawRect(0, height, getWidth(), getHeight(), mWavePaint);
            }
            // 设置遮罩效果，绘制遮罩
            mWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mBallBitmap, 0, 0, mWavePaint);
            mWavePaint.setXfermode(null);
            canvas.restoreToCount(sc);
        }
    }

    /**
     * 设置进度，并且以动画的形式上涨到该进度
     * @param progress 进度
     * @param duration 持续时间
     * @param delay    延时
     */
    @SuppressLint("ObjectAnimatorBinding")
    public void startProgress(int progress, long duration, long delay) {
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }
        if (mWaveStopAnimator != null && mWaveStopAnimator.isRunning()) {
            mWaveStopAnimator.cancel();
        }
        isWaveMoving = true;
        mProgressAnimator = ObjectAnimator.ofInt(this, "Progress", progress);
        mProgressAnimator.setDuration(duration);
        mProgressAnimator.setStartDelay(delay);
        mProgressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mWaveStopAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 改变曲线的偏移，达到波浪运动的效果
                mOffsetA += mWaveSpeedA;
                mOffsetB += mWaveSpeedB;
                invalidate();
            }
        });
        mProgressAnimator.start();
    }
}
