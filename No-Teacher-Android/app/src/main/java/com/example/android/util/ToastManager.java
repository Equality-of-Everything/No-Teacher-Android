package com.example.android.util;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.no_teacher_andorid.R;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/20 14:20
 * @Decription :
 */

public class ToastManager {

    /**
     * @param context:
     * @param message:
     * @return void
     * @author Lee
     * @description 自定义Toast
     * @date 2024/5/20 14:34
     */
    public static void showCustomToast(Context context, String message) {
        Toast toast = new Toast(context);
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int height = display.getHeight();

        toast.setGravity(Gravity.CENTER, 0, height / 3);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tvMessage = view.findViewById(R.id.ErrorTips);
        tvMessage.setText(message);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }




}






















