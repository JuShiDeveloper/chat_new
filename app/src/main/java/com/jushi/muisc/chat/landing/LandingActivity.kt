package com.jushi.muisc.chat.landing

import android.view.View
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R
import kotlinx.android.synthetic.main.activity_regist_layout.*

/**
 * 登陆/注册界面
 */
class LandingActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.activity_regist_layout)
    }

    override fun initWidget() {
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        to_register_btn.setOnClickListener {
            landing_layout.visibility = View.GONE
            register_layout.visibility = View.VISIBLE
        }
        register_page_back_btn.setOnClickListener {
            landing_layout.visibility = View.VISIBLE
            register_layout.visibility = View.GONE
        }
    }

    override fun initResource() {

    }
}