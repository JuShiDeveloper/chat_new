package com.jushi.muisc.chat.friends.my


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.jushi.muisc.chat.common.manager.ActivityManager
import com.jushi.muisc.chat.common.transform.CircleTransform
import com.jushi.muisc.chat.common.utils.SaveUtils
import com.jushi.muisc.chat.friends.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * A simple [Fragment] subclass.
 *  我的页面
 */
class MyFragment : ViewPagerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_my, container, false)
        return rootView
    }

    override fun initResource() {
        checkUserInfo()
    }

    /**
     * 检查用户信息（用户名、头像）
     */
    private fun checkUserInfo() {
        val imagePath = SaveUtils.getInstance(context).saveUserImage
        if (!TextUtils.isEmpty(imagePath)) {
            Glide.with(context)
                    .load(imagePath)
                    .transform(CircleTransform(context))
                    .error(R.drawable.round_header)
                    .into(iv_userImage)
        }
        if (isLogin()) {
            tv_userName.text = EMClient.getInstance().currentUser
        } else tv_userName.text = context!!.getString(R.string.click_login)
    }

    override fun initWidget() {

    }

    override fun setListener() {
        ll_userInfo.setOnClickListener {
            if (isLogin()) {
                ActivityManager.startSettingsActivity(context)
                return@setOnClickListener
            }
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isLogin(): Boolean {
        return EMClient.getInstance().isLoggedInBefore
    }
}
