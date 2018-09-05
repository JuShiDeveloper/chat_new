package com.jushi.muisc.chat.landing

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
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
            showRegisterLayout()
        }
        register_page_back_btn.setOnClickListener {
            showLandingLayout()
        }
    }

    private fun showLandingLayout() {
        landing_layout.visibility = View.VISIBLE
        register_layout.visibility = View.GONE
    }

    private fun showRegisterLayout() {
        landing_layout.visibility = View.GONE
        register_layout.visibility = View.VISIBLE
    }

    private fun setRegisterBtnClickListener() {
        register_btn.setOnClickListener {
            registerName = et_register_name.text.toString()
            startPsw = et_regist_psw.text.toString()
            endPsw = et_confirm_psw.text.toString()
            if (TextUtils.isEmpty(registerName)) { //判断注册用户名是否为空
                register_failure_hint.text = getString(R.string.not_input_register_name)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(startPsw)) { //判断注册密码是否为空
                register_failure_hint.text = getString(R.string.please_input_psw)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(endPsw)){ //判断再次输入密码是否为空
                register_failure_hint.text = getString(R.string.please_input_psw_again)
                return@setOnClickListener
            }
            if (startPsw != endPsw) { //判断两次输入密码是否一致
                register_failure_hint.text = getString(R.string.input_psw_not_equals)
                return@setOnClickListener
            }
            if (startPsw.length < 6){ //判断密码是否在6位数以上
                register_failure_hint.text = getString(R.string.input_psw_must_)
                return@setOnClickListener
            }
            Observable.just("")
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        try {
                            EMClient.getInstance().createAccount(registerName, endPsw)
                            runOnUiThread { registerSuccess() }
                        } catch (e: HyphenateException) {
                            e.printStackTrace()
                            runOnUiThread { registerFailure() }
                        }
                    }
                    .subscribe({}, {})
        }
    }

    /**
     * 注册失败
     */
    private fun registerFailure() {
        register_failure_hint.text = getString(R.string.user_name_exists)
    }

    /**
     * 注册成功
     */
    private fun registerSuccess() {
        /*---------清空注册数据-----------*/
        et_register_name.setText("")
        et_regist_psw.setText("")
        et_confirm_psw.setText("")
        register_failure_hint.text = ""
        /*-----------显示登陆的布局------------------*/
        showLandingLayout()
        et_landing_number.setText(registerName)

    }


    override fun initResource() {

    }

    override fun onBackPressed() {
        if (landing_layout.visibility == View.GONE) {
            showLandingLayout()
        } else super.onBackPressed()
    }
}