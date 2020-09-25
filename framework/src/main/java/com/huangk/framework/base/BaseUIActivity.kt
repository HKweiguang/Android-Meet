package com.huangk.framework.base

import android.os.Bundle
import com.huangk.framework.utils.SystemUI

open class BaseUIActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUI.fixSystemUI(this)
    }
}