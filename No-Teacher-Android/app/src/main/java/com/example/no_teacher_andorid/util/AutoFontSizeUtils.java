package com.example.no_teacher_andorid.util;

import android.text.TextUtils;

/**
 * @Author wpt
 * @Date 2023/5/15-18:43
 * @desc
 */
public class AutoFontSizeUtils {

    ///defaultSize 默认的字体大小
    ///defaultCount 默认字体大小时的个数
    ///text 内容

    /**
     *
     * @param defaultSize 默认的字体大小
     * @param defaultCount 默认字体大小时的个数
     * @param text 内容
     * @return
     */
    public static float getFontSize(float defaultSize,int defaultCount,String text) {
        int length = TextUtils.isEmpty(text) ? 0 : text.length();
        return (length < defaultCount) ? defaultSize : defaultSize * defaultCount / (length + 1);
    }
}
