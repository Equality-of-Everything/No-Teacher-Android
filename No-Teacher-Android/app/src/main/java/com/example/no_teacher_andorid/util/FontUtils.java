package com.example.no_teacher_andorid.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @Author wpt
 * @Date 2022/11/28-18:45
 * @desc 字体工具类
 */
public class FontUtils {

    private Typeface puHuiRegularTypeface;//

    private Typeface puhuiHeavyTypeface;//方正楷体

    private static class FounderFontHolder {
        private static FontUtils instance = new FontUtils();
    }

    public Typeface getPuHuiHeavyTypefaceTypeface(Context context) {
        if (context == null) {
            throw new NullPointerException("context must no null");
        }
        if (puhuiHeavyTypeface == null) {
            puhuiHeavyTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/alibaba_puhui_ti_heavy.ttf");
        }
        return puhuiHeavyTypeface;
    }

    public Typeface getPuHuiRegularTypeface(Context context) {
        if (context == null) {
            throw new NullPointerException("context must no null");
        }
        if (puHuiRegularTypeface == null) {
            puHuiRegularTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/alibaba_puhui_ti_regular.ttf");
        }
        return puHuiRegularTypeface;
    }

    private FontUtils() {
    }

    public static FontUtils getInstance() {

        return FounderFontHolder.instance;
    }

}
