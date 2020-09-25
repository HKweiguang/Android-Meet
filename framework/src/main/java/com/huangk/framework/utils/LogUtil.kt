package com.huangk.framework.utils

import android.app.Activity
import android.util.Log
import com.huangk.framework.BuildConfig
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.util.*

object LogUtil {

    enum class LogLevel {
        V,
        D,
        I,
        W,
        E,
    }

    fun log(text: String, level: LogLevel, activity: Activity? = null) {
        if (BuildConfig.LOG_DEBUG) {
            if (text.isNotEmpty()) {
                when (level) {
                    LogLevel.V -> {
                        Log.v(BuildConfig.LOG_TAG, text)
                    }
                    LogLevel.D -> {
                        Log.d(BuildConfig.LOG_TAG, text)
                    }
                    LogLevel.I -> {
                        Log.i(BuildConfig.LOG_TAG, text)
                    }
                    LogLevel.W -> {
                        Log.w(BuildConfig.LOG_TAG, text)
                    }
                    LogLevel.E -> {
                        Log.e(BuildConfig.LOG_TAG, text)
                    }
                }
                activity?.run {
                    writeToFile(this, text, level.name)
                }
            }
        }
    }

    private fun writeToFile(activity: Activity, text: String, level: String) {
        val fileName = "${activity.cacheDir.absolutePath}/Meet.log"
        val log = "${TimeUtil.format(Date().time)} $level: $text \n"
        val file = File(fileName)
        var fos: FileOutputStream? = null
        var bw: BufferedWriter? = null
        try {
            fos = FileOutputStream(file, true)
            bw = BufferedWriter(OutputStreamWriter(fos, Charset.forName("gbk")))
            bw.write(log)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bw?.run {
                try {
                    close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            fos?.run {
                try {
                    close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}