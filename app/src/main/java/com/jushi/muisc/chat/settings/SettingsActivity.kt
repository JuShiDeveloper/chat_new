package com.jushi.muisc.chat.settings

import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.jushi.base.activity.BaseActivity
import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.common.transform.CircleTransform
import com.jushi.muisc.chat.common.utils.SaveUtils
import com.jushi.muisc.chat.common.utils.ToastUtils
import com.jushi.muisc.chat.friends.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings_layout.*

/**
 * 设置页面
 */
class SettingsActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.activity_settings_layout)
    }

    override fun initWidget() {
        initToolbar()
        setBtnClickListener()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun setBtnClickListener() {
        tv_exit_login.setOnClickListener {
            toLoginOut()
        }
        tv_userName_setting.setOnClickListener {
            if (!isLogin()) {
                startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
            }
        }

    }

    /**
     * 退出当前账号
     */
    private fun toLoginOut() {
        EMClient.getInstance().logout(false, object : EMCallBack {
            override fun onSuccess() {
                runOnUiThread {
                    startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
                    ToastUtils.show(this@SettingsActivity, getString(R.string.exit_login_success))
                    finish()
                }
            }

            override fun onProgress(progress: Int, status: String?) {
            }

            override fun onError(code: Int, error: String?) {
            }

        })
    }

    override fun initResource() {
        initUserInfo()
    }

    private fun initUserInfo() {
        if (isLogin()) {
            if (!isLogin()) { //未登陆
                tv_userName_setting.text = getString(R.string.click_login)
                Glide.with(this)
                        .load(R.drawable.round_header)
                        .transform(CircleTransform(this))
                        .into(iv_userImage_setting)
                return
            }
            //登陆
            tv_userName_setting.text = EMClient.getInstance().currentUser
            val imagePath = SaveUtils.getInstance(this).saveUserImage
            if (!TextUtils.isEmpty(imagePath)) {
                Glide.with(this)
                        .load(imagePath)
                        .transform(CircleTransform(this))
                        .error(R.drawable.round_header)
                        .into(iv_userImage_setting)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isLogin(): Boolean {
        return EMClient.getInstance().isLoggedInBefore
    }
}