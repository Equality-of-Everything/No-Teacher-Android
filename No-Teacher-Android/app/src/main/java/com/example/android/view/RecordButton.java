package com.example.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/6/5 15:02
 * @Decription:
 */
public class RecordButton extends androidx.appcompat.widget.AppCompatButton {

    private OnRecordListener mListener;
    private OnRecordStartListener mStartListener;
    private OnRecordStopListener mStopListener; // 新添加的结束录音监听器

    public RecordButton(Context context) {
        super(context);
        init();
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText("Record");
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartListener != null) {
                    mStartListener.onStartRecord();
                }
            }
        });
    }

    public void setOnRecordListener(OnRecordListener listener) {
        mListener = listener;
    }

    public void setOnRecordStartListener(OnRecordStartListener listener) {
        mStartListener = listener;
    }

    public void setOnRecordStopListener(OnRecordStopListener listener) {
        mStopListener = listener;
    }

    public interface OnRecordListener {
        void onStop();
    }

    public interface OnRecordStartListener {
        void onStartRecord();
    }

    // 新添加的结束录音监听器接口
    public interface OnRecordStopListener {
        void onStopRecord();
    }
}
