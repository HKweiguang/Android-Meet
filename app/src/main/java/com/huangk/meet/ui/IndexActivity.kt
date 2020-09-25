package com.huangk.meet.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.huangk.framework.base.BaseActivity
import com.huangk.framework.entity.Constants
import com.huangk.framework.utils.SpUtils
import com.huangk.meet.MainActivity
import com.huangk.meet.R

class IndexActivity : BaseActivity() {

    private val mHandler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == SKIP_MAIN) {
            startMain()
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 1000 * 2)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 进入主页
     */
    private fun startMain() {
        // 判断 App 是否是第一次 install
        val isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true)
        val intent = Intent()
        if (isFirstApp) {
            // 跳转到引导页
            intent.setClass(this@IndexActivity, GuideActivity::class.java)
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false)
        } else {
            // 是否登陆过
            val token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "")
            if (token.isEmpty()) {
                // 跳转登录页
                intent.setClass(this@IndexActivity, LoginActivity::class.java)
            } else {
                // 跳转主页
                intent.setClass(this@IndexActivity, MainActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SKIP_MAIN: Int = 1000
    }
}