package com.zhongke.common.utils

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zhilehuo.advenglish.R
import com.zhilehuo.advenglish.base.BaseApplication

/**
 * 扩展类
 */

/**
 * 快速转dp的扩展函数
 */
val Float.dp: Float
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * 获取颜色
 */
fun Int.asColor() = ContextCompat.getColor(BaseApplication.mInstance, this)

/**
 * 获取drawable
 */
fun Int.asDrawable() = ContextCompat.getDrawable(BaseApplication.mInstance, this)


/**
 * 设置View的圆角与颜色
 */
fun View.setRoundRectBg(color: Int = Color.RED, cornerRadius: Int = 15.dp) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius.toFloat())
        shape = GradientDrawable.RECTANGLE
    }
}

fun View.isVisible() = this.visibility == View.VISIBLE
fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

//*****************点击防抖**********************//
private var <T : View>T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTimeKey) != null) getTag(R.id.triggerLastTimeKey) as Long else 0
    set(value) {
        setTag(R.id.triggerLastTimeKey, value)
    }

/**
 * 给view添加一个延迟的属性（用来屏蔽连击操作）
 */
private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelayKey) != null) getTag(R.id.triggerDelayKey) as Long else -1
    set(value) {
        setTag(R.id.triggerDelayKey, value)
    }

fun View.clickAble(): Boolean {
    var clickable = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        triggerLastTime = currentClickTime
        clickable = true
    }

    return clickable
}

//设置一个防抖的点击事件
fun <T : View> T.singleClick(delay: Long = 2000L, block: (T) -> Unit) {
    triggerDelay = delay
    setOnClickListener {
        if (clickAble()) {
            block(this)
        }
    }
}
//*****************点击防抖**********************//

fun Fragment.getDrawable(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(requireContext(), id) ?: ColorDrawable(Color.TRANSPARENT)
}


