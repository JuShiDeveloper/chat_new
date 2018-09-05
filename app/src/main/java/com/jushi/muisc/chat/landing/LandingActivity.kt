package com.jushi.muisc.chat.landing

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R
import kotlinx.android.synthetic.main.activity_regist_layout.*
import rx.Observable
import rx.schedulers.Schedulers

/**
 * 登陆/注册界面
 */
class LandingActivity : BaseActivity() {
    /*---------注册填写的信息(注册名和密码)-----------*/
    private lateinit var registerName: String
    private lateinit var startPsw: String
    private lateinit var endPsw: String

    /*---------登陆填写的信息(登陆名和密码)---------*/
    private lateinit var loginName: String
    private lateinit var loginPsw: String

    override fun setContentView() {
        setContentView(R.layout.activity_regist_layout)
    }

    override fun initWidget() {
        setBtnClickListener()
        setRegisterBtnClickListener()
        setLoginBtnClickListener()
    }

    private fun setBtnClickListener() {
        to_register_btn.setOnClickListener {
            showRegisterLayout()
        }
        register_page_back_btn.setOnClickListener {
            showLoginLayout()
        }
    }

    private fun showLoginLayout() {
        login_layout.visibility = View.VISIBLE
        register_layout.visibility = View.GONE
    }

    private fun showRegisterLayout() {
        login_layout.visibility = View.GONE
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
            if (TextUtils.isEmpty(endPsw)) { //判断再次输入密码是否为空
                register_failure_hint.text = getString(R.string.please_input_psw_again)
                return@setOnClickListener
            }
            if (startPsw != endPsw) { //判断两次输入密码是否一致
                register_failure_hint.text = getString(R.string.input_psw_not_equals)
                return@setOnClickListener
            }
            if (startPsw.length < 6) { //判断密码是否在6位数以上
                register_failure_hint.text = getString(R.string.input_psw_must_)
                return@setOnClickListener
            }
            Observable.just("")
                    .subscribeOn(Schedulers.newThread())
                    .map {
                        try {
                            //注册
                            EMClient.getInstance().createAccount(registerName, endPsw)
                            runOnUiThread { registerSuccess() }
                        } catch (e: HyphenateException) {
                            e.printStackTrace()
                            runOnUiThread { registerFailure() }
                        }
                    }.subscribe({}, {})
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
        /*-----------显示登陆的布局并显示刚才注册的用户名------------------*/
        showLoginLayout()
        et_login_number.setText(registerName)
    }

    private fun setLoginBtnClickListener() {
        landing_btn.setOnClickListener {
            loginName = et_login_number.text.toString()
            loginPsw = et_landing_psw.text.toString()
            if (TextUtils.isEmpty(loginName)) { //判断输入的账号是否为空
                login_hint.text = getString(R.string.user_name_must_not_empty)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(loginPsw)) { //判断密码是否为空
                login_hint.text = getString(R.string.psw_must_not_empty)
                return@setOnClickListener
            }
            if (loginPsw.length < 6) { //密码是否在6位数以上
                login_hint.text = getString(R.string.psw_input_error)
                return@setOnClickListener
            }
            EMClient.getInstance().login(loginName, loginPsw, LoginCallBack())
        }
    }

    override fun initResource() {

    }

    override fun onBackPressed() {
        if (login_layout.visibility == View.GONE) {
            showLoginLayout()
        } else super.onBackPressed()
    }

    /**
     * 登陆回调
     */
    inner class LoginCallBack : EMCallBack {
        private val NOT_EXIST = "User dosn't exist"
        private val WRONG = "Username or password is wrong"
        private val ALREADY_LOGIN = "User is already login"
        private var loginStatus: String = ""

        override fun onSuccess() {

        }

        override fun onProgress(progress: Int, status: String?) {

        }

        override fun onError(code: Int, error: String) {
            if (error == NOT_EXIST) {
                loginStatus = getString(R.string.User_do_not_exist)
            }
            if (error == WRONG) {
                loginStatus = getString(R.string.Username_or_password_is_wrong)
            }
            if (error == ALREADY_LOGIN) {
                loginStatus = getString(R.string.User_is_already_login)
            }
            runOnUiThread { login_hint.text = loginStatus }
        }

    }
}