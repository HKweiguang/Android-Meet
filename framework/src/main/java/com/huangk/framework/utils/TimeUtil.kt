package com.huangk.framework.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
object TimeUtil {

    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val sdf1 = SimpleDateFormat("HH:mm:ss")

    fun format(ms: Long) = sdf.format(ms)

    fun format1(ms: Long) = sdf1.format(ms)

}