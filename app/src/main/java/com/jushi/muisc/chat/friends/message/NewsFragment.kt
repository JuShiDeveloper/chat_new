package com.jushi.muisc.chat.friends.message


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.jushi.base.fragment.ViewPagerFragment

import com.jushi.muisc.chat.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody


/**
 *消息页面(所有的会话)
 */
class NewsFragment : ViewPagerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false)
        }
        return rootView
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        val allConversations = EMClient.getInstance().chatManager().allConversations

    }


}
