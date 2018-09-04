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
        setRegisterBtnClickListener()
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

    private fun setRegisterBtnClickListener() {
        register_btn.setOnClickListener {
            if (!isPswEquals()) {
                register_failure_hint.text = getString(R.string.input_psw_not_equals)

            }
        }
    }

    private fun isPswEquals(): Boolean {
        var startPsw = et_regist_psw.text.toString()
        var secondPsw = et_confirm_psw.text.toString()
        return startPsw == secondPsw
    }

    override fun initResource() {

    }
}