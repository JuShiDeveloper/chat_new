package com.jushi.muisc.chat.landing

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R
import kotlinx.android.synthetic.main.activity_regist_layout.*
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * 登陆/注册界面
 */
class LandingActivity : BaseActivity() {

    private lateinit var startPsw: String
    private lateinit var endPsw: String
    private lateinit var registerName: String

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
            registerName = et_register_name.text.toString()
            if (!isPswEquals()) {
                register_failure_hint.text = getString(R.string.input_psw_not_equals)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(registerName)) {
                register_failure_hint.text = getString(R.string.not_input_register_name)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(startPsw)){
                register_failure_hint.text = getString(R.string.please_input_psw)
                return@setOnClickListener
            }
            Observable.just("")
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        try {
                            EMClient.getInstance().createAccount(registerName, endPsw)
                        } catch (e: HyphenateException) {
                            e.printStackTrace()
                        }
                    }
                    .subscribe({},{})
        }
    }

    private fun isPswEquals(): Boolean {
        startPsw = et_regist_psw.text.toString()
        endPsw = et_confirm_psw.text.toString()
        return startPsw == endPsw
    }

    override fun initResource() {

    }
}