package com.huangk.meet.base

import android.app.Application
import com.huangk.framework.FrameWork

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FrameWork.getInstance().initFrameWork(this)
    }
}