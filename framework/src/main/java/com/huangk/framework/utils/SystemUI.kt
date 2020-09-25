package com.huangk.framework.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View

object SystemUI {

    fun fixSystemUI(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 获取最顶层 View
            activity.apply {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN and View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }
}