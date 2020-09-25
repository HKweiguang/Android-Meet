package com.huangk.meet.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.huangk.framework.base.BaseActivity
import com.huangk.meet.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mHandler: Handler

    private var textTime = TIME

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mHandler = Handler(mainLooper) {
            when (it.what) {
                H_TIME -> {
                    textTime--
                    btn_send_code.setText("${textTime}s")
                    if (textTime > 0) {
                        mHandler.sendEmptyMessageDelayed(H_TIME, 1000)
                    } else {
                        btn_send_code.isEnabled = true
                        btn_send_code.text = resources.getString(R.string.text_login_send)
                    }
                }
            }
            false
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val H_TIME: Int = 1001
        private const val TIME = 60
    }
}