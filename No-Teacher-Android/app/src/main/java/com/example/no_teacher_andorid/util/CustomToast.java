package com.example.no_teacher_andorid.util;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhilehuo.advenglish.R;


public class CustomToast {
    private static Toast mToast;
    private static final Handler mainHandler;

    static {
        mainHandler = new Handler(Looper.getMainLooper());
    }
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private static Toast sToast;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private static Runnable q = new Runnable() {
        public void run() {
            sToast.cancel();
        }
    };

    public static void showToast(Context context, String text) {
        showToast(context,text,2000);
    }

    public static void showToast(Context mContext, String text, int duration) {
        if (mContext == null){
            return;
        }
        Thread thread = Thread.currentThread();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                showUpRToast(mContext,text,duration);
            } else {
                mainHandler.post(()->{
                    showUpRToast(mContext,text,duration);
                });
            }
        } else {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                showLowerRToast(mContext,text,duration);
            } else {
                mainHandler.post(()->{
                    showLowerRToast(mContext,text,duration);
                });
            }
        }
    }

    private static void showUpRToast(Context context, String text, int duration){
        sHandler.removeCallbacks(q);
        if (sToast == null){
            sToast = new Toast(context);
        }
        sHandler.postDelayed(q, duration);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        //根据自己需要对view设置text或其他样式
        sToast.setView(view);
        TextView textView = view.findViewById(R.id.tv_toast_message);
        textView.setText(text);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    private static void showLowerRToast(Context context, String text, int duration){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P){
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mHandler.removeCallbacks(r);
            if (mToast != null)
                mToast.setText(text);
            else
                mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mHandler.postDelayed(r, duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public static void showToast(Context mContext, int resId) {
        showToast(mContext, resId, 2000);
    }
    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
