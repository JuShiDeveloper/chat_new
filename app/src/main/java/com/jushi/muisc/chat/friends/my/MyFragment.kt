package com.jushi.muisc.chat.friends.my


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
import com.jushi.muisc.chat.common.transform.CircleTransform
import com.jushi.muisc.chat.common.utils.SaveUtils
import kotlinx.android.synthetic.main.nav_header_main.*

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
                    .into(header_imageView)
        }
        if (isLogin()) {
            login_tv.text = EMClient.getInstance().currentUser
        }
    }

    override fun initWidget() {

    }

    override fun setListener() {

    }

    private fun isLogin(): Boolean {
        return EMClient.getInstance().isLoggedInBefore
    }
}
