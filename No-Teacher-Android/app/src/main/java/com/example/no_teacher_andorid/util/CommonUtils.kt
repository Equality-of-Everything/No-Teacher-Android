package com.zhilehuo.advenglish.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.zhilehuo.advenglish.R

/**
 * Created by fushize on 2023/11/24.
 */

object CommonUtils {

    //文库标签的颜色
    val colorArray = intArrayOf(
        R.color.color_FF44D7B6,
        R.color.color_FF01AAA2,
        R.color.color_FF7C7EE4,
        R.color.color_FF32C5FF,
        R.color.color_FF6DD400,
        R.color.color_FFFF9500,
        R.color.color_FFD4B07E,
        R.color.color_FFF56178,
        R.color.color_FF7BD2D8,
        R.color.color_FFCC95D9,
        R.color.color_FF3DBC80,
        R.color.color_FFDD4ABC)

    fun getAccuracyColor(context: Context, accuracy: Double): Int {
        val colorResId = if (accuracy >= 0 && accuracy < 0.5) {
            R.color.color_correct_ratio_1
        } else if (accuracy >= 0.5 && accuracy < 0.75) {
            R.color.color_correct_ratio_2
        } else if (accuracy >= 0.75 && accuracy < 1) {
            R.color.color_correct_ratio_3
        } else {
            R.color.color_correct_ratio_4
        }
        return ContextCompat.getColor(context, colorResId)
    }

    fun getAccuracyDrawable(context: Context, accuracy: Double): Drawable? {
        val colorResId = if (accuracy >= 0 && accuracy < 0.5) {
            R.drawable.ic_correct_ratio_1
        } else if (accuracy >= 0.5 && accuracy < 0.75) {
            R.drawable.ic_correct_ratio_2
        } else if (accuracy >= 0.75 && accuracy < 1) {
            R.drawable.ic_correct_ratio_3
        } else {
            R.drawable.ic_correct_ratio_4
        }
        return ContextCompat.getDrawable(context, colorResId)
    }

    fun getClickRatioColor(context: Context, color: Int): Int {
        val colorId = when (color) {
            1 -> R.color.color_click_ratio_1
            2 -> R.color.color_click_ratio_2
            3 -> R.color.color_click_ratio_3
            4 -> R.color.color_click_ratio_4
            5 -> R.color.color_click_ratio_5
            else -> R.color.color_click_ratio_1
        }
        return ContextCompat.getColor(context, colorId)
    }

    fun getClickRatioLeftDrawable(context: Context, color: Int): Drawable? {
        val drawableId = when (color) {
            1 -> R.drawable.ic_click_ratio_1
            2 -> R.drawable.ic_click_ratio_2
            3 -> R.drawable.ic_click_ratio_3
            4 -> R.drawable.ic_click_ratio_4
            5 -> R.drawable.ic_click_ratio_5
            else -> R.drawable.ic_click_ratio_1
        }
        return ContextCompat.getDrawable(context, drawableId)
    }
}