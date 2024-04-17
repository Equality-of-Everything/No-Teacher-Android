package com.zhilehuo.advenglish.util

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Glide工具类，支持加载圆角图片
 * Created by fushize on 2023/11/7.
 */
object ImageLoaderUtil {
    // 加载网络图片并添加圆角
    fun loadImageWithRoundedCorners(context: Context, imageUrl: String, imageView: ImageView,
        radius: Int = 0) {
        val options = RequestOptions().transform(RoundedCorners(dpToPx(context, radius)))

        Glide.with(context).load(imageUrl).apply(options).into(imageView)
    }

    // 将dp转换为像素（px）
    private fun dpToPx(context: Context, dp: Int): Int {
        val resources = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }
}
